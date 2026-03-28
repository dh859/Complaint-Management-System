package com.cms.cmsapp.department.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentDto {

	private Long id;
    private String name;
    private String contactEmail;
    private String location;
	private Long managerId;
    private String managerName;
    private int activeStaffCount;
    private int openComplaintCount;
}
