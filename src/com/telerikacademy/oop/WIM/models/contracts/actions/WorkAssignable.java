package com.telerikacademy.oop.WIM.models.contracts.actions;

import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;

import java.util.List;

public interface WorkAssignable {

    List<WorkItem> getWork();

    boolean assignWork(WorkItem workToDo);

    boolean unAssignWork(WorkItem workToLeave);
}
