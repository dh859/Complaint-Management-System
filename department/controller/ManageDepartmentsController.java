package com.cms.cmsapp.department.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.cmsapp.department.dto.DepartmentCreationReq;
import com.cms.cmsapp.department.dto.DepartmentDto;
import com.cms.cmsapp.department.entity.ExternalDepartment;
import com.cms.cmsapp.department.service.DepartmentService;
import com.cms.cmsapp.user.service.UserService;


@RestController
@RequestMapping("/api/admin/departments")
@PreAuthorize("hasRole('ADMIN')")
public class ManageDepartmentsController {

    private final DepartmentService departmentService;
    private final UserService userService;

    public ManageDepartmentsController(DepartmentService departmentService, UserService userService) {
        this.departmentService = departmentService;
        this.userService = userService;
    }


    @GetMapping
    public List<DepartmentDto> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{deptId}")
   public DepartmentDto getDepartmentById(@PathVariable Long deptId){
        return departmentService.getDepartmentById(deptId).toDepartmentDto();
    }

    @PostMapping
    public DepartmentDto createDepartment(@RequestBody DepartmentCreationReq departmentCreationReq){
        return departmentService.createDepartment(departmentCreationReq);
    } 

    // ADMIN
    @PutMapping("/{deptId}")
    public DepartmentDto updateDepartment(@PathVariable Long deptId,@RequestBody DepartmentDto departmentDto){
        ExternalDepartment department=departmentService.getDepartmentById(deptId);
        department.setName(departmentDto.getName());
        department.setContactEmail(departmentDto.getContactEmail());
        department.setLocation(departmentDto.getLocation());
        department.setManager(userService.getById(departmentDto.getManagerId()));
        return departmentService.updateDepartment(department);
    }

    // // ADMIN
    // @PutMapping("/{deptId}/head")
    // public ResponseEntity<?> assignDepartmentHead() {}

    @DeleteMapping("/{deptId}")
    public String deleteDepartment(@PathVariable Long deptId) {
        try {
            departmentService.deleteDepartment(deptId);
            return "Department Deleted Successfully";
        } catch (Exception e) {
            return "Error deleting department: " + e.getMessage();
        }
    }

    @GetMapping("/search/{name}")
    public DepartmentDto searchDepartment(@PathVariable String name){
        return departmentService.search(name);
    }
}
