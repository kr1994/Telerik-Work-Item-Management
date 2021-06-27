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

public class AssignWorkToTeamTests {


    WIMRepository repository;
    AssignWorkToTeam command;
    Bug bug;
    FeedBack fb;
    Story story;
    List<String> parameters;
    String expected, actual;
    Team team;


    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        command = new AssignWorkToTeam(factory, repository);
        story = new StoryImpl(LEN_10, LEN_15, StoryStatus.INPROGRESS.toString(), priority, person, StorySize.SMALL);
        fb = new FeedBackImpl(LEN_10, LEN_15, FeedBackStatus.UNSCHEDULED.toString(), 2);
        bug = new BugImpl(LEN_10, LEN_15, BugStatus.ACTIVE.toString(), priority, person, severity, steps);
        parameters = new ArrayList<>();
        team = new TeamImpl("MyTeam");

        Person person1 = new PersonImpl(LEN_10);
        repository.addPerson(person1);
        team.addPerson(person1);

        person1 = new PersonImpl("testPesho");
        repository.addPerson(person1);
        team.addPerson(person1);

        person = new PersonImpl("dobri");
        repository.addPerson(person);
        team.addPerson(person);

        Admin admin = new AdminImpl(person.getName(), team.getTeamName());
        repository.setAuthor(admin);

        Board board1 = new BoardImpl("testBoard1");
        Board board2 = new BoardImpl("testBoard2");
        team.addBoard(board1);
        team.addBoard(board2);
        repository.addTeam(team);

    }

    @Test
    public void Command_Should_AssignFeedbackToTeam_When_CorrectParameters() {
        repository.addFeedBack(fb);
        repository.addBug(bug);
        parameters = List.of(fb.getIdentity(), team.getTeamName());
        expected = String.format(CommandConstants.WORK_ADDED_TO_TEAM_SUCCESSFULLY,
                fb.getID(),
                team.getTeamName());

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(fb.getID(),
                team.getTeamMembers().get(0).getWork().get(0).getID());
        Assertions.assertEquals(fb.getID(),
                team.getTeamMembers().get(1).getWork().get(0).getID());
        Assertions.assertEquals(fb.getID(),
                team.getTeamMembers().get(2).getWork().get(0).getID());
        Assertions.assertEquals(fb.getID(),
                team.getTeamBoards().get(0).getWork().get(0).getID());
        Assertions.assertEquals(0,
                team.getTeamBoards().get(1).getWork().size());

    }

    @Test
    public void Command_Should_AssignBugToTeam_When_CorrectParameters() {
        repository.addFeedBack(fb);
        repository.addBug(bug);
        parameters = List.of(bug.getIdentity(), team.getTeamName());
        expected = String.format(CommandConstants.WORK_ADDED_TO_TEAM_SUCCESSFULLY,
                bug.getID(),
                team.getTeamName());

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(bug.getID(),
                team.getTeamMembers().get(0).getWork().get(0).getID());
        Assertions.assertEquals(bug.getID(),
                team.getTeamMembers().get(1).getWork().get(0).getID());
        Assertions.assertEquals(bug.getID(),
                team.getTeamMembers().get(2).getWork().get(0).getID());
        Assertions.assertEquals(bug.getID(),
                team.getTeamBoards().get(0).getWork().get(0).getID());
        Assertions.assertEquals(0,
                team.getTeamBoards().get(1).getWork().size());
        Assertions.assertEquals(bug.getAssignee(),
                team.getTeamMembers().get(0));
    }

    @Test
    public void Command_Should_AssignStoryToTeam_When_CorrectParameters() {
        repository.addFeedBack(fb);
        repository.addStory(story);
        parameters = List.of(story.getIdentity(), team.getTeamName());
        expected = String.format(CommandConstants.WORK_ADDED_TO_TEAM_SUCCESSFULLY,
                story.getID(),
                team.getTeamName());

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(story.getID(),
                team.getTeamMembers().get(0).getWork().get(0).getID());
        Assertions.assertEquals(story.getID(),
                team.getTeamMembers().get(1).getWork().get(0).getID());
        Assertions.assertEquals(story.getID(),
                team.getTeamMembers().get(2).getWork().get(0).getID());
        Assertions.assertEquals(story.getID(),
                team.getTeamBoards().get(0).getWork().get(0).getID());
        Assertions.assertEquals(0,
                team.getTeamBoards().get(1).getWork().size());
        Assertions.assertEquals(story.getAssignee(),
                team.getTeamMembers().get(0));
    }

    @Test
    public void Command_Should_Throw_When_IncorrectParameters() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> command.execute(List.of("Wrong","myTeam")));
    }

    @Test
    public void Command_Should_Throw_When_IncorrectSizeParameters() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> command.execute(List.of("Wrong")));
    }

    @Test
    public void Command_Should_Throw_When_IncorrectSizeParameters2() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> command.execute(new ArrayList<>()));
    }

    @Test
    public void Command_Should_Throw_When_IncorrectTeam() {
        Team emptyTeam = new TeamImpl("Empty team");
        repository.addTeam(emptyTeam);
        repository.addStory(story);

        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> command.execute(List.of(story.getIdentity(), emptyTeam.getTeamName())));
    }

    @Test
    public void Command_Should_Throw_When_NullParameters() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> command.execute(null));
    }

}
