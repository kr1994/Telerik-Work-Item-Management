package com.telerikacademy.oop.WIM.commands;

import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.AdminImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.BugImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.FeedBackImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.StoryImpl;
import com.telerikacademy.oop.WIM.models.common.enums.*;
import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.items.Bug;
import com.telerikacademy.oop.WIM.models.contracts.items.FeedBack;
import com.telerikacademy.oop.WIM.models.contracts.items.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.CommonConstants.*;
import static com.telerikacademy.oop.WIM.models.common.Constants.FB_RATTING_MAST_BE_INTEGER;
import static com.telerikacademy.oop.WIM.models.common.Utils.historyChangeFromTo;
import static com.telerikacademy.oop.WIM.models.common.Utils.wrongField;

public class ChangeWIStateTests {

    WIMRepository repository = new WIMRepositoryImpl();
    ChangeWIState command = new ChangeWIState(factory, repository);
    Bug bug;
    FeedBack fb;
    Story story;
    List<String> parameters;
    String expected, actual;


    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        command = new ChangeWIState(factory, repository);
        story = new StoryImpl(LEN_10, LEN_15, StoryStatus.INPROGRESS.toString(), priority, person, StorySize.SMALL);
        fb = new FeedBackImpl(LEN_10, LEN_15, FeedBackStatus.UNSCHEDULED.toString(), 2);
        bug = new BugImpl(LEN_10, LEN_15, BugStatus.ACTIVE.toString(), priority, person, severity, steps);
        parameters = new ArrayList<>();
        team.addPerson(person);
        Admin admin = new AdminImpl(person.getName(), team.getTeamName());
        repository.setAuthor(admin);
    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_CorrectParameters() {
        repository.addFeedBack(fb);

        parameters = List.of(fb.getIdentity(), OpenField.RATING.toString(), "5");

        expected = historyChangeFromTo(WIType.FEEDBACK.toString() + " " + fb.getID(),
                OpenField.RATING.toString(),
                "" + 2,
                "" + 5);
        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_CorrectParameters1() {
        repository.addFeedBack(fb);

        parameters = List.of(fb.getIdentity(), OpenField.RATING.toString(), "+");

        expected = historyChangeFromTo(WIType.FEEDBACK.toString() + " " + fb.getID(),
                OpenField.RATING.toString(),
                "" + 2,
                "" + 3);
        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_CorrectParameters2() {
        repository.addFeedBack(fb);

        parameters = List.of(fb.getIdentity(), OpenField.RATING.toString(), "-");

        expected = historyChangeFromTo(WIType.FEEDBACK.toString() + " " + fb.getID(),
                OpenField.RATING.toString(),
                "" + 2,
                "" + 1);
        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_IncorrectInput() {
        repository.addFeedBack(fb);

        parameters = List.of(fb.getIdentity(), OpenField.SEVERITY.toString(), "-");

        expected = wrongField(WIType.FEEDBACK.toString(), OpenField.SEVERITY);
        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_IncorrectInput2() {
        repository.addFeedBack(fb);

        parameters = List.of(fb.getIdentity(), OpenField.RATING.toString(), "done");

        expected = FB_RATTING_MAST_BE_INTEGER;
        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_IncorrectInput3() {
        repository.addFeedBack(fb);

        parameters = List.of(fb.getIdentity(), OpenField.STATUS.toString(), BugStatus.ACTIVE.toString());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(parameters));

    }

    @Test
    public void Command_Should_Throw_When_LowSizeParameters() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(new ArrayList<>()));
    }

    @Test
    public void Command_Should_Throw_When_WrongParameters() {
        parameters.add("Wrong");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(parameters));
    }

    @Test
    public void Command_Should_Throw_When_WrongIndex() {
        repository.addFeedBack(fb);
        parameters = List.of("Wrong", OpenField.RATING.toString(), "5");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(parameters));

    }

}