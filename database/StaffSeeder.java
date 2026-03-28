package com.cms.cmsapp.database;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.common.Enums.DepartmentRole;
import com.cms.cmsapp.common.Enums.Role;
import com.cms.cmsapp.common.exceptions.ResourceNotFoundException;
import com.cms.cmsapp.department.entity.ExternalDepartment;
import com.cms.cmsapp.department.entity.Staff;
import com.cms.cmsapp.department.repository.DepartmentRepo;
import com.cms.cmsapp.department.repository.StaffRepo;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.repository.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StaffSeeder {

    private final UserRepo userRepository;
    private final DepartmentRepo departmentRepository;
    private final StaffRepo staffRepository;


    public void seed() {
        ExternalDepartment department = departmentRepository
                .findById(1L)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        List<User> agents = userRepository.findByRole(Role.AGENT).orElseThrow(() -> new ResourceNotFoundException("No AGENT users found"));

        if (agents.isEmpty()) {
            log.info("No AGENT users found to seed as staff.");
            return;
        }

        for (User user : agents) {
            boolean alreadyExists = staffRepository
                    .existsByUserAndDepartment(user, department);

            if (alreadyExists) {
                continue;
            }

            Staff staff = Staff.builder()
                    .user(user)
                    .department(department)
                    .departmentRole(DepartmentRole.AGENT)
                    .isActive(true)
                    .onLeave(false)
                    .skills(Set.of(Category.GENERAL)) 
                    .build();

            staffRepository.save(staff);
        }

        log.info("Staff seeding completed.");
    }
}
