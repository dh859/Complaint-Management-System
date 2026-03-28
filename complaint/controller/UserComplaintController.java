package com.cms.cmsapp.complaint.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.cmsapp.application.Security.Utils.UserDetailsDto;
import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.complaint.dto.ComplaintDto;
import com.cms.cmsapp.complaint.dto.CreateComplaintDto;
import com.cms.cmsapp.complaint.dto.ShortComplaintDto;
import com.cms.cmsapp.complaint.dto.UserComplaintDto;
import com.cms.cmsapp.complaint.service.ComplaintService;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.service.UserService;


@RestController
@RequestMapping("/api/user/complaints")
@PreAuthorize("hasRole('USER')")
public class UserComplaintController {

    private final ComplaintService complaintService;
    private final UserService userService;

    public UserComplaintController(ComplaintService complaintService, UserService userService) {
        this.complaintService = complaintService;
        this.userService = userService;
    }

    @PostMapping(value = "/create",consumes = "multipart/form-data")
    public ShortComplaintDto createComplaint(@ModelAttribute CreateComplaintDto complaintDto,@AuthenticationPrincipal UserDetailsDto userDto) {

        User user=userService.getByUsername(userDto.getUsername());
        return complaintService.create(complaintDto,user).toShortComplaintDto();
    }

    @GetMapping("/my")
    public List<ShortComplaintDto> getAll(@AuthenticationPrincipal UserDetailsDto userDto){
        User user=userService.getByUsername(userDto.getUsername());
        return complaintService.getComplaintsByUser(user);
    }

    @GetMapping("/{complaintId}")
    public UserComplaintDto getById(@PathVariable Long complaintId,@AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        User user=userService.getByUsername(userDetailsDto.getUsername());
        return complaintService.getComplaintByComplaintIdAndUser(complaintId, user);
    }

    @PutMapping("/{id}/cancel")
    public UserComplaintDto canceComplaint(@PathVariable Long id, @AuthenticationPrincipal UserDetailsDto userDetailsDto, String remarks) {
        User user=userService.getByUsername(userDetailsDto.getUsername());
        return complaintService.updateComplaintStatus(id, Status.CANCELED, user, remarks).toUserComplaintDto();
    }
}
