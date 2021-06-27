package com.telerikacademy.oop.WIM.commands._constants;

import com.telerikacademy.oop.WIM.commands.showingCommands.ShowBoardActivity;

public class CommandConstants {

    public static final String OFF = "off";
    public static final String BUG = "Bug";
    public static final String STORY = "Story";
    public static final String TEAMS = "teams";
    public static final String BOARDS = "boards";
    public static final String PEOPLE = "people";
    public static final String FEEDBACK = "Feedback";
    public static final String PERSON = "The new Person to add";
    public static final String NEW_TEAM = "The new team to add";
    public static final String WORK_ITEM_ID = "Work Item's ID";
    public static final String WORK_ITEMS = "Work Items";
    public static final String COMMENT_CONTEND = "Comment's contend";
    public static final String TEAM_NAME = "Team's name";
    public static final String PERSON_NAME = "The new person's name";
    public static final String TEAM_BOARD = "The new board";
    public static final String TEAM_BOARDS = "team boards";
    public static final String TERMINATION_COMMAND = "Exit";
    public static final String REPORT_SEPARATOR = "####################";
    public static final String LOGIN = "login";
    public static final String COMMAND_CANNOT_BE_NULL_OR_EMPTY = "Command cannot be null or empty.";
    public static final String UNCLOSED_STATEMENT_IN_COMMAND_LINE = "Unclosed statement in command line.";
    public static final String WORK_DELETED = "Work whit id - %d has been deleted.";
    public static final String OBJECTS_TO_COMMENT = "Objects to comment";
    public static final String VALID_WORK_ITEM_TYPE = "The third parameter mast be a valid work item type";
    public static final String TEAM_MEMBERS = "Team members";
    public static final String SORTED_BY_PRIORITY = " sorted by priority";
    public static final String SORTED_BY_TITLE = " sorted by title";
    public static final String SORTED_BY_SIZE = " sorted by size";
    public static final String SORTED_BY_SEVERITY = " sorted by severity";
    public static final String SORTED_BY_RATING = " sorted by rating";
    public static final String SORTED_BY_ID = " sorted by item id";
    public static final String BOARDS_OF_TEAM_ARE = "The boards of '%s' are:%n";
    public static final String BOARD_ACTIVITY_IS = "Board '%s' activity is:%n";
    public static final String TEAM_ACTIVITY_IS = "Team '%s' activity is:%n";
    public static final String NO_WORK_ASSIGNABLE = "There are no boards nor members in team '%s'.";
    public static final String WORK_ITEM_NOT_FOUND_IN_TEAM = "Work item whit ID:'%d' not found in team '%s'";
    public static final String BOARD_NOT_FOUND = "Board '%s' not Found in team '%s'";
    public static final String MEMBERS_ARE = "The members of '%s' are:%n%s";
    public static final String NO_BOARDS_IN_TEAM = "Team '%s' doesn't contain any boards";

    public static final String COMMENT_ADDED_SUCCESSFULLY = "%s added comment successfully to Work Item ID: %d!";
    public static final String TEAM_ADDED_SUCCESSFULLY = "Team '%s' created successfully.";
    public static final String PERSON_ADDED_SUCCESSFULLY = "%s registered successfully.";
    public static final String WORK_UNASSIGNED_SUCCESSFULLY = "Work with ID %d unassigned from %s successfully.";
    public static final String WORK_ASSIGNED_SUCCESSFULLY = "Work with ID %d assigned to %s successfully.";
    public static final String MEMBER_ADDED_TO_TEAM_SUCCESSFULLY = "Adding '%s' to team %s successfully.";
    public static final String BOARD_EXISTS_IN_TEAM = "Board with the same name already exists in the team.";
    public static final String WORK_ADDED_TO_TEAM_SUCCESSFULLY = "Work with ID %d assigned to %s successfully.";
    public static final String UNSUPPORTED_WORK_ITEM_TYPE = "Unsupported work item type!";
    public static final String USER_LOGGED_IN_ALREADY = "User %s is logged in! Please log out first!";
    public static final String USER_LOGGED_IN = "User %s is logged successfully!";
    public static final String LOGGED_OUT = "Logout successful!";
    public static final String ITEM_ADDED_TO_TEAM_BOARD = "added '%s' number %d to %s's board '%s' successfully!";
    public static final String TEAM_OR_BOARD_NOT_FOUND = "Team - '%s' or Board - '%s' not found!";
    public static final String WRONG_ID_OR_FIELD_NAME = "The second parameter in ChangeWIState " +
            "mast be an integer - Work Item's ID.%n And the third mast be an changeable field:%n" +
            "STATUS%n" + "PRIORITY%n" + "SEVERITY%n" + "SIZE%n" + "RATING%n";
    public static final String THE_NEW_VALUE_IN_CHANGE_STATE = "The input value in Change State";
    public static final String USER_NOT_LOGGED = "You need to login first.";
    public static final String BOARD_CREATED = "Board '%s' created in team '%s' successfully.";
    public static final String WORK_ITEM_NOT_FOUND_IN_PERSON = "Work item %d not found in %s's work list";
    public static final String UNSUPPORTED_FILTER_OPTION = "Unsupported filter option.";
    public static final String UNSPECIFIED_ASSIGNEE = "Unspecified assignee in filter.";
    public static final String UNSPECIFIED_STATUS = "Unspecified status in filter.";
    public static final String FILTER_OPTIONS_SUCCESSFULLY = "Filter options:%nby Assignee name: %s%nby status type: " +
            "%s%n modified successfully.";
    public static final String SORTING_OPTIONS = "Sorting options";
    public static final String UNSUPPORTED_SORTING_OPTION = "Unsupported sorting option.";
    public static final String ORDER_TO_SORT = "Order to sort";
    public static final String WORK_ALREADY_ASSIGNED = "Work item %d already assigned to %s";
    public static final String THE_FIRST_PARAMETER_INTEGER = "The first parameter mast be the work item id - an integer.";
    public static final String ALREADY_IN_TEAM = " Adding member, but %s is already in team %s";

}
