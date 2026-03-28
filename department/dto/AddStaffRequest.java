package com.cms.cmsapp.department.dto;

import java.util.Set;

import com.cms.cmsapp.common.Enums.Category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddStaffRequest {
    private Long userId;
    private Long departmentId;
    private String role;
    private Set<Category> skills;
}
