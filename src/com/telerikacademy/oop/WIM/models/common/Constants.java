package com.telerikacademy.oop.WIM.models.common;

public class Constants {

    // Comment
    public static final int CONTENT_MIN_SIZE = 10;
    public static final int CONTENT_MAX_SIZE = 50;
    public static final String COMMENT_CONTEND = "Comment's content";
    public static final String COMMENT_AUTHOR = "Comment's Author";

    // Names
    public static final int USERNAME_MIN_SIZE = 5;
    public static final int USERNAME_MAX_SIZE = 15;
    public static final int TEAM_NAME_MIN_SIZE = 5;
    public static final int TEAM_NAME_MAX_SIZE = 15;
    public static final int BOARD_NAME_MIN_SIZE = 5;
    public static final int BOARD_NAME_MAX_SIZE = 15;

    // Titles
    public static final int BUG_TITLE_MIN_SIZE = 10;
    public static final int BUG_TITLE_MAX_SIZE = 50;
    public static final int STORY_TITLE_MIN_SIZE = 10;
    public static final int STORY_TITLE_MAX_SIZE = 50;
    public static final int FB_TITLE_MIN_SIZE = 10;
    public static final int FB_TITLE_MAX_SIZE = 50;

    // Descriptions
    public static final int BUG_DESCRIPTION_MIN_SIZE = 10;
    public static final int BUG_DESCRIPTION_MAX_SIZE = 500;
    public static final int STORY_DESCRIPTION_MIN_SIZE = 10;
    public static final int STORY_DESCRIPTION_MAX_SIZE = 500;
    public static final int FB_DESCRIPTION_MIN_SIZE = 10;
    public static final int FB_DESCRIPTION_MAX_SIZE = 500;
    public static final String TASK = "Task";
    public static final String COMMENT = "Comment";
    public static final String HISTORY_LOG = "History log";
    public static final String STATUS = "Status";
    public static final String NEW_VALUE = "The new field value";
    public static final String TITLE = "Work item's title";
    public static final String DESCRIPTION = "Work item's description";
    public static final String TEAM_MEMBER = "Team Member";
    public static final String BOARD_NAME = "The Board name";
    public static final String WORK = "Work item";
    public static final String STEPS = "Steps to reproduce";


    public static final String NULL_ERR = "%s can't be empty.";
    public static final String STRING_IN_BOUNDS_ERR = "%s must be between %d and %d characters long.";
    public static final String INT_IN_BOUNDS = "%s must be between %d and %d.";
    public static final String IS_MORE_THAN = "%s must be more then %d.";
    public static final String UNIQUE_IN_THE_APP_ERR = "%s must be unique in the app.";
    public static final String UNSUPPORTED_STORY_STATUS = "Unsupported Story status.";
    public static final String UNSUPPORTED_BUG_STATUS = "Unsupported Bug status.";
    public static final String UNSUPPORTED_FB_STATUS = "Unsupported Feedback status.";
    public static final String UNSUPPORTED_PRIORITY = "Unsupported priority.";
    public static final String UNSUPPORTED_SEVERITY = "Unsupported severity.";
    public static final String UNSUPPORTED_STATUS = "Unsupported status.";
    public static final String UNSUPPORTED_STORY_SIZE = "Unsupported story size.";
    public static final String FB_RATTING_MAST_BE_INTEGER = "FeedBack ratting mast be an integer.%n";
    public static final String WRONG_PRIORITY = "Wrong value as 'priority' input.";
    public static final String NOT_FOUND_IN_MAP = "%s is not found in the map.";
    public static final String ARE_NO_FOUND = "There are no %s found.";

    //assigning new value to field
    public static final String ERR_CANT_ASSIGN = "Can't assign %s to %s.%n";
    public static final String ERR_ALREADY_AT_IT = "%s already at %s.%n";
    public static final String WRONG_FIELD = "Cant use %s in %s.%n";

    //history events
    public static final String HISTORY_EFFECT_PLACE_FROM_OLD_TO_NEW = "changed %s %s from %s to %s.";
    public static final String HISTORY_ACTED_OBJECT = "%s %s.";

}
