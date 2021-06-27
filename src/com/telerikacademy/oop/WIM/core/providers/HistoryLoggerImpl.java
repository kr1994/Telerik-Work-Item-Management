package com.telerikacademy.oop.WIM.core.providers;

import com.telerikacademy.oop.WIM.core.contracts.HistoryLogger;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.actions.HistoryLogged;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;
import com.telerikacademy.oop.WIM.models.contracts.items.History;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.LOGGED_OUT;
import static com.telerikacademy.oop.WIM.models.common.Constants.HISTORY_LOG;

public class HistoryLoggerImpl implements HistoryLogger {

    private final WIMFactory factory;
    private final WIMRepository repository;

    public HistoryLoggerImpl(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        factory = WIMFactory;
        repository = WIMRepository;
    }


    @Override
    public String takeLogPerson(String message) {
        Validator.validateNotNull(message, HISTORY_LOG);

        History event = factory.createHistory(repository.getAuthor(), message);
        Person author = repository.getPeople().get(repository.getAuthor().getName());

        writeHistory(event, author);

        if (message.equalsIgnoreCase(LOGGED_OUT)) {
            repository.getAuthor().logOut();
            repository.setAuthor(null);
        }

        return message;
    }

    @Override
    public String takeLog(WorkItem item, String message) {
        Validator.validateNotNull(message, HISTORY_LOG);

        History event = factory.createHistory(repository.getAuthor(), message);

        writeHistory(event, item);
        return message;
    }

    @Override
    public String takeLog(Board board, String message) {
        Validator.validateNotNull(message, HISTORY_LOG);

        History event = factory.createHistory(repository.getAuthor(), message);

        writeHistory(event, board);

        return message;
    }

    @Override
    public String takeLog(Person person, String message) {
        Validator.validateNotNull(message, HISTORY_LOG);

        if(!person.getName().equalsIgnoreCase(repository.getAuthor().getName())){
            History event = factory.createHistory(repository.getAuthor(), message);
            writeHistory(event, person);
        }
        return message;
    }

    @Override
    public String takeLog(WorkItem item, Person person, String message) {
        takeLog(item,message);

        if(!person.getName().equalsIgnoreCase(repository.getAuthor().getName())){
           takeLog(person, message);
        }
        return message;
    }

    @Override
    public String takeLog(WorkItem item, Board board, String message) {
        takeLog(item,message);
        return takeLog(board,message);
    }

    private void writeHistory(History event, HistoryLogged obj) {
        obj.addHistory(event);
    }

}
