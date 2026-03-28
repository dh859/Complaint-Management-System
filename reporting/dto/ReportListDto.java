package com.cms.cmsapp.reporting.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportListDto {
    
    List<ReportDto> reportList;
}
