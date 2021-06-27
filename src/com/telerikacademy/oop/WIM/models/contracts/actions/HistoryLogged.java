package com.telerikacademy.oop.WIM.models.contracts.actions;

import com.telerikacademy.oop.WIM.models.contracts.items.History;

import java.util.List;

public interface HistoryLogged {
    List<History> getHistory();

    String getHistoryToString();

    void addHistory(History historyLog);

}
