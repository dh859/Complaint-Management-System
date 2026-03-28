package com.cms.cmsapp.rule.rules;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "assignment_rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Example: SAME_DEPARTMENT, MATCH_SKILL, LOWEST_WORKLOAD
     */
    @Column(nullable = false, length = 50)
    private String ruleCode;

    @Column(nullable = false)
    private int priorityOrder;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;
}
