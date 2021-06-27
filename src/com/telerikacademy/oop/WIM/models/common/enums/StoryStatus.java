package com.telerikacademy.oop.WIM.models.common.enums;

import static com.telerikacademy.oop.WIM.models.common.Constants.*;

public enum StoryStatus {

    NOTDONE,
    INPROGRESS,
    DONE;


    @Override
    public String toString() {
        switch (this) {
            case DONE:
                return "Done";
            case NOTDONE:
                return "NotDone";
            case INPROGRESS:
                return "InProgress";
            default:
                throw new IllegalArgumentException("Unsupported StoryStatus.");
        }
    }

    public static String nextStoryStatus(String oldStatus) {
        switch (StoryStatus.valueOf(oldStatus.toUpperCase())) {
            case NOTDONE:
                return INPROGRESS.toString();
            case INPROGRESS:
            case DONE:
                return DONE.toString();
            default:
                throw new IllegalArgumentException(UNSUPPORTED_STORY_STATUS);
        }
    }

    public static String lastStoryStatus(String oldStatus) {
        switch (StoryStatus.valueOf(oldStatus.toUpperCase())) {
            case DONE:
                return INPROGRESS.toString();
            case INPROGRESS:
            case NOTDONE:
                return NOTDONE.toString();
            default:
                throw new IllegalArgumentException(UNSUPPORTED_STORY_STATUS);
        }
    }

    public static String searchForFilter(String status) {
        if (status.equalsIgnoreCase(NOTDONE.toString()))
            return NOTDONE.toString();
        else if (status.equalsIgnoreCase(INPROGRESS.toString()))
            return INPROGRESS.toString();
        else if (status.equalsIgnoreCase(DONE.toString()))
            return DONE.toString();
        else
            return UNSUPPORTED_STATUS;
    }
}
