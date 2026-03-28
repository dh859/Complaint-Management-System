package com.cms.cmsapp.common.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DepartmentRole {
    MANAGER,AGENT;

    @JsonCreator
    public static DepartmentRole fromString(String value) {
        if (value == null) {
            return null;
        }
        for (DepartmentRole role : DepartmentRole.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
