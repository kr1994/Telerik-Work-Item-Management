package com.telerikacademy.oop.WIM.core.factories;

import com.telerikacademy.oop.WIM.commands.*;
import com.telerikacademy.oop.WIM.commands.AddingAssigningCommands.*;
import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.commands._enums.CommandType;
import com.telerikacademy.oop.WIM.commands.showingCommands.*;
import com.telerikacademy.oop.WIM.commands.showingCommands.listing.*;
import com.telerikacademy.oop.WIM.core.contracts.CommandFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;

public class CommandFactoryImpl implements CommandFactory {

    private static final String INVALID_COMMAND = "Invalid command name: %s!";

    @Override
    public Command createCommand(String commandTypeAsString,
                                 WIMFactory factory,
                                 WIMRepository repository) {

        CommandType commandType = CommandType.valueOf(commandTypeAsString.toUpperCase());

        switch (commandType) {

            case LOGIN:
                return new Login(factory, repository);
            case LOGOUT:
                return new Logout(factory, repository);
            case STATUS:
                return new Status(factory, repository);
            case REGISTERNEWPERSON:
                return new RegisterNewPerson(factory, repository);
            case SHOWALLPEOPLE:
                return new ShowAllPeople(factory, repository);
            case ADDPERSONTOTEAM:
                return new AddPersonToTeam(factory, repository);
            case ASSIGNWORKTOPERSON:
                return new AssignWorkToPerson(factory, repository);
            case UNASSIGNWORKTOPERSON:
                return new UnAssignWorkToPerson(factory, repository);
            case REGISTERNEWTEAM:
                return new RegisterNewTeam(factory, repository);
            case SHOWALLTEAMS:
                return new ShowAllTeams(factory, repository);
            case SHOWMEMBERSINTEAM:
                return new ShowMembersInTeam(factory, repository);
            case ASSIGNWORKTOTEAM:
                return new AssignWorkToTeam(factory, repository);
            case UNASSIGNWORKTOTEAM:
                return new UnAssignWorkToTeam(factory, repository);
            case SHOWTEAMBOARDS:
                return new ShowTeamBoards(factory, repository);
            case ADDCOMMENT:
                return new AddComment(factory, repository);
            case SHOWTEAMACTIVITY:
                return new ShowTeamActivity(factory, repository);
            case CREATEITEMINBOARD:
                return new CreateItemInBoard(factory, repository);
            case CHANGEWISTATE:
                return new ChangeWIState(factory, repository);
            case CREATEBOARDINTEAM:
                return new CreateBoardInTeam(factory,repository);
            case WIMLISTOF:
                return new WIMListOf(factory,repository);
            case WIMFILTERBY:
                return new WIMFilterBy(factory,repository);
            case WIMSORTBY:
                return new WIMSortBy(factory,repository);
            case SHOWBOARDACTIVITY:
                return new ShowBoardActivity(factory, repository);
        }
        throw new IllegalArgumentException(String.format(INVALID_COMMAND, commandTypeAsString));
    }

}