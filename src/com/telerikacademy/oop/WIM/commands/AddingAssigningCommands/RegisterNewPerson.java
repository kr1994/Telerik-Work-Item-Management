package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.commands._constants.CommandConstants;
import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.HistoryLogger;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.core.providers.HistoryLoggerImpl;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Person;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.PERSON_NAME;
import static com.telerikacademy.oop.WIM.models.common.Constants.UNIQUE_IN_THE_APP_ERR;

import java.util.List;

public class RegisterNewPerson implements Command {


    private static final int PARAMETERS_MIN_SIZE = 1;

    private final String PARAMETERS = "Parameters in " + "RegisterNewPerson";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;
    private final HistoryLogger logger;

    public RegisterNewPerson(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
        logger = new HistoryLoggerImpl(WIMFactory, WIMRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        String personNameInput = parameters.get(0);
        validateInput(personNameInput);

        Person newPerson = WIMFactory.registerNewPerson(personNameInput);
        WIMRepository.addPerson(newPerson);

        return logger.takeLog(newPerson, String
                .format(CommandConstants.PERSON_ADDED_SUCCESSFULLY,
                newPerson.toString()));
    }

    private void validateInput(String nameInput) {
        Validator.validateNotNull(nameInput, PERSON_NAME);
        if (Validator.isInCollection(nameInput,
                WIMRepository.getPeople().values()))
            throw new IllegalArgumentException(String.format(UNIQUE_IN_THE_APP_ERR,
                    nameInput));
    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }
}
