package com.telerikacademy.oop.WIM.models;

import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.telerikacademy.oop.WIM.CommonConstants.LEN_10;

public class AdminImplTests {

    WIMRepository repository = new WIMRepositoryImpl();

    @Test
    public void Constructor_Should_CreatePerson_Whit_CorrectInput() {
        Admin person = new AdminImpl(LEN_10,"12345678901234");

        Assertions.assertEquals(LEN_10, person.getName());
        Assertions.assertEquals("12345678901234", person.getTeam());
    }
    @Test

    public void Logout_Should_NullifyTeamFromAdmin_Whit_CorrectInput() {
        Admin person = new AdminImpl(LEN_10,"12345678901234");

        person.logOut();
        Assertions.assertNull(person.getTeam());
    }

}
