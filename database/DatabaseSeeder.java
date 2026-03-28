package com.cms.cmsapp.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@Profile("local | prod")
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UserSeeder userSeeder;
    private final DepartmentSeeder departmentSeeder;
    private final ComplaintSeeder complaintSeeder;
    private final RuleSeeder ruleSeeder;
    private final CategoryRuleSeeder categoryRuleSeeder;
    private final StaffSeeder staffSeeder;

    @Override
    public void run(String ...args) {
        ruleSeeder.seed();
        userSeeder.seed();
        departmentSeeder.seed();
        staffSeeder.seed();
        complaintSeeder.seed();
        categoryRuleSeeder.seed();
    }
    
}
