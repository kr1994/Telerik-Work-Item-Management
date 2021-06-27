package com.telerikacademy.oop.WIM.models.common.enums;

import static com.telerikacademy.oop.WIM.models.common.Constants.UNSUPPORTED_PRIORITY;

public enum Priority {
    LOW,
    MEDIUM,
    HIGH;

    @Override
    public String toString() {
        switch (this){
            case LOW:
                return "Low";
            case MEDIUM:
                return "Medium";
            case HIGH:
                return "High";
            default:
                return UNSUPPORTED_PRIORITY;
        }
    }

    public static Priority nextPriority(Priority old) {
        switch (old) {
            case LOW:
                return MEDIUM;
            default:
                return HIGH;
        }
    }

    public static Priority lastPriority(Priority old) {
        switch (old) {
            case HIGH:
                return MEDIUM;
            default:
                return LOW;
        }
    }
}
