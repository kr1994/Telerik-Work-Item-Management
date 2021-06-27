package com.telerikacademy.oop.WIM.models.ItemsImpl.work;

import com.telerikacademy.oop.WIM.models.common.enums.*;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.actions.Identify;
import com.telerikacademy.oop.WIM.models.contracts.items.Story;

import static com.telerikacademy.oop.WIM.models.common.Constants.*;
import static com.telerikacademy.oop.WIM.models.common.Utils.*;
import static com.telerikacademy.oop.WIM.models.common.enums.StorySize.lastStorySize;
import static com.telerikacademy.oop.WIM.models.common.enums.StorySize.nextStorySize;
import static com.telerikacademy.oop.WIM.models.common.enums.StoryStatus.lastStoryStatus;
import static com.telerikacademy.oop.WIM.models.common.enums.StoryStatus.nextStoryStatus;

public class StoryImpl extends TaskBase implements Story {

    private StorySize size;

    public StoryImpl(String title, String description, String status,
                     Priority priority, Person assignee, StorySize size) {

        super(title, description, status, priority, assignee);
        this.size = size;
    }

    @Override
    public StorySize getSize() {
        return size;
    }

    @Override
    public String toString() {
        return String.format("%s %s" +
                        ", size %s%n",
                capitaliseFirst(WIType.STORY.toString()),
                super.toString(),
                size.toString()
        );
    }

    @Override
    public int compareTo(Story story) {
        int current = this.getSize().ordinal();
        int given = story.getSize().ordinal();
        if (current == given)
            return compareTo((Identify) story);
        return current > given ? 1 : -1;
    }

    //protected
    @Override
    protected String changeField(OpenField field, String newValue) {
        switch (field) {
            case SIZE:
                try {
                    StorySize newSize = StorySize.valueOf(
                            newValue.toUpperCase());
                    return setSize(newSize);
                } catch (IllegalArgumentException e) {
                    return cantAssign(newValue, OpenField.SIZE);
                }
            case STATUS:
                if (newValue.equalsIgnoreCase(getStatus()))
                    return alreadyDone(newValue, OpenField.STATUS);
                return assignStatus(takeStatus(newValue));
            default:
                return super.changeField(field, newValue);
        }
    }

    @Override
    protected String progressField(OpenField field) {
        switch (field) {
            case SIZE:
                return setSize(nextStorySize(getSize()));
            case STATUS:
                String current = getStatus();
                if (current.equalsIgnoreCase(
                        StoryStatus.DONE.toString()))
                    return alreadyDone(current, OpenField.STATUS);
                return assignStatus(nextStoryStatus(getStatus()));
            default:
                return super.progressField(field);
        }
    }

    @Override
    protected String regressField(OpenField field) {
        switch (field) {
            case SIZE:
                return setSize(lastStorySize(getSize()));
            case STATUS:
                String current = getStatus();
                if (current.equalsIgnoreCase(
                        StoryStatus.NOTDONE.toString()))
                    return alreadyDone(current, OpenField.STATUS);

                return assignStatus(lastStoryStatus(getStatus()));
            default:
                return regressField(field);
        }
    }

    @Override
    protected String takeStatus(String status) {
        try {
            return StoryStatus.valueOf(status.toUpperCase()).toString();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format(ERR_CANT_ASSIGN,
                    status,
                    "Story")
            );
        }
    }

    @Override
    protected int takeTitleMinSize() {
        return STORY_TITLE_MIN_SIZE;
    }

    @Override
    protected int takeTitleMaxSize() {
        return STORY_TITLE_MAX_SIZE;
    }

    @Override
    protected int takeDescriptionMinSize() {
        return STORY_DESCRIPTION_MIN_SIZE;
    }

    @Override
    protected int takeDescriptionMaxSize() {
        return STORY_DESCRIPTION_MAX_SIZE;
    }

    //private
    private String setSize(StorySize newValue) {
        if (newValue.equals(size))
            return alreadyDone(newValue.toString(), OpenField.SIZE);
        StorySize old = size;
        size = newValue;
        return historyChangeFromTo(WIType.STORY.toString() + " " + getID(),
                OpenField.SIZE.toString(),
                old.toString(),
                size.toString());

    }
}



