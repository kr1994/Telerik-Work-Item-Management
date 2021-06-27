package com.telerikacademy.oop.WIM.models.ItemsImpl.work;

import com.telerikacademy.oop.WIM.models.common.enums.*;
import com.telerikacademy.oop.WIM.models.contracts.actions.Identify;
import com.telerikacademy.oop.WIM.models.contracts.items.Story;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.oop.WIM.CommonConstants.*;

public class StoryTests {

    Story story;
    StorySize size = StorySize.SMALL;
    String status = StoryStatus.NOTDONE.toString();


    @Test//constructor
    public void Constructor_Should_CreateBug_When_CorrectInput() {
        story = new StoryImpl(LEN_10, LEN_10, status, priority, person, size);

        Assertions.assertEquals(LEN_10, story.getTitle());
        Assertions.assertEquals(LEN_10, story.getDescription());
        Assertions.assertEquals(status, story.getStatus());
        Assertions.assertEquals(priority, story.getPriority());
        Assertions.assertEquals(person, story.getAssignee());
        Assertions.assertEquals(size, story.getSize());
    }

    @Test
    public void Constructor_Should_CreateBug_When_CorrectInputAndNullPerson() {
        story = new StoryImpl(LEN_10, LEN_10, status, priority,
                null, size);
        Assertions.assertEquals(LEN_10, story.getTitle());
        Assertions.assertEquals(LEN_10, story.getDescription());
        Assertions.assertEquals(status, story.getStatus());
        Assertions.assertEquals(priority, story.getPriority());
        Assertions.assertNull(story.getAssignee());
        Assertions.assertEquals(size, story.getSize());
    }

