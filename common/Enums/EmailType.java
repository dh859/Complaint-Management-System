package com.cms.cmsapp.common.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EmailType {
    PASSWORD_RESET,
    ACCOUNT_VERIFICATION,
    COMPLAINT_SUBMISSION,
    COMPLAINT_ASSIGNMENT,
    FORWARD_TO_DEPARTMENT,
    STATUS_UPDATE,
    ESCALATION,
    SIMPLE_NOTIFICATION,
    WELCOME_EMAIL;

     @JsonCreator
    public static EmailType fromString(String value) {
        if (value == null) {
            return null;
        }
        for (EmailType emailType : EmailType.values()) {
            if (emailType.name().equalsIgnoreCase(value)) {
                return emailType;
            }
        }
        throw new IllegalArgumentException("Invalid email type: " + value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
