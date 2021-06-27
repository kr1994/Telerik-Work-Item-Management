package com.telerikacademy.oop.WIM.models.ItemsImpl.work;

import com.telerikacademy.oop.WIM.models.common.enums.*;
import com.telerikacademy.oop.WIM.models.contracts.items.Bug;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.telerikacademy.oop.WIM.CommonConstants.*;

public class BugTests {

    private BugImpl newBug;
    private final String status = BugStatus.FIXED.toString();


    @Test//constructor
    public void Constructor_Should_CreateBug_When_CorrectInput() {

        newBug = new BugImpl(LEN_10, LEN_10, status, priority, person, severity, steps);

        Assertions.assertEquals(LEN_10, newBug.getTitle());
        Assertions.assertEquals(LEN_10, newBug.getDescription());
        Assertions.assertEquals(status, newBug.getStatus());
        Assertions.assertEquals(priority, newBug.getPriority());
        Assertions.assertEquals(person, newBug.getAssignee());
        Assertions.assertEquals(severity, newBug.getSeverity());
        Assertions.assertEquals(steps, newBug.getSteps());
    }

    @Test
    public void Constructor_Should_CreateBug_When_CorrectInputAndNullPerson() {

        newBug = new BugImpl(LEN_10, LEN_10, status, priority, null, severity, steps);

        Assertions.assertEquals(LEN_10, newBug.getTitle());
        Assertions.assertEquals(LEN_10, newBug.getDescription());
        Assertions.assertEquals(status, newBug.getStatus());
        Assertions.assertEquals(priority, newBug.getPriority());
        Assertions.assertEquals(null, newBug.getAssignee());
        Assertions.assertEquals(severity, newBug.getSeverity());
        Assertions.assertEquals(steps, newBug.getSteps());
    }

