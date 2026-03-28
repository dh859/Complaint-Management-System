package com.cms.cmsapp.complaint.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.cmsapp.application.Security.Utils.UserDetailsDto;
import com.cms.cmsapp.common.Enums.Role;
import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.complaint.dto.ComplaintDto;
import com.cms.cmsapp.complaint.dto.ShortComplaintDto;
import com.cms.cmsapp.complaint.service.ComplaintService;
import com.cms.cmsapp.department.entity.ExternalDepartment;
import com.cms.cmsapp.department.service.DepartmentService;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.service.UserService;


@RestController
@RequestMapping("/api/admin/complaints")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER','AGENT')")
public class AdminComplaintController {

	private final ComplaintService complaintService;
	private final UserService userService;
	private final DepartmentService departmentService;

	public AdminComplaintController(ComplaintService complaintService, UserService userService,
			DepartmentService departmentService) {
		this.complaintService = complaintService;
		this.userService = userService;
		this.departmentService = departmentService;
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	public List<ShortComplaintDto> getAll() {
		return complaintService.getAllComplaints();
	}

	@GetMapping("/department")
	@PreAuthorize("hasAnyRole('MANAGER')")
	public List<ShortComplaintDto> getDeparmentComplaints(@AuthenticationPrincipal UserDetailsDto userdto) {
		User user = userService.getById(userdto.getUserId());
		ExternalDepartment dep = departmentService.getDepartmentByManager(user);
		return complaintService.getComplaintsByDepartment(dep);
	}

	@GetMapping("/agent")
	@PreAuthorize("hasAnyRole('AGENT')")
	public List<ShortComplaintDto> getAgentComplaints(@AuthenticationPrincipal UserDetailsDto userdto) {
		User user = userService.getById(userdto.getUserId());
		return complaintService.getComplaintsByAssignedAgent(user);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER','AGENT')")
	public ComplaintDto getById(@PathVariable Long id, @AuthenticationPrincipal UserDetailsDto userDetailsDto) {
		User user = userService.getById(userDetailsDto.getUserId());
		if (user.getRole().equals(Role.ADMIN)) {
			return complaintService.getComplaintById(id);
		} else if (user.getRole().equals(Role.MANAGER)) {
			ExternalDepartment dep = departmentService.getDepartmentByManager(user);
			return complaintService.getComplaintByIdAndDepartment(id, dep);
		} else {
			return complaintService.getComplaintByIdAndAssignedAgent(id, user);
		}
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteComplaint(@PathVariable Long id) {
		complaintService.deleteComplaint(id);
	}

	@PostMapping("/complaints/{id}/override-close")
	public ComplaintDto overrideClose(@PathVariable Long id, @AuthenticationPrincipal UserDetailsDto userDetailsDto,String remarks) {
		User user = userService.getById(userDetailsDto.getUserId());
		return complaintService.updateComplaintStatus(id, Status.REOPENED, user, remarks).toComplaintDto();
	}

	@PutMapping("/{complaintId}/assign/{agentId}")
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	public ShortComplaintDto assignComplaint(@PathVariable Long complaintId, @PathVariable Long agentId, @AuthenticationPrincipal UserDetailsDto userDetailsDto, String remarks) {
		User user = userService.getById(agentId);
		User actingUser = userService.getById(userDetailsDto.getUserId());
		return complaintService.assignComplaint(complaintId, user, actingUser, remarks).toShortComplaintDto();
	}

	@PutMapping("/{complaintId}/status")
	public ComplaintDto updateStatus(@PathVariable Long complaintId, @RequestBody String status, @AuthenticationPrincipal UserDetailsDto userDetailsDto, String remarks) {
		User user = userService.getById(userDetailsDto.getUserId());
		return complaintService.updateComplaintStatus(complaintId, Status.fromString(status), user, remarks).toComplaintDto();
	}

	@PutMapping("/{complaintId}/forward/{departmentId}")
	public ShortComplaintDto forwardComplaint(@PathVariable Long complaintId, @PathVariable Long departmentId, @AuthenticationPrincipal UserDetailsDto userDetailsDto, String remarks) {
		User user = userService.getById(userDetailsDto.getUserId());
		return complaintService.forwardComplaint(complaintId, departmentId, user, remarks).toShortComplaintDto();
	}

}
