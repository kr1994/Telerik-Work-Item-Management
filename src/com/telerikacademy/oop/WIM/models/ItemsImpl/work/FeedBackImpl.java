package com.telerikacademy.oop.WIM.models.ItemsImpl.work;

import com.telerikacademy.oop.WIM.models.common.enums.FeedBackStatus;
import com.telerikacademy.oop.WIM.models.common.enums.OpenField;
import com.telerikacademy.oop.WIM.models.common.enums.WIType;
import com.telerikacademy.oop.WIM.models.contracts.actions.Identify;
import com.telerikacademy.oop.WIM.models.contracts.items.FeedBack;

import static com.telerikacademy.oop.WIM.models.common.Constants.*;
import static com.telerikacademy.oop.WIM.models.common.Utils.*;
import static com.telerikacademy.oop.WIM.models.common.enums.FeedBackStatus.lastFBStatus;
import static com.telerikacademy.oop.WIM.models.common.enums.FeedBackStatus.nextFBStatus;

public class FeedBackImpl extends WorkItemBase implements FeedBack {

    private int rating;

    public FeedBackImpl(String title, String description, String status, int rating) {

        super(title, description, status);
        this.rating = rating;
    }


    @Override
    public int getRating() {
        return rating;
    }

    public String toString() {
        return String.format("%s %s"
                        + ", rating %d%n",
                capitaliseFirst(WIType.FEEDBACK.toString()),
                super.toString(),
                getRating());
    }

    @Override
    public int compareTo(FeedBack fb) {
        int current = this.getRating();
        int given = fb.getRating();
        if (current == given)
            return compareTo((Identify) fb);
        return current > given ? 1 : -1;
    }

    //protected
    @Override
    protected String changeField(OpenField field, String newValue) {
        switch (field) {
            case RATING:
                try {
                    int newRating = Integer.parseInt(newValue);
                    return setRating(newRating);
                } catch (NumberFormatException e) {
                    return FB_RATTING_MAST_BE_INTEGER;
                }
            case STATUS:
                return assignStatus(takeStatus(newValue));
            default:
                return wrongField(WIType.FEEDBACK.toString(), field);
        }
    }

    @Override
    protected String progressField(OpenField field) {
        switch (field) {
            case RATING:
                return setRating(rating + 1);
            case STATUS:
                String current = getStatus();
                if (current.equalsIgnoreCase(
                        FeedBackStatus.DONE.toString()))
                    return alreadyDone(current, OpenField.STATUS);

                return assignStatus(nextFBStatus(current));
            default:
                return wrongField(WIType.FEEDBACK.toString(), field);
        }
    }

    @Override
    protected String regressField(OpenField field) {
        switch (field) {
            case RATING:
                return setRating(rating - 1);
            case STATUS:
                String current = getStatus();
                if (current.equalsIgnoreCase(
                        FeedBackStatus.NEW.toString()))
                    return alreadyDone(current, OpenField.STATUS);

                return assignStatus(lastFBStatus(current));
            default:
                return wrongField(WIType.FEEDBACK.toString(), field);
        }
    }

    @Override
    protected String takeStatus(String status) {
        try {
            return FeedBackStatus.valueOf(status.toUpperCase()).toString();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format(ERR_CANT_ASSIGN,
                    status,
                    "Feedback status")
            );
        }
    }

    @Override
    protected int takeTitleMinSize() {
        return FB_TITLE_MIN_SIZE;
    }

    @Override
    protected int takeTitleMaxSize() {
        return FB_TITLE_MAX_SIZE;
    }

    @Override
    protected int takeDescriptionMinSize() {
        return FB_DESCRIPTION_MIN_SIZE;
    }

    @Override
    protected int takeDescriptionMaxSize() {
        return FB_DESCRIPTION_MAX_SIZE;
    }

    //private
    private String setRating(int newRating) {
        if (newRating == rating)
            return alreadyDone("" + newRating, OpenField.RATING);
        int old = rating;
        rating = newRating;
        return historyChangeFromTo(WIType.FEEDBACK.toString() + " " + getID(),
                OpenField.RATING.toString(),
                "" + old,
                "" + rating);
    }
}