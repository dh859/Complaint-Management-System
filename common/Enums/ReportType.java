package com.cms.cmsapp.common.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ReportType {
    SUMMARY_REPORT,
    SYSTEM_REPORT,
    DEPARTMENT_REPORT,
    CATEGORY_REPORT,
    AGENT_REPORT;

    @JsonCreator
    public static ReportType fromString(String value) {
        if (value == null) {
            return null;
        }
        for (ReportType reportType : ReportType.values()) {
            if (reportType.name().equalsIgnoreCase(value)) {
                return reportType;
            }
        }
        throw new IllegalArgumentException("Invalid report type: " + value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
