package com.cms.cmsapp.user.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.cmsapp.common.Enums.Role;
import com.cms.cmsapp.user.dto.UserCreationRequest;
import com.cms.cmsapp.user.dto.UserDto;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.service.UserService;


@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class ManageUsersController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public ManageUsersController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getById(userId).toUserDto();
    }


    @PostMapping
    public UserDto createUser(@RequestBody UserCreationRequest req) {
        User user = User.builder()
                .username(req.getUsername())
                .fullname(req.getFullname())
                .password(passwordEncoder.encode(req.getPassword()))
                .email(req.getEmail())
                .role(Role.fromString(req.getRole()))
                .build();

        return userService.createUser(user).toUserDto();
    }


    @PutMapping("/{userId}/role")
    public UserDto changeUserRole(@PathVariable Long userId, @RequestBody String newRole) {
        User user = userService.getById(userId);
        user.setRole(Role.fromString(newRole));
        return userService.update(user);
    }

    @PutMapping("/{userId}/status")
    public UserDto enableOrDisableUser(@PathVariable Long userId, @RequestBody boolean enable) {
        User user = userService.getById(userId);
        user.setActive(enable);
        return userService.update(user);
    }

    // ADMIN
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return "User with ID " + userId + " has been deleted.";
        } catch (Exception e) {
            return "Error deleting user with ID " + userId + ": " + e.getMessage();
        }
    }
}
