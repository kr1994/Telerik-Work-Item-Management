package com.telerikacademy.oop.WIM.models.ItemsImpl.work;

import com.telerikacademy.oop.WIM.models.common.enums.OpenField;
import com.telerikacademy.oop.WIM.models.common.enums.Priority;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.actions.Identify;
import com.telerikacademy.oop.WIM.models.contracts.items.Task;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;

import static com.telerikacademy.oop.WIM.models.common.Constants.TASK;
import static com.telerikacademy.oop.WIM.models.common.Utils.*;
import static com.telerikacademy.oop.WIM.models.common.enums.Priority.lastPriority;
import static com.telerikacademy.oop.WIM.models.common.enums.Priority.nextPriority;

public abstract class TaskBase extends WorkItemBase implements Task {

    private Priority priority;
    private Person assignee;

    public TaskBase(String title, String description, String status,
                    Priority priority, Person assignee) {

        super(title, description, status);
        assignAssignee(assignee);
        this.priority = priority;
    }


    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Person getAssignee() {
        return assignee;
    }

    @Override
    public void assignAssignee(Person assignee) {
        // assignee can be set to null
        this.assignee = assignee;
    }

    //protected
    @Override //  methods used to change protected fields.
    protected String changeField(OpenField field, String newValue) {
        if (field.equals(OpenField.PRIORITY)) {
            try {
                Priority newPriority = Priority
                        .valueOf(newValue.toUpperCase());

                return assignPriority(newPriority);

            } catch (IllegalArgumentException e) {
                return cantAssign(newValue, OpenField.PRIORITY);
            }
        }
        return wrongField(TASK, field);
    }

    @Override
    protected String progressField(OpenField field) {
        if (field.equals(OpenField.PRIORITY))
            return assignPriority(nextPriority(getPriority()));
        return wrongField(TASK, field);
    }

    @Override
    protected String regressField(OpenField field) {
        if (field.equals(OpenField.PRIORITY))
            return assignPriority(lastPriority(getPriority()));
        return wrongField(TASK, field);
    }

    @Override
    public String toString() {
        return String.format("%s"
                        + ", priority %s"
                        + ", assignee %s",
                super.toString(),
                priority.toString(),
                assignee.getName()
        );
    }

    @Override
    public int compareTo(Task task) {
        int current = this.getPriority().ordinal();
        int given = task.getPriority().ordinal();
        if (current == given)
            return compareTo((Identify) task);
        return current > given ? 1 : -1;
    }

    @Override
    public int compareTo(Object o) {
       return compareTo((WorkItem) o);
    }

    //private
    private String assignPriority(Priority newValue) {
        if (newValue.equals(priority))
            return alreadyDone(newValue.toString(), OpenField.PRIORITY);

        Priority old = priority;
        priority = newValue;
        return historyChangeFromTo(TASK + " "+ getID(),
                OpenField.PRIORITY.toString(),
                old.toString(),
                priority.toString());
    }
}