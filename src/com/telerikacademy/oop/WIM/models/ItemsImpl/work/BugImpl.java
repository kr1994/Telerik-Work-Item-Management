package com.telerikacademy.oop.WIM.models.ItemsImpl.work;

import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.common.enums.*;
import com.telerikacademy.oop.WIM.models.contracts.actions.Identify;
import com.telerikacademy.oop.WIM.models.contracts.items.Bug;
import com.telerikacademy.oop.WIM.models.contracts.Person;

import static com.telerikacademy.oop.WIM.models.common.Constants.*;
import static com.telerikacademy.oop.WIM.models.common.Utils.*;
import static com.telerikacademy.oop.WIM.models.common.enums.BugStatus.lastBugStatus;
import static com.telerikacademy.oop.WIM.models.common.enums.BugStatus.nextBugStatus;
import static com.telerikacademy.oop.WIM.models.common.enums.Severity.lastSeverity;
import static com.telerikacademy.oop.WIM.models.common.enums.Severity.nextSeverity;

import java.util.List;


public class BugImpl extends TaskBase implements Bug {

    private Severity severity;
    private List<String> stepsToReproduce;

    public BugImpl(String title, String description, String status, Priority priority,
                   Person assignee, Severity severity, List<String> stepsToReproduce) {

        super(title, description, status, priority, assignee);
        setSteps(stepsToReproduce);
        this.severity = severity;
    }

    @Override
    public List<String> getSteps() {
        return List.copyOf(stepsToReproduce);
    }

    @Override
    public Severity getSeverity() {
        return severity;
    }

    public String toString() {
        return String.format("%s %s" +
                        ", severity %s" +
                        ", steps %s%n",
                capitaliseFirst(WIType.BUG.toString()),
                super.toString(),
                getSeverity().toString(),
                getSteps().toString());
    }

    @Override
    public int compareTo(Bug bug) {
        int current = getSeverity().ordinal();
        int given = bug.getSeverity().ordinal();
        if (current == given)
            return compareTo((Identify) bug);
        return current > given ? 1 : -1;
    }

    //protected
    @Override
    protected String changeField(OpenField field, String newValue) {
        switch (field) {
            case SEVERITY:
                try {
                    Severity newSeverity = Severity.valueOf(newValue.toUpperCase());
                    return assignSeverity(newSeverity);

                } catch (IllegalArgumentException e) {
                    return cantAssign(newValue, OpenField.STATUS);
                }
            case STATUS:
                if (newValue.equalsIgnoreCase(getStatus())) {
                    return alreadyDone(newValue, OpenField.STATUS);
                }
                return assignStatus(takeStatus(newValue));
            default:
                return super.changeField(field, newValue);
        }
    }

    @Override
    protected String progressField(OpenField field) {
        switch (field) {
            case SEVERITY:
                return assignSeverity(nextSeverity(getSeverity()));
            case STATUS:
                String current = getStatus();
                if (current.equalsIgnoreCase(BugStatus.FIXED.toString()))
                    return alreadyDone(current, OpenField.STATUS);

                return assignStatus(nextBugStatus(current));
            default:
                return super.progressField(field);
        }
    }

    @Override
    protected String regressField(OpenField field) {
        switch (field) {
            case SEVERITY:
                return assignSeverity(lastSeverity(getSeverity()));
            case STATUS:
                String current = getStatus();
                if (current.equalsIgnoreCase(BugStatus.ACTIVE.toString()))
                    return alreadyDone(current, OpenField.STATUS);

                return assignStatus(lastBugStatus(current));
            default:
                return super.regressField(field);
        }
    }

    @Override
    protected String takeStatus(String status) {
        try {
            return BugStatus.valueOf(status.toUpperCase()).toString();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format(ERR_CANT_ASSIGN,
                    status,
                    "BugStatus")
            );
        }
    }

    @Override
    protected int takeTitleMinSize() {
        return BUG_TITLE_MIN_SIZE;
    }

    @Override
    protected int takeTitleMaxSize() {
        return BUG_TITLE_MAX_SIZE;
    }

    @Override
    protected int takeDescriptionMinSize() {
        return BUG_DESCRIPTION_MIN_SIZE;
    }

    @Override
    protected int takeDescriptionMaxSize() {
        return BUG_DESCRIPTION_MAX_SIZE;
    }

    //private
    private void setSteps(List<String> stepsToReproduce) {
        Validator.validateNotNull(stepsToReproduce, STEPS);
        if (stepsToReproduce.size() == 0)
            throw new IllegalArgumentException(String.format(NULL_ERR, STEPS));

        this.stepsToReproduce = List.copyOf(stepsToReproduce);
    }

    private String assignSeverity(Severity newValue) {
        if (newValue.equals(severity))
            return alreadyDone(newValue.toString(), OpenField.SEVERITY);
        Severity old = severity;
        severity = newValue;
        return historyChangeFromTo(WIType.BUG.toString()+" "+getID(),
                OpenField.SEVERITY.toString(),
                old.toString(),
                severity.toString());
    }
}


