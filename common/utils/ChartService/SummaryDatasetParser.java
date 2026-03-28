package com.cms.cmsapp.common.utils.ChartService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cms.cmsapp.reporting.entity.Report;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SummaryDatasetParser {

    private final ObjectMapper mapper ;

    public SummaryDatasetParser(ObjectMapper objectMapper) {
        mapper = objectMapper;
    }

    public SummaryDataset parse(Report report) {

        try {
            if (report.getContent() == null) {
                return defaultDataset();
            }

            Map<String, Object> data =
                    mapper.readValue(report.getContent(), Map.class);

            return new SummaryDataset(
                    getString(data, "reportTitle", "Complaint Summary Report"),
                    getString(data, "generatedOn", report.getCreatedAt().toString()),
                    getInt(data, "totalComplaints"),
                    getInt(data, "open"),
                    getInt(data, "assigned"),
                    getInt(data, "inProgress"),
                    getInt(data, "resolved"),
                    getInt(data, "forwarded"),
                    getInt(data, "reopened"),
                    getInt(data, "closed"),
                    getInt(data, "canceled"),
                    getMap(data, "departmentCounts"),
                    getMap(data, "categoryCounts")
            );

        } catch (Exception e) {
            return defaultDataset();
        }
    }

    private  int getInt(Map<String, Object> map, String key) {
        return map.get(key) == null ? 0 : ((Number) map.get(key)).intValue();
    }

    private  String getString(Map<String, Object> map, String key, String def) {
        return map.get(key) == null ? def : map.get(key).toString();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Integer> getMap(Map<String, Object> map, String key) {
        if (map.get(key) == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> raw = (Map<String, Object>) map.get(key);
        Map<String, Integer> result = new HashMap<>();

        raw.forEach((k, v) -> result.put(k, ((Number) v).intValue()));

        return result;
    }

    private SummaryDataset defaultDataset() {

        Map<String, Integer> departments = new HashMap<>();
        departments.put("IT", 120);
        departments.put("HR", 70);
        departments.put("Finance", 80);
        departments.put("Admin", 50);

        Map<String, Integer> categories = new HashMap<>();
        categories.put("Technical", 150);
        categories.put("Service", 90);
        categories.put("Billing", 80);

        return new SummaryDataset(
                "Complaint Summary Report",
                LocalDate.now().toString(),

                320, 85, 45, 60, 100, 12, 8, 90, 20,

                departments,
                categories
        );
    }

}
