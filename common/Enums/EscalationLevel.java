package com.cms.cmsapp.common.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EscalationLevel {

    NONE(0),
    LEVEL1(1),
    LEVEL2(2),
    LEVEL3(3);

    private final int level;

    EscalationLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean isHigherThan(EscalationLevel other) {
        return this.level > other.level;
    }

    public boolean isLowerThan(EscalationLevel other) {
        return this.level < other.level;
    }

    public EscalationLevel next() {
        return switch (this) {
            case NONE -> LEVEL1;
            case LEVEL1 -> LEVEL2;
            case LEVEL2 -> LEVEL3;
            case LEVEL3 -> LEVEL3; // safe max
        };
    }

    public boolean isMaxLevel() {
        return this == LEVEL3;
    }

    public boolean isNone() {
        return this == NONE;
    }

    @JsonCreator
    public static EscalationLevel fromString(String value) {
        if (value == null) return NONE;

        for (EscalationLevel level : values()) {
            if (level.name().equalsIgnoreCase(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid escalation level: " + value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
