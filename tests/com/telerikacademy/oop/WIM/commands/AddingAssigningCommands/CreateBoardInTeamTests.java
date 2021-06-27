package com.telerikacademy.oop.WIM.commands.AddingAssigningCommands;

import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.AdminImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.BoardImpl;
import com.telerikacademy.oop.WIM.models.TeamImpl;
import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.CommonConstants.factory;
import static com.telerikacademy.oop.WIM.CommonConstants.person;

public class CreateBoardInTeamTests {
    String input;
    WIMRepository repository;
    Team team;
    CreateBoardInTeam command;

    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        team = new TeamImpl("myTeam");
        team.addPerson(person);
        repository.addTeam(team);
        repository.addPerson(person);
        Admin admin = new AdminImpl(person.getName(), team.getTeamName());
        repository.setAuthor(admin);
        command = new CreateBoardInTeam(factory, repository);
    }

    @Test
    public void Command_Should_CreateBoard_When_CorrectInput() {
        input = "New Board";
        command.execute(List.of(input, "myteam"));

        Assertions.assertEquals(input.toLowerCase(),
                team.getTeamBoards().get(0).getBoardName().toLowerCase());
        Assertions.assertEquals(1,
                team.getTeamBoards().get(0).getHistory().size());
        Assertions.assertEquals(0,
                team.getTeamBoards().get(0).getWork().size());
    }

    @Test
    public void Command_Should_Throw_When_BoardExists() {
        input = "New Board";
        team.addBoard(new BoardImpl(input));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(List.of(input, "myteam")));
    }

    @Test
    public void Command_Should_Throw_When_InvalidInput() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(new ArrayList<>()));
    }

    @Test
    public void Command_Should_Throw_When_InvalidInput2() {
        List<String> inputList = new ArrayList<>();
        inputList.add("Wrong");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(inputList));
    }

    @Test
    public void Command_Should_Throw_When_WrongTeam() {
        List<String> inputList = new ArrayList<>();
        inputList.add("Wrong");
        inputList.add("WrongTeam");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(inputList));
    }

    @Test
    public void Command_Should_Throw_When_BoardAlreadyInTeam() {
        List<String> inputList = new ArrayList<>();
        inputList.add("AlreadyExists");
        inputList.add("WrongTeam");
        team.addBoard(new BoardImpl("AlreadyExists"));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(inputList));
    }

}
