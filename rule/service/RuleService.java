package com.cms.cmsapp.rule.service;

import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.rule.repository.CategoryRuleRepo;
import com.cms.cmsapp.rule.repository.SystemRuleRepo;
import com.cms.cmsapp.rule.rules.CategoryRule;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RuleService {

    private final SystemRuleRepo systemRuleRepo;
    private final CategoryRuleRepo categoryRuleRepo;

    /* ================= CACHES ================= */

    private final Map<String, String> systemRuleCache =
            new ConcurrentHashMap<>();

    private final Map<Category, List<CategoryRule>> categoryRuleCache =
            new ConcurrentHashMap<>();

    /* ================= INIT ================= */

    @PostConstruct
    public void loadRules() {
        refresh();
    }

    /* ================= SYSTEM RULE API ================= */

    public String getString(String key) {
        return systemRuleCache.get(key);
    }

    public String getString(String key, String defaultValue) {
        return systemRuleCache.getOrDefault(key, defaultValue);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(systemRuleCache.get(key));
        } catch (Exception ex) {
            log.warn("Invalid integer value for rule {}", key);
            return defaultValue;
        }
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(systemRuleCache.get(key));
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return systemRuleCache.containsKey(key)
                ? Boolean.parseBoolean(systemRuleCache.get(key))
                : defaultValue;
    }

    public boolean exists(String key) {
        return systemRuleCache.containsKey(key);
    }

    public <E extends Enum<E>> E getEnum(
            String key,
            Class<E> enumType,
            E defaultValue
    ) {
        try {
            return Enum.valueOf(enumType, systemRuleCache.get(key));
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public Set<String> getSet(String key) {
        return getSet(key, ",");
    }

    public Set<String> getSet(String key, String delimiter) {
        if (!exists(key)) {
            return Set.of();
        }
        return Arrays.stream(systemRuleCache.get(key).split(delimiter))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toSet());
    }

    /* ================= CATEGORY RULE API ================= */

    public List<CategoryRule> getAllCategoryRules() {
        return categoryRuleCache.values()
                .stream()
                .flatMap(Collection::stream)
                .toList();
    }

    public List<CategoryRule> getCategoryRules(Category category) {
        return categoryRuleCache.getOrDefault(category, List.of());
    }

    public Map<Category, List<CategoryRule>> getCategoryRuleMap() {
        return Collections.unmodifiableMap(categoryRuleCache);
    }

    /**
     * Score text against category rules
     * Used by CategorisationService
     */
    public Map<Category, Integer> scoreCategories(String text) {

        String normalized = text.toLowerCase();
        Map<Category, Integer> scoreMap = new EnumMap<>(Category.class);

        for (Map.Entry<Category, List<CategoryRule>> entry
                : categoryRuleCache.entrySet()) {

            int score = 0;

            for (CategoryRule rule : entry.getValue()) {
                if (rule.isActive()
                        && normalized.contains(rule.getKeyword().toLowerCase())) {
                    score += rule.getWeight();
                }
            }

            if (score > 0) {
                scoreMap.put(entry.getKey(), score);
            }
        }

        return scoreMap;
    }

    /* ================= REFRESH ================= */

    public synchronized void refresh() {

        /* ---- System Rules ---- */
        systemRuleCache.clear();

        systemRuleRepo.findByActiveTrue()
                .orElse(List.of())
                .forEach(rule ->
                        systemRuleCache.put(
                                rule.getRuleKey(),
                                rule.getRuleValue()
                        )
                );

        /* ---- Category Rules ---- */
        categoryRuleCache.clear();

        categoryRuleCache.putAll(
                categoryRuleRepo.findByIsActiveTrue()
                        .stream()
                        .collect(Collectors.groupingBy(
                                CategoryRule::getCategory,
                                ConcurrentHashMap::new,
                                Collectors.toList()
                        ))
        );

        log.info(
                "RuleService loaded {} system rules and {} category groups",
                systemRuleCache.size(),
                categoryRuleCache.size()
        );
    }
}
