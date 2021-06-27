package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.HistoryLogger;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.core.providers.HistoryLoggerImpl;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.Team;

import java.util.List;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;

public class AddPersonToTeam implements Command {

    private static final int PARAMETERS_MIN_SIZE = 2;

    private final String PARAMETERS = "Parameters in " + "AddPersonToTeam";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;
    private final HistoryLogger logger;


    public AddPersonToTeam(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
        logger = new HistoryLoggerImpl(WIMFactory, WIMRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        String personName = parameters.get(0);
        String teamName = parameters.get(1);

        return implement(personName, teamName);
    }

    private String implement(String personNameInput, String teamNameInput) {
        validateInput(personNameInput, teamNameInput);

        Person person = WIMRepository.getPeople().get(personNameInput.toLowerCase());
        Team team = WIMRepository.getTeams().get(teamNameInput.toLowerCase());

        if (team.addPerson(person))
            return logger.takeLog(person, String
                    .format(MEMBER_ADDED_TO_TEAM_SUCCESSFULLY,
                            person.toString(),
                            team.getTeamName()));
        else
            return String.format(ALREADY_IN_TEAM,
                    person.toString(),
                    team.getTeamName());
    }


    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }

    private void validateInput(String personNameInput, String teamNameInput) {
        Validator.validatePerson(personNameInput, WIMRepository.getPeople());
        Validator.validateTeam(teamNameInput, WIMRepository.getTeams());
    }

}
