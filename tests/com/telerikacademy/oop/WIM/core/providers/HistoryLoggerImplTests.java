package com.telerikacademy.oop.WIM.core.providers;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.core.contracts.CommandFactory;
import com.telerikacademy.oop.WIM.core.contracts.HistoryLogger;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.core.factories.CommandFactoryImpl;
import com.telerikacademy.oop.WIM.core.factories.WIMFactoryImpl;
import com.telerikacademy.oop.WIM.models.AdminImpl;
import com.telerikacademy.oop.WIM.models.PersonImpl;
import com.telerikacademy.oop.WIM.models.TeamImpl;
import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.items.Bug;
import com.telerikacademy.oop.WIM.models.contracts.items.Comment;
import com.telerikacademy.oop.WIM.models.contracts.items.FeedBack;
import com.telerikacademy.oop.WIM.models.contracts.items.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.USER_LOGGED_IN;

public class HistoryLoggerImplTests {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss");
    WIMRepository repository;
    WIMFactory factory;
    CommandFactory cf;
    HistoryLogger logger;
    Command command;
    Person person;
    Bug bug;
    Story story;
    FeedBack fb;
    String expected, actual, commandStr;
    Comment comment;


    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        factory = new WIMFactoryImpl();
        logger = new HistoryLoggerImpl(factory, repository);
        cf = new CommandFactoryImpl();
    }

    @Test
    public void HistoryLogger_Should_LoggLogin_correctly() {
        String date = "[" + LocalDateTime.now().format(formatter) + "]";
        date += " Dobri " + String.format(USER_LOGGED_IN, "Dobri");

        commandStr = "login";
        command = cf.createCommand(commandStr, factory, repository);
        String result = command.execute(List.of("Dobri", "Myteam"));
        logger.takeLogPerson(result);

        commandStr = "ShowTeamActivity";
        command = cf.createCommand(commandStr, factory, repository);
        expected = "myteam" + System.lineSeparator()
                + "Team myteam history by time:" + System.lineSeparator()
                + date;

        actual = command.execute(List.of("MyTeam"));

        Assertions.assertEquals(expected, actual);
    }
}