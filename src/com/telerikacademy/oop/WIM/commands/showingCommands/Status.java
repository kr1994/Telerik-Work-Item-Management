package com.telerikacademy.oop.WIM.commands.showingCommands;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.commands.showingCommands.listing.WIMFilterBy;
import com.telerikacademy.oop.WIM.commands.showingCommands.listing.WIMSortBy;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.WorkItemBase;

import java.util.List;

public class Status implements Command {

    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;

    public Status(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        return String.format("Current user: %s%n" +
                        "number of teams: %d%n" +
                        "number of people: %d%n" +
                        "number of work items %d%n" +
                        "filter By Assignee - %s%n" +
                        "filter by Status - %s%n" +
                        "Sorting - %s",
                WIMRepository.getAuthor().toString(),
                WIMRepository.getTeams().size(),
                WIMRepository.getPeople().size(),
                WorkItemBase.getNumberOfItems(),
                WIMFilterBy.filterAssigneeBy(),
                WIMFilterBy.filterStatusBy(),
                WIMSortBy.showFormat()
        );
    }

}
