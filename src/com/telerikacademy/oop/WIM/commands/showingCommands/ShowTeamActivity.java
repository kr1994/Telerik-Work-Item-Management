package com.telerikacademy.oop.WIM.commands.showingCommands;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Team;

import java.util.List;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.NO_BOARDS_IN_TEAM;

public class ShowTeamActivity implements Command {

    private static final int PARAMETERS_MIN_SIZE = 1;

    private final String PARAMETERS = "Parameters in " + "ShowTeamActivity";
    private WIMFactory WIMFactory;
    private WIMRepository WIMRepository;

    public ShowTeamActivity(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        String teamName = parameters.get(0);
        validateInput(teamName);

        Team team = WIMRepository.getTeams().get(teamName.toLowerCase());
        if (team.getTeamBoards().isEmpty())
            throw new IllegalArgumentException(String.format(NO_BOARDS_IN_TEAM,
                    team.getTeamName()
            ));
        return team.getHistoryToStringByTime();
    }

    private void validateInput(String teamName) {
        Validator.validateTeam(teamName, WIMRepository.getTeams());
    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }

}
