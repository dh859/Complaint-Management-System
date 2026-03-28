package com.cms.cmsapp.database;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.cms.cmsapp.common.Enums.Role;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.repository.UserRepo;

import net.datafaker.Faker;

@Component
public class UserSeeder {

    private final UserRepo userRepository;
    private final Faker faker;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserSeeder(UserRepo userRepository,BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.faker = new Faker(Locale.forLanguageTag("en-IN"));
        this.passwordEncoder = passwordEncoder;
    }

    public void seed() {
        if (userRepository.count() > 0) {
            System.out.println("Users table not empty, skipping user seeding...");
            return;
        }

        List<User> users = new ArrayList<>();
        addUsers(users, 3, Role.ADMIN);
        addUsers(users, 20, Role.USER);
        addUsers(users, 5, Role.MANAGER);
        addUsers(users, 10, Role.AGENT);
        

        userRepository.saveAll(users);
        System.out.println("Seeded Users successfully");
    }

    private String generateIndianMobile() {
        // ensures regex ^[6-9][0-9]{9}$
        int firstDigit = faker.number().numberBetween(6, 9);
        long rest = faker.number().numberBetween(100000000L, 999999999L);
        return firstDigit + String.valueOf(rest);
    }

    private void addUsers(List<User> users,int count,Role role){
        for (int i = 1; i <= count; i++) {
            String username=role.toValue().toLowerCase()+"_"+String.valueOf(i);
            User user = User.builder()
                    .username(username)
                    .fullname(faker.name().fullName())
                    .password(passwordEncoder.encode(username)) 
                    .email(faker.internet().emailAddress(username))
                    .role(role)
                    .contactNumber(generateIndianMobile())
                    .address(faker.address().streetAddress())
                    .city(faker.address().city())
                    .state(faker.address().state())
                    .pincode(faker.address().zipCode())
                    .createdAt(LocalDateTime.now())
                    .isActive(true)
                    .build();

            users.add(user);
        }
    }
}
