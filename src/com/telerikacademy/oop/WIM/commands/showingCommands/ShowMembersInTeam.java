package com.telerikacademy.oop.WIM.commands.showingCommands;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Team;

import java.util.List;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.MEMBERS_ARE;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.TEAM_MEMBERS;

public class ShowMembersInTeam implements Command {

    private static final int PARAMETERS_MIN_SIZE = 1;

    private final String PARAMETERS = "Parameters in " + "ShowMembersInTeam";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;

    public ShowMembersInTeam(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        String teamName = parameters.get(0);
        Validator.validateTeam(teamName, WIMRepository.getTeams());

        Team team = WIMRepository.getTeams().get(teamName.toLowerCase());
        Validator.validateNotNull(team.getTeamMembers(), TEAM_MEMBERS);

        return String.format(MEMBERS_ARE,
                team.getTeamName(),
                team.getTeamMembers().toString());

    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }


}
