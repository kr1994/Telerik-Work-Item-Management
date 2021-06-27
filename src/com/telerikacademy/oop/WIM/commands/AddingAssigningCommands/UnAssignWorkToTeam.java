package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.commands._constants.CommandConstants;
import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.HistoryLogger;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.core.providers.HistoryLoggerImpl;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.actions.WorkAssignable;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;

import java.util.List;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;

public class UnAssignWorkToTeam implements Command {

    private static final int PARAMETERS_MIN_SIZE = 2;

    private final String PARAMETERS = "Parameters in " + "UnAssignWorkToTeam";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;
    private final UnAssignWorkToPerson unAssignWork;
    private final HistoryLogger logger;


    public UnAssignWorkToTeam(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
        unAssignWork = new UnAssignWorkToPerson(WIMFactory, WIMRepository);
        logger = new HistoryLoggerImpl(WIMFactory, WIMRepository);
    }


    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        int workId;
        try {
            workId = Integer.parseInt(parameters.get(0));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(THE_FIRST_PARAMETER_INTEGER);
        }
        String teamName = parameters.get(1);
        return implement(workId, teamName);
    }

    private String implement(int workID, String teamNameInput) {
        validateInput(workID, teamNameInput);

        WorkItem workToLeave = WIMRepository.getWorkByID(workID);
        Team team = WIMRepository.getTeams()
                .get(teamNameInput.toLowerCase());
        validateInput(workToLeave, team);

        unAssignWorkFromTeam(team, workToLeave);

        return String.format(CommandConstants.WORK_UNASSIGNED_SUCCESSFULLY,
                workID,
                team.getTeamName());
    }

    private void unAssignWorkFromTeam(Team team, WorkItem workToLeave) {
        team.getTeamMembers()
                .forEach(m -> unAssignWork.execute(
                        List.of(workToLeave.getIdentity(), m.getName())));
        team.getTeamBoards()
                .forEach(b -> {
                    if (b.unAssignWork(workToLeave))
                        logger.takeLog(b, String.format(WORK_UNASSIGNED_SUCCESSFULLY,
                                workToLeave.getID(),
                                b.getBoardName()));
                });
    }

    private void validateInput(int workID, String teamName) {
        Validator.validateWorkID(workID, WIMRepository.getWork());
        Validator.validateTeam(teamName, WIMRepository.getTeams());


    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }

    private void validateInput(WorkItem workToLeave, Team team) {
        if (team.getWorkAssignable().stream()
                .map(WorkAssignable::getWork)
                .noneMatch(workItems -> workItems.contains(workToLeave)))
            throw new IllegalArgumentException(String.format(WORK_ITEM_NOT_FOUND_IN_TEAM,
                    workToLeave.getID(),
                    team.getTeamName()));
    }

}