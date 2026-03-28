package com.cms.cmsapp.database;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.rule.repository.CategoryRuleRepo;
import com.cms.cmsapp.rule.rules.CategoryRule;

@Component
@RequiredArgsConstructor
public class CategoryRuleSeeder  {

    private final CategoryRuleRepo categoryRuleRepo;

    public void seed() {

        /* ELECTRICITY */
        seed("power cut", 10, Category.ELECTRICITY);
        seed("no electricity", 10, Category.ELECTRICITY);
        seed("transformer", 8, Category.ELECTRICITY);
        seed("voltage", 7, Category.ELECTRICITY);
        seed("short circuit", 9, Category.ELECTRICITY);
        seed("electric pole", 6, Category.ELECTRICITY);
        seed("meter fault", 6, Category.ELECTRICITY);
        seed("blackout", 9, Category.ELECTRICITY);

        /* WATER */
        seed("no water", 10, Category.WATER);
        seed("water leakage", 8, Category.WATER);
        seed("pipe burst", 9, Category.WATER);
        seed("sewage", 8, Category.WATER);
        seed("drainage", 7, Category.WATER);
        seed("dirty water", 6, Category.WATER);
        seed("overflow", 7, Category.WATER);
        seed("tanker", 5, Category.WATER);

        /* ROAD */
        seed("pothole", 9, Category.ROAD);
        seed("road damage", 8, Category.ROAD);
        seed("broken road", 8, Category.ROAD);
        seed("traffic jam", 6, Category.ROAD);
        seed("signal not working", 7, Category.ROAD);
        seed("street light", 6, Category.ROAD);
        seed("speed breaker", 5, Category.ROAD);
        seed("road accident", 9, Category.ROAD);

        /* SANITATION */
        seed("garbage", 8, Category.SANITATION);
        seed("waste collection", 7, Category.SANITATION);
        seed("dumping", 6, Category.SANITATION);
        seed("dirty area", 5, Category.SANITATION);
        seed("open drain", 7, Category.SANITATION);
        seed("foul smell", 6, Category.SANITATION);
        seed("cleanliness", 5, Category.SANITATION);

        /* HEALTH */
        seed("hospital", 6, Category.HEALTH);
        seed("ambulance", 9, Category.HEALTH);
        seed("emergency ward", 9, Category.HEALTH);
        seed("doctor unavailable", 7, Category.HEALTH);
        seed("medicine shortage", 8, Category.HEALTH);
        seed("primary health center", 6, Category.HEALTH);
        seed("medical negligence", 9, Category.HEALTH);

        /* POLICE */
        seed("theft", 8, Category.POLICE);
        seed("robbery", 9, Category.POLICE);
        seed("harassment", 7, Category.POLICE);
        seed("illegal activity", 7, Category.POLICE);
        seed("assault", 9, Category.POLICE);
        seed("police station", 6, Category.POLICE);
        seed("fir", 6, Category.POLICE);
        seed("noise complaint", 5, Category.POLICE);

        /* EDUCATION */
        seed("school", 6, Category.EDUCATION);
        seed("college", 6, Category.EDUCATION);
        seed("teacher absent", 7, Category.EDUCATION);
        seed("exam issue", 7, Category.EDUCATION);
        seed("scholarship", 6, Category.EDUCATION);
        seed("admission", 5, Category.EDUCATION);
        seed("classroom", 5, Category.EDUCATION);

        /* TRANSPORT */
        seed("bus", 6, Category.TRANSPORT);
        seed("bus delay", 7, Category.TRANSPORT);
        seed("conductor", 5, Category.TRANSPORT);
        seed("metro", 6, Category.TRANSPORT);
        seed("train", 6, Category.TRANSPORT);
        seed("ticket issue", 5, Category.TRANSPORT);
        seed("overcrowding", 6, Category.TRANSPORT);

        /* GENERAL */
        seed("complaint", 1, Category.GENERAL);
        seed("issue", 1, Category.GENERAL);
        seed("problem", 1, Category.GENERAL);
    }

    private void seed(String keyword, int weight, Category category) {
        if (!categoryRuleRepo.existsByKeywordIgnoreCaseAndCategory(keyword, category)) {
            CategoryRule categoryRule = CategoryRule.builder()
                    .keyword(keyword)
                    .weight(weight)
                    .category(category)
                    .isActive(true)
                    .build();
            
            categoryRuleRepo.save(categoryRule);
        }
    }
}
