package com.telerikacademy.oop.WIM.commands.showingCommands;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.HistoryLogger;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.core.providers.HistoryLoggerImpl;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.BOARDS_OF_TEAM_ARE;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.TEAM_BOARDS;
import static com.telerikacademy.oop.WIM.models.common.Utils.addHistoryToAll;

import java.util.List;

public class ShowTeamBoards implements Command {

    private static final int PARAMETERS_MIN_SIZE = 1;

    private final String PARAMETERS = "Parameters in " + "ShowTeamBoards";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;
    private final HistoryLogger logger;

    public ShowTeamBoards(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
        logger = new HistoryLoggerImpl(WIMFactory, WIMRepository);

    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        String teamName = parameters.get(0);
        validateInput(teamName);

        Team team = WIMRepository.getTeams().get(teamName.toLowerCase());
        Validator.notEmpty(team.getTeamBoards(), TEAM_BOARDS);
        String report = String.format(BOARDS_OF_TEAM_ARE, team.getTeamName())
                + getAllTeamBoards(team).trim();

        addHistoryToAll(team.getTeamBoards(), report, logger);
        return report;
    }

    private void validateInput(String teamName) {
        Validator.validateTeam(teamName, WIMRepository.getTeams());
    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }

    private String getAllTeamBoards(Team team) {
        return team.getTeamBoards().stream()
                .map(Board::getBoardName)
                .reduce("", (a, b) ->
                        a + System.lineSeparator() + b);
    }

}
