package com.telerikacademy.oop.WIM.models.ItemsImpl.work;

import com.telerikacademy.oop.WIM.models.AdminImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.CommentImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.HistoryImpl;
import com.telerikacademy.oop.WIM.models.TeamImpl;
import com.telerikacademy.oop.WIM.models.common.enums.BugStatus;
import com.telerikacademy.oop.WIM.models.common.enums.FeedBackStatus;
import com.telerikacademy.oop.WIM.models.common.enums.StorySize;
import com.telerikacademy.oop.WIM.models.common.enums.StoryStatus;
import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.items.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.oop.WIM.CommonConstants.*;

public class WorkItemTests {


    @Test
    public void GetHistoryToString_Should_ReturnCorrectMessage_When_Called() {
        Team team = new TeamImpl("Admins team");
        Admin admin = new AdminImpl("Admin", team.getTeamName());
        History history = new HistoryImpl(admin, " test History");
        History history2 = new HistoryImpl(admin, " test History2");
        Bug newBug = new BugImpl(LEN_10, LEN_10, BugStatus.FIXED.toString(), priority,
                person, severity, steps);


        newBug.addHistory(history);
        newBug.addHistory(history2);

        String expected = String.format("[%s] Admin  test History%n" +
                        "[%s] Admin  test History2",
                LocalDateTime.now().format(formatter),
                LocalDateTime.now().format(formatter));

        String actual = newBug.getHistoryToString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void OrderingWI_Should_GiveSortByTitle_When_CalledOnWorkItems() {
        Bug bug = new BugImpl("baaaaaaaaaaaaaaa", LEN_10, BugStatus.ACTIVE.toString(), priority, person, severity, steps);
        Story story = new StoryImpl("aaaaaaaaagdfgdfgd", LEN_10, StoryStatus.NOTDONE.toString(), priority, person, StorySize.LARGE);
        FeedBack fb = new FeedBackImpl("csdasdasdasddfgdfg", LEN_10, FeedBackStatus.SCHEDULED.toString(), 3);
        Bug bug2 = new BugImpl("dasddduuudfgdfg", LEN_10, BugStatus.FIXED.toString(), priority, person, severity, steps);

        List<WorkItem> list = List.of(fb, bug2, story, bug);

        List<WorkItem> expected = List.of(story, bug, fb, bug2);
        List<WorkItem> actual = list.stream().sorted().collect(Collectors.toList());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void AddComment_Should_AddComment_When_CorrectInput() {
        Bug bug = new BugImpl("baaaaaaaaaaaaaaa", LEN_10, BugStatus.ACTIVE.toString(), priority, person, severity, steps);
        Comment comment = new CommentImpl("asdasdasdasd","asdasdasdas");

        bug.addComment(comment);

        Assertions.assertEquals("asdasdasdas", bug.getComments().get(0).getAuthor());
        Assertions.assertEquals("asdasdasdasd", bug.getComments().get(0).getContent());
    }


}
