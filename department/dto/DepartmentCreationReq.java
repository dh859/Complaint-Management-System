package com.cms.cmsapp.department.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentCreationReq {
    private String name;
	private String contactEmail;
	private String location;
	private Long manager_id;
}
