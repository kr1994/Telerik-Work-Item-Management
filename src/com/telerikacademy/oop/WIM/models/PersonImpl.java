package com.telerikacademy.oop.WIM.models;

import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.items.History;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.PERSON_NAME;
import static com.telerikacademy.oop.WIM.models.common.Constants.*;
import static com.telerikacademy.oop.WIM.models.common.Utils.capitaliseFirst;

public class PersonImpl implements Person {

    private String name;
    private final List<WorkItem> work;
    private final List<History> history;


    public PersonImpl(String name) {
        setName(name);
        work = new ArrayList<>();
        history = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<WorkItem> getWork() {
        return new ArrayList<>(work);
    }

    @Override
    public boolean assignWork(WorkItem workToDo) {
        Validator.validateNotNull(workToDo, WORK);
        if (!work.contains(workToDo))
            return work.add(workToDo);

        return false;

    }

    @Override
    public boolean unAssignWork(WorkItem workToLeave) {
        if (workToLeave != null)
            return work.remove(workToLeave);
        return false;
    }

    @Override
    public List<History> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public String getHistoryToString() {
        return getHistory().stream()
                .map(Object::toString)
                .reduce("", (a, b) -> a + b)
                .trim();
    }

    @Override
    public void addHistory(History historyLog) {
        Validator.validateNotNull(historyLog, HISTORY_LOG);
        history.add(historyLog);
    }

    @Override
    public String toString() {
        return capitaliseFirst(name);
    }

    @Override
    public String getIdentity() {
        return getName();
    }

    @Override// auto generated equals by name only
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonImpl person = (PersonImpl) o;
        return name.equals(person.name);
    }

    @Override//auto generated
    public int hashCode() {
        return Objects.hash(name);
    }

    //protected
    protected void setName(String name) {
        Validator.validateNotNull(name, PERSON_NAME);
        this.name = Validator.trimmedStringInBounds(name,
                PERSON_NAME,
                USERNAME_MIN_SIZE,
                USERNAME_MAX_SIZE)
                .toLowerCase();
    }
}
