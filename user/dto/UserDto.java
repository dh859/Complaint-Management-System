package com.cms.cmsapp.user.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    
    private String username;
    private String fullname;
    private String email;
    private String role; 
    private boolean isActive; 
    private LocalDateTime createdAt;
    private String contactNumber;
    private String address;
    private String state;
    private String city;
    private String pincode;
}
