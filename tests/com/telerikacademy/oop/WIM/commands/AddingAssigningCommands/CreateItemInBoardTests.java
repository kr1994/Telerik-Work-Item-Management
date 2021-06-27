package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.core.factories.WIMFactoryImpl;
import com.telerikacademy.oop.WIM.models.AdminImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.BoardImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.WorkItemBase;
import com.telerikacademy.oop.WIM.models.PersonImpl;
import com.telerikacademy.oop.WIM.models.common.enums.BugStatus;
import com.telerikacademy.oop.WIM.models.common.enums.FeedBackStatus;
import com.telerikacademy.oop.WIM.models.common.enums.StorySize;
import com.telerikacademy.oop.WIM.models.common.enums.StoryStatus;
import com.telerikacademy.oop.WIM.models.contracts.Admin;
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
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;

public class CreateItemInBoardTests {

    public static final int numberOfCreatedItemsInTest = 1;
    private String com = "CreateItemInBoardTest";
    private String bug = "bug";
    private List<String> parameters = new ArrayList<>();
    private String title = LEN_10;
    private String description = LEN_55;
    private String teamName = LEN_10;
    private String boardName = LEN_10;
    private CreateItemInBoard command = new CreateItemInBoard(factory, repository);
    private int rating = 5;
    Admin admin;

    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        factory = new WIMFactoryImpl();
        board = new BoardImpl(LEN_10);
        person = new PersonImpl(LEN_10);

