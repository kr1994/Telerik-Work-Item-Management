package com.telerikacademy.oop.WIM.models.contracts.actions;

import com.telerikacademy.oop.WIM.models.contracts.Person;

public interface Assignable {

    void assignAssignee(Person assignee);

    Person getAssignee();
}
