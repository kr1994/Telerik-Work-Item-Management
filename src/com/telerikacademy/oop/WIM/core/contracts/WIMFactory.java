package com.telerikacademy.oop.WIM.core.contracts;

import com.telerikacademy.oop.WIM.models.common.enums.Priority;
import com.telerikacademy.oop.WIM.models.common.enums.Severity;
import com.telerikacademy.oop.WIM.models.common.enums.StorySize;
import com.telerikacademy.oop.WIM.models.contracts.*;
import com.telerikacademy.oop.WIM.models.contracts.items.*;

import java.util.List;

public interface WIMFactory {

    Person registerNewPerson(String username);

    Admin createNewAdmin(String name, String teamName);

    Comment createComment(String content, Admin author);

    Team createTeam(String name);

    Board createBoard(String boardName);

    Bug createBug(String title, String description, String status, Priority priority,
                  Person assignee, Severity severity, List<String> steps);

    Story createStory(String title, String description, String status, Priority priority,
                      Person assignee, StorySize size);

    FeedBack createFB(String title, String description, String status, int rating);

    History createHistory(Admin author, String message);

}
