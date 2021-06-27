package com.telerikacademy.oop.WIM.core.factories;

import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.models.AdminImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.BugImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.FeedBackImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.StoryImpl;
import com.telerikacademy.oop.WIM.models.PersonImpl;
import com.telerikacademy.oop.WIM.models.TeamImpl;
import com.telerikacademy.oop.WIM.models.common.enums.Priority;
import com.telerikacademy.oop.WIM.models.common.enums.Severity;
import com.telerikacademy.oop.WIM.models.common.enums.StorySize;
import com.telerikacademy.oop.WIM.models.contracts.*;
import com.telerikacademy.oop.WIM.models.ItemsImpl.*;
import com.telerikacademy.oop.WIM.models.contracts.items.*;

import java.util.List;

public class WIMFactoryImpl implements WIMFactory {


    @Override
    public Person registerNewPerson(String username) {
        return new PersonImpl(username);
    }

    @Override
    public Admin createNewAdmin(String name, String teamName) {
        return new AdminImpl(name, teamName);
    }

    public Comment createComment(String content, Admin author) {
        return new CommentImpl(content, author.toString());
    }

    @Override
    public Team createTeam(String name) {
        return new TeamImpl(name);
    }

    @Override
    public Board createBoard(String boardName) {
        return new BoardImpl(boardName);
    }

    @Override
    public Bug createBug(String title, String description, String status, Priority priority,
                         Person assignee, Severity severity, List<String> steps) {
        return new BugImpl(title, description, status, priority, assignee, severity, steps);
    }

    @Override
    public Story createStory(String title, String description, String status, Priority priority,
                             Person assignee, StorySize size) {
        return new StoryImpl(title, description, status, priority, assignee, size);
    }

    @Override
    public FeedBack createFB(String title, String description, String status, int rating) {
        return new FeedBackImpl( title, description, status, rating);
    }

    @Override
    public History createHistory(Admin author, String message) {
        return new HistoryImpl(author, message);
    }

}
