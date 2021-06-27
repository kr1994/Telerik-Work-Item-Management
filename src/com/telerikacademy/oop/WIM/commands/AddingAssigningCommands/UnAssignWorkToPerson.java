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

public class UnAssignWorkToPerson implements Command {

    private static final int PARAMETERS_MIN_SIZE = 2;

    private final String PARAMETERS = "Parameters in " + "UnAssignWorkToPerson";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;
    private final HistoryLogger logger;

    public UnAssignWorkToPerson(WIMFactory WIMFactory, WIMRepository WIMRepository) {
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
        String personName = parameters.get(1);
        return implement(workID, personName);
    }

    private String implement(int workID, String personName) {
        validateInput(workID, personName);

        Person person = WIMRepository.getPeople()
                .get(personName.toLowerCase());
        WorkItem work = WIMRepository.getWorkByID(workID);


        if (person.unAssignWork(work)) {
            if (isWorkAssignable(workID, WIMRepository.getTasks())) {
                Assignable task = WIMRepository.getAssignableByID(workID);
                if (task.getAssignee().getName()
                        .equalsIgnoreCase(person.getName()))
                    task.assignAssignee(null);
            }
            return logger.takeLog(work, person, String
                    .format(WORK_UNASSIGNED_SUCCESSFULLY,
                            workID,
                            person.toString()));
        } else
            return String.format(WORK_ITEM_NOT_FOUND_IN_PERSON,
                    workID,
                    person.toString());
    }

    private void validateInput(int workID, String personName) {
        Validator.validatePerson(personName, WIMRepository.getPeople());
        Validator.validateWorkID(workID, WIMRepository.getWork());
    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }
}
