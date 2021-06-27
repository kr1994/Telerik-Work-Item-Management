package com.telerikacademy.oop.WIM.models.ItemsImpl.work;

import com.telerikacademy.oop.WIM.models.AdminImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.BoardImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.HistoryImpl;
import com.telerikacademy.oop.WIM.models.PersonImpl;
import com.telerikacademy.oop.WIM.models.common.enums.Priority;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.items.History;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static com.telerikacademy.oop.WIM.CommonConstants.*;

public class BoardImplTests {

    private BoardImpl newBoard;

    @Test//constructor
    public void Constructor_Should_CreateBoard_When_CorrectInput() {
        newBoard = new BoardImpl("NewBoard");
        Assertions.assertEquals("newboard", newBoard.getBoardName());
    }

    @Test
    public void Constructor_Should_Throw_When_Board_Has_LowSizeInput() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BoardImpl(LEN_2));
    }

    @Test
    public void Constructor_Should_Throw_When_Board_Has_HighSizeInput() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BoardImpl(LEN_10 + LEN_10));
    }

    @Test
    public void Constructor_Should_Throw_When_Board_Has_NullInput() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BoardImpl(null));
    }

    @Test
    public void AssignWork_Should_Add_WorkItem_To_Board_When_Passed_Correct_Input() {
        newBoard = new BoardImpl("NewBoard");
        Person petur = new PersonImpl("Petur");
        WorkItem w = new BugImpl("BiiigBugggg", "desciption", "active",
                Priority.LOW, petur, severity, steps);
        Assertions.assertTrue(newBoard.assignWork(w));
    }

    @Test
    public void AssignWork_Should_Return_False_When_Passed_Null() {
        newBoard = new BoardImpl("NewBoard");
        Assertions.assertFalse(newBoard.assignWork(null));
    }

    @Test
    public void UnAssignWork_Should_Remove_WorkItem_From_Board_When_Passed_Correct_Input() {
        newBoard = new BoardImpl("NewBoard");
        Person petur = new PersonImpl("Petur");
        WorkItem w = new BugImpl("BiiigBugggg", "desciption", "active",
                Priority.LOW, petur, severity, steps);
        newBoard.assignWork(w);
        Assertions.assertTrue(newBoard.unAssignWork(w));
    }

    @Test
    public void UnAssignWork_Should_Return_False_Passed_Null() {
        newBoard = new BoardImpl("NewBoard");
        Assertions.assertFalse(newBoard.unAssignWork(null));
    }

    @Test
    public void AddHistory_Should_Add_Log_To_BoardHistory_When_Passed_Correct_Input() {
        newBoard = new BoardImpl("NewBoard");
        History history = new HistoryImpl(new AdminImpl(admin.toString(), team.getTeamName()),
                "A long time ago...");
        newBoard.addHistory(history);
        Assertions.assertTrue(newBoard.getHistory().contains(history));
    }

    @Test
    public void AddHistory_Should_Throw_When_Passed_Null() {
        newBoard = new BoardImpl("NewBoard");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> newBoard.addHistory(null));
    }

    @Test
    public void GetWork_Should_Return_ListOfAllWorkItems() {
        newBoard = new BoardImpl("NewBoard");
        Person petur = new PersonImpl("Petur");
        WorkItem bug = new BugImpl("BiiigBugggg", "description", "active",
                Priority.LOW, petur, severity, steps);
        newBoard.assignWork(bug);
        WorkItem story = new StoryImpl("BiiigStory", "description", "InProgress",
                Priority.LOW, petur, storySize);
        newBoard.assignWork(story);
        WorkItem feedback = new FeedBackImpl("SmallFeeback", "description",
                "Unscheduled", rating);
        newBoard.assignWork(feedback);

        List<WorkItem> testList = new ArrayList<WorkItem>();
        testList.add(bug);
        testList.add(story);
        testList.add(feedback);

        Assertions.assertEquals(newBoard.getWork(), testList);
    }

    @Test
    public void GetWork_Should_Return_One_Of_Two_Duplicated_Values() {
        newBoard = new BoardImpl("NewBoard");
        Person petur = new PersonImpl("Petur");
        WorkItem bug = new BugImpl("BiiigBugggg", "description", "active",
                Priority.LOW, petur, severity, steps);
        newBoard.assignWork(bug);
        newBoard.assignWork(bug);

        List<WorkItem> testList = new ArrayList<WorkItem>();
        testList.add(bug);

        Assertions.assertEquals(newBoard.getWork(), testList);
    }

    @Test
    public void GetWork_WhenThereIsNoItemAdded_Should_Return_EmptyListOfWorkItems() {
        newBoard = new BoardImpl("NewBoard");

        Assertions.assertEquals(newBoard.getWork(), new ArrayList<WorkItem>());
    }

    @Test
    public void GetHistory_Should_Return_ListOfAllHistoryLoggers() {
        newBoard = new BoardImpl("NewBoard");
        History history = new HistoryImpl(new AdminImpl(admin.toString(), team.getTeamName()),
                "A long time ago...");
        newBoard.addHistory(history);
        History history2 = new HistoryImpl(new AdminImpl(admin.toString(), team.getTeamName()),
                "... that's the end!");
        newBoard.addHistory(history2);

        List<History> testList = new ArrayList<History>();
        testList.add(history);
        testList.add(history2);

        Assertions.assertEquals(newBoard.getHistory(), testList);
    }

    @Test
    public void GetHistory_WhenThereIsNoItemAdded_Should_Return_EmptyListOfHistoryLoggers() {
        newBoard = new BoardImpl("NewBoard");

        Assertions.assertEquals(newBoard.getHistory(), new ArrayList<History>());
    }

}
