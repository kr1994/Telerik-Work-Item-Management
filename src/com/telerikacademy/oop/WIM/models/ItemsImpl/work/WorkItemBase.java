package com.telerikacademy.oop.WIM.models.ItemsImpl.work;

import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.common.enums.OpenField;
import com.telerikacademy.oop.WIM.models.contracts.actions.Identify;
import com.telerikacademy.oop.WIM.models.contracts.items.Comment;
import com.telerikacademy.oop.WIM.models.contracts.items.History;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.telerikacademy.oop.WIM.models.common.Constants.*;
import static com.telerikacademy.oop.WIM.models.common.Utils.abbreviate;
import static com.telerikacademy.oop.WIM.models.common.Utils.historyChangeFromTo;

public abstract class WorkItemBase implements WorkItem {

    private static int numberOfItems = 0;

    private final int workID;
    private final List<History> workHistory;
    private final List<Comment> workCommends;

    private String title;
    private String status;                      // different enum to string for each WI
    private String description;

    public WorkItemBase(String title, String description, String status) {
        setTitle(title);
        setStatus(status);
        setDescription(description);
        this.workHistory = new ArrayList<>();
        this.workCommends = new ArrayList<>();
        this.workID = ++numberOfItems; // mast be last
    }

    //static
    public static int getNumberOfItems() {
        return numberOfItems;
    }

    @Override
    public String toString() {
        return String.format("ID %s" +
                        ", %s" +
                        ", status %s" +
                        ", description %s",
                workID,
                title,
                status,
                abbreviate(description)
        );
    }

    @Override
    public int getID() {
        return workID;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(workCommends);
    }

    @Override
    public String getCommentsToString() {
        return getComments().stream()
                .map(Comment::toString)
                .reduce("", (a, b) -> a + System.lineSeparator() + b)
                .trim();
    }

    @Override
    public List<History> getHistory() {
        return new ArrayList<>(workHistory);
    }

    @Override
    public String getHistoryToString() {
        String result = getHistory().stream()
                .map(Object::toString)
                .reduce("", (a, b) -> a + b);
        return result.trim();
    }

    @Override
    public void addHistory(History historyLog) {
        Validator.validateNotNull(historyLog, HISTORY_LOG);
        workHistory.add(historyLog);
    }

    @Override
    public void addComment(Comment commentToAdd) {
        Validator.validateNotNull(commentToAdd, COMMENT);
        workCommends.add(commentToAdd);
    }

    @Override
    public String assignNewValueToAccessibleField(OpenField field, String newValueInput) {
        Validator.validateNotNull(newValueInput, NEW_VALUE);

        String newValue = newValueInput.trim().toUpperCase();

        switch (newValue) {
            case "+":
                return progressField(field);
            case "-":
                return regressField(field);
            default:
                return changeField(field, newValue);
        }
    }

    @Override//auto generated compare only by ID
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkItemBase that = (WorkItemBase) o;
        return workID == that.workID;
    }

    @Override//auto generated to use ID only
    public int hashCode() {
        return Objects.hash(workID);
    }

    @Override
    public int compareTo(WorkItem item) {
        String current = getTitle();
        String given = item.getTitle();
        int minSize = Math.min(current.length(), given.length());

        for (int i = 0; i < minSize; i++) {
            if (current.charAt(i) == given.charAt(i))
                continue;
            return current.charAt(i) > given.charAt(i) ? 1 : -1;
        }
        if (current.length() == given.length())
            return compareTo((Identify) item);
        return current.length() > given.length() ? 1 : -1;
    }

    @Override
    public int compareTo(Identify item) {
        int thisID = Integer.parseInt(getIdentity());
        int itemID = Integer.parseInt(item.getIdentity());
        return thisID > itemID ? 1 : -1;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public String getIdentity() {
        return "" + workID;
    }

    //protected
    protected String assignStatus(String status) {
        String old = this.status;
        this.status = status;
        return historyChangeFromTo(WORK + " " + getID(),
                OpenField.STATUS.toString(),
                old,
                this.status);
    }

    //private
    private void setDescription(String description) {
        Validator.validateNotNull(description, DESCRIPTION);
        this.description = Validator.trimmedStringInBounds(description,
                DESCRIPTION,
                takeDescriptionMinSize(),
                takeDescriptionMaxSize());
    }

    private void setTitle(String title) {
        Validator.validateNotNull(title, TITLE);
        this.title = Validator.trimmedStringInBounds(title,
                TITLE,
                takeTitleMinSize(),
                takeTitleMaxSize());
    }

    private void setStatus(String status) {
        Validator.validateNotNull(status, STATUS);
        this.status = takeStatus(status);
    }

    //abstract
    protected abstract String progressField(OpenField field);

    protected abstract String regressField(OpenField field);

    protected abstract String changeField(OpenField field, String newValue);

    protected abstract String takeStatus(String statusToGet);

    protected abstract int takeTitleMinSize();

    protected abstract int takeTitleMaxSize();

    protected abstract int takeDescriptionMinSize();

    protected abstract int takeDescriptionMaxSize();

}
