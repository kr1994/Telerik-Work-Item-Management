package com.telerikacademy.oop.WIM.models.ItemsImpl.work;

import com.telerikacademy.oop.WIM.CommonConstants;
import com.telerikacademy.oop.WIM.models.PersonImpl;
import com.telerikacademy.oop.WIM.models.common.Utils;
import com.telerikacademy.oop.WIM.models.common.enums.*;
import com.telerikacademy.oop.WIM.models.contracts.items.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.oop.WIM.CommonConstants.*;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.BUG;
import static com.telerikacademy.oop.WIM.models.common.Utils.cantAssign;
import static com.telerikacademy.oop.WIM.models.common.Utils.wrongField;

public class TaskTest {

    private final String status = BugStatus.FIXED.toString();

    @Test//priority
    public void ChangeField_Should_ChangePriorityCorrectly_When_CorrectInput() {

        Bug testBug = new BugImpl(LEN_10, LEN_10, status, Priority.MEDIUM,
                person, severity, steps);
        testBug.assignNewValueToAccessibleField(OpenField.PRIORITY, Priority.LOW.toString());

        Assertions.assertEquals(Priority.LOW,
                testBug.getPriority());
    }

    @Test
    public void ChangeField_Should_ChangePriorityCorrectly_When_CorrectInput2() {

        Bug testBug = new BugImpl(LEN_10, LEN_10, status, Priority.MEDIUM,
                person, severity, steps);

        testBug.assignNewValueToAccessibleField(OpenField.PRIORITY, "-");

        Assertions.assertEquals(Priority.LOW,
                testBug.getPriority());
    }

    @Test
    public void ChangeField_Should_NotThrow_When_InputCantChangePriorityMore() {

        Bug testBug = new BugImpl(LEN_10, LEN_10, status, Priority.LOW,
                person, severity, steps);

        testBug.assignNewValueToAccessibleField(OpenField.PRIORITY, "+");
        testBug.assignNewValueToAccessibleField(OpenField.PRIORITY, "+");
        testBug.assignNewValueToAccessibleField(OpenField.PRIORITY, "+");
        testBug.assignNewValueToAccessibleField(OpenField.PRIORITY, "+");
        testBug.assignNewValueToAccessibleField(OpenField.PRIORITY, "+");
        testBug.assignNewValueToAccessibleField(OpenField.PRIORITY, "+");

        Assertions.assertEquals(Priority.HIGH,
                testBug.getPriority());
    }

    @Test
    public void ChangeField_Should_ReturnCorrectMessage_When_IncorrectInput() {

        WorkItem testBug = new BugImpl(LEN_10, LEN_10, status, Priority.MEDIUM,
                person, severity, steps);
        String wrongInput = "Wrong input";

        String test = testBug.assignNewValueToAccessibleField(OpenField.PRIORITY, wrongInput);
        String result = cantAssign(
                wrongInput,
                OpenField.PRIORITY);

        Assertions.assertEquals(result, test);
    }

    @Test
    public void ChangeField_Should_ReturnCorrectMessage_When_IncorrectInput2() {

        WorkItem testBug = new BugImpl(LEN_10, LEN_10, status, Priority.MEDIUM,
                person, severity, steps);

        String test = testBug.assignNewValueToAccessibleField(OpenField.SIZE, StorySize.SMALL.toString());
        String result = wrongField(
                "Task",
                OpenField.SIZE);

        Assertions.assertEquals(result, test);
    }

    @Test
    public void ChangeField_Should_NotThrow_When_InputCantChangePriorityMore2() {

        Bug testBug = new BugImpl(LEN_10, LEN_10, status, Priority.LOW,
                person, severity, steps);

        testBug.assignNewValueToAccessibleField(OpenField.PRIORITY, "+");
        testBug.assignNewValueToAccessibleField(OpenField.PRIORITY, "+");
        testBug.assignNewValueToAccessibleField(OpenField.PRIORITY, "+");
        testBug.assignNewValueToAccessibleField(OpenField.PRIORITY, "+");
        testBug.assignNewValueToAccessibleField(OpenField.PRIORITY, "+");
        testBug.assignNewValueToAccessibleField(OpenField.PRIORITY, "+");

        Assertions.assertEquals(Priority.HIGH,
                testBug.getPriority());
    }

    @Test
    public void OrderingWI_Should_GiveSortByPriority_When_CalledOnTasks() {
        Task bug = new BugImpl("baaaaaaaaaaaaaaa", LEN_10, BugStatus.ACTIVE.toString(), Priority.LOW, new PersonImpl("Katerine"), severity, steps);
        Task story = new StoryImpl("aaaaaaaaagdfgdfgd", LEN_10, StoryStatus.NOTDONE.toString(), Priority.MEDIUM, new PersonImpl("Dobri"), StorySize.LARGE);
        Task story1 = new StoryImpl("aazaaaaaagdfgdfgd", LEN_10, StoryStatus.NOTDONE.toString(), Priority.LOW, new PersonImpl("Dobri"), StorySize.LARGE);
        Task bug21 = new BugImpl("daszddduuudfgdfg", LEN_10, BugStatus.FIXED.toString(), Priority.HIGH, new PersonImpl("Anastasia"), severity, steps);
        Task bug2 = new BugImpl("dasddzduuudfgdfg", LEN_10, BugStatus.FIXED.toString(), Priority.MEDIUM, new PersonImpl("Anastasia"), severity, steps);
        Task bug22 = new BugImpl("dasddduuzudfgdfg", LEN_10, BugStatus.FIXED.toString(), Priority.HIGH, new PersonImpl("Anastasia"), severity, steps);

        List<Task> list = List.of(story, bug, bug2,story1,bug22,bug21);

        List<Task> expected = List.of(bug, story1,story, bug2,bug21,bug22);
        List<Task> actual = list.stream()
                .sorted(Task::compareTo)
                .collect(Collectors.toList());

        Assertions.assertEquals(expected, actual);
    }
}
