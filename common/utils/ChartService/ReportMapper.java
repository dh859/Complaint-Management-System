package com.cms.cmsapp.common.utils.ChartService;

import com.cms.cmsapp.common.Enums.ReportType;
import com.cms.cmsapp.reporting.entity.Report;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportMapper {

    private final ObjectMapper objectMapper;
    private final SummaryDatasetParser summaryDatasetParser;
    private final SystemReportDatasetParser systemReportDatasetParser;

    
    public Dataset toDataset(Report report) {

        if (report == null) {
            throw new IllegalArgumentException("Report cannot be null");
        }

        ReportType type = report.getReportType();

        switch (type) {

            case ReportType.SUMMARY_REPORT:
                return summaryDatasetParser.parse(report);

            case ReportType.SYSTEM_REPORT:
                return systemReportDatasetParser.parse(report);
            default:
                throw new IllegalArgumentException(
                        "Unsupported report type: " + type
                );
        }
    }

 
    public String toJson(Object dataset) {
        try {
            return objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(dataset);
        } catch (Exception e) {
            return "{}";
        }
    }
}
