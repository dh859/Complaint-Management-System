package com.cms.cmsapp.department.dto;

import java.util.Set;

import com.cms.cmsapp.common.Enums.Category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StaffDetailsDto {
    
    private Long staffId;
    private String departmentName;
    private String fullName;
    private String departmentRole;
    private boolean isActive;
    private Set<Category> skills;

    private Long userId;
    private String userName;
    private String email;
    private String contactNumber; 
    private String address;
    private String state;
    private String city;
}
