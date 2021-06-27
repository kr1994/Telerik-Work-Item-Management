package com.telerikacademy.oop.WIM.models.contracts.items;

import com.telerikacademy.oop.WIM.models.ItemsImpl.work.WorkItemBase;
import com.telerikacademy.oop.WIM.models.common.enums.OpenField;
import com.telerikacademy.oop.WIM.models.contracts.actions.Commentable;
import com.telerikacademy.oop.WIM.models.contracts.actions.HistoryLogged;
import com.telerikacademy.oop.WIM.models.contracts.actions.Identify;

public interface WorkItem extends Comparable, Commentable, HistoryLogged, Identify {

    int getID();

    String getTitle();

    String getDescription();

    String getStatus();

    //public method used to change protected fields.
    String assignNewValueToAccessibleField(OpenField field, String newValue);

    int compareTo(WorkItem item);

    int compareTo(Identify item);
}
