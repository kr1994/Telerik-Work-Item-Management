package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.commands._constants.CommandConstants;
import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.models.PersonImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.CommonConstants.*;

public class RegisterNewPersonTests {

    RegisterNewPerson command;

    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        command = new RegisterNewPerson(factory, repository);
        repository.setAuthor(admin);
    }


    @Test
    public void Command_Should_RegisterPerson_When_CorrectParameters() {
        String expected = String.format(CommandConstants.PERSON_ADDED_SUCCESSFULLY,
                LEN_10);
        List<String> list = List.of(LEN_10);
        String actual = command.execute(list);

        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(repository.getPeople().containsKey(LEN_10));
    }

    @Test
    public void Command_Should_Throw_When_IncorrectParameters() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(new ArrayList<>()));
    }

    @Test
    public void Command_Should_Throw_When_ShortName() {
        List<String> list = List.of(LEN_2);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(list));
    }

    @Test
    public void Command_Should_Throw_When_LongName () {
        List<String> list = List.of(LEN_55);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(list));
    }

    @Test
    public void Command_Should_Throw_When_PersonExists () {
        repository.addPerson(new PersonImpl(LEN_10));

        List<String> list = List.of(LEN_10);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(list));
    }
}
