package com.cms.cmsapp.department.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cms.cmsapp.common.exceptions.ResourceNotFoundException;
import com.cms.cmsapp.department.dto.DepartmentCreationReq;
import com.cms.cmsapp.department.dto.DepartmentDto;
import com.cms.cmsapp.department.entity.ExternalDepartment;
import com.cms.cmsapp.department.repository.DepartmentRepo;
import com.cms.cmsapp.user.entity.User;


@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepo departmentRepo;


	public ExternalDepartment getDepartmentById(Long id) {
		return departmentRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found by Id : " + id));
	}

	public DepartmentDto createDepartment(DepartmentCreationReq req) {
		ExternalDepartment dep = new ExternalDepartment();
		dep.setName(req.getName());
		dep.setContactEmail(req.getContactEmail());
		return departmentRepo.save(dep).toDepartmentDto();
	}

	public void deleteDepartment(Long id) {
		departmentRepo.deleteById(id);
	}

	public List<DepartmentDto> getAllDepartments() {
		return departmentRepo.findAll().stream().map(ExternalDepartment::toDepartmentDto).toList();
	}

	public DepartmentDto search(String name) {
		return departmentRepo.findByNameAllIgnoreCase(name).orElseThrow(
				() -> new ResourceNotFoundException("Department not found by name : " + name))
				.toDepartmentDto();
	}

	public ExternalDepartment getDepartmentByManager(User manager){
	   return  departmentRepo.findByManager(manager).orElseThrow(()->new ResourceNotFoundException("Department Not FoundBy this Manager :"+manager.getUserId()));
     }

	 public DepartmentDto updateDepartment(ExternalDepartment department){
		return departmentRepo.save(department).toDepartmentDto();
	
	 }

	 public void assignManagerToDepartment(Long departmentId, User manager) {
		ExternalDepartment department = getDepartmentById(departmentId);
		department.setManager(manager);
		departmentRepo.save(department);
	}

	
}
