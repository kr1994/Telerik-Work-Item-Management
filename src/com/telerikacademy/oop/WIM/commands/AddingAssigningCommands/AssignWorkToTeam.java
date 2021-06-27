package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.commands._constants.CommandConstants;
import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.HistoryLogger;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.core.providers.HistoryLoggerImpl;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.actions.Assignable;
import com.telerikacademy.oop.WIM.models.contracts.actions.WorkAssignable;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;
import static com.telerikacademy.oop.WIM.models.common.Validator.isWorkAssignable;

public class AssignWorkToTeam implements Command {


    private static final int PARAMETERS_MIN_SIZE = 2;

    private final String PARAMETERS = "Parameters in " + "AssignWorkToTeam";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;
    private final HistoryLogger logger;

    public AssignWorkToTeam(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
        logger = new HistoryLoggerImpl(WIMFactory, WIMRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        int workID;
        try {
            workID = Integer.parseInt(parameters.get(0));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(THE_FIRST_PARAMETER_INTEGER);
        }
        String teamName = parameters.get(1);
        return implement(workID, teamName);

    }

    private String implement(int workID, String inputName) {
        validateInput(workID, inputName);

        Team team = WIMRepository.getTeams().get(inputName.toLowerCase());
        validateInput(team);
        assignWork(workID,team);

        String report = String.format(CommandConstants.WORK_ADDED_TO_TEAM_SUCCESSFULLY,
                workID,
                team.getTeamName());

        team.getTeamMembers().forEach(m -> logger.takeLog(m, report));
        return logger.takeLog(WIMRepository.getWorkByID(workID),
                report);
    }

    private void assignWork(int workID, Team team) {
        List<WorkAssignable> obj = new ArrayList<>(team.getTeamMembers());
        obj.add(team.getTeamBoards().get(0)); //write on one board

        WorkItem work = WIMRepository.getWorkByID(workID);
        obj.forEach(o -> o.assignWork(work));
        if (isWorkAssignable(workID, WIMRepository.getTasks())) {
            Assignable task = WIMRepository.getAssignableByID(workID);
            task.assignAssignee(team.getTeamMembers().get(0));
            // assign the field in the item to the first team member
        }
    }

    private void validateInput(Team team) {
        if (team.getTeamMembers().isEmpty() && team.getTeamBoards().isEmpty())
            throw new IllegalArgumentException(String.format(
                    NO_WORK_ASSIGNABLE,
                    team.getTeamName()));
    }

    private void validateInput(int workID, String teamName) {
        Validator.validateTeam(teamName, WIMRepository.getTeams());
        Validator.validateWorkID(workID, WIMRepository.getWork());
    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }
}