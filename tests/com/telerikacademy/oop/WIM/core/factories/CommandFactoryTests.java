package com.telerikacademy.oop.WIM.core.factories;

import com.telerikacademy.oop.WIM.commands.AddingAssigningCommands.*;
import com.telerikacademy.oop.WIM.commands.ChangeWIState;
import com.telerikacademy.oop.WIM.commands.Login;
import com.telerikacademy.oop.WIM.commands.Logout;
import com.telerikacademy.oop.WIM.commands.showingCommands.*;
import com.telerikacademy.oop.WIM.commands.showingCommands.listing.WIMFilterBy;
import com.telerikacademy.oop.WIM.commands.showingCommands.listing.WIMListOf;
import com.telerikacademy.oop.WIM.commands.showingCommands.listing.WIMSortBy;
import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.core.contracts.CommandFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CommandFactoryTests {

    WIMRepository repository;
    CommandFactory commandFactory;
    WIMFactory factory;
    String input, expStr, actualStr;

    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        commandFactory = new CommandFactoryImpl();
        factory = new WIMFactoryImpl();
        input = expStr = actualStr = "";
    }

    @Test
    public void Login() {
        input = "login";
        expStr = new Login(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void logout() {
        input = "logout";
        expStr = new Logout(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void Status() {
        input = "Status";
        expStr = new Status(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void RegisterNewPerson() {
        input = "RegisterNewPerson";
        expStr = new RegisterNewPerson(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void ShowAllPeople() {
        input = "ShowAllPeople";
        expStr = new ShowAllPeople(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void AddPersonToTeam() {
        input = "AddPersonToTeam";
        expStr = new AddPersonToTeam(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void AssignWorkToPerson() {
        input = "AssignWorkToPerson";
        expStr = new AssignWorkToPerson(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void UnAssignWorkToPerson() {
        input = "UnAssignWorkToPerson";
        expStr = new UnAssignWorkToPerson(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void RegisterNewTeam() {
        input = "RegisterNewTeam";
        expStr = new RegisterNewTeam(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void ShowAllTeams() {
        input = "ShowAllTeams";
        expStr = new ShowAllTeams(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void ShowMembersInTeam() {
        input = "ShowMembersInTeam";
        expStr = new ShowMembersInTeam(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void AssignWorkToTeam() {
        input = "AssignWorkToTeam";
        expStr = new AssignWorkToTeam(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void UnAssignWorkToTeam() {
        input = "UnAssignWorkToTeam";
        expStr = new UnAssignWorkToTeam(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void ShowTeamBoards() {
        input = "ShowTeamBoards";
        expStr = new ShowTeamBoards(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void AddComment() {
        input = "AddComment";
        expStr = new AddComment(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void ShowTeamActivity() {
        input = "ShowTeamActivity";
        expStr = new ShowTeamActivity(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void CreateItemInBoard() {
        input = "CreateItemInBoard";
        expStr = new CreateItemInBoard(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void ChangeWIState() {
        input = "ChangeWIState";
        expStr = new ChangeWIState(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void CreateBoardInTeam() {
        input = "CreateBoardInTeam";
        expStr = new CreateBoardInTeam(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void WIMListOf() {
        input = "WIMListOf";
        expStr = new WIMListOf(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void WIMFilterBy() {
        input = "WIMFilterBy";
        expStr = new WIMFilterBy(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void WIMSortBy() {
        input = "WIMSortBy";
        expStr = new WIMSortBy(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void ShowBoardActivity() {
        input = "ShowBoardActivity";
        expStr = new ShowBoardActivity(factory, repository).toString();
        actualStr = commandFactory.createCommand(input, factory, repository).toString();

        expStr = expStr.substring(0, expStr.indexOf("@"));
        actualStr = actualStr.substring(0, actualStr.indexOf("@"));

        Assertions.assertEquals(expStr, actualStr);
    }

    @Test
    public void CommandFactory_Should_Throw_Whit_InvalidInput() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> commandFactory.createCommand(
                        "InvalidInput",
                        factory,
                        repository)
        );
    }





/*


            case WIMLISTOF:
                return new WIMListOf(factory,repository);
            case WIMFILTERBY:
                return new WIMFilterBy(factory,repository);
            case WIMSORTBY:
                return new WIMSortBy(factory,repository);
            case SHOWBOARDACTIVITY:
                return new ShowBoardActivity(factory, repository);
*/
}
