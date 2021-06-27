package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.HistoryLogger;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.core.providers.HistoryLoggerImpl;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.actions.Assignable;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;

import java.util.List;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;
import static com.telerikacademy.oop.WIM.models.common.Validator.isWorkAssignable;

public class AssignWorkToPerson implements Command {

    private static final int PARAMETERS_MIN_SIZE = 2;

    private final String PARAMETERS = "Parameters in " + "AssignWorkToPerson";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;
    private final HistoryLogger logger;

    public AssignWorkToPerson(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
        logger = new HistoryLoggerImpl(WIMFactory, WIMRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        int workID;
        String personName;
        try {
            workID = Integer.parseInt(parameters.get(0));
            personName = parameters.get(1);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(THE_FIRST_PARAMETER_INTEGER);
        }
        return implement(workID, personName);
    }

    private String implement(int workID, String personNameInput) {
        validateInput(workID, personNameInput);

        Person person = WIMRepository.getPeople().get(personNameInput.trim().toLowerCase());
        WorkItem work = WIMRepository.getWorkByID(workID);

        if (work != null) {
            if (person.assignWork(work)) {
                if (isWorkAssignable(workID, WIMRepository.getTasks())) {
                    Assignable task = WIMRepository.getAssignableByID(workID);
                    task.assignAssignee(person);
                }
                return logger.takeLog(work, person, String.format(WORK_ASSIGNED_SUCCESSFULLY,
                        workID,
                        person.toString()));
            }
            return String.format(WORK_ALREADY_ASSIGNED,
                    work.getID(),
                    person.toString());
        }
        return String.format(WORK_DELETED, workID);
    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }

    private void validateInput(int workID, String personNameInput) {
        Validator.validatePerson(personNameInput, WIMRepository.getPeople());
        Validator.validateWorkID(workID, WIMRepository.getWork());
    }
}
