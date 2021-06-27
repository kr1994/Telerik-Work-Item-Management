package com.telerikacademy.oop.WIM.models.contracts;

import com.telerikacademy.oop.WIM.models.contracts.actions.HistoryLogged;
import com.telerikacademy.oop.WIM.models.contracts.actions.Identify;
import com.telerikacademy.oop.WIM.models.contracts.actions.WorkAssignable;
import com.telerikacademy.oop.WIM.models.contracts.items.History;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;

import java.util.ArrayList;
import java.util.List;

public interface Person extends WorkAssignable, HistoryLogged, Identify {

    String getName();

}
