package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.commands.ChangeWIState;
import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.AdminImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.BugImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.FeedBackImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.StoryImpl;
import com.telerikacademy.oop.WIM.models.PersonImpl;
import com.telerikacademy.oop.WIM.models.common.enums.*;
import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.items.Bug;
import com.telerikacademy.oop.WIM.models.contracts.items.FeedBack;
import com.telerikacademy.oop.WIM.models.contracts.items.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.CommonConstants.*;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;

public class AssignWorkToPersonTests {


    WIMRepository repository = new WIMRepositoryImpl();
    AssignWorkToPerson command = new AssignWorkToPerson(factory, repository);
    Bug bug;
    FeedBack fb;
    Story story;
    List<String> parameters;
    String expected, actual;


    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        command = new AssignWorkToPerson(factory, repository);
        story = new StoryImpl(LEN_10, LEN_15, StoryStatus.INPROGRESS.toString(), priority, person, StorySize.SMALL);
        fb = new FeedBackImpl(LEN_10, LEN_15, FeedBackStatus.UNSCHEDULED.toString(), 2);
        bug = new BugImpl(LEN_10, LEN_15, BugStatus.ACTIVE.toString(), priority, person, severity, steps);
        parameters = new ArrayList<>();
        person = new PersonImpl(LEN_10);
        repository.addPerson(new PersonImpl("testPewo"));
        repository.addPerson(person);
        repository.addPerson(new PersonImpl("Dobri"));
        team.addPerson(person);
        Admin admin = new AdminImpl(person.getName(), team.getTeamName());
        repository.setAuthor(admin);
    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_CorrectParameters() {
        repository.addFeedBack(fb);
        repository.addBug(bug);
        parameters = List.of(fb.getIdentity(), person.getName());
        expected = String.format(WORK_ASSIGNED_SUCCESSFULLY,
                fb.getID(),
                person.getName());

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_CorrectParameters2() {
        repository.addFeedBack(fb);
        repository.addBug(bug);
        parameters = List.of(bug.getIdentity(), "Dobri");
        expected = String.format(WORK_ASSIGNED_SUCCESSFULLY,
                bug.getID(),
                "Dobri");

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_WorkAlreadyAssigned() {
        repository.addFeedBack(fb);
        repository.addBug(bug);
        parameters = List.of(fb.getIdentity(), person.getName());
        person.assignWork(fb);
        expected = String.format(WORK_ALREADY_ASSIGNED,
                fb.getID(),
                person.toString());

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_WorkAlreadyNotInRepo() {
        repository.addStory(story);
        repository.addBug(bug);
        parameters = List.of(fb.getIdentity(), person.getName());
        expected = String.format(WORK_DELETED, fb.getID());

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void Command_Should_Throw_When_IncorrectInput() {
        parameters.add("Wrong");
        parameters.add("WrongAgain");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(parameters));

    }

    @Test
    public void Command_Should_Throw_When_LowSizeParameters() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(new ArrayList<>()));
    }

    @Test
    public void Command_Should_Throw_When_WrongParameters() {
        parameters.add("Wrong");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(parameters));
    }

}
