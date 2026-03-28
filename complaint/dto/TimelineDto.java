package com.cms.cmsapp.complaint.dto;

import java.time.LocalDateTime;

import com.cms.cmsapp.common.Enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimelineDto {

    private Status status;
    private String changedBy;
    private LocalDateTime changedAt;
    private String remark;
    
}
