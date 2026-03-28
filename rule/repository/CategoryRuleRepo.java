package com.cms.cmsapp.rule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.rule.rules.CategoryRule;


@Repository
public interface CategoryRuleRepo extends JpaRepository<CategoryRule, Long> {
    public List<CategoryRule> findByCategoryAndIsActive(Category category, boolean active);
    public List<CategoryRule> findByIsActiveTrue();
    public List<CategoryRule> findByIsActive(boolean active);
    public boolean existsByKeywordIgnoreCaseAndCategory(
            String keyword,
            Category category
    );
}


