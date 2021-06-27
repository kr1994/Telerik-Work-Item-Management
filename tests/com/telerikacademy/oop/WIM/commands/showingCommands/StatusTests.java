package com.telerikacademy.oop.WIM.commands.showingCommands;

import com.telerikacademy.oop.WIM.commands.showingCommands.Status;
import com.telerikacademy.oop.WIM.commands.showingCommands.listing.WIMFilterBy;
import com.telerikacademy.oop.WIM.commands.showingCommands.listing.WIMSortBy;
import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.BugImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.WorkItemBase;
import com.telerikacademy.oop.WIM.models.TeamImpl;
import com.telerikacademy.oop.WIM.models.common.enums.BugStatus;
import com.telerikacademy.oop.WIM.models.contracts.items.Bug;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.WIM.CommonConstants.*;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.OFF;

public class StatusTests {
    WIMRepositoryImpl repo = new WIMRepositoryImpl();
    Status status = new Status(factory, repo);

    @Test
    public void Command_Should_GiveCorrectResult_When_Called()  {
        WIMFilterBy filterReset = new WIMFilterBy(factory,repo);
        filterReset.execute(List.of("off"));
        WIMSortBy sortReset = new WIMSortBy(factory,repo);
        sortReset.execute(List.of("off"));

        Bug newBug = new BugImpl(LEN_10, LEN_10, BugStatus.FIXED.toString(), priority,
                person, severity, steps);
        repo.setAuthor(admin);
        repo.addPerson(admin);
        repo.addTeam(new TeamImpl(LEN_15));
        repo.addTeam(new TeamImpl("MyTeam"));
        repo.addBug(newBug);

        String expected = String.format("Current user: %s%n" +
                        "number of teams: %d%n" +
                        "number of people: %d%n" +
                        "number of work items %d%n" +
                        "filter By Assignee - %s%n" +
                        "filter by Status - %s%n" +
                        "Sorting - %s",
                LEN_10, //repo.getAuthor().toString(),
                2, //repo.getTeams().size(),
                1,//repo.getPeople().size(),
                WorkItemBase.getNumberOfItems(),//repo.getWork().size(),
                OFF,//WIMFilterBy.filterAssigneeBy(),
                OFF,//WIMFilterBy.filterStatusBy(),
                OFF//WIMSortBy.showFormat()
        );

        String actual = status.execute(new ArrayList<>());

        Assertions.assertEquals(expected,actual);
    }
}
