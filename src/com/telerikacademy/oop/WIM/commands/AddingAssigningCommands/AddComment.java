package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.commands._constants.CommandConstants;
import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.HistoryLogger;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.core.providers.HistoryLoggerImpl;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;
import com.telerikacademy.oop.WIM.models.contracts.items.Comment;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;
import static com.telerikacademy.oop.WIM.models.common.Utils.addHistoryToAll;


public class AddComment implements Command {


    private static final int PARAMETERS_MIN_SIZE = 2;

    private final String PARAMETERS = "Parameters in " + "AddComment";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;
    private final HistoryLogger logger;

    public AddComment(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
        logger = new HistoryLoggerImpl(WIMFactory, WIMRepository);

    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        int workIndex;
        try {
            workIndex = Integer.parseInt(parameters.get(0));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(THE_FIRST_PARAMETER_INTEGER);
        }
        String content = parameters.get(1);
        return implement(content, workIndex);

    }

    private String implement(String content, int workIndex) {
        validateInput(content, workIndex);

        WorkItem item = WIMRepository.getWorkByID(workIndex);
        Admin author = WIMRepository.getAuthor();
        Comment newComment = WIMFactory.createComment(content, author);
        List<Board> boards = findAllBoardsContaining(item);

        item.addComment(newComment);
        String report = String.format(CommandConstants.COMMENT_ADDED_SUCCESSFULLY,
                author.toString(),
                workIndex);
        addHistoryToAll(boards,report,logger);
        return logger.takeLog(item,report);
    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
        Validator.notEmpty(WIMRepository.getCommentable(), OBJECTS_TO_COMMENT);
    }

    private void validateInput(String content, int index) {
        Validator.validateWorkID(index, WIMRepository.getWork());
        Validator.validateNotNull(content, COMMENT_CONTEND);
    }

    private List<Board> findAllBoardsContaining(WorkItem item) {
        return WIMRepository.getTeams().values().stream()
                .map(t -> t.getTeamBoards().stream()
                        .filter(b -> b.getWork().contains(item))
                        .findAny()
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
