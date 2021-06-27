package com.telerikacademy.oop.WIM.commands;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.HistoryLogger;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.core.providers.HistoryLoggerImpl;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.common.enums.OpenField;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;
import static com.telerikacademy.oop.WIM.models.common.Utils.addHistoryToAll;

public class ChangeWIState implements Command {

    private static final int PARAMETERS_MIN_SIZE = 3;

    private final String PARAMETERS = "Parameters in " + "ChangeWIState";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;
    private final HistoryLogger logger;

    public ChangeWIState(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
        logger = new HistoryLoggerImpl(WIMFactory, WIMRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        int workItemID;
        OpenField field;
        String newValue;

        try {
            workItemID = Integer.parseInt(parameters.get(0));
            field = OpenField.valueOf(parameters.get(1).toUpperCase());
            newValue = parameters.get(2);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(WRONG_ID_OR_FIELD_NAME);
        }
        return implement(workItemID, field, newValue);
    }

    private String implement(int workItemID, OpenField field, String newValue) {
        validateInput(workItemID, newValue);

        WorkItem item = WIMRepository.getWorkByID(workItemID);
        String report = item.assignNewValueToAccessibleField(field, newValue);

        List<Board> boards = findAllBoardsContaining(item);
        addHistoryToAll(boards, report, logger);
        logger.takeLog(item, report);

        return report;
    }

    private void validateInput(int workItemID, String newValue) {
        Validator.validateWorkID(workItemID, WIMRepository.getWork());
        Validator.validateNotNull(newValue, THE_NEW_VALUE_IN_CHANGE_STATE);
    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
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
