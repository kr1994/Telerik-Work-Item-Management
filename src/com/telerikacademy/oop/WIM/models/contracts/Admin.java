package com.telerikacademy.oop.WIM.models.contracts;

import com.telerikacademy.oop.WIM.models.contracts.actions.HistoryLogged;

public interface Admin extends Person {

    String getTeam();

    void logOut();
}
