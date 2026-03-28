package com.cms.cmsapp.rule.rules;

import com.cms.cmsapp.common.Enums.RuleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "system_rules",
    indexes = {
        @Index(name = "idx_rule_key", columnList = "ruleKey", unique = true),
        @Index(name = "idx_rule_type", columnList = "ruleType")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(nullable = false, unique = true, length = 100)
    private String ruleKey;

   
    @Column(nullable = false, length = 255)
    private String ruleValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RuleType ruleType;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;
}