    @Test
    public void Constructor_Should_Throw_When_LowSizeInput1() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BugImpl(LEN_2, LEN_10, status, priority,
                        person, severity, steps));
    }

    @Test
    public void Constructor_Should_Throw_When_LowSizeInput2() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BugImpl(LEN_10, LEN_2, status, priority,
                        person, severity, steps));
    }

    @Test
    public void Constructor_Should_Throw_When_LowSizeInput3() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BugImpl(LEN_10, LEN_10, "", priority,
                        person, severity, steps));
    }

    @Test
    public void Constructor_Should_Throw_When_LowSizeInput4() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BugImpl(LEN_10, LEN_10, null, priority,
                        person, severity, steps));
    }

    @Test
    public void Constructor_Should_Throw_When_LowSizeInput5() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BugImpl(LEN_10, LEN_10, status, priority,
                        person, severity, new ArrayList<>()));
    }

    @Test
    public void Constructor_Should_Throw_When_HighSizeInput() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BugImpl(LEN_55, LEN_10, status, priority,
                        person, severity, steps));
    }

    @Test
    public void Constructor_Should_Throw_When_HighSizeInput2() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BugImpl(LEN_10, LEN_550, status, priority,
                        person, severity, steps));
    }

    @Test//status
    public void ChangeField_Should_ChangeStatusCorrectly_When_CorrectInput() {

        WorkItem testBug = new BugImpl(LEN_10, LEN_10, status, priority,
                person, severity, steps);
        testBug.assignNewValueToAccessibleField(OpenField.STATUS, BugStatus.ACTIVE.toString());

        Assertions.assertEquals(BugStatus.ACTIVE.toString(),
                testBug.getStatus());
    }

    @Test
    public void ChangeField_Should_ChangeStatusCorrectly_When_CorrectInput2() {

        WorkItem testBug = new BugImpl(LEN_10, LEN_10, status, priority,
                person, severity, steps);
        testBug.assignNewValueToAccessibleField(OpenField.STATUS, "-");

        Assertions.assertEquals(BugStatus.ACTIVE.toString(),
                testBug.getStatus());
    }

    @Test
    public void ChangeField_Should_NotThrow_When_InputCantChangeStatusMore() {

        WorkItem testBug = new BugImpl(LEN_10, LEN_10, status, priority,
                person, severity, steps);

        testBug.assignNewValueToAccessibleField(OpenField.STATUS, "+");
        testBug.assignNewValueToAccessibleField(OpenField.STATUS, "+");
        testBug.assignNewValueToAccessibleField(OpenField.STATUS, "+");

        Assertions.assertEquals(BugStatus.FIXED.toString(),
                testBug.getStatus());
    }

    @Test
    public void ChangeField_Should_NotThrow_When_InputCantChangeStatusMore2() {

        WorkItem testBug = new BugImpl(LEN_10, LEN_10, status, priority,
                person, severity, steps);

        testBug.assignNewValueToAccessibleField(OpenField.STATUS, "Fixed");

        Assertions.assertEquals(BugStatus.FIXED.toString(),
                testBug.getStatus());
    }

    @Test//severity
    public void ChangeField_Should_ChangeSeverityCorrectly_When_CorrectInput() {

        Bug testBug = new BugImpl(LEN_10, LEN_10, status, priority,
                person, Severity.MAJOR, steps);
        testBug.assignNewValueToAccessibleField(OpenField.SEVERITY, Severity.MINOR.toString());

        Assertions.assertEquals(Severity.MINOR,
                testBug.getSeverity());
    }

    @Test
    public void ChangeField_Should_ChangeSeverityCorrectly_When_CorrectInput2() {

        Bug testBug = new BugImpl(LEN_10, LEN_10, status, priority,
                person, Severity.MAJOR, steps);

        testBug.assignNewValueToAccessibleField(OpenField.SEVERITY, "-");

        Assertions.assertEquals(Severity.MINOR,
                testBug.getSeverity());
    }

    @Test
    public void ChangeField_Should_NotThrow_When_InputCantChangeSeverityMore() {

        Bug testBug = new BugImpl(LEN_10, LEN_10, status, priority,
                person, Severity.MINOR, steps);

        testBug.assignNewValueToAccessibleField(OpenField.SEVERITY, "+");
        testBug.assignNewValueToAccessibleField(OpenField.SEVERITY, "+");
        testBug.assignNewValueToAccessibleField(OpenField.SEVERITY, "+");
        testBug.assignNewValueToAccessibleField(OpenField.SEVERITY, "+");
        testBug.assignNewValueToAccessibleField(OpenField.SEVERITY, "+");
        testBug.assignNewValueToAccessibleField(OpenField.SEVERITY, "+");

        Assertions.assertEquals(Severity.CRITICAL,
                testBug.getSeverity());
    }

    @Test
    public void ChangeField_Should_NotThrow_When_InputCantChangeSeverityMore2() {

        Bug testBug = new BugImpl(LEN_10, LEN_10, status, priority,
                person, Severity.MINOR, steps);

        testBug.assignNewValueToAccessibleField(OpenField.SEVERITY, Severity.MINOR.toString());

        Assertions.assertEquals(Severity.MINOR,
                testBug.getSeverity());
    }

    @Test
    public void ToString_Should_GiveCorrectOutput_When_Called() {

        Bug testBug = new BugImpl(LEN_10, LEN_10, status, priority,
                person, Severity.MINOR, steps);
        String expected = String.format("Bug ID %d, 1234567890, status Fixed, description 1234567890," +
                        " priority Medium, assignee 1234567890, severity Minor, steps [1, 2, 3]" + System.lineSeparator(),
                WorkItemBase.getNumberOfItems());

        String actual = testBug.toString();

        Assertions.assertEquals(expected,
                actual);
    }

    @Test
    public void Constructor_Should_Create_When_ManyWordsAsTitleDescription() {
        String title = "aa aa ss dd aa";
        String description = "aa dd ff gg kk ii uu yy tt rr ee ww jhhhj";
        Bug testBug = new BugImpl(title, description,
                status, priority, person, Severity.MINOR, steps);

        Assertions.assertEquals(title, testBug.getTitle());
    }

}
