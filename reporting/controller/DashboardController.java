package com.cms.cmsapp.reporting.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.cmsapp.application.Security.Utils.UserDetailsDto;
import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.common.Enums.Role;
import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.complaint.service.ComplaintService;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.service.UserService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final UserService userService;
    private final ComplaintService complaintService;

    public DashboardController(UserService userService, ComplaintService complaintService) {
        this.userService = userService;
        this.complaintService = complaintService;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Long> getDashboardAdmin() {
        Map<String,Long> data= new HashMap<>();
        data.put("totalComplaints", complaintService.countAllComplaints());
        data.put("openComplaints", complaintService.countComplaintsByStatus(Status.OPEN));
        data.put("resolvedComplaints",complaintService.countComplaintsByStatus(Status.RESOLVED));
        data.put("totalUsers", userService.countByIsActive(true));
        data.put("activeUsers", userService.countByRoleAndIsActive(Role.USER, true));
        data.put("activeManagers", userService.countByRoleAndIsActive(Role.MANAGER, true));
        data.put("activeAgents", userService.countByRoleAndIsActive(Role.AGENT, true));
        return data;
    }

    @GetMapping("/agent")
    @PreAuthorize("hasRole('AGENT')")
    public Map<String, Long> getDashboardAgent(@AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        User user=userService.getById(userDetailsDto.getUserId());
        Map<String,Long> data= new HashMap<>();
        data.put("totalComplaints", complaintService.countComplaintsByAssignedAgent(user));
        data.put("openComplaints", complaintService.countComplaintsByAssignedAgentAndStatus(user,Status.OPEN));
        data.put("resolvedComplaints",complaintService.countComplaintsByAssignedAgentAndStatus(user,Status.RESOLVED));
        data.put("forwarderComplaints",complaintService.countComplaintsByAssignedAgentAndStatus(user, Status.CLOSED));
        data.put("in_progressComplaints",complaintService.countComplaintsByAssignedAgentAndStatus(user, Status.IN_PROGRESS));
        
        return data;
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('MANAGER')")
    public Map<String, Long> getDashboardManager() {
		Map<String, Long> data = new HashMap<>();
		data.put("totalComplaints", complaintService.countAllComplaints());
		data.put("openComplaints", complaintService.countComplaintsByStatus(Status.OPEN));
		data.put("resolvedComplaints", complaintService.countComplaintsByStatus(Status.RESOLVED));
		data.put("closedComplaints", complaintService.countComplaintsByStatus(Status.CLOSED));
		data.put(Category.ELECTRICITY.toString().toLowerCase(),
				complaintService.countComplaintsByCategoryAndStatus(Category.ELECTRICITY, Status.OPEN));
		data.put(Category.WATER.toString().toLowerCase(),
				complaintService.countComplaintsByCategoryAndStatus(Category.WATER, Status.OPEN));
		data.put(Category.ROAD.toString().toLowerCase(),
				complaintService.countComplaintsByCategoryAndStatus(Category.ROAD, Status.OPEN));
		data.put(Category.SANITATION.toString().toLowerCase(),
				complaintService.countComplaintsByCategoryAndStatus(Category.SANITATION, Status.OPEN));
		data.put(Category.HEALTH.toString().toLowerCase(),
				complaintService.countComplaintsByCategoryAndStatus(Category.HEALTH, Status.OPEN));
		data.put(Category.POLICE.toString().toLowerCase(),
				complaintService.countComplaintsByCategoryAndStatus(Category.POLICE, Status.OPEN));
		data.put(Category.EDUCATION.toString().toLowerCase(),
				complaintService.countComplaintsByCategoryAndStatus(Category.EDUCATION, Status.OPEN));
		data.put(Category.TRANSPORT.toString().toLowerCase(),
				complaintService.countComplaintsByCategoryAndStatus(Category.TRANSPORT, Status.OPEN));
		data.put(Category.GENERAL.toString().toLowerCase(),
				complaintService.countComplaintsByCategoryAndStatus(Category.GENERAL, Status.OPEN));

		return data;
	}

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public Map<String, Long> getDashboardUser(@AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        User user=userService.getById(userDetailsDto.getUserId());
        Map<String,Long> data= new HashMap<>();
        data.put("totalComplaints", complaintService.countComplaintsByRaisedByUser(user));
        data.put("openComplaints", complaintService.countComplaintsByRaisedByUserAndStatus(user,Status.OPEN));
        data.put("resolvedComplaints",complaintService.countComplaintsByRaisedByUserAndStatus(user,Status.RESOLVED));

        return data;
    }
}
