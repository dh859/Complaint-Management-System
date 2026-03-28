package com.cms.cmsapp.database;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.common.Enums.Priority;
import com.cms.cmsapp.common.Enums.Role;
import com.cms.cmsapp.complaint.dto.CreateComplaintDto;
import com.cms.cmsapp.complaint.service.ComplaintService;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.repository.UserRepo;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;


@Component
@RequiredArgsConstructor
public class ComplaintSeeder {

    private final ComplaintService complaintService;
    private final UserRepo userRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    public void seed() {

        if (complaintService.countAllComplaints() > 0) {
            System.out.println("Complaints already exist. Skipping ComplaintSeeder.");
            return;
        }

        List<User> users = userRepository.findByRole(Role.USER)
                .orElseThrow();

        if (users.isEmpty()) {
            System.out.println("Users table is empty. Cannot seed complaints.");
            return;
        }

        int complaintCount = users.size() * 2;

        for (int i = 0; i < complaintCount; i++) {

            User raisedBy = randomUser(users);

            CreateComplaintDto request = new CreateComplaintDto();
            request.setSubject(faker.commerce().productName());
            request.setDescription(faker.lorem().paragraph(4));
            request.setCategory(randomEnum(Category.class));
            request.setPriority(randomEnum(Priority.class));

            complaintService.create(request, raisedBy);
        }

        System.out.println("Seeded " + complaintCount + " complaints successfully.");
    }

    private User randomUser(List<User> users) {
        return users.get(random.nextInt(users.size()));
    }

    private <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        T[] values = clazz.getEnumConstants();
        return values[random.nextInt(values.length)];
    }
}
