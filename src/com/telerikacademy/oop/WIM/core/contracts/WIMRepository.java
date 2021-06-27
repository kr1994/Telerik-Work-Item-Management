package com.telerikacademy.oop.WIM.core.contracts;

import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.actions.Assignable;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.actions.Commentable;
import com.telerikacademy.oop.WIM.models.contracts.items.*;

import java.util.List;
import java.util.Map;

public interface WIMRepository {

    void setAuthor(Admin newAuthor);

    //add
    void addPerson(Person userToAdd);

    void addTeam(Team teamToAdd);

    void addBug(Bug toshkoBug);

    void addStory(Story vladyStory);

    void addFeedBack(FeedBack tomyFeedBack);

    //get
    List<WorkItem> getWork();

    WorkItem getWorkByID(int ID);

    Assignable getAssignableByID(int ID);

    List<Task> getTasks();

    List<Assignable> getAssignable();

    List<FeedBack> getFeedBack();

    List<Story> getStory();

    List<Bug> getBugs();

    Map<String, Team> getTeams();

    Map<String, Person> getPeople();

    Admin getAuthor();

    List<Commentable> getCommentable();

}
