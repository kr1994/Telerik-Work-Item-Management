package com.telerikacademy.oop.WIM.commands;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.Team;

import java.util.List;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;
import static com.telerikacademy.oop.WIM.models.common.Constants.*;

public class Login implements Command {

    private static final int PARAMETERS_MIN_SIZE = 2;

    private final String PARAMETERS = "Parameters in " + "Login";

    private final WIMFactory factory;
    private final WIMRepository repository;


    public Login(WIMFactory factory, WIMRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        String username = parameters.get(0);
        String teamName = parameters.get(1);

        return login(username, teamName);
    }

    private String login(String username, String teamName) {
        Validator.validateNotNull(teamName, TEAM_NAME);
        Validator.validateNotNull(username, PERSON_NAME);
        String usernameValid = Validator.trimmedStringInBounds(username, PERSON_NAME, USERNAME_MIN_SIZE, USERNAME_MAX_SIZE);
        String teamNameValid = Validator.trimmedStringInBounds(teamName, TEAM_NAME, TEAM_NAME_MIN_SIZE, TEAM_NAME_MAX_SIZE);

        if (repository.getAuthor() != null) {
            return String.format(USER_LOGGED_IN_ALREADY, repository.getAuthor());
        }
        if (repository.getPeople().size() == 0) {
            return firstTimeBootUp(usernameValid, teamNameValid);
        }
        validateInput(usernameValid, teamNameValid);

        Admin newAdmin = factory.createNewAdmin(usernameValid, teamNameValid);

        repository.setAuthor(newAdmin);

        return String.format(USER_LOGGED_IN, newAdmin.toString());

    }

    private String firstTimeBootUp(String usernameValid, String teamNameValid) {
        Team firstTeam = factory.createTeam(teamNameValid);
        Admin firstAdmin = factory.createNewAdmin(usernameValid, teamNameValid);
        firstTeam.addPerson(firstAdmin);

        repository.addTeam(firstTeam);
        repository.addPerson(firstAdmin);
        repository.setAuthor(firstAdmin);

        return String.format(USER_LOGGED_IN, firstAdmin.toString());
    }

    private void validateInput(String usernameValid, String teamNameValid) {
        Validator.validateTeam(teamNameValid, repository.getTeams());
        Validator.validatePerson(usernameValid, repository.getPeople());
        validatePersonInTeam(usernameValid,teamNameValid);
    }

    private void validatePersonInTeam(String usernameValid, String teamNameValid) {
        if (!repository.getTeams().get(teamNameValid).getTeamMembers()
                .contains( repository.getPeople().get(usernameValid)))
            throw new IllegalArgumentException("No such member in team");

    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }

}
