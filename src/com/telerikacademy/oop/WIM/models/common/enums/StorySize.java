package com.telerikacademy.oop.WIM.models.common.enums;

import com.telerikacademy.oop.WIM.models.common.Constants;

import static com.telerikacademy.oop.WIM.models.common.Constants.UNSUPPORTED_STORY_SIZE;

public enum StorySize {
    SMALL,
    MEDIUM,
    LARGE;

    @Override
    public String toString() {
        switch (this){
            case SMALL:
                return "Small";
            case MEDIUM:
                return "Medium";
            case LARGE:
                return "Large";
            default:
                throw new IllegalArgumentException(UNSUPPORTED_STORY_SIZE);
        }
    }

    public static StorySize nextStorySize(StorySize old) {
        switch (old) {
            case SMALL:
                return MEDIUM;
            default:
                return LARGE;
        }
    }

    public static StorySize lastStorySize(StorySize old) {
        switch (old) {
            case LARGE:
                return MEDIUM;
            default:
                return SMALL;
        }
    }
}
