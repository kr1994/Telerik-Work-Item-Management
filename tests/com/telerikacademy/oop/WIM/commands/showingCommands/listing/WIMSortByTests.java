package com.telerikacademy.oop.WIM.commands.showingCommands.listing;

import com.telerikacademy.oop.WIM.commands.showingCommands.listing.WIMFilterBy;
import com.telerikacademy.oop.WIM.commands.showingCommands.listing.WIMListOf;
import com.telerikacademy.oop.WIM.commands.showingCommands.listing.WIMSortBy;
import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.AdminImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.BugImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.FeedBackImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.StoryImpl;
import com.telerikacademy.oop.WIM.models.PersonImpl;
import com.telerikacademy.oop.WIM.models.TeamImpl;
import com.telerikacademy.oop.WIM.models.common.enums.*;
import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.items.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.oop.WIM.CommonConstants.*;
import static com.telerikacademy.oop.WIM.CommonConstants.LEN_15;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;
import static com.telerikacademy.oop.WIM.models.common.Utils.capitaliseFirst;

public class WIMSortByTests {
    WIMRepository repository;
    Person person1, person2, person3, person4, person5;
    Bug bug1, bug2, bug3, bug4, bug5, bug6;
    Story story1, story2, story3, story4, story5, story6;
    FeedBack fb1, fb2, fb3, fb4, fb5, fb6;
    List<Bug> bugs = new ArrayList<>();
    List<Story> storyList = new ArrayList<>();
    List<FeedBack> fbList = new ArrayList<>();
    List<Task> tasks = new ArrayList<>();
    List<WorkItem> work = new ArrayList<>();
    Team myTeam = new TeamImpl("myTeam");
    Person dobri = new PersonImpl("Dobri");
    Admin admin;
    WIMFilterBy filter;
    WIMListOf listOf;
    WIMSortBy sortBy;
    String expected, actual, title, report;

