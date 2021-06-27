package com.telerikacademy.oop.WIM.commands.showingCommands;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;

import java.util.List;

public class ShowAllTeams implements Command {

    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;

    public ShowAllTeams(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        return implement();
    }

    private String implement() {
        return WIMRepository.getTeams()
                .keySet()
                .toString();
    }
}