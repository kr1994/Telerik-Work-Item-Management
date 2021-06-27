package com.telerikacademy.oop.WIM.commands;

import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.models.PersonImpl;
import com.telerikacademy.oop.WIM.models.TeamImpl;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.CommonConstants.*;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;

public class LoginTests {

    Login login;

    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        login = new Login(factory, repository);
    }

    @Test
    public void Command_Should_Throw_When_IncorrectParameters() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> login.execute(new ArrayList<>()));
    }

    @Test
    public void Command_Should_Throw_When_NullName() {
        List<String> list = new ArrayList<>();

        list.add(null);
        list.add(LEN_10);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> login.execute(list));
    }

    @Test
    public void Command_Should_Throw_When_IncorrectNameSizeLow() {
        List<String> list = List.of(LEN_2, LEN_10);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> login.execute(list));
    }

    @Test
    public void Command_Should_Throw_When_IncorrectNameSizeHigh() {
        List<String> list = List.of(LEN_55, LEN_10);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> login.execute(list));
    }

    @Test
    public void Command_Should_Throw_When_NullTeam() {
        List<String> list = new ArrayList<>();

        list.add(LEN_10);
        list.add(null);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> login.execute(list));
    }

    @Test
    public void Command_Should_Throw_When_IncorrectTeamSizeLow() {
        List<String> list = List.of(LEN_10, LEN_2);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> login.execute(list));
    }

    @Test
    public void Command_Should_Throw_When_IncorrectTeamSizeHigh() {
        List<String> list = List.of(LEN_10, LEN_55);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> login.execute(list));
    }

    @Test
    public void Command_Should_ReturnCorrectMessage_When_OtherUserIsLogged() {
        List<String> list = List.of(LEN_10, LEN_10);
        repository.setAuthor(admin);

        Assertions.assertEquals(String.format(USER_LOGGED_IN_ALREADY, repository.getAuthor()),
                login.execute(list));
    }

    @Test
    public void Command_Should_RegisterFirstPerson_When_NoPeopleInRepository() {
        List<String> list = List.of(LEN_10, LEN_15);

        String expected = String.format(USER_LOGGED_IN, LEN_10);
        String actual = login.execute(list);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(LEN_10, repository.getAuthor().getName());
        Assertions.assertEquals(LEN_15, repository.getAuthor().getTeam());
        Assertions.assertFalse(repository.getTeams().isEmpty());
        Assertions.assertEquals(1, repository.getTeams().size());
        Assertions.assertTrue(repository.getTeams().containsKey(LEN_15));
        Assertions.assertFalse(repository.getPeople().isEmpty());
        Assertions.assertEquals(1, repository.getPeople().size());
        Assertions.assertTrue(repository.getPeople().containsKey(LEN_10));
    }

    @Test
    public void Command_Should_LogInPerson_When_CorrectInput() {
        person = new PersonImpl(LEN_10);
        team = new TeamImpl(LEN_15);
        team.addPerson(person);
        repository.addTeam(team);
        repository.addPerson(person);

        List<String> list = List.of(LEN_10, LEN_15);

        String expected = String.format(USER_LOGGED_IN, person.toString());
        String actual = login.execute(list);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(repository.getAuthor().getName(),
                person.toString());
        Assertions.assertEquals(repository.getAuthor().getTeam(),
                team.getTeamName());

    }

    @Test
    public void Command_Should_Throw_When_WrongTeam() {
        person = new PersonImpl(LEN_10);
        team = new TeamImpl(LEN_15);
        team.addPerson(person);
        repository.addTeam(team);
        repository.addPerson(person);

        List<String> list = List.of(LEN_10, LEN_10);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> login.execute(list));

    }

    @Test
    public void Command_Should_Throw_When_WrongPerson() {
        //not empty repository
        Person person15 = new PersonImpl(LEN_15);
        Team team15 = new TeamImpl(LEN_15);
        team15.addPerson(person15);
        repository.addTeam(team15);
        repository.addPerson(person15);

        Team team10 = new TeamImpl(LEN_10);
        repository.addTeam(team10);

        List<String> list = List.of(LEN_10, LEN_10);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> login.execute(list));
    }

     @Test
    public void Command_Should_Throw_When_WrongPersonForTeam() {
        Person person10 = new PersonImpl(LEN_10);
        Team team10 = new TeamImpl(LEN_10);
        team10.addPerson(person10);
        repository.addTeam(team10);
        repository.addPerson(person10);

        Person person15 = new PersonImpl(LEN_15);
        Team team15 = new TeamImpl(LEN_15);
        team15.addPerson(person15);
        repository.addTeam(team15);
        repository.addPerson(person15);

        List<String> list = List.of(LEN_10, LEN_15);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> login.execute(list));
    }

    @Test
    public void Command_Should_Throw_When_WrongTeamForPerson() {
        Person person10 = new PersonImpl(LEN_10);
        Team team10 = new TeamImpl(LEN_10);
        team10.addPerson(person10);
        repository.addTeam(team10);
        repository.addPerson(person10);

        Person person15 = new PersonImpl(LEN_15);
        Team team15 = new TeamImpl(LEN_15);
        team15.addPerson(person15);
        repository.addTeam(team15);
        repository.addPerson(person15);

        List<String> list = List.of(LEN_15, LEN_10);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> login.execute(list));
    }


}
