package com.cms.cmsapp.common.utils.ChartService;

import com.cms.cmsapp.reporting.entity.Report;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SystemReportDatasetParser {

    private final ObjectMapper mapper ;

    public SystemReportDatasetParser(ObjectMapper objectMapper) {
        mapper = objectMapper;
    }

    public SystemReportDataset parse(Report report) {

        try {
            if (report.getContent() == null) {
                return defaultDataset();
            }

            Map<String, Object> root =
                    mapper.readValue(report.getContent(), Map.class);

            Map<String, Object> complaints =
                    (Map<String, Object>) root.get("complaints");

            Map<String, Object> users =
                    (Map<String, Object>) root.get("users");

            Map<String, Object> system =
                    (Map<String, Object>) root.get("system");

            return new SystemReportDataset(
                    getString(root, "reportTitle", "System Report"),
                    getString(root, "generatedOn", report.getCreatedAt().toString()),

                    new SystemReportDataset.Complaints(
                            getInt(complaints, "total"),
                            getInt(complaints, "open"),
                            getInt(complaints, "assigned"),
                            getInt(complaints, "inProgress"),
                            getInt(complaints, "resolved"),
                            getInt(complaints, "forwarded"),
                            getInt(complaints, "reopened"),
                            getInt(complaints, "closed"),
                            getInt(complaints, "canceled")
                    ),

                    new SystemReportDataset.Users(
                            getInt(users, "total"),
                            getInt(users, "admins"),
                            getInt(users, "managers"),
                            getInt(users, "agents"),
                            getInt(users, "users")
                    ),

                    new SystemReportDataset.SystemMetrics(
                            getInt(system, "totalMemoryMB"),
                            getInt(system, "usedMemoryMB"),
                            getInt(system, "availableProcessors"),
                            getInt(system, "activeThreads")
                    )
            );

        } catch (Exception e) {
            return defaultDataset();
        }
    }

    private  int getInt(Map<String, Object> map, String key) {
        return map == null || map.get(key) == null
                ? 0
                : ((Number) map.get(key)).intValue();
    }

    private  String getString(Map<String, Object> map, String key, String def) {
        return map == null || map.get(key) == null
                ? def
                : map.get(key).toString();
    }

    private  SystemReportDataset defaultDataset() {
        return new SystemReportDataset(
                "System Report",
                LocalDate.now().toString(),

                new SystemReportDataset.Complaints(
                        320, 85, 45, 60, 100, 12, 8, 90, 20
                ),

                new SystemReportDataset.Users(
                        120, 5, 10, 25, 80
                ),

                new SystemReportDataset.SystemMetrics(
                        8192, 5420, 8, 146
                )
        );
    }
}
