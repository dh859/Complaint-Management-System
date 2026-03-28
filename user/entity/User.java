package com.cms.cmsapp.user.entity;

import java.time.LocalDateTime;

import com.cms.cmsapp.common.Enums.Role;
import com.cms.cmsapp.user.dto.UserDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "users")
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String username;
    private String fullname;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean isActive;

    private LocalDateTime createdAt;

    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Enter a valid Indian mobile number")
    private String contactNumber;

    private String address;
    private String state;
    private String city;
    private String pincode;

    public UserDto toUserDto() {
        return new UserDto(userId, username, fullname, email, role.name(), isActive, createdAt, contactNumber, address,
                state, city, pincode);
    }

    public User() {
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }

}
