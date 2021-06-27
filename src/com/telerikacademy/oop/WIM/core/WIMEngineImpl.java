package com.telerikacademy.oop.WIM.core;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.factories.CommandFactoryImpl;
import com.telerikacademy.oop.WIM.core.factories.WIMFactoryImpl;
import com.telerikacademy.oop.WIM.core.providers.CommandParserImpl;
import com.telerikacademy.oop.WIM.core.providers.ConsoleReader;
import com.telerikacademy.oop.WIM.core.providers.ConsoleWriter;
import com.telerikacademy.oop.WIM.core.contracts.*;
import com.telerikacademy.oop.WIM.core.providers.HistoryLoggerImpl;

import java.util.List;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;

public class WIMEngineImpl implements Engine {


    private final WIMFactory WIMFactory;
    private final Writer writer;
    private final Reader reader;
    private final CommandParser commandParser;
    private final WIMRepository WIMRepository;
    private final CommandFactory commandFactory;
    private final HistoryLogger historyLogger;

    public WIMEngineImpl() {
        WIMFactory = new WIMFactoryImpl();
        writer = new ConsoleWriter();
        reader = new ConsoleReader();
        commandParser = new CommandParserImpl();
        WIMRepository = new WIMRepositoryImpl();
        commandFactory = new CommandFactoryImpl();
        historyLogger = new HistoryLoggerImpl(WIMFactory, WIMRepository);
    }

    public void start() {

        while (true) {
            try {
                String commandAsString = reader.readLine();
                if (commandAsString.equalsIgnoreCase(TERMINATION_COMMAND)) {
                    if (WIMRepository.getAuthor() != null)
                        authorLogout();
                    break;
                }
                processCommand(commandAsString);

            } catch (Exception ex) {
                writer.writeLine(ex.getMessage() != null && !ex.getMessage().isEmpty() ? ex.getMessage() : ex.toString());
                writer.writeLine(REPORT_SEPARATOR);
            }
        }
    }

    private void processCommand(String commandAsString) {
        if (commandAsString == null || commandAsString.trim().equals("")) {
            throw new IllegalArgumentException(COMMAND_CANNOT_BE_NULL_OR_EMPTY);
        }

        String commandTypeAsString = commandParser.parseCommand(commandAsString);
        checkPermissionToExecute(commandTypeAsString);

        Command command = commandFactory.createCommand(commandTypeAsString, WIMFactory, WIMRepository);
        List<String> parameters = commandParser.parseParameters(commandAsString);
        try {
            String executionResult = command.execute(parameters);

            writer.writeLine(historyLogger.takeLogPerson(executionResult));
            writer.writeLine(REPORT_SEPARATOR);
        } catch (IllegalArgumentException e) {
            writer.writeLine(historyLogger.takeLogPerson(e.getMessage()));
            writer.writeLine(REPORT_SEPARATOR);
        }
    }

    private void checkPermissionToExecute(String commandTypeAsString) {
        if (!(commandTypeAsString.equalsIgnoreCase(LOGIN))) {
            if (WIMRepository.getAuthor() == null) {
                throw new IllegalArgumentException(USER_NOT_LOGGED);
            }
        }
    }

    private void authorLogout() {
        WIMRepository.getAuthor().logOut();
        WIMRepository.setAuthor(null);
    }
}
