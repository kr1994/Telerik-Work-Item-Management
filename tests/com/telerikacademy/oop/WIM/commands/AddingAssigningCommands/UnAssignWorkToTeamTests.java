package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.commands._constants.CommandConstants;
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
import com.telerikacademy.oop.WIM.models.contracts.actions.WorkAssignable;
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
import static com.telerikacademy.oop.WIM.CommonConstants.LEN_10;

public class UnAssignWorkToTeamTests {

    WIMRepository repository;
    UnAssignWorkToTeam command;
    Bug bug;
    FeedBack fb;
    Story story;
    List<String> parameters;
    String expected, actual;
    Team team;


    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        command = new UnAssignWorkToTeam(factory, repository);
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
    public void Command_Should_UnAssignBugToTeam_When_CorrectParameters() {
        parameters = List.of(bug.getIdentity(), team.getTeamName());
        expected = String.format(CommandConstants.WORK_UNASSIGNED_SUCCESSFULLY,
                bug.getID(),
                team.getTeamName());

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(team.getTeamMembers().stream()
                .map(WorkAssignable::getWork)
                .anyMatch(workItems -> workItems.contains(bug)));
        Assertions.assertFalse(team.getTeamBoards().stream()
                .map(WorkAssignable::getWork)
                .anyMatch(workItems -> workItems.contains(bug)));
        Assertions.assertNull(bug.getAssignee());


    }

    @Test
    public void Command_Should_UnAssignStoryToTeam_When_CorrectParameters() {
        parameters = List.of(story.getIdentity(), team.getTeamName());
        expected = String.format(CommandConstants.WORK_UNASSIGNED_SUCCESSFULLY,
                story.getID(),
                team.getTeamName());

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(team.getTeamMembers().stream()
                .map(WorkAssignable::getWork)
                .anyMatch(workItems -> workItems.contains(story)));
        Assertions.assertFalse(team.getTeamBoards().stream()
                .map(WorkAssignable::getWork)
                .anyMatch(workItems -> workItems.contains(story)));
        Assertions.assertNull(story.getAssignee());


    }

    @Test
    public void Command_Should_UnAssignFeedbackToTeam_When_CorrectParameters() {
        parameters = List.of(fb.getIdentity(), team.getTeamName());
        expected = String.format(CommandConstants.WORK_UNASSIGNED_SUCCESSFULLY,
                fb.getID(),
                team.getTeamName());

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(team.getTeamMembers().stream()
                .map(WorkAssignable::getWork)
                .anyMatch(workItems -> workItems.contains(fb)));
        Assertions.assertFalse(team.getTeamBoards().stream()
                .map(WorkAssignable::getWork)
                .anyMatch(workItems -> workItems.contains(fb)));
    }

    @Test
    public void Command_Should_Throw_When_IncorrectWorkID() {
        parameters = List.of("Wrong", team.getTeamName());

        Assertions.assertThrows(IllegalArgumentException.class,
                ()->command.execute(parameters) );
    }

    @Test
    public void Command_Should_Throw_When_WorkNotInTeam() {
        Team newTeam = new TeamImpl("new team");
        newTeam.addPerson(new PersonImpl("Jon Joy"));
        repository.addTeam(newTeam);

        parameters = List.of(fb.getIdentity(), newTeam.getTeamName());

        Assertions.assertThrows(IllegalArgumentException.class,
                ()->command.execute(parameters) );
    }

    @Test
    public void Command_Should_Throw_When_WrongInputSize() {
        parameters = List.of(fb.getIdentity());

        Assertions.assertThrows(IllegalArgumentException.class,
                ()->command.execute(parameters) );
    }

    @Test
    public void Command_Should_Throw_When_WrongInput() {
        parameters = List.of("Wrong");

        Assertions.assertThrows(IllegalArgumentException.class,
                ()->command.execute(parameters) );
    }

    @Test
    public void Command_Should_Throw_When_WrongInput2() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()->command.execute(new ArrayList<>()) );
    }

    @Test
    public void Command_Should_Throw_When_NullInput() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()->command.execute(null) );
    }





}
