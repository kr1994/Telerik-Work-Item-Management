package com.telerikacademy.oop.WIM.commands.showingCommands;

import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.models.AdminImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.BoardImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.HistoryImpl;
import com.telerikacademy.oop.WIM.models.TeamImpl;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;
import com.telerikacademy.oop.WIM.models.contracts.items.History;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.CommonConstants.*;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.TEAM_ACTIVITY_IS;

public class ShowTeamActivityTests {

    ShowTeamActivity command;
    String expected, actual;
    List<String> parameters;
    Team team;
    Board board;

    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        command = new ShowTeamActivity(factory, repository);
        repository.setAuthor(admin);
        parameters = new ArrayList<>();
        team = new TeamImpl("myTeam");
        repository.addTeam(team);
    }

    @Test
    public void Command_Should_ShowTeamActivity_When_CorrectParameters() {
        board = new BoardImpl("Board1");
        team.addBoard(board);
        History history = new HistoryImpl(new AdminImpl(admin.toString(), team.getTeamName()),
                "A long time ago...");
        board.addHistory(history);
        History history2 = new HistoryImpl(new AdminImpl(admin.toString(), team.getTeamName()),
                "... that's the end!");
        board.addHistory(history2);
        expected = String.format("Team %s history by time:%n", team.getTeamName())
                + team.getTeamBoards().stream()
                .map(Board::getHistoryToString)
                .reduce("", (a, b) ->
                        a + System.lineSeparator() + b)
                .trim();
        parameters = List.of(team.getTeamName());
        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Command_Should_Throw_When_WrongTeam() {
        board = new BoardImpl("Board1");
        team.addBoard(board);
        History history = new HistoryImpl(new AdminImpl(admin.toString(), team.getTeamName()),
                "A long time ago...");
        board.addHistory(history);
        expected = String.format("Team %s history by time:%n", team.getTeamName())
                + team.getTeamBoards().stream()
                .map(Board::getHistoryToString)
                .reduce("", (a, b) ->
                        a + System.lineSeparator() + b)
                .trim();
        parameters = List.of("WrongTeam");

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
    public void Command_Should_ShowEmptyTeamActivity_When_ThereIsNoHistory_InBoard() {
        board = new BoardImpl("Board1");
        team.addBoard(board);
        expected = String.format("Team %s history by time:%n", team.getTeamName())
                + team.getTeamBoards().stream()
                .map(Board::getHistoryToString)
                .reduce("", (a, b) ->
                        a + System.lineSeparator() + b)
                .trim();
        parameters = List.of(team.getTeamName());
        actual = command.execute(parameters);

        Assertions.assertEquals(expected, actual);
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
