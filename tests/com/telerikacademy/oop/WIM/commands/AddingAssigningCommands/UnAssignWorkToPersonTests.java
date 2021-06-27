package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.AdminImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.BoardImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.BugImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.FeedBackImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.StoryImpl;
import com.telerikacademy.oop.WIM.models.PersonImpl;
import com.telerikacademy.oop.WIM.models.TeamImpl;
import com.telerikacademy.oop.WIM.models.common.enums.BugStatus;
import com.telerikacademy.oop.WIM.models.common.enums.FeedBackStatus;
import com.telerikacademy.oop.WIM.models.common.enums.StorySize;
import com.telerikacademy.oop.WIM.models.common.enums.StoryStatus;
import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;
import com.telerikacademy.oop.WIM.models.contracts.items.Bug;
import com.telerikacademy.oop.WIM.models.contracts.items.FeedBack;
import com.telerikacademy.oop.WIM.models.contracts.items.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.CommonConstants.*;
import static com.telerikacademy.oop.WIM.CommonConstants.person;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.WORK_ITEM_NOT_FOUND_IN_PERSON;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.WORK_UNASSIGNED_SUCCESSFULLY;

public class UnAssignWorkToPersonTests {


    WIMRepository repository;
    UnAssignWorkToPerson command;
    Bug bug;
    FeedBack fb;
    Story story;
    List<String> parameters;
    String expected, actual;
    Team team;


    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        command = new UnAssignWorkToPerson(factory, repository);
        story = new StoryImpl(LEN_10, LEN_15, StoryStatus.INPROGRESS.toString(), priority, person, StorySize.SMALL);
        repository.addStory(story);
        fb = new FeedBackImpl(LEN_10, LEN_15, FeedBackStatus.UNSCHEDULED.toString(), 2);
        repository.addFeedBack(fb);
        bug = new BugImpl(LEN_10, LEN_15, BugStatus.ACTIVE.toString(), priority, person, severity, steps);
        repository.addBug(bug);
        parameters = new ArrayList<>();
        team = new TeamImpl("MyTeam");

        Person person1 = new PersonImpl(LEN_10);
        person1.assignWork(bug);
        person1.assignWork(fb);
        person1.assignWork(story);
        repository.addPerson(person1);
        team.addPerson(person1);

        person1 = new PersonImpl("testPesho");
        person1.assignWork(bug);
        person1.assignWork(fb);
        person1.assignWork(story);
        repository.addPerson(person1);
        team.addPerson(person1);

        person1 = new PersonImpl("dobri");
        person1.assignWork(bug);
        person1.assignWork(fb);
        person1.assignWork(story);
        bug.assignAssignee(person1);
        story.assignAssignee(person1);
        repository.addPerson(person1);
        team.addPerson(person1);

        Admin admin = new AdminImpl(person1.getName(), team.getTeamName());
        repository.setAuthor(admin);

        Board board1 = new BoardImpl("testBoard1");
        Board board2 = new BoardImpl("testBoard2");
        team.addBoard(board1);
        team.addBoard(board2);
        repository.addTeam(team);
        board1.assignWork(bug);
        board1.assignWork(story);
        board1.assignWork(fb);

    }

    @Test
    public void Command_Should_UnAssignBugToPerson_When_CorrectParameters() {
        Person Tozi = team.getTeamMembers().get(1);
        parameters = List.of(bug.getIdentity(), Tozi.getName());
        expected = String.format(WORK_UNASSIGNED_SUCCESSFULLY,
                bug.getID(),
                Tozi.toString());

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Tozi.getWork().contains(bug));
        Assertions.assertNotEquals(bug.getAssignee(), Tozi);
        Assertions.assertTrue(Tozi.getWork().contains(fb));
        Assertions.assertTrue(Tozi.getWork().contains(story));
    }

    @Test
    public void Command_Should_UnAssignStoryToPerson_When_CorrectParameters() {
        Person Tozi = team.getTeamMembers().get(1);
        parameters = List.of(story.getIdentity(), Tozi.getName());
        expected = String.format(WORK_UNASSIGNED_SUCCESSFULLY,
                story.getID(),
                Tozi.toString());

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Tozi.getWork().contains(story));
        Assertions.assertNotEquals(story.getAssignee(), Tozi);
        Assertions.assertTrue(Tozi.getWork().contains(fb));
        Assertions.assertTrue(Tozi.getWork().contains(bug));
    }

    @Test
    public void Command_Should_UnAssignFeedbackToPerson_When_CorrectParameters() {
        Person Tozi = team.getTeamMembers().get(1);
        parameters = List.of(fb.getIdentity(), Tozi.getName());
        expected = String.format(WORK_UNASSIGNED_SUCCESSFULLY,
                fb.getID(),
                Tozi.toString());

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Tozi.getWork().contains(fb));
        Assertions.assertTrue(Tozi.getWork().contains(story));
        Assertions.assertTrue(Tozi.getWork().contains(bug));
    }

    @Test
    public void Command_Should_UnAssignBugToPersonAndPutNullAsAssignee_When_CorrectParameters() {
        Person Tozi = bug.getAssignee();
        parameters = List.of(bug.getIdentity(), Tozi.getName());
        expected = String.format(WORK_UNASSIGNED_SUCCESSFULLY,
                bug.getID(),
                Tozi.toString());

        actual = command.execute(parameters);

        Assertions.assertNull(bug.getAssignee());
        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Tozi.getWork().contains(bug));
        Assertions.assertTrue(Tozi.getWork().contains(fb));
        Assertions.assertTrue(Tozi.getWork().contains(story));
    }

    @Test
    public void Command_Should_UnAssignStoryToPersonAndPutNullAsAssignee_When_CorrectParameters() {
        Person Tozi = story.getAssignee();
        parameters = List.of(story.getIdentity(), Tozi.getName());
        expected = String.format(WORK_UNASSIGNED_SUCCESSFULLY,
                story.getID(),
                Tozi.toString());

        actual = command.execute(parameters);

        Assertions.assertNull(story.getAssignee());
        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(Tozi.getWork().contains(story));
        Assertions.assertTrue(Tozi.getWork().contains(fb));
        Assertions.assertTrue(Tozi.getWork().contains(bug));
    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_WorkNotOnPerson() {
        Person Tozi = story.getAssignee();
        Tozi.unAssignWork(story);
        parameters = List.of(story.getIdentity(), Tozi.getName());
        expected = String.format(WORK_ITEM_NOT_FOUND_IN_PERSON,
                story.getID(),
                Tozi.toString());

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void Command_Should_Throw_When_IncorrectWorkItemIndex() {
        parameters = List.of("Wrong", story.getIdentity());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(parameters));
    }

    @Test
    public void Command_Should_Throw_When_IncorrectInputSize() {
        parameters = List.of(story.getIdentity());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(parameters));
    }

    @Test
    public void Command_Should_Throw_When_IncorrectInput() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(List.of("Wrong")));
    }

    @Test
    public void Command_Should_Throw_When_IncorrectInput2() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(new ArrayList<>()));
    }

    @Test
    public void Command_Should_Throw_When_NullInput() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(null));
    }

}
