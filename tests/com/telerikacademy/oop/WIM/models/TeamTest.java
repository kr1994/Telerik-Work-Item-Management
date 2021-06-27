package com.telerikacademy.oop.WIM.models;

import com.telerikacademy.oop.WIM.models.ItemsImpl.BoardImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.HistoryImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.BugImpl;
import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;
import com.telerikacademy.oop.WIM.models.contracts.items.History;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.telerikacademy.oop.WIM.CommonConstants.*;

public class TeamTest {


    Team team;
    Board board;


    @BeforeEach
    public void bootUp() {
        team = new TeamImpl("mayTeam");
        board = new BoardImpl("myBoard");
        person = new PersonImpl(LEN_10);
        team.addBoard(board);
        team.addPerson(person);
    }

    @Test//constructor
    public void Constructor_Should_CreateTeam_When_CorrectInput() {
        Team team = new TeamImpl("TestTeam");

        Assertions.assertEquals("testteam", team.getTeamName());
        Assertions.assertEquals(0, team.getTeamBoards().size());
        Assertions.assertEquals(0, team.getTeamMembers().size());
    }

    @Test
    public void Constructor_Should_Throw_When_LowSizeInput() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new TeamImpl("Te"));
    }

    @Test
    public void AddPerson_Should_ReturnTrue_When_CorrectInput() {
        team.removePerson(person);

        Assertions.assertTrue(team.addPerson(person));
    }

    @Test
    public void AddPerson_Should_ReturnFalse_When_MemberAlreadyInTeam() {

        team.addPerson(person);

        Assertions.assertFalse(team.addPerson(person));
    }

    @Test
    public void RemovePerson_Should_ReturnTrue_When_CorrectInput() {

        team.addPerson(person);

        Assertions.assertTrue(team.removePerson(person));
    }

    @Test
    public void RemovePerson_Should_ReturnFalse_When_MemberIsNotInTeam() {
        team.removePerson(person);

        Assertions.assertFalse(team.removePerson(person));
    }

    @Test
    public void AddingBoard_Should_ReturnTrue_When_CorrectInput() {
        Board brd = new BoardImpl(" a new board");
        Assertions.assertTrue(team.addBoard(brd));
    }

    @Test
    public void AddingBoard_Should_Throw_When_BoardAlreadyIn() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> team.addBoard(board));
    }

    @Test
    public void Identify_Should_GiveCorrectResult_When_Called() {
        Assertions.assertEquals(team.getTeamName(), team.getIdentity());
    }

    @Test
    public void GetIndexByBoard_Should_GiveCorrectResult_When_Called() {
        Assertions.assertEquals(0, team.getIndexOfBoard("myBoard"));
    }


    @Test
    public void GetHistoryToStringByMember_Should_ReturnCorrectMessage_When_Called() {
        Admin admin = new AdminImpl("Admin", team.getTeamName());
        History history = new HistoryImpl(admin, " test History");

        person.addHistory(history);
        person.addHistory(history);
        person.addHistory(history);
        String expected = String.format("Team mayteam history by members:%n" +
                        "[%s] Admin  test History%n" +
                        "[%s] Admin  test History%n" +
                        "[%s] Admin  test History",
                LocalDateTime.now().format(formatter),
                LocalDateTime.now().format(formatter),
                LocalDateTime.now().format(formatter));

        String actual = team.getHistoryToStringByMember();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void GetHistoryToStringByMember_Should_ReturnCorrectMessage_When_Called2() {
        Admin admin = new AdminImpl("Admin", team.getTeamName());
        History history = new HistoryImpl(admin, " test History");
        History history2 = new HistoryImpl(admin, " test History2");

        team.addPerson(admin);

        admin.addHistory(history2);
        person.addHistory(history);
        person.addHistory(history);
        String expected = String.format("Team mayteam history by members:%n" +
                        "[%s] Admin  test History%n" +
                        "[%s] Admin  test History%n" +
                        "[%s] Admin  test History2",
                LocalDateTime.now().format(formatter),
                LocalDateTime.now().format(formatter),
                LocalDateTime.now().format(formatter));

        String actual = team.getHistoryToStringByMember();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void GetHistoryToStringByTime_Should_ReturnCorrectMessage_When_Called() {
        Admin admin = new AdminImpl("Admin", team.getTeamName());
        History history = new HistoryImpl(admin, " test History");

        person.addHistory(history);
        person.addHistory(history);
        person.addHistory(history);
        String expected = String.format("Team mayteam history by time:%n" +
                        "[%s] Admin  test History%n" +
                        "[%s] Admin  test History%n" +
                        "[%s] Admin  test History",
                LocalDateTime.now().format(formatter),
                LocalDateTime.now().format(formatter),
                LocalDateTime.now().format(formatter));

        String actual = team.getHistoryToStringByTime();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void GetHistoryToStringByTime_Should_ReturnCorrectMessage_When_Called2() {
        Admin admin = new AdminImpl("Admin", team.getTeamName());
        History history = new HistoryImpl(admin, " test History");
        History history2 = new HistoryImpl(admin, " test History2");

        team.addPerson(admin);

        admin.addHistory(history2);
        person.addHistory(history);
        person.addHistory(history);
        String expected = String.format("Team mayteam history by time:%n" +
                        "[%s] Admin  test History2%n" +
                        "[%s] Admin  test History%n" +
                        "[%s] Admin  test History",
                LocalDateTime.now().format(formatter),
                LocalDateTime.now().format(formatter),
                LocalDateTime.now().format(formatter));

        String actual = team.getHistoryToStringByTime();

        Assertions.assertEquals(expected, actual);
    }


}
