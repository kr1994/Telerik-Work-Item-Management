package com.telerikacademy.oop.WIM.models.common.enums;

import com.telerikacademy.oop.WIM.models.contracts.items.Story;

import static com.telerikacademy.oop.WIM.models.common.Constants.UNSUPPORTED_FB_STATUS;

public enum FeedBackStatus {
    NEW,
    UNSCHEDULED,
    SCHEDULED,
    DONE;

    @Override
    public String toString() {
    switch (this){
        case NEW:
            return "New";
        case UNSCHEDULED:
            return "Unscheduled";
        case SCHEDULED:
            return "Scheduled";
        case DONE:
            return "Done";
        default:
            throw new IllegalArgumentException(UNSUPPORTED_FB_STATUS);

    }

}

    public static String nextFBStatus(String oldStatus) {
        switch (FeedBackStatus.valueOf(oldStatus.toUpperCase())) {
            case NEW:
                return UNSCHEDULED.toString();
            case UNSCHEDULED:
                return SCHEDULED.toString();
            default:
                return DONE.toString();
        }
    }

    public static String lastFBStatus(String oldStatus) {
        switch (FeedBackStatus.valueOf(oldStatus.toUpperCase())) {
            case DONE:
                return SCHEDULED.toString();
            case SCHEDULED:
                return UNSCHEDULED.toString();
            default:
                return NEW.toString();
        }
    }

    public static String searchForFilter(String status){
        if(status.equalsIgnoreCase(NEW.toString()))
            return NEW.toString();
        else if(status.equalsIgnoreCase(UNSCHEDULED.toString()))
            return UNSCHEDULED.toString();
        else if(status.equalsIgnoreCase(SCHEDULED.toString()))
            return SCHEDULED.toString();
        else if(status.equalsIgnoreCase(DONE.toString()))
            return DONE.toString();
        else
            return StoryStatus.searchForFilter(status);
    }

}
/*
private enum ColdDrink {
        PEPSI("Pepsi"), COKE("Coca Cola"), SPRITE("Sprite");
        private String brandname;
        private ColdDrink(String brand) {
            this.brandname = brand;
        }

        @Override
        public String toString(){
            return brandname;
        }
    }

 */