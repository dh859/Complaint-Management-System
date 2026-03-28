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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserComplaintDto {
    private Long id;
    private String subject;
    private String description;

    private Category category;
    private Priority priority;
    private Status status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private List<FileDto> attatchments = new ArrayList<>();

    private List<TimelineDto> timelines = new ArrayList<>();
}
