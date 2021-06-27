package com.telerikacademy.oop.WIM.models;

import com.telerikacademy.oop.WIM.models.ItemsImpl.HistoryImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.BugImpl;
import com.telerikacademy.oop.WIM.models.common.enums.BugStatus;
import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.items.Bug;
import com.telerikacademy.oop.WIM.models.contracts.items.History;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.telerikacademy.oop.WIM.CommonConstants.*;
import static com.telerikacademy.oop.WIM.CommonConstants.steps;

public class PersonImplTests {

    private String len10 = "1234567890";
    private String len15 = "123456789012345";
    private String len2 = "12";
    private String len55 = "1234567890" +
            "1234567890" +
            "1234567890" +
            "1234567890" +
            "1234567890" +
            "12345";


    @Test
    public void Constructor_Should_CreatePerson_Whit_CorrectInput() {
        Person person = new PersonImpl(len10);

        Assertions.assertEquals(len10, person.getName());

    }

    @Test
    public void Constructor_Should_Throw_Whit_IncorrectInput() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new PersonImpl(len2));

    }

    @Test
    public void Constructor_Should_Throw_Whit_IncorrectInput2() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new PersonImpl(len55));
    }

    @Test
    public void AddingWork_Should_Throw_Whit_NullInput() {
        Person person = new PersonImpl(len10);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> person.assignWork(null));
    }

    @Test
    public void AddingWork_Should_ReturnTrue_Whit_ValidInput() {
        Person person = new PersonImpl(len10);
        Bug newBug = new BugImpl(LEN_10, LEN_10, BugStatus.FIXED.toString(), priority,
                person, severity, steps);

        Assertions.assertTrue(person.assignWork(newBug));
    }

    @Test
    public void AddingWork_Should_ReturnFalse_Whit_DuplicateWork() {
        Person person = new PersonImpl(len10);
        Bug newBug = new BugImpl(LEN_10, LEN_10, BugStatus.FIXED.toString(), priority,
                person, severity, steps);

        person.assignWork(newBug);

        Assertions.assertFalse(person.assignWork(newBug));
    }

    @Test
    public void ToString_Should_ReturnCorrectSpelling_Whit_OddName() {
        Person person = new PersonImpl("pppPPPppPPpP");

        Assertions.assertEquals("Pppppppppppp", person.toString());
    }

    @Test
    public void RemovingWork_Should_ReturnFalse_Whit_InvalidWork() {
        Person person = new PersonImpl(len10);
        Bug newBug = new BugImpl(LEN_10, LEN_10, BugStatus.FIXED.toString(), priority,
                person, severity, steps);

        Assertions.assertFalse(person.unAssignWork(newBug));
    }

    @Test
    public void RemovingWork_Should_ReturnTrue_Whit_ValidWork() {
        Person person = new PersonImpl(len10);
        Bug newBug = new BugImpl(LEN_10, LEN_10, BugStatus.FIXED.toString(), priority,
                person, severity, steps);

        person.assignWork(newBug);

        Assertions.assertTrue(person.unAssignWork(newBug));
    }

    @Test
    public void RemovingWork_Should_NotThrow_Whit_NullInput() {
        Person person = new PersonImpl(len10);
        ArrayList<WorkItem> list = new ArrayList();
        person.unAssignWork(null);

        Assertions.assertEquals(list, person.getWork());
    }

    @Test
    public void GetHistoryToString_Should_ReturnCorrectMessage_When_Called() {
        Team team = new TeamImpl("Admins team");
        Admin admin = new AdminImpl("Admin", team.getTeamName());
        History history = new HistoryImpl(admin, " test History");
        History history2 = new HistoryImpl(admin, " test History2");
        team.addPerson(person);
        team.addPerson(admin);

        person.addHistory(history);
        person.addHistory(history2);

        String expected = String.format("[%s] Admin  test History%n" +
                        "[%s] Admin  test History2",
                LocalDateTime.now().format(formatter),
                LocalDateTime.now().format(formatter));

        String actual = person.getHistoryToString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void HashCode_Should_UseNameOnly_When_Called() {
        int expected = person.getName().hashCode()/100;
        int actual = person.hashCode()/100;
        Assertions.assertEquals(expected, actual);
    }


}
