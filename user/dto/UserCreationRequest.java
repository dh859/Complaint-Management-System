package com.cms.cmsapp.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationRequest {
    private String username;
    private String fullname;
    private String password;
    private String email;
    private String role; 
}