    @BeforeEach
    public void bootUp() {
        person1 = new PersonImpl("aaaaaaa");
        person2 = new PersonImpl("bbbbbbbbbbbb");
        person3 = new PersonImpl("ccccccccc");
        person4 = new PersonImpl("aaaabbb");
        person5 = new PersonImpl("aaaaaaaaaa");

        bug1 = new BugImpl("aaaaaaaaaaaaa", LEN_55, BugStatus.FIXED.toString(), Priority.LOW, person1, Severity.CRITICAL, steps);
        bug2 = new BugImpl("bbbbbbbbbbbba", LEN_55, BugStatus.ACTIVE.toString(), Priority.LOW, person3, Severity.CRITICAL, steps);
        bug3 = new BugImpl("cccccccccca", LEN_55, BugStatus.FIXED.toString(), Priority.HIGH, person2, Severity.MAJOR, steps);
        bug4 = new BugImpl("aaabbbbbbbba", LEN_55, BugStatus.ACTIVE.toString(), Priority.HIGH, person2, Severity.MINOR, steps);
        bug5 = new BugImpl("aaccccccccca", LEN_55, BugStatus.FIXED.toString(), Priority.MEDIUM, person3, Severity.MINOR, steps);
        bug6 = new BugImpl("aaaaaaaaaaasaaa", LEN_55, BugStatus.FIXED.toString(), Priority.LOW, person4, Severity.MAJOR, steps);
        bugs = List.of(bug1, bug2, bug3, bug4, bug5, bug6);

        story1 = new StoryImpl("aaaaasaaaaaaaa", LEN_15, StoryStatus.DONE.toString(), Priority.MEDIUM, person3, StorySize.LARGE);
        story2 = new StoryImpl("aaaaabaaaaaaaa", LEN_15, StoryStatus.DONE.toString(), Priority.LOW, person5, StorySize.SMALL);
        story3 = new StoryImpl("aaaaagaaaaaaa", LEN_15, StoryStatus.INPROGRESS.toString(), Priority.HIGH, person5, StorySize.SMALL);
        story4 = new StoryImpl("aaaaaaaaaaaaaaaa", LEN_15, StoryStatus.INPROGRESS.toString(), Priority.MEDIUM, person5, StorySize.SMALL);
        story5 = new StoryImpl("aavaaaaaaaaa", LEN_15, StoryStatus.NOTDONE.toString(), Priority.LOW, person5, StorySize.MEDIUM);
        story6 = new StoryImpl("aazasaaaaaaa", LEN_15, StoryStatus.NOTDONE.toString(), Priority.HIGH, person1, StorySize.SMALL);
        storyList = List.of(story1, story2, story3, story4, story5, story6);

        fb1 = new FeedBackImpl("bbbbbbbbbbaaa", LEN_15, FeedBackStatus.DONE.toString(), 20);
        fb2 = new FeedBackImpl("bbbbbbbbbbaaa", LEN_15, FeedBackStatus.DONE.toString(), 2);
        fb3 = new FeedBackImpl("bbbbbbbbaaa", LEN_15, FeedBackStatus.UNSCHEDULED.toString(), 21);
        fb4 = new FeedBackImpl("bbbbbbbbbbaaa", LEN_15, FeedBackStatus.UNSCHEDULED.toString(), 12);
        fb5 = new FeedBackImpl("bbbbbbbasdaaa", LEN_15, FeedBackStatus.SCHEDULED.toString(), 2);
        fb6 = new FeedBackImpl("bbbbbbbbbbaaaaaa", LEN_15, FeedBackStatus.UNSCHEDULED.toString(), 2);
        fbList = List.of(fb1, fb2, fb3, fb4, fb5, fb6);

        tasks.addAll(bugs);
        tasks.addAll(storyList);
        work.addAll(tasks);
        work.addAll(fbList);

        repository = new WIMRepositoryImpl();
        repository.addPerson(person1);
        repository.addPerson(person2);
        repository.addPerson(person3);
        repository.addPerson(person4);
        repository.addPerson(person5);
        repository.addBug(bug1);
        repository.addBug(bug2);
        repository.addBug(bug3);
        repository.addBug(bug4);
        repository.addBug(bug5);
        repository.addBug(bug6);
        repository.addStory(story1);
        repository.addStory(story2);
        repository.addStory(story3);
        repository.addStory(story4);
        repository.addStory(story5);
        repository.addStory(story6);
        repository.addFeedBack(fb1);
        repository.addFeedBack(fb2);
        repository.addFeedBack(fb3);
        repository.addFeedBack(fb4);
        repository.addFeedBack(fb5);
        repository.addFeedBack(fb6);

        myTeam.addPerson(dobri);
        repository.addTeam(myTeam);
        admin = new AdminImpl(dobri.getName(), "myteam");
        repository.setAuthor(admin);
        filter = new WIMFilterBy(factory, repository);
        filter.execute(List.of("off"));
        listOf = new WIMListOf(factory, repository);
        listOf.execute(List.of(""));
        sortBy = new WIMSortBy(factory, repository);
        sortBy.execute(List.of("off"));
    }

