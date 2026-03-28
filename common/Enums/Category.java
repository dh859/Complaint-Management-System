package com.cms.cmsapp.common.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
    ELECTRICITY,
    WATER,
    ROAD,
    SANITATION,
    HEALTH,
    POLICE,
    EDUCATION,
    TRANSPORT,
    GENERAL;

    @JsonCreator
    public static Category fromString(String value) {
        if (value == null) {
            return null;
        }
        for (Category category : Category.values()) {
            if (category.name().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category: " + value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
