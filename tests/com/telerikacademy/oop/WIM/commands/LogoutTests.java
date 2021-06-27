package com.telerikacademy.oop.WIM.commands;

import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.BugImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.telerikacademy.oop.WIM.CommonConstants.*;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.LOGGED_OUT;

public class LogoutTests {

    Logout logout = new Logout(factory, repository);

    @Test
    public void Command_Should_ReturnCorrectMessage_When_Called() {

        Assertions.assertEquals(LOGGED_OUT,
                logout.execute(new ArrayList<>()));
    }

}
