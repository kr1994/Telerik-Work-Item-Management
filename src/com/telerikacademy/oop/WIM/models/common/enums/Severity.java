package com.telerikacademy.oop.WIM.models.common.enums;

import static com.telerikacademy.oop.WIM.models.common.Constants.UNSUPPORTED_SEVERITY;

public enum Severity {
    MINOR,
    MAJOR,
    CRITICAL;

    @Override
    public String toString() {
        switch (this){
            case MINOR:
                return "Minor";
            case MAJOR:
                return "Major";
            case CRITICAL:
                return "Critical";
            default:
                throw new IllegalArgumentException(UNSUPPORTED_SEVERITY);
        }
    }

    public static Severity nextSeverity(Severity oldValue){
        switch (oldValue){
            case MINOR:
                return MAJOR;
            default:
                return CRITICAL;

        }
    }

    public static Severity lastSeverity(Severity oldValue){
        switch (oldValue){
            case CRITICAL:
                return MAJOR;
            default:
                return MINOR;
        }
    }
}
