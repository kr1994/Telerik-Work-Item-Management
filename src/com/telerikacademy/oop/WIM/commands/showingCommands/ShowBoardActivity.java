package com.telerikacademy.oop.WIM.commands.showingCommands;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.HistoryLogger;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.core.providers.HistoryLoggerImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.BoardImpl;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;

import java.util.List;
import java.util.Map;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;

public class ShowBoardActivity implements Command {


    private static final int PARAMETERS_MIN_SIZE = 2;

    private final String PARAMETERS = "Parameters in " + "ShowBoardActivity";
    private WIMFactory WIMFactory;
    private WIMRepository WIMRepository;
    private final HistoryLogger logger;

    public ShowBoardActivity(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
        logger = new HistoryLoggerImpl(WIMFactory, WIMRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        String boardName = parameters.get(0);
        String teamName = parameters.get(1);
        validateInput(teamName);

        Team team = WIMRepository.getTeams().get(teamName);
        validateInput(team, boardName);

        Board board = WIMRepository.getTeams().get(teamName).getBoard(boardName);

        String report = String.format(BOARD_ACTIVITY_IS, board.getBoardName())
                + board.getHistoryToString().trim();

        return logger.takeLog(board, report);
    }

    private void validateInput(Team team, String boardName) {
        Validator.validateBoard(boardName);
        if (team.getBoard(boardName) == null)
            throw new IllegalArgumentException(String.format(BOARD_NOT_FOUND,
                    boardName,
                    team.getTeamName()
            ));
    }

    private void validateInput(String teamName) {
        Validator.validateTeam(teamName, WIMRepository.getTeams());
    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }
}
