package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.HistoryLogger;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.core.providers.HistoryLoggerImpl;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;

import java.util.List;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;

public class CreateBoardInTeam implements Command {

    private static final int PARAMETERS_MIN_SIZE = 2;

    private final String PARAMETERS = "Parameters in " + "CreateBoardInTeam";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;
    private final HistoryLogger logger;

    public CreateBoardInTeam(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
        logger = new HistoryLoggerImpl(WIMFactory, WIMRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        String boardName = parameters.get(0).trim().toLowerCase();
        String teamName = parameters.get(1).trim().toLowerCase();
        validateInput(boardName, teamName);

        Board newBoard = WIMFactory.createBoard(boardName);
        Team team = WIMRepository.getTeams().get(teamName);
        team.addBoard(newBoard);

        return logger.takeLog(newBoard, String.format(BOARD_CREATED,
                newBoard.getBoardName(),
                team.getTeamName()));
    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }

    private void validateInput(String boardName, String teamName) {
        Validator.validateTeam(teamName, WIMRepository.getTeams());
        Team team = WIMRepository.getTeams().get(teamName);
        Validator.validateBoard(boardName);
        if (Validator.isInCollection(boardName, team.getTeamBoards()))
            throw new IllegalArgumentException(BOARD_EXISTS_IN_TEAM);
    }
}
