package com.cms.cmsapp.complaint.dto;

import java.time.LocalDateTime;

import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.common.Enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShortComplaintDto {
    private Long complaintId;

    private String subject;
    private String description;

    private Status status;

    private Category category;

    private LocalDateTime createdAt;

    private String raisedByUser_Name;

    private String assignedToUser_Name;

    private String department_Name;

    private String priority;
}
