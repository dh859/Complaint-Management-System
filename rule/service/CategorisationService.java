package com.cms.cmsapp.rule.service;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.common.Enums.Priority;
import com.cms.cmsapp.complaint.entity.Complaint;
import com.cms.cmsapp.rule.repository.CategoryRuleRepo;
import com.cms.cmsapp.rule.rules.CategoryRule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategorisationService {

    private final CategoryRuleRepo categoryRuleRepository;
    private final RuleService ruleService;

    /* ===================================================== */

    public void categorise(Complaint complaint) {

        Category category = resolveCategoryByRules(complaint);
        complaint.setCategory(category);

        Priority priority = resolvePriority(category, complaint);
        complaint.setPriority(priority);

        applySla(complaint, category, priority);

        log.info(
                "Complaint {} categorised as {} with priority {}",
                complaint.getComplaintId(),
                category,
                priority
        );
    }

    /* ================= CATEGORY RESOLUTION ================= */

    private Category resolveCategoryByRules(Complaint complaint) {

        String text = buildText(complaint);

        if (text.isBlank()) {
            log.warn("Complaint {} has empty text. Using fallback.",
                    complaint.getComplaintId());
            return getFallbackCategory();
        }

        List<CategoryRule> rules = categoryRuleRepository.findByIsActiveTrue();

        if (rules.isEmpty()) {
            log.warn("No active category rules found.");
            return getFallbackCategory();
        }

        Map<Category, Integer> scoreMap = new EnumMap<>(Category.class);

        for (CategoryRule rule : rules) {

            if (rule.getKeyword() == null || rule.getKeyword().isBlank()) {
                continue;
            }

            // Support multiple keywords: "network, wifi, internet"
            String[] keywords = rule.getKeyword().split(",");

            for (String rawKeyword : keywords) {

                String keyword = rawKeyword.trim().toLowerCase();

                if (keyword.isBlank()) {
                    continue;
                }

                // Proper word boundary matching
                Pattern pattern = Pattern.compile(
                        "\\b" + Pattern.quote(keyword) + "\\b"
                );

                if (pattern.matcher(text).find()) {

                    scoreMap.merge(
                            rule.getCategory(),
                            rule.getWeight(),
                            Integer::sum
                    );

                    log.debug("Matched keyword '{}' for category {}",
                            keyword,
                            rule.getCategory());
                }
            }
        }

        if (!scoreMap.isEmpty()) {

            Category bestMatch = scoreMap.entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(getFallbackCategory());

            log.info("Complaint {} matched category {} with score {}",
                    complaint.getComplaintId(),
                    bestMatch,
                    scoreMap.get(bestMatch));

            return bestMatch;
        }

        log.info("No keyword match found for complaint {}",
                complaint.getComplaintId());

        return getFallbackCategory();
    }

    /* ================= PRIORITY ================= */

    private Priority resolvePriority(
            Category category,
            Complaint complaint
    ) {

        // User override
        if (complaint.getPriority() != null) {
            return complaint.getPriority();
        }

        int slaHours = getSlaHours(category);

        int threshold = ruleService.getInt(
                "HIGH_PRIORITY_SLA_THRESHOLD",
                24
        );

        if (slaHours <= threshold) {
            return Priority.HIGH;
        }

        return Priority.MEDIUM;
    }

    /* ================= SLA ================= */

    private void applySla(
            Complaint complaint,
            Category category,
            Priority priority
    ) {

        int baseHours = getSlaHours(category);

        int adjustedHours = switch (priority) {
            case CRITICAL -> Math.max(1, baseHours / 4);
            case HIGH -> Math.max(2, baseHours / 2);
            case MEDIUM -> baseHours;
            case LOW -> baseHours * 2;
        };

        complaint.setSlaDeadLine(
                LocalDateTime.now().plusHours(adjustedHours)
        );
    }

    /* ================= HELPERS ================= */

    private String buildText(Complaint complaint) {

        String subject = complaint.getSubject() == null
                ? ""
                : complaint.getSubject();

        String description = complaint.getDescription() == null
                ? ""
                : complaint.getDescription();

        return (subject + " " + description)
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", " ") // remove punctuation
                .replaceAll("\\s+", " ")
                .trim();
    }

    private Category getFallbackCategory() {

        // Configured default first
        Category configuredDefault = ruleService.getEnum(
                "DEFAULT_CATEGORY",
                Category.class,
                null
        );

        if (configuredDefault != null) {
            return configuredDefault;
        }

        // Otherwise return highest weighted active rule category
        List<CategoryRule> rules = categoryRuleRepository.findByIsActiveTrue();

        if (!rules.isEmpty()) {
            return rules.stream()
                    .max((r1, r2) ->
                            Integer.compare(r1.getWeight(), r2.getWeight()))
                    .map(CategoryRule::getCategory)
                    .orElse(Category.GENERAL);
        }

        return Category.GENERAL;
    }

    private int getSlaHours(Category category) {
        return ruleService.getInt(
                "SLA_" + category.name() + "_HOURS",
                48
        );
    }
}
