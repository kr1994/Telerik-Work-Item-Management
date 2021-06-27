package com.telerikacademy.oop.WIM.commands.showingCommands;

import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.BoardImpl;
import com.telerikacademy.oop.WIM.models.TeamImpl;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.CommonConstants.*;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.BOARDS_OF_TEAM_ARE;

public class ShowTeamBoardsTests {

    ShowTeamBoards command;
    String expected, actual;
    List<String> parameters;
    Team team;

    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        command = new ShowTeamBoards(factory, repository);
        repository.setAuthor(admin);
        parameters = new ArrayList<>();
        team = new TeamImpl("myTeam");
        repository.addTeam(team);
    }

    @Test
    public void Command_Should_ShowTeamBoards_When_CorrectParameters() {
        team.addBoard(new BoardImpl("Board1"));
        team.addBoard(new BoardImpl("Board2"));
        team.addBoard(new BoardImpl("Board3"));
        team.addBoard(new BoardImpl("Board4"));
        expected = String.format(BOARDS_OF_TEAM_ARE, team.getTeamName())
                + team.getTeamBoards().stream()
                .map(Board::getBoardName)
                .reduce("", (a, b) ->
                        a + System.lineSeparator() + b)
                .trim();
        parameters = List.of("myTeam");

        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Command_Should_Throw_When_WrongTeam() {
        team.addBoard(new BoardImpl("Board1"));
        expected = String.format(BOARDS_OF_TEAM_ARE, team.getTeamName())
                + team.getTeamBoards().stream()
                .map(Board::getBoardName)
                .reduce("", (a, b) ->
                        a + System.lineSeparator() + b)
                .trim();
        parameters = List.of("Wrong");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(parameters));
    }

    @Test
    public void Command_Should_Throw_When_NoBoardsInTeam() {

        parameters = List.of("myTeam");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(parameters));
    }

    @Test
    public void Command_Should_Throw_When_EmptyParameters() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(new ArrayList<>()));
    }


    @Test
    public void Command_Should_Throw_When_NullParameters() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> command.execute(null));
    }
}