    @Test
    public void Constructor_Should_Throw_When_LowSizeInput1() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new StoryImpl(LEN_2, LEN_10, StoryStatus.NOTDONE.toString(), priority,
                        person, size));
    }

    @Test
    public void Constructor_Should_Throw_When_LowSizeInput2() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new StoryImpl(LEN_10, LEN_2, StoryStatus.DONE.toString(), priority,
                        person, size));
    }

    @Test
    public void Constructor_Should_Throw_When_LowSizeInput3() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new StoryImpl(LEN_10, LEN_10, "", priority,
                        person, size));
    }

    @Test
    public void Constructor_Should_Throw_When_HighSizeInput1() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new StoryImpl(LEN_55, LEN_10, StoryStatus.NOTDONE.toString(), priority,
                        person, size));
    }

    @Test
    public void Constructor_Should_Throw_When_HighSizeInput2() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new StoryImpl(LEN_10, LEN_550, status, priority,
                        person, size));
    }

    @Test//status
    public void ChangeField_Should_ChangeStatusCorrectly_When_CorrectInput() {
        WorkItem story1 = new StoryImpl(LEN_10, LEN_10, status, priority, person, size);

        story1.assignNewValueToAccessibleField(OpenField.STATUS, StoryStatus.NOTDONE.toString());

        Assertions.assertEquals(StoryStatus.NOTDONE.toString(),
                story1.getStatus());
    }

    @Test
    public void ChangeField_Should_ChangeStatusCorrectly_When_CorrectInput2() {
        WorkItem story1 = new StoryImpl(LEN_10, LEN_10, StoryStatus.INPROGRESS.toString(),
                priority, person, size);

        story1.assignNewValueToAccessibleField(OpenField.STATUS, "-");

        Assertions.assertEquals(StoryStatus.NOTDONE.toString(),
                story1.getStatus());
    }

    @Test
    public void ChangeField_Should_ChangeStatusCorrectly_When_CorrectInput3() {
        WorkItem story1 = new StoryImpl(LEN_10, LEN_10, StoryStatus.INPROGRESS.toString(),
                priority, person, size);

        story1.assignNewValueToAccessibleField(OpenField.STATUS, "+");

        Assertions.assertEquals(StoryStatus.DONE.toString(),
                story1.getStatus());
    }

    @Test
    public void ChangeField_Should_NotThrow_When_InputCantChangeStatusMore() {
        WorkItem story1 = new StoryImpl(LEN_10, LEN_10, StoryStatus.INPROGRESS.toString(),
                priority, person, size);

        story1.assignNewValueToAccessibleField(OpenField.STATUS, "+");
        story1.assignNewValueToAccessibleField(OpenField.STATUS, "+");
        story1.assignNewValueToAccessibleField(OpenField.STATUS, "+");
        story1.assignNewValueToAccessibleField(OpenField.STATUS, "+");
        story1.assignNewValueToAccessibleField(OpenField.STATUS, "+");

        Assertions.assertEquals(StoryStatus.DONE.toString(),
                story1.getStatus());
    }

    @Test//size
    public void ChangeField_Should_ChangeSizeCorrectly_When_CorrectInput() {
        WorkItem story1 = new StoryImpl(LEN_10, LEN_10, status, priority, person, size);

        story1.assignNewValueToAccessibleField(OpenField.SIZE, StorySize.MEDIUM.toString());
        Story story2 = (Story) story1;

        Assertions.assertEquals(StorySize.MEDIUM,
                story2.getSize());
    }

    @Test
    public void ChangeField_Should_ChangeSizeCorrectly_When_CorrectInput2() {
        WorkItem story1 = new StoryImpl(LEN_10, LEN_10, StoryStatus.INPROGRESS.toString(),
                priority, person, StorySize.LARGE);

        story1.assignNewValueToAccessibleField(OpenField.SIZE, "-");
        Story story2 = (Story) story1;

        Assertions.assertEquals(StorySize.MEDIUM,
                story2.getSize());
    }

    @Test
    public void ChangeField_Should_ChangeStoryCorrectly_When_CorrectInput3() {
        WorkItem story1 = new StoryImpl(LEN_10, LEN_10, StoryStatus.INPROGRESS.toString(),
                priority, person, size);

        story1.assignNewValueToAccessibleField(OpenField.SIZE, "+");
        Story story2 = (Story) story1;


        Assertions.assertEquals(StorySize.MEDIUM,
                story2.getSize());
    }

    @Test
    public void ChangeField_Should_NotThrow_When_InputCantChangeSizeMore() {
        WorkItem story1 = new StoryImpl(LEN_10, LEN_10, StoryStatus.INPROGRESS.toString(),
                priority, person, size);

        story1.assignNewValueToAccessibleField(OpenField.SIZE, "+");
        story1.assignNewValueToAccessibleField(OpenField.SIZE, "+");
        story1.assignNewValueToAccessibleField(OpenField.SIZE, "+");
        story1.assignNewValueToAccessibleField(OpenField.SIZE, "+");
        story1.assignNewValueToAccessibleField(OpenField.SIZE, "+");
        story1.assignNewValueToAccessibleField(OpenField.SIZE, "+");
        story1.assignNewValueToAccessibleField(OpenField.SIZE, "+");
        Story story2 = (Story) story1;


        Assertions.assertEquals(StorySize.LARGE,
                story2.getSize());
    }

    @Test
    public void ToString_Should_GiveCorrectOutput_When_Called() {

        Story story = new StoryImpl(LEN_10, LEN_10, StoryStatus.INPROGRESS.toString(), priority,
                person, StorySize.LARGE);
        String expected = String.format("Story ID %d, 1234567890, status InProgress, description 1234567890, " +
                        "priority Medium, assignee 1234567890, size Large" + System.lineSeparator(),
                WorkItemBase.getNumberOfItems());

        String actual = story.toString();

        Assertions.assertEquals(expected,
                actual);
    }

    @Test
    public void OrderingWI_Should_GiveSortBySize_When_CalledOnStory() {
        Story story = new StoryImpl("2aaaaaaaaagdfgdfgd", LEN_10, StoryStatus.NOTDONE.toString(), priority, person, StorySize.LARGE);
        Story story1 = new StoryImpl("1aaaaaaaaagdfgdfgd", LEN_10, StoryStatus.DONE.toString(), priority, person, StorySize.SMALL);
        Story story2 = new StoryImpl("3aaaaaaaaagdfgdfgd", LEN_10, StoryStatus.NOTDONE.toString(), priority, person, StorySize.SMALL);
        Story story3 = new StoryImpl("4aaaaaaaaagdfgdfgd", LEN_10, StoryStatus.INPROGRESS.toString(), priority, person, StorySize.MEDIUM);
        Story story4 = new StoryImpl("5aaaaaaaaagdfgdfgd", LEN_10, StoryStatus.NOTDONE.toString(), priority, person, StorySize.MEDIUM);

        List<Story> list = List.of(story1, story2, story3, story4, story);

        List<Story> expected = List.of(story1, story2, story3, story4, story);
        List<Story> actual = list.stream().sorted(Story::compareTo).collect(Collectors.toList());

        Assertions.assertEquals(expected, actual);
    }

}
