package com.telerikacademy.oop.WIM.models.contracts.items;

import com.telerikacademy.oop.WIM.models.common.enums.Priority;
import com.telerikacademy.oop.WIM.models.contracts.actions.Assignable;
import com.telerikacademy.oop.WIM.models.contracts.Person;

public interface Task extends WorkItem, Assignable, Comparable {

    Person getAssignee();

    Priority getPriority();

    int compareTo(Task task);
}
