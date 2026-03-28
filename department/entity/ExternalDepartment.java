package com.cms.cmsapp.department.entity;

import java.util.Set;

import com.cms.cmsapp.department.dto.DepartmentDto;
import com.cms.cmsapp.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "external_departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalDepartment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long departmentId;

	private String name;
	private String contactEmail;
	private String location;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private User manager;

	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Staff> departmentUsers;

	public DepartmentDto toDepartmentDto() {
		return new DepartmentDto(departmentId, name, contactEmail, location,manager.getUserId(), manager.getFullname(), departmentUsers.size(),0);
	}
}