        team.addBoard(board);
        repository.addPerson(person);
        repository.addTeam(team);
        command = new CreateItemInBoard(factory, repository);
        admin = new AdminImpl(person.getName(),teamName);
        repository.setAuthor(admin);
    }

    @Test
    public void Command_Should_CreateBug_Whit_CorrectInput() {
        {
            parameters.add(teamName);
            parameters.add(boardName);
            /*0*/
            parameters.add(bug);
            /*1*/
            parameters.add(title);
            /*2*/
            parameters.add(description);
            /*3*/
            parameters.add(BugStatus.ACTIVE.toString());
            /*4*/
            parameters.add(priority.toString());
            /*5*/
            parameters.add(person.getName());
            /*6*/
            parameters.add(severity.toString());
            /*7-9*/
            parameters.addAll(steps);
        }
        String expected = String.format(ITEM_ADDED_TO_TEAM_BOARD,
                BUG,
                WorkItemBase.getNumberOfItems() + numberOfCreatedItemsInTest,
                team.getTeamName(),
                team.getTeamBoards().get(0).getBoardName());

        String actual = command.execute(parameters);
        Bug bug = repository.getBugs().get(0);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(title, bug.getTitle());
        Assertions.assertEquals(description, bug.getDescription());
        Assertions.assertEquals(person, bug.getAssignee());
        Assertions.assertEquals(BugStatus.ACTIVE.toString(), bug.getStatus());
        Assertions.assertEquals(priority, bug.getPriority());
        Assertions.assertEquals(severity, bug.getSeverity());
        Assertions.assertEquals(steps.get(0), bug.getSteps().get(0));
        Assertions.assertEquals(steps.get(steps.size() - 1),
                bug.getSteps().get(steps.size() - 1));
        Assertions.assertEquals(steps.size(), bug.getSteps().size());
        Assertions.assertEquals(WorkItemBase.getNumberOfItems(),
                bug.getID());
    }

    @Test
    public void Command_Should_CreateStory_Whit_CorrectInput() {
        {
            parameters.add(teamName);
            parameters.add(boardName);
            /*0*/
            parameters.add("story");
            /*1*/
            parameters.add(title);
            /*2*/
            parameters.add(description);
            /*3*/
            parameters.add(StoryStatus.DONE.toString());
            /*4*/
            parameters.add(priority.toString());
            /*5*/
            parameters.add(person.getName());
            /*6*/
            parameters.add(StorySize.LARGE.toString());
        }
        String expected = String.format(ITEM_ADDED_TO_TEAM_BOARD,
                STORY,
                WorkItemBase.getNumberOfItems() + numberOfCreatedItemsInTest,
                team.getTeamName(),
                team.getTeamBoards().get(0).getBoardName());

        String actual = command.execute(parameters);
        Story story = repository.getStory().get(0);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(title, story.getTitle());
        Assertions.assertEquals(description, story.getDescription());
        Assertions.assertEquals(person, story.getAssignee());
        Assertions.assertEquals(StoryStatus.DONE.toString(), story.getStatus());
        Assertions.assertEquals(priority, story.getPriority());
        Assertions.assertEquals(StorySize.LARGE, story.getSize());
        Assertions.assertEquals(WorkItemBase.getNumberOfItems(),
                story.getID());
    }

    @Test
    public void Command_Should_CreateFeedback_Whit_CorrectInput() {
        {
            parameters.add(teamName);
            parameters.add(boardName);
            /*0*/
            parameters.add("feedback");
            /*1*/
            parameters.add(title);
            /*2*/
            parameters.add(description);
            /*3*/
            parameters.add(FeedBackStatus.SCHEDULED.toString());
            /*4*/
            parameters.add(rating + "");
        }
        String expected = String.format(ITEM_ADDED_TO_TEAM_BOARD,
                FEEDBACK,
                WorkItemBase.getNumberOfItems() + numberOfCreatedItemsInTest,
                team.getTeamName(),
                team.getTeamBoards().get(0).getBoardName());

        String actual = command.execute(parameters);
        FeedBack feedBack = repository.getFeedBack().get(0);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(title, feedBack.getTitle());
        Assertions.assertEquals(description, feedBack.getDescription());
        Assertions.assertEquals(FeedBackStatus.SCHEDULED.toString(), feedBack.getStatus());
        Assertions.assertEquals(rating, feedBack.getRating());
        Assertions.assertEquals(WorkItemBase.getNumberOfItems(),
                feedBack.getID());
    }

    @Test
    public void Command_Should_Throw_Whit_WrongSize() {
        {
            parameters.add(teamName);
            parameters.add(boardName);
            /*0*/
            parameters.add("story");
            /*1*/
            parameters.add(title);
            /*2*/
            parameters.add(description);
            /*3*/
            parameters.add(StoryStatus.DONE.toString());
            /*4*/
            parameters.add(priority.toString());
            /*5*/
            parameters.add(person.getName());
            /*6*/
            parameters.add("wrong");
        }

        Assertions.assertThrows(IllegalArgumentException.class,
                ()->  command.execute(parameters));
    }


    @Test
    public void Command_Should_Throw_Whit_WrongSeverity() {
        {
            parameters.add(teamName);
            parameters.add(boardName);
            /*0*/
            parameters.add(bug);
            /*1*/
            parameters.add(title);
            /*2*/
            parameters.add(description);
            /*3*/
            parameters.add(BugStatus.ACTIVE.toString());
            /*4*/
            parameters.add(priority.toString());
            /*5*/
            parameters.add(person.getName());
            /*6*/
            parameters.add("wrong");
            /*7-9*/
            parameters.addAll(steps);
        }

        Assertions.assertThrows(IllegalArgumentException.class,
                ()->  command.execute(parameters));
    }

    @Test
    public void Command_Should_Throw_Whit_WrongAssignee() {
        {
            parameters.add(teamName);
            parameters.add(boardName);
            /*0*/
            parameters.add(bug);
            /*1*/
            parameters.add(title);
            /*2*/
            parameters.add(description);
            /*3*/
            parameters.add(BugStatus.ACTIVE.toString());
            /*4*/
            parameters.add(priority.toString());
            /*5*/
            parameters.add("wrong");
            /*6*/
            parameters.add(severity.toString());
            /*7-9*/
            parameters.addAll(steps);
        }

        Assertions.assertThrows(IllegalArgumentException.class,
                ()->  command.execute(parameters));
    }

    @Test
    public void Command_Should_Throw_Whit_NullAssignee() {
        {
            parameters.add(teamName);
            parameters.add(boardName);
            /*0*/
            parameters.add(bug);
            /*1*/
            parameters.add(title);
            /*2*/
            parameters.add(description);
            /*3*/
            parameters.add(BugStatus.ACTIVE.toString());
            /*4*/
            parameters.add(priority.toString());
            /*5*/
            parameters.add(null);
            /*6*/
            parameters.add(severity.toString());
            /*7-9*/
            parameters.addAll(steps);
        }

        Assertions.assertThrows(IllegalArgumentException.class,
                ()->  command.execute(parameters));
    }

    @Test
    public void Command_Should_Throw_Whit_WrongPriority() {
        {
            parameters.add(teamName);
            parameters.add(boardName);
            /*0*/
            parameters.add(bug);
            /*1*/
            parameters.add(title);
            /*2*/
            parameters.add(description);
            /*3*/
            parameters.add(BugStatus.ACTIVE.toString());
            /*4*/
            parameters.add("wrong");
            /*5*/
            parameters.add(person.getName());
            /*6*/
            parameters.add(severity.toString());
            /*7-9*/
            parameters.addAll(steps);
        }

        Assertions.assertThrows(IllegalArgumentException.class,
                ()->  command.execute(parameters));
    }

    @Test
    public void Command_Should_Throw_Whit_WrongRating() {
        {
            parameters.add(teamName);
            parameters.add(boardName);
            /*0*/
            parameters.add("feedback");
            /*1*/
            parameters.add(title);
            /*2*/
            parameters.add(description);
            /*3*/
            parameters.add(FeedBackStatus.SCHEDULED.toString());
            /*4*/
            parameters.add("wrong");
        }

       Assertions.assertThrows(IllegalArgumentException.class,
               ()->  command.execute(parameters));
    }

    @Test
    public void Command_Should_Throw_Whit_IncorrectWIType() {
        {
            parameters.add(teamName);
            parameters.add(boardName);
            /*0*/
            parameters.add("wrong");
            /*1*/
            parameters.add(title);
            /*2*/
            parameters.add(description);
            /*3*/
            parameters.add(FeedBackStatus.SCHEDULED.toString());
            /*4*/
            parameters.add(rating + "");
        }

        Assertions.assertThrows(IllegalArgumentException.class,
                ()->  command.execute(parameters));
    }

    @Test
    public void Command_Should_Throw_Whit_WrongBoard() {
        {
            parameters.add(teamName);
            parameters.add("wrongBoard");
            /*0*/
            parameters.add("feedback");
            /*1*/
            parameters.add(title);
            /*2*/
            parameters.add(description);
            /*3*/
            parameters.add(FeedBackStatus.SCHEDULED.toString());
            /*4*/
            parameters.add(rating + "");
        }


        Assertions.assertThrows(IllegalArgumentException.class,
                ()->  command.execute(parameters));
    }
}
