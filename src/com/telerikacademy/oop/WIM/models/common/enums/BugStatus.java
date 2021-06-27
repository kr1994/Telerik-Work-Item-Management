package com.telerikacademy.oop.WIM.models.common.enums;


import static com.telerikacademy.oop.WIM.models.common.Constants.UNSUPPORTED_BUG_STATUS;

public enum BugStatus {
    ACTIVE,
    FIXED;

    @Override
    public String toString() {
        switch (this){
            case ACTIVE:
                return "Active";
            case FIXED:
                return "Fixed";
            default:
                throw new IllegalArgumentException(UNSUPPORTED_BUG_STATUS);
        }
    }

    public static String nextBugStatus(String oldStatus) {
        return FIXED.toString();
    }

    public static String lastBugStatus(String oldStatus) {
        return ACTIVE.toString();
    }

    public static String searchForFilter(String status){
        if(status.equalsIgnoreCase(ACTIVE.toString()))
            return ACTIVE.toString();
        else if(status.equalsIgnoreCase(FIXED.toString()))
            return FIXED.toString();
        else
            return FeedBackStatus.searchForFilter(status);
    }
}
