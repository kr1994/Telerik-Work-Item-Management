package com.telerikacademy.oop.WIM.models.contracts.items;

import com.telerikacademy.oop.WIM.models.contracts.actions.HistoryLogged;
import com.telerikacademy.oop.WIM.models.contracts.actions.Identify;
import com.telerikacademy.oop.WIM.models.contracts.actions.WorkAssignable;

import java.util.List;

public interface Board extends WorkAssignable , HistoryLogged, Identify {

    boolean assignWork(WorkItem work);

    String getBoardName();

    List<WorkItem> getWork();

    boolean unAssignWork(WorkItem work);

}