    @Test
    public void SortBy_Should_SortBugsCorrectly_Whit_CorrectInput() {
        title = "List of bug"+SORTED_BY_ID;
        report = bugs.stream()
                .map(Object::toString)
                .reduce("", (a, b) -> a + b);
        expected = title + System.lineSeparator() + report.trim();


        actual = listOf.execute(List.of("bug"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void SortBy_Should_SortBugsCorrectlyBySeverity_Whit_CorrectInput() {
        title = "List of bug"+SORTED_BY_SEVERITY;
        report = bugs.stream()
                .sorted(Bug::compareTo)
                .map(Object::toString)
                .reduce("", (a, b) -> a + b);
        expected = title + System.lineSeparator() + report.trim();

        sortBy.execute(List.of("severity"));
        actual = listOf.execute(List.of("bug"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void SortBy_Should_SortStoryCorrectly_Whit_CorrectInput() {
        title = "List of story"+SORTED_BY_ID;
        report = storyList.stream()
                .map(Object::toString)
                .reduce("", (a, b) -> a + b);
        expected = title + System.lineSeparator() + report.trim();


        actual = listOf.execute(List.of("story"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void SortBy_Should_SortStoryCorrectlyBySize_Whit_CorrectInput() {
        title = "List of story" + SORTED_BY_SIZE;
        report = storyList.stream()
                .sorted(Story::compareTo)
                .map(Object::toString)
                .reduce("", (a, b) -> a + b);
        expected = title + System.lineSeparator() + report.trim();

        sortBy.execute(List.of("size"));
        actual = listOf.execute(List.of("story"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void SortBy_Should_SortFeedbackCorrectly_Whit_CorrectInput() {
        title = "List of feedback" + SORTED_BY_ID;
        report = fbList.stream()
                .map(Object::toString)
                .reduce("", (a, b) -> a + b);
        expected = title + System.lineSeparator() + report.trim();


        actual = listOf.execute(List.of("feedback"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void SortBy_Should_SortFeedbackCorrectlyByRating_Whit_CorrectInput() {
        title = "List of feedback" + SORTED_BY_RATING;
        report = fbList.stream()
                .sorted(FeedBack::compareTo)
                .map(Object::toString)
                .reduce("", (a, b) -> a + b);
        expected = title + System.lineSeparator() + report.trim();

        sortBy.execute(List.of("rating"));
        actual = listOf.execute(List.of("feedback"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void SortBy_Should_SortTasksCorrectly_Whit_CorrectInput() {
        title = "List of all work items  sorted by priority";
        report = tasks.stream()
                .sorted(Task::compareTo)
                .map(Object::toString)
                .reduce("", (a, b) -> a + b);
        expected = title + System.lineSeparator() + report.trim();

        sortBy.execute(List.of("priority"));
        actual = listOf.execute(List.of());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void SortBy_Should_SortTasksCorrectlyByRating_Whit_CorrectInput() {
        title = "List of all work items  filter by assignee: "
                + capitaliseFirst(person1.getName())
                + " sorted by priority";
        report = tasks.stream()
                .filter(t -> t.getAssignee().getName().equalsIgnoreCase(person1.getName()))
                .sorted(Task::compareTo)
                .map(Object::toString)
                .reduce("", (a, b) -> a + b);
        expected = title + System.lineSeparator() + report.trim();

        filter.execute(List.of("Assignee", person1.getName()));
        sortBy.execute(List.of("priority"));
        actual = listOf.execute(List.of());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void SortBy_Should_SortWorkItemsCorrectly_Whit_CorrectInput() {
        title = "List of all work items " + SORTED_BY_ID;
        report = work.stream()
                .sorted(Comparator.comparingInt(a -> Integer.parseInt(a.getIdentity())))
                .map(Object::toString)
                .reduce("", (a, b) -> a + b);
        expected = title + System.lineSeparator() + report.trim();

        actual = listOf.execute(List.of());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void SortBy_Should_SortCorrectlyByTitle_Whit_CorrectInput() {
        title = "List of all work items " + SORTED_BY_TITLE;
        report = work.stream()
                .sorted(WorkItem::compareTo)
                .map(Object::toString)
                .reduce("", (a, b) -> a + b);
        expected = title + System.lineSeparator() + report.trim();

        sortBy.execute(List.of("title"));
        actual = listOf.execute(List.of());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void SortBy_Should_Throw_when_InvalidFormat() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> sortBy.execute(List.of("wrong")));
    }

    @Test
    public void SortBy_Should_SortWorkItemsCorrectly_Whit_CorrectInput2() {
        title = "List of all work items  filter by status: Done" + SORTED_BY_ID;
        report = work.stream()
                .filter(w -> w.getStatus().equalsIgnoreCase(StoryStatus.DONE.toString()))
                .sorted(Comparator.comparingInt(a -> Integer.parseInt(a.getIdentity())))
                .map(Object::toString)
                .reduce("", (a, b) -> a + b);
        expected = title + System.lineSeparator() + report.trim();

        filter.execute(List.of("status", "done"));
        actual = listOf.execute(List.of());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void SortBy_Should_SortWorkItemsCorrectly_Whit_CorrectInput3() {
        title = "List of all work items  filter by status: InProgress"+SORTED_BY_ID;
        report = tasks.stream()
                .filter(w -> w.getStatus().equalsIgnoreCase(StoryStatus.INPROGRESS.toString()))
                .sorted(Comparator.comparingInt(a -> Integer.parseInt(a.getIdentity())))
                .map(Object::toString)
                .reduce("", (a, b) -> a + b);
        expected = title + System.lineSeparator() + report.trim();

        filter.execute(List.of("status", "inprogress"));
        actual = listOf.execute(List.of());

        Assertions.assertEquals(expected, actual);
    }

}
