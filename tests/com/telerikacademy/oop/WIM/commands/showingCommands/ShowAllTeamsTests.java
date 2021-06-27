package com.telerikacademy.oop.WIM.commands.showingCommands;

import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.models.TeamImpl;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.telerikacademy.oop.WIM.CommonConstants.factory;
import static com.telerikacademy.oop.WIM.CommonConstants.repository;

public class ShowAllTeamsTests {

    ShowAllTeams command;

    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        command = new ShowAllTeams(factory, repository);
    }

    @Test
    public void Command_Should_ShowAllTeams_When_Called() {
        Team team1 =new TeamImpl("Team1");
        repository.addTeam(team1);
        Team team2 =new TeamImpl("Team2");
        repository.addTeam(team2);
        Team team3 =new TeamImpl("Team3");
        repository.addTeam(team3);
        String expected = repository.getTeams().keySet().toString();

        String actual = command.execute(new ArrayList<>());

        Assertions.assertEquals(expected, actual);
    }
}
