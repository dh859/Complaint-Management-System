package com.cms.cmsapp.database;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cms.cmsapp.common.Enums.Role;
import com.cms.cmsapp.department.entity.ExternalDepartment;
import com.cms.cmsapp.department.repository.DepartmentRepo;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.repository.UserRepo;

import net.datafaker.Faker;

@Component
public class DepartmentSeeder {

    private final DepartmentRepo departmentRepository;
    private final UserRepo userRepository;
    private final Faker faker;

    private static final List<String> DEPARTMENTS = List.of(
            "Sales",
            "Accounts",
            "Finance",
            "HR",
            "IT Support",
            "Operations",
            "Customer Support",
            "Legal",
            "Administration"
    );

    public DepartmentSeeder(
            DepartmentRepo departmentRepository,
            UserRepo userRepository) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.faker = new Faker(java.util.Locale.forLanguageTag("en-IN"));
    }

    public void seed() {
        if (departmentRepository.count() > 0) {
            System.out.println("Departments already exist, skipping...");
            return;
        }

        List<User> managers =
                userRepository.findByRole(Role.MANAGER)
                        .orElse(List.of());

        if (managers.isEmpty()) {
            System.out.println("No MANAGER users found, skipping department seeding...");
            return;
        }

        int departmentCount = Math.min(managers.size(), DEPARTMENTS.size());
        List<ExternalDepartment> departments = new ArrayList<>();

        for (int i = 0; i < departmentCount; i++) {

            String deptName = DEPARTMENTS.get(i);

            ExternalDepartment department = ExternalDepartment.builder()
                    .name(deptName)
                    .manager(managers.get(i))
                    .location(faker.address().city())
                    .contactEmail(
                            deptName.toLowerCase().replace(" ", "") + "@cms.com"
                    )
                    .build();

            departments.add(department);
        }

        departmentRepository.saveAll(departments);
        System.out.println("Seeded " + departments.size() + " Departments");
    }
}
