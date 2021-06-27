package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.commands._constants.CommandConstants;
import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.models.TeamImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.CommonConstants.*;

public class RegisterNewTeamTests {

    RegisterNewTeam command;

    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        command = new RegisterNewTeam(factory, repository);
        repository.setAuthor(admin);
    }

    @Test
    public void Command_Should_RegisterTeam_When_CorrectParameter() {
        String input = "myteam";
        String expected = String.format(CommandConstants.TEAM_ADDED_SUCCESSFULLY,
                input);
        String actual = command.execute(List.of(input));

        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(repository.getTeams().containsKey(input));
    }
    @Test
    public void Command_Should_Throw_When_TeamExists() {
        String input = "myteam";
        repository.addTeam(new TeamImpl(input));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(List.of(input)));

    }

    @Test
    public void Command_Should_Throw_When_IncorrectParameterSize() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(new ArrayList<>()));
    }

}
