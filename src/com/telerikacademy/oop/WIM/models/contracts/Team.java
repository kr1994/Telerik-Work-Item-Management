package com.telerikacademy.oop.WIM.models.contracts;

import com.telerikacademy.oop.WIM.models.contracts.actions.Identify;
import com.telerikacademy.oop.WIM.models.contracts.actions.WorkAssignable;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;
import com.telerikacademy.oop.WIM.models.contracts.items.History;

import java.util.List;

public interface Team extends Identify {

    boolean addPerson(Person personToAdd);

    boolean removePerson(Person personToRemove);

    boolean addBoard(Board boardToAdd);

    String getTeamName();

    List<Board> getTeamBoards();

    List<Person> getTeamMembers();

    List<WorkAssignable> getWorkAssignable();

    int getIndexOfBoard(String boardName);

    Board getBoard(String boardName);

    String getHistoryToStringByMember();

    String getHistoryToStringByTime();

    List<History> getTeamHistory();
}
