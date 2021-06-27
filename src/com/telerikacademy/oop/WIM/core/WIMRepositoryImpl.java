package com.telerikacademy.oop.WIM.core;

import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.WorkItemBase;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.actions.Assignable;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.actions.Commentable;
import com.telerikacademy.oop.WIM.models.contracts.items.*;

import java.util.*;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;


public class WIMRepositoryImpl implements WIMRepository {


    private static Admin author;

    private final Map<String, Team> teams;
    private final Map<String, Person> people;
    private final List<Bug> toshkoBugs;
    private final List<Story> vladyStory;
    private final List<FeedBack> tomyFeedback;


    public WIMRepositoryImpl() {
        teams = new HashMap<>();
        author = null;
        people = new HashMap<>();
        toshkoBugs = new ArrayList<>();
        vladyStory = new ArrayList<>();
        tomyFeedback = new ArrayList<>();
    }

    @Override
    public void setAuthor(Admin newAuthor) {
        author = newAuthor;
    }

    //add
    @Override
    public void addTeam(Team teamToAdd) {
        Validator.validateNotNull(teamToAdd, NEW_TEAM);
        teams.put(teamToAdd.getTeamName(), teamToAdd);
    }

    @Override
    public void addBug(Bug toshkoBug) {
        Validator.validateNotNull(toshkoBug, BUG);
        toshkoBugs.add(toshkoBug);
    }

    @Override
    public void addStory(Story vladyStory) {
        Validator.validateNotNull(vladyStory, STORY);
        this.vladyStory.add(vladyStory);
    }

    @Override
    public void addFeedBack(FeedBack tomyFeedBack) {
        Validator.validateNotNull(tomyFeedBack, FEEDBACK);
        this.tomyFeedback.add(tomyFeedBack);
    }

    @Override
    public void addPerson(Person personToAdd) {
        Validator.validateNotNull(personToAdd, PERSON);
        people.put(personToAdd.getName(), personToAdd);
    }

    //get
    @Override
    public Admin getAuthor() {
        return author;
    }

    @Override
    public List<Bug> getBugs() {
        return new ArrayList<>(toshkoBugs);
    }

    @Override
    public List<Task> getTasks() {
        List<Task> result = new ArrayList<>(getBugs());
        result.addAll(getStory());
        return result;
    }

    @Override
    public List<Story> getStory() {
        return new ArrayList<>(vladyStory);
    }

    @Override
    public List<WorkItem> getWork() {
        List<WorkItem> result = new ArrayList<>(getTasks());
        result.addAll(getFeedBack());
        return result;
    }

    public WorkItem getWorkByID(int ID){
        List<WorkItem> work = getWork();
        Validator.validateIntRange(ID,WORK_ITEM_ID,0, WorkItemBase.getNumberOfItems());
        return work.stream().filter(w->w.getID() == ID)
                .findFirst().orElse(null);
    }

    public Assignable getAssignableByID(int ID){
        List<Task> work = getTasks();
        Validator.validateIntRange(ID,WORK_ITEM_ID,0, WorkItemBase.getNumberOfItems());
        return work.stream().filter(w->w.getID() == ID)
                .findFirst().orElse(null);
    }

    @Override
    public Map<String, Team> getTeams() {
        return new HashMap<>(teams);
    }

    @Override
    public List<FeedBack> getFeedBack() {
        return new ArrayList<>(tomyFeedback);
    }

    @Override
    public Map<String, Person> getPeople() {
        return new HashMap<>(people);
    }

    @Override
    public List<Assignable> getAssignable() {
        List<Assignable> result = new ArrayList<>(getBugs());
        result.addAll(getStory());
        return result;
    }

    @Override
    public List<Commentable> getCommentable() {
        return new ArrayList<>(getWork());
    }
}
