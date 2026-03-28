package com.cms.cmsapp.common.utils.ChartService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SystemReportDataset implements Dataset {

    private String reportTitle;
    private String generatedOn;

    private Complaints complaints;
    private Users users;
    private SystemMetrics system;

    @Data
    @AllArgsConstructor
    public static class Complaints {
        private int total;
        private int open;
        private int assigned;
        private int inProgress;
        private int resolved;
        private int forwarded;
        private int reopened;
        private int closed;
        private int canceled;
    }

    @Data
    @AllArgsConstructor
    public static class Users {
        private int total;
        private int admins;
        private int managers;
        private int agents;
        private int users;
    }

    @Data
    @AllArgsConstructor
    public static class SystemMetrics {
        private int totalMemoryMB;
        private int usedMemoryMB;
        private int availableProcessors;
        private int activeThreads;
    }
}
