package com.telerikacademy.oop.WIM.commands.showingCommands.listing;

import com.telerikacademy.oop.WIM.commands.AddingAssigningCommands.AssignWorkToPerson;
import com.telerikacademy.oop.WIM.commands.showingCommands.listing.WIMFilterBy;
import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.BugImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.FeedBackImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.StoryImpl;
import com.telerikacademy.oop.WIM.models.PersonImpl;
import com.telerikacademy.oop.WIM.models.common.enums.BugStatus;
import com.telerikacademy.oop.WIM.models.common.enums.FeedBackStatus;
import com.telerikacademy.oop.WIM.models.common.enums.StorySize;
import com.telerikacademy.oop.WIM.models.common.enums.StoryStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.CommonConstants.*;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;

public class WIMFilterByTests {

    WIMFilterBy command = new WIMFilterBy(factory, repository);
    List<String> parameters;
    String expected, actual;

    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        command = new WIMFilterBy(factory, repository);
        parameters = new ArrayList<>();
    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_CorrectParameters() {
        command.execute(List.of("off"));
        parameters = List.of("off");
        expected = String.format(FILTER_OPTIONS_SUCCESSFULLY,
                OFF,
                OFF);

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_CorrectParameters2() {
        command.execute(List.of("off"));
        repository.addPerson(new PersonImpl("Dobri"));
        parameters = List.of("assignee", "dobri");
        expected = String.format(FILTER_OPTIONS_SUCCESSFULLY,
                "Dobri",
                OFF);

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_CorrectParameters3() {
        command.execute(List.of("off"));
        repository.addPerson(new PersonImpl("Dobri"));
        parameters = List.of("status", "done");
        expected = String.format(FILTER_OPTIONS_SUCCESSFULLY,
                OFF,
                "Done");

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_CorrectParameters4() {
        command.execute(List.of("off"));
        repository.addPerson(new PersonImpl("Dobri"));
        parameters = List.of("status", "done","assignee","dobri");
        expected = String.format(FILTER_OPTIONS_SUCCESSFULLY,
                "Dobri",
                "Done");

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void Command_Should_Throw_When_WrongInput() {
        repository.addPerson(new PersonImpl("Dobri"));
        parameters = List.of("status", "done","assignee","dobri");
        expected = String.format(FILTER_OPTIONS_SUCCESSFULLY,
                "Dobri",
                "Done");

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void Command_Should_Throw_When_IncorrectInput() {
        parameters.add("Wrong");
        parameters.add("WrongAgain");

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
    public void Command_Should_RememberAlreadySetOptions_When_CorrectParameters() {
        repository.addPerson(new PersonImpl("Dobri"));
        command.execute(List.of("assignee","Dobri"));

        command = new WIMFilterBy(factory,repository);
        parameters = List.of("status", "done");
        expected = String.format(FILTER_OPTIONS_SUCCESSFULLY,
                "Dobri",
                "Done");

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void FilterBy_Should_Throw_When_InvalidInput(){
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> command.execute(List.of("assignee")));
    }

    @Test
    public void FilterBy_Should_Throw_When_InvalidInput2(){
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> command.execute(List.of("status")));
    }
}
