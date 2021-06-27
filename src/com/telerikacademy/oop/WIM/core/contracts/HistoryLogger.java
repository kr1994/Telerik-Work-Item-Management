package com.telerikacademy.oop.WIM.core.contracts;

import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;

public interface HistoryLogger {

    String takeLogPerson(String message);

    String takeLog(WorkItem item, String message);

    String takeLog(Board board, String message);

    String takeLog(Person person, String message);

    String takeLog(WorkItem item, Person person, String message);

    String takeLog(WorkItem item, Board board, String message);

}
