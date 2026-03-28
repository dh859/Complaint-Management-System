package com.cms.cmsapp.database;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.cms.cmsapp.common.Enums.RuleType;
import com.cms.cmsapp.rule.repository.SystemRuleRepo;
import com.cms.cmsapp.rule.rules.SystemRule;

@Component
@RequiredArgsConstructor
public class RuleSeeder {

    private final SystemRuleRepo ruleRepository;

  
    public void seed() {
        if (ruleRepository.count() > 0) {
            System.out.println("System rules already exist, skipping...");
            return;
        }

        seedPriorityRules();
        seedClassificationRules();
        seedAssignmentRules();
        seedEscalationRules();

        System.out.println("System rules seeded successfully.");
    }

    private void seed(String key, String value, RuleType type, String description) {
        SystemRule rule = SystemRule.builder()
                .ruleKey(key)
                .ruleValue(value)
                .ruleType(type)
                .description(description)
                .build();
        ruleRepository.save(rule);
    }

    /* ===================== PRIORITY RULES ===================== */

    private void seedPriorityRules() {
        seed("PRIORITY_KEYWORD_ENABLED", "true",
                RuleType.PRIORITY, "Enable keyword-based priority boost");

        seed("PRIORITY_CRITICAL_KEYWORDS",
                "fire,accident,death,emergency",
                RuleType.PRIORITY, "Keywords forcing CRITICAL priority");

        seed("PRIORITY_HIGH_KEYWORDS",
                "outage,leak,injury,failure",
                RuleType.PRIORITY, "Keywords boosting HIGH priority");

        seed("PRIORITY_SLA_OVERRIDE_HOURS", "24",
                RuleType.PRIORITY, "SLA threshold for priority override");

        seed("PRIORITY_CITIZEN_URGENT_ENABLED", "true",
                RuleType.PRIORITY, "Allow citizen to mark complaint urgent");

        seed("PRIORITY_DEFAULT_LEVEL", "MEDIUM",
                RuleType.PRIORITY, "Default priority level");
    }

    /* ================= CLASSIFICATION RULES =================== */

    private void seedClassificationRules() {
        seed("AUTO_CLASSIFICATION_ENABLED", "true",
                RuleType.CATEGORIZATION, "Enable automatic categorization");

        seed("CLASSIFICATION_MIN_SCORE", "5",
                RuleType.CATEGORIZATION, "Minimum score required for classification");

        seed("CLASSIFICATION_FALLBACK_CATEGORY", "GENERAL",
                RuleType.CATEGORIZATION, "Fallback category when no match found");

        seed("CLASSIFICATION_ALLOW_OVERRIDE", "true",
                RuleType.CATEGORIZATION, "Allow admin to override category");

        seed("CLASSIFICATION_MAX_KEYWORDS_MATCH", "10",
                RuleType.CATEGORIZATION, "Max keywords used during classification");
    }

    /* =================== ASSIGNMENT RULES ===================== */

    private void seedAssignmentRules() {
        seed("AUTO_ASSIGN_ENABLED", "true",
                RuleType.ASSIGNMENT, "Enable auto assignment");

        seed("AUTO_ASSIGN_AFTER_MINUTES", "30",
                RuleType.ASSIGNMENT, "Minutes before auto assignment");

        seed("ASSIGN_SAME_DEPARTMENT_ONLY", "true",
                RuleType.ASSIGNMENT, "Restrict assignment to same department");

        seed("ASSIGN_MATCH_CATEGORY_SKILL", "true",
                RuleType.ASSIGNMENT, "Match staff skill with complaint category");

        seed("ASSIGN_LOWEST_WORKLOAD", "true",
                RuleType.ASSIGNMENT, "Assign staff with lowest workload");

        seed("ASSIGN_MAX_ACTIVE_CASES", "10",
                RuleType.ASSIGNMENT, "Maximum active cases per staff");

        seed("REASSIGN_ON_STAFF_INACTIVE", "true",
                RuleType.ASSIGNMENT, "Reassign if staff becomes inactive");

        seed("REASSIGN_AFTER_INACTIVITY_HOURS", "24",
                RuleType.ASSIGNMENT, "Hours of inactivity before reassignment");
    }

    /* =================== ESCALATION RULES ===================== */

    private void seedEscalationRules() {
        seed("ESCALATION_ENABLED", "true",
                RuleType.ESCALATION, "Enable escalation mechanism");

        seed("ESCALATE_AFTER_SLA", "true",
                RuleType.ESCALATION, "Escalate after SLA breach");

        seed("ESCALATION_LEVEL_1_HOURS", "24",
                RuleType.ESCALATION, "Hours before first escalation");

        seed("ESCALATION_LEVEL_2_HOURS", "48",
                RuleType.ESCALATION, "Hours before second escalation");

        seed("ESCALATION_REASSIGN_ON_BREACH", "true",
                RuleType.ESCALATION, "Reassign complaint on escalation");

        seed("ESCALATION_NOTIFY_ENABLED", "true",
                RuleType.ESCALATION, "Send notifications on escalation");

        seed("ESCALATION_MAX_LEVEL", "2",
                RuleType.ESCALATION, "Maximum escalation level");
    }

    
}
