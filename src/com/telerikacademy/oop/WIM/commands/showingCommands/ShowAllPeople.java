package com.telerikacademy.oop.WIM.commands.showingCommands;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;

import java.util.List;

public class ShowAllPeople implements Command {

    private final WIMRepository WIMRepository;

    public ShowAllPeople(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMRepository = WIMRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        return WIMRepository
                .getPeople()
                .keySet()
                .toString();
    }
}
