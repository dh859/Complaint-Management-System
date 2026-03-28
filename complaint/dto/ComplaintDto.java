package com.cms.cmsapp.complaint.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.common.Enums.EscalationLevel;
import com.cms.cmsapp.common.Enums.Priority;
import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.file.FileDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComplaintDto {
    private Long id;
    private String subject;
    private String description;

    private Category category;
    private Priority priority;
    private Status status;

    private String departmentName;
    private String assignedStaffName;
    private String createdByName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean overdue;
    private EscalationLevel escalationLevel;
    
    private List<FileDto> attatchments = new ArrayList<>();

    private List<TimelineDto> timelines = new ArrayList<>();

    private List<EscalationDto> escalations = new ArrayList<>();
}
