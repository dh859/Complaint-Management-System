package com.cms.cmsapp.reporting.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportDto {
    
    Long reportId;
    String type;
    Long generatedByUserId;
    LocalDateTime createdAt;
    String reportTitle;
    String generatedByUsername;
    String generatedByUserFullname;

}
