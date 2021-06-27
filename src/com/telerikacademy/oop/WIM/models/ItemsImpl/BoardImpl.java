package com.telerikacademy.oop.WIM.models.ItemsImpl;

import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;
import com.telerikacademy.oop.WIM.models.contracts.items.History;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.models.common.Constants.*;

public class BoardImpl implements Board {

    private final List<History> boardHistory;
    private final List<WorkItem> workOnBoard;
    private String boardName;

    public BoardImpl(String boardName) {
        setBoardName(boardName);
        boardHistory = new ArrayList<>();
        workOnBoard = new ArrayList<>();
    }

    @Override
    public boolean assignWork(WorkItem work) {
        if (work != null && !workOnBoard.contains(work)) {
            workOnBoard.add(work);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addHistory(History historyLog) {
        Validator.validateNotNull(historyLog, HISTORY_LOG);
        boardHistory.add(historyLog);
    }

    @Override
    public String getBoardName() {
        return boardName;
    }

    @Override
    public List<WorkItem> getWork() {
        return new ArrayList<>(workOnBoard);
    }

    @Override
    public boolean unAssignWork(WorkItem work) {
        if(work == null)
            return false;
        workOnBoard.remove(work);
        return true;
    }

    @Override
    public List<History> getHistory() {
        return new ArrayList<>(boardHistory);
    }

    @Override
    public String getHistoryToString() {
        return getHistory().stream()
                .map(Object::toString)
                .reduce("", (a, b) -> a + b);
    }

    @Override
    public String getIdentity() {
        return getBoardName();
    }

    @Override
    public String toString() {
        return String.format("Board Name: %s%n" +
                        "Work: %s%n" +
                        "History: %s%n"
                        , getBoardName()
                        , getWork().toString()
                        , getHistoryToString());
    }

    //private
    private void setBoardName(String boardName) {
        Validator.validateNotNull(boardName, BOARD_NAME);
        this.boardName = Validator.trimmedStringInBounds(boardName,
                BOARD_NAME,
                BOARD_NAME_MIN_SIZE,
                BOARD_NAME_MAX_SIZE)
                .toLowerCase();
    }
}