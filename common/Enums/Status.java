package com.cms.cmsapp.common.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    OPEN,
    ASSIGNED,
    IN_PROGRESS,
    FORWARDED,
    RESOLVED,
    CANCELED,
    CLOSED,
    REOPENED;

    @JsonCreator
    public static Status fromString(String value) {
        if (value == null) {
            return null;
        }
        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
