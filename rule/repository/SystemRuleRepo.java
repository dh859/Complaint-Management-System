package com.cms.cmsapp.rule.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.cmsapp.rule.rules.SystemRule;

public interface SystemRuleRepo extends JpaRepository<SystemRule, Long> {

    Optional<SystemRule> findByRuleKey(String ruleKey);
    Optional<SystemRule> findByRuleKeyAndActiveTrue(String ruleKey);
    Optional<List<SystemRule>> findByActiveTrue();
}

