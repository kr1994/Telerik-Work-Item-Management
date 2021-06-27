package com.telerikacademy.oop.WIM.models;

import com.telerikacademy.oop.WIM.models.contracts.Admin;

public class AdminImpl extends PersonImpl implements Admin {

    private String teamName;

    public AdminImpl(String name, String teamName) {
        super(name);
        setTeam(teamName);
    }

    @Override
    public String getTeam() {
        return teamName;
    }

    @Override
    public void logOut() {
        setTeam(null);
    }

    //private
    private void setTeam(String teamNameInput) {
        teamName = teamNameInput;
    }
}
