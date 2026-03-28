package com.cms.cmsapp.department.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StaffRecordDto {
    private Long staffId;
    private String departmentName;
    private String fullName;
    private String departmentRole;
    private boolean isActive;
}
