package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.commands._constants.CommandConstants;
import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.core.factories.WIMFactoryImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.BugImpl;
import com.telerikacademy.oop.WIM.models.TeamImpl;
import com.telerikacademy.oop.WIM.models.common.enums.BugStatus;
import com.telerikacademy.oop.WIM.models.contracts.items.Bug;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.CommonConstants.*;
import static com.telerikacademy.oop.WIM.CommonConstants.steps;
import static com.telerikacademy.oop.WIM.models.common.Utils.capitaliseFirst;

public class AddCommentTests {

    private WIMFactory factory;
    private WIMRepository repo;
    private Bug bug;
    Command command;
    String expected, actual, commentStr;


    @BeforeEach
    public void bootUp() {
        factory = new WIMFactoryImpl();
        repo = new WIMRepositoryImpl();
        team = new TeamImpl(LEN_10);
        bug = new BugImpl(LEN_10, LEN_10, BugStatus.ACTIVE.toString(),
                priority, person, severity, steps);

        repo.addTeam(team);
        repo.addPerson(admin);
        repo.setAuthor(admin);
        repo.addBug(bug);
        team.addBoard(board);
        board.assignWork(bug);
        person.assignWork(bug);
        command = new AddComment(factory, repo);
    }

    @Test
    public void Command_Should_AddComment_Whit_CorrectInput() {
        commentStr = "This is a test comment.";
        expected = String.format(CommandConstants.COMMENT_ADDED_SUCCESSFULLY,
                admin.toString(),
                bug.getID());
        String toString = String.format("%s  wrote: '%s'%n",
                admin.toString(),
                commentStr)
                .trim();

        actual = command.execute(List.of(bug.getIdentity(), commentStr));

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(1, bug.getComments().size());
        Assertions.assertEquals(toString, bug.getCommentsToString());

    }

    @Test
    public void Command_Should_Throw_Whit_NullContent() {
        List<String> parameters = new ArrayList<>();
        parameters.add(bug.getIdentity());
        parameters.add(null);

        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> command.execute(parameters));
    }

    @Test
    public void Command_Should_Throw_Whit_NullParameters() {

        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> command.execute(null));
    }

    @Test
    public void Command_Should_Throw_Whit_WrongItemID() {
        commentStr = "This is a test comment.";
        List<String> parameters = new ArrayList<>();
        parameters.add("-1");
        parameters.add(commentStr);

        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> command.execute(parameters));
    }

    @Test
    public void Command_Should_Throw_Whit_WrongItemID2() {
        commentStr = "This is a test comment.";
        List<String> parameters = new ArrayList<>();
        parameters.add("Wrong");
        parameters.add(commentStr);

        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> command.execute(parameters));
    }

}
