package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.HistoryLogger;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.core.providers.HistoryLoggerImpl;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.common.enums.Priority;
import com.telerikacademy.oop.WIM.models.common.enums.Severity;
import com.telerikacademy.oop.WIM.models.common.enums.StorySize;
import com.telerikacademy.oop.WIM.models.common.enums.WIType;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.items.*;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;
import static com.telerikacademy.oop.WIM.models.common.Constants.*;

public class CreateItemInBoard implements Command {
    private static final int PARAMETERS_MIN_SIZE = 7;
    private static final int numberOfExtractedParameters = 2;

    private final String PARAMETERS = "Parameters in " + "CreateItemInBoard";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;
    private final CreateBoardItem createBoardItem;
    private final HistoryLogger logger;


    public CreateItemInBoard(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
        createBoardItem = new CreateBoardItem(WIMFactory, WIMRepository);
        logger = new HistoryLoggerImpl(WIMFactory, WIMRepository);

    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        String teamName = parameters.get(0).trim().toLowerCase();
        String boardName = parameters.get(1).trim().toLowerCase();
        validateInput(teamName, boardName);

        Team team = WIMRepository.getTeams().get(teamName);
        Board board = team.getBoard(boardName);
        List<String> newParameters = parameters.subList(numberOfExtractedParameters, parameters.size());

        return createBoardItem.execute(team, board, newParameters);
    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }

    private void validateInput(String teamName, String boardName) {
        Validator.validateTeam(teamName, WIMRepository.getTeams());
        Team team = WIMRepository.getTeams().get(teamName);
        Validator.validateBoard(boardName);
        if (!Validator.isInCollection(boardName, team.getTeamBoards()))
            throw new IllegalArgumentException(String.format(TEAM_OR_BOARD_NOT_FOUND,
                    teamName,
                    boardName));
    }

    private static class CreateBoardItem {
        private static final int BUG_PARAMETERS_MIN_SIZE = 8;
        private static final int STORY_PARAMETERS_MIN_SIZE = 7;
        private static final int FEEDBACK_PARAMETERS_MIN_SIZE = 4;
        private static final int TASK_PARAMETERS_MIN_SIZE = 6;

        private final String PARAMETERS = "Parameters in " + "CreateBoardItem";
        private final WIMFactory WIMFactory;
        private final WIMRepository WIMRepository;
        private final HistoryLogger logger;

        CreateBoardItem(WIMFactory WIMFactory, WIMRepository WIMRepository) {
            this.WIMFactory = WIMFactory;
            this.WIMRepository = WIMRepository;
            logger = new HistoryLoggerImpl(WIMFactory, WIMRepository);
        }

        String execute(Team team, Board board, List<String> parameters) {
            validateInput(parameters);
            WIType workItemType;
            String title, description, status;

            try {
                workItemType = WIType.valueOf(parameters.get(0).trim().toUpperCase());
                title = parameters.get(1);
                description = parameters.get(2);
                status = parameters.get(3);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(VALID_WORK_ITEM_TYPE);
            }
            if (WIType.FEEDBACK.equals(workItemType))
                return implementFeedback(team, board, title, description, status, parameters);

            return implementTask(team, board, workItemType, title, description, status, parameters);

        }

        private void validateInput(List<String> parameters) {
            Validator.validateParameters(parameters, PARAMETERS, FEEDBACK_PARAMETERS_MIN_SIZE);
        }

        private String implementFeedback(Team team, Board board, String title,
                                         String description, String status, List<String> parameters) {
            validateInput(title, description, status);
            int rating;
            try {
                rating = Integer.parseInt(parameters.get(4));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(FB_RATTING_MAST_BE_INTEGER);
            }
            FeedBack feedBack = WIMFactory.createFB(title, description, status, rating);
            WIMRepository.addFeedBack(feedBack);
            board.assignWork(feedBack);

            String report = String.format(ITEM_ADDED_TO_TEAM_BOARD,
                    FEEDBACK,
                    feedBack.getID(),
                    team.getTeamName(),
                    board.getBoardName());
            return logger.takeLog(feedBack, board, report);
        }

        private String implementTask(Team team, Board board, WIType workItemType, String title,
                                     String description, String status, List<String> parameters) {
            validateInput(title, description, status);
            Validator.validateParameters(parameters, PARAMETERS, TASK_PARAMETERS_MIN_SIZE);

            Priority priority;
            try {
                priority = Priority.valueOf(parameters.get(4).trim().toUpperCase());
            } catch (Exception e) {
                throw new IllegalArgumentException(WRONG_PRIORITY);
            }
            String assigneeName = parameters.get(5);
            validatePersonName(assigneeName);

            Person assignee = WIMRepository.getPeople().get(assigneeName.trim().toLowerCase());
            switch (workItemType) {
                case BUG:
                    return implementBug(team, board, title, description, status, priority, assignee, parameters);
                case STORY:
                    return implementStory(team, board, title, description, status, priority, assignee, parameters);
                default:
                    throw new IllegalArgumentException(UNSUPPORTED_WORK_ITEM_TYPE);
            }
        }

        private void validatePersonName(String assigneeName) {
            Validator.validateNotNull(assigneeName, PERSON_NAME);
            if (!Validator.isInCollection(assigneeName, WIMRepository.getPeople().values()))
                throw new IllegalArgumentException(String.format(NOT_FOUND_IN_MAP, assigneeName));
        }

        private void validateInput(String title, String description, String status) {
            Validator.validateNotNull(title, TITLE);
            Validator.validateNotNull(description, DESCRIPTION);
            Validator.validateNotNull(status, STATUS);
        }

        private String implementBug(Team team, Board board, String title, String description, String status,
                                    Priority priority, Person assignee, List<String> parameters) {
            Validator.validateIntIsNotLessThan(parameters.size(), PARAMETERS, BUG_PARAMETERS_MIN_SIZE);
            Severity severity;
            try {
                severity = Severity.valueOf(parameters.get(6).toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(UNSUPPORTED_SEVERITY);
            }
            List<String> steps = new ArrayList<>(parameters.subList(7, parameters.size()));

            Bug bug = WIMFactory.createBug(title, description, status, priority, assignee, severity, steps);
            WIMRepository.addBug(bug);
            board.assignWork(bug);
            assignee.assignWork(bug);

            String report = String.format(ITEM_ADDED_TO_TEAM_BOARD,
                    BUG,
                    bug.getID(),
                    team.getTeamName(),
                    board.getBoardName());
            return addHistoryToAll(bug, assignee, board, report);

        }

        private String implementStory(Team team, Board board, String title, String description, String status, Priority priority,
                                      Person assignee, List<String> parameters) {
            Validator.validateIntIsNotLessThan(parameters.size(), PARAMETERS, STORY_PARAMETERS_MIN_SIZE);
            StorySize size;
            try {
                size = StorySize.valueOf(parameters.get(6).toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(UNSUPPORTED_STORY_SIZE);
            }
            Story story = WIMFactory.createStory(title, description, status, priority, assignee, size);
            WIMRepository.addStory(story);
            board.assignWork(story);
            assignee.assignWork(story);

            String report = String.format(ITEM_ADDED_TO_TEAM_BOARD,
                    STORY,
                    story.getID(),
                    team.getTeamName(),
                    board.getBoardName());
            return addHistoryToAll(story, assignee, board, report);
        }

        private String addHistoryToAll(Task item, Person assignee, Board board, String report) {
            logger.takeLog(item, assignee, report);
            return logger.takeLog(board, report);
        }
    }
}
