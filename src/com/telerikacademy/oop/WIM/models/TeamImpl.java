package com.telerikacademy.oop.WIM.models;

import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.actions.WorkAssignable;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.items.History;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;
import static com.telerikacademy.oop.WIM.models.common.Constants.*;

public class TeamImpl implements Team {

    private String teamName;
    private final List<Person> members;
    private final List<Board> boards;

    public TeamImpl(String teamName) {
        setTeamName(teamName);
        members = new ArrayList<>();
        boards = new ArrayList<>();
    }


    public int getIndexOfBoard(String boardName) {
        return boards.indexOf(getBoard(boardName));
    }

    public Board getBoard(String boardName) {
        return boards.stream()
                .filter(board -> board.getBoardName()
                        .equalsIgnoreCase(boardName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean addPerson(Person personToAdd) {
        Validator.validateNotNull(personToAdd, TEAM_MEMBER);
        if (!members.contains(personToAdd))
            return members.add(personToAdd);
        return false;
    }

    @Override
    public boolean removePerson(Person personToRemove) {
        return members.remove(personToRemove);
    }

    @Override
    public boolean addBoard(Board boardToAdd) {
        Validator.validateNotNull(boardToAdd, TEAM_BOARD);
        if (!boards.contains(boardToAdd))
            return boards.add(boardToAdd);
        throw new IllegalArgumentException(BOARD_EXISTS_IN_TEAM);
    }

    @Override
    public String getHistoryToStringByMember() {
        String title = String.format("Team %s history by members:%n", getTeamName());
        StringBuilder report = new StringBuilder();
        getTeamMembers().forEach(m -> {
            report.append(m.getHistoryToString());
            report.append(System.lineSeparator());
        });
        return title + report.toString().trim();
    }

    public String getHistoryToStringByTime() {
        String title = String.format("Team %s history by time:%n", getTeamName());
        StringBuilder report = new StringBuilder();
        getTeamHistory().forEach(h ->  report.append(h.toString()));
        return title + report.toString().trim();
    }

    @Override
    public List<History> getTeamHistory() {
        List<History> list = new ArrayList<>();
        getTeamMembers()
                .forEach(m -> list.addAll(m.getHistory()));
        return list.stream().sorted().collect(Collectors.toList());
    }

    @Override
    public String getTeamName() {
        return teamName;
    }

    @Override
    public List<Person> getTeamMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public List<Board> getTeamBoards() {
        return new ArrayList<>(boards);
    }
    @Override
    public List<WorkAssignable> getWorkAssignable() {
        List<WorkAssignable>result = new ArrayList<>(getTeamBoards());
        result.addAll(getTeamMembers());
        return result;
    }

    @Override
    public String getIdentity() {
        return getTeamName();
    }

    //private
    private void setTeamName(String teamName) {
        Validator.validateNotNull(teamName, TEAM_NAME);
        this.teamName = Validator.trimmedStringInBounds(teamName,
                TEAM_NAME,
                TEAM_NAME_MIN_SIZE,
                TEAM_NAME_MAX_SIZE)
                .toLowerCase();
    }
}
