package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.commands._constants.CommandConstants;
import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Team;

import java.util.List;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.TEAM_NAME;
import static com.telerikacademy.oop.WIM.models.common.Constants.UNIQUE_IN_THE_APP_ERR;

public class RegisterNewTeam implements Command {

    private static final int PARAMETERS_MIN_SIZE = 1;

    private final String PARAMETERS = "Parameters in " + "RegisterNewTeam";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;

    public RegisterNewTeam(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        String teamNameInput = parameters.get(0);
        validateInput(teamNameInput);

        Team newTeam = WIMFactory.createTeam(teamNameInput);

        WIMRepository.addTeam(newTeam);
        return String.format(CommandConstants.TEAM_ADDED_SUCCESSFULLY,
                newTeam.getTeamName());
    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }

    private void validateInput(String nameInput) {
        Validator.validateNotNull(nameInput, TEAM_NAME);
        if (Validator.isInCollection(nameInput,
                WIMRepository.getTeams().values()))
            throw new IllegalArgumentException(String.format(UNIQUE_IN_THE_APP_ERR,
                    nameInput));
    }
}
