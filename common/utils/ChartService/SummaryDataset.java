package com.cms.cmsapp.common.utils.ChartService;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SummaryDataset implements Dataset {

    private String reportTitle;
    private String generatedOn;

    private int totalComplaints;
    private int open;
    private int assigned;
    private int inProgress;
    private int resolved;
    private int forwarded;
    private int reopened;
    private int closed;
    private int canceled;

    private Map<String, Integer> departmentCounts;

    private Map<String, Integer> categoryCounts;
}
