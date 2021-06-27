package com.telerikacademy.oop.WIM.commands.showingCommands;

import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.BoardImpl;
import com.telerikacademy.oop.WIM.models.PersonImpl;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.CommonConstants.*;
import static com.telerikacademy.oop.WIM.CommonConstants.repository;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.BOARDS_OF_TEAM_ARE;

public class ShowAllPeopleTests {

    ShowAllPeople command;

    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        command = new ShowAllPeople(factory, repository);
    }

    @Test
    public void Command_Should_ShowAllPeople_When_Called() {
        Person person1 = new PersonImpl("Person1");
        repository.addPerson(person1);
        Person person12 = new PersonImpl("Person12");
        repository.addPerson(person12);
        Person person13 = new PersonImpl("Person13");
        repository.addPerson(person13);
        String expected = repository.getPeople().keySet().toString();

        String actual = command.execute(new ArrayList<>());

        Assertions.assertEquals(expected, actual);
    }

}
