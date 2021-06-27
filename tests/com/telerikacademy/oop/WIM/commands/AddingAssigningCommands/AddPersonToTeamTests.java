package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.core.factories.WIMFactoryImpl;
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
import static com.telerikacademy.oop.WIM.CommonConstants.admin;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.ALREADY_IN_TEAM;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.MEMBER_ADDED_TO_TEAM_SUCCESSFULLY;

public class AddPersonToTeamTests {
    private WIMFactory factory;
    private WIMRepository repo;
    Command command;
    String expected, actual;


    @BeforeEach
    public void bootUp() {
        factory = new WIMFactoryImpl();
        repo = new WIMRepositoryImpl();
        team = new TeamImpl(LEN_10);

        repo.addTeam(team);
        repo.addPerson(admin);
        repo.setAuthor(admin);
        command = new AddPersonToTeam(factory, repo);
    }

    @Test
    public void Command_Should_AddPersonToTeam_Whit_CorrectInput() {
        Person dobri = new PersonImpl("Dobri");
        repo.addPerson(dobri);
        Team myTeam = new TeamImpl("My team");
        repo.addTeam(myTeam);

        expected = String.format(MEMBER_ADDED_TO_TEAM_SUCCESSFULLY,
                dobri.toString(),
                myTeam.getTeamName());

        actual = command.execute(List.of("Dobri", "my team"));

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(1, dobri.getHistory().size());
    }

    @Test
    public void Command_Should_ReturnCorrectMessageIfPersonIsInTheTeamAlready_Whit_CorrectInput() {
        Person dobri = new PersonImpl("Dobri");
        repo.addPerson(dobri);
        Team myTeam = new TeamImpl("My team");
        myTeam.addPerson(dobri);
        repo.addTeam(myTeam);

        expected = String.format(ALREADY_IN_TEAM,
                dobri.toString(),
                myTeam.getTeamName());

        actual = command.execute(List.of("Dobri", "my team"));

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(1, myTeam.getTeamMembers().size());
    }

    @Test
    public void Command_Should_Throw_Whit_MissingPerson() {
        Team myTeam = new TeamImpl("My team");
        repo.addTeam(myTeam);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(List.of("Dobri", "my team")));
    }

    @Test
    public void Command_Should_Throw_Whit_MissingTeam() {
        Person dobri = new PersonImpl("Dobri");
        repo.addPerson(dobri);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(List.of("Dobri", "my team")));
    }

    @Test
    public void Command_Should_Throw_Whit_WrongSizedInput() {
        Person dobri = new PersonImpl("Dobri");
        repo.addPerson(dobri);
        Team myTeam = new TeamImpl("My team");
        repo.addTeam(myTeam);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(List.of("Dobri")));
    }

    @Test
    public void Command_Should_Throw_Whit_EmptyInput() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(new ArrayList<>()));
    }

    @Test
    public void Command_Should_Throw_Whit_NullInput() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(null));
    }

}
