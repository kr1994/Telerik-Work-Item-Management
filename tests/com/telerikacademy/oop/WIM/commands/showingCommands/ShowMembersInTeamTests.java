package com.telerikacademy.oop.WIM.commands.showingCommands;

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
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.MEMBERS_ARE;

public class ShowMembersInTeamTests {
    ShowMembersInTeam command;
    Team team1;

    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        command = new ShowMembersInTeam(factory, repository);
        team1 = new TeamImpl("Team1");
        Person person1 = new PersonImpl("person1");
        Person person2 = new PersonImpl("person2");
        Person person3 = new PersonImpl("person3");
        team1.addPerson(person1);
        team1.addPerson(person2);
        team1.addPerson(person3);
        repository.addPerson(person1);
        repository.addPerson(person2);
        repository.addPerson(person3);
        repository.addTeam(team1);

    }

    @Test
    public void Command_Should_ShowAllTeamMembers_When_CorrectParameters() {

        String expected = String.format(MEMBERS_ARE,
                team1.getTeamName(),
                team1.getTeamMembers().toString());

        String actual = command.execute(List.of("team1"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Command_Should_Throw_When_EmptyMembers() {

        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> command.execute(List.of("team")));
    }

    @Test
    public void Command_Should_Throw_When_IncorrectTeam() {

        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> command.execute(List.of("Wrong")));
    }

    @Test
    public void Command_Should_Throw_When_NullParameters() {

        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> command.execute(null));
    }

    @Test
    public void Command_Should_Throw_When_IncorrectParametersSize() {

        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> command.execute(new ArrayList<>()));
    }
}
