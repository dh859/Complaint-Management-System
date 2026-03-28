package com.cms.cmsapp.department.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.common.Enums.DepartmentRole;
import com.cms.cmsapp.common.exceptions.ResourceNotFoundException;
import com.cms.cmsapp.department.dto.AddStaffRequest;
import com.cms.cmsapp.department.dto.StaffDetailsDto;
import com.cms.cmsapp.department.dto.StaffRecordDto;
import com.cms.cmsapp.department.entity.ExternalDepartment;
import com.cms.cmsapp.department.entity.Staff;
import com.cms.cmsapp.department.repository.StaffRepo;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StaffService {

        private final StaffRepo staffRepo;
        private final DepartmentService departmentService;
        private final UserService userService;

        public void removeStaffFromDepartment(Long staffId) {
                Staff staff = staffRepo.findById(staffId)
                                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
                staff.setActive(false);
                staffRepo.save(staff);
        }

        public void updateStaffRole(Long staffId, String newRole) {
                Staff staff = staffRepo.findById(staffId)
                                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
                staff.setDepartmentRole(DepartmentRole.fromString(newRole));
                staffRepo.save(staff);
        }

        public void updateStaffSkills(Long staffId, Set<Category> newSkills) {
                Staff staff = staffRepo.findById(staffId)
                                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
                staff.setSkills(newSkills);
                staffRepo.save(staff);
        }

        public void reactivateStaff(Long staffId) {
                Staff staff = staffRepo.findById(staffId)
                                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
                staff.setActive(true);
                staffRepo.save(staff);
        }

        public void addStaffToDepartment(AddStaffRequest req) {
                ExternalDepartment department = departmentService.getDepartmentById(req.getDepartmentId());
                User user = userService.getById(req.getUserId());
                Staff staff = Staff.builder()
                                .user(user)
                                .department(department)
                                .departmentRole(DepartmentRole.fromString(req.getRole()))
                                .skills(req.getSkills())
                                .isActive(true)
                                .build();
                department.getDepartmentUsers().add(staff);
                staffRepo.save(staff);
        }

        public StaffDetailsDto getStaffDetails(Long staffId) {
                Staff staff = staffRepo.findById(staffId)
                                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
                return staff.toStaffDetailsDto();
        }

        public List<StaffRecordDto> getAllStaffInDepartment(Long departmentId) {
                ExternalDepartment department = departmentService.getDepartmentById(departmentId);
                List<Staff> staffList = staffRepo.findByDepartment(department)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Staff not Found by Department Id :" + departmentId));
                return staffList.stream().map(Staff::toStaffRecordDto).toList();
        }

        public List<StaffRecordDto> getAllActiveStaff() {
                List<Staff> staffList = staffRepo.findByIsActiveTrue()
                                .orElseThrow(() -> new ResourceNotFoundException("No Active Staff Found"));
                return staffList.stream().map(Staff::toStaffRecordDto).toList();
        }

        public List<StaffRecordDto> getStaffByRole(String role) {
                DepartmentRole departmentRole = DepartmentRole.fromString(role);
                List<Staff> staffList = staffRepo.findByDepartmentRole(departmentRole)
                                .orElseThrow(() -> new ResourceNotFoundException("No Staff Found with Role :" + role));
                return staffList.stream().map(Staff::toStaffRecordDto).toList();
        }

        public List<StaffRecordDto> getStaffBySkill(String skill) {
                Category category = Category.fromString(skill);
                List<Staff> staffList = staffRepo.findByIsActiveTrue()
                                .orElseThrow(() -> new ResourceNotFoundException("No Active Staff Found"));
                return staffList.stream().filter(staff -> staff.getSkills().contains(category))
                                .map(Staff::toStaffRecordDto)
                                .toList();
        }

        public List<StaffRecordDto> getStaffByDepartmentAndSkill(Long departmentId, String skill) {
                ExternalDepartment department = departmentService.getDepartmentById(departmentId);
                Category category = Category.fromString(skill);
                List<Staff> staffList = staffRepo.findByIsActiveTrueAndDepartmentAndSkillsContaining(department, category)
                                .orElseThrow(() -> new ResourceNotFoundException("No Active Staff Found with Skill :"
                                                + skill + " in Department Id :" + departmentId));
                return staffList.stream().map(Staff::toStaffRecordDto)
                                .toList();
        }
}
