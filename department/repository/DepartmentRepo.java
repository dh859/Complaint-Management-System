package com.cms.cmsapp.department.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.cmsapp.department.entity.ExternalDepartment;
import com.cms.cmsapp.user.entity.User;



@Repository
public interface DepartmentRepo extends JpaRepository<ExternalDepartment, Long> {
	public Optional<ExternalDepartment> findByNameAllIgnoreCase(String name);

	public Optional<ExternalDepartment> findByManager(User manager);
}
