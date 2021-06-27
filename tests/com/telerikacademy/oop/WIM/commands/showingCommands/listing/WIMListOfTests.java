package com.telerikacademy.oop.WIM.commands.showingCommands.listing;

import com.telerikacademy.oop.WIM.commands.showingCommands.listing.WIMListOf;
import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.telerikacademy.oop.WIM.CommonConstants.factory;

public class WIMListOfTests {
    WIMRepository repository;
    WIMListOf listOf;


    @BeforeEach
    public void bootUp() {
        repository = new WIMRepositoryImpl();
        listOf = new WIMListOf(factory,repository);
    }


    @Test
    public void ListOf_Should_Throw_Whit_IncorrectInput() {

        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> listOf.execute(List.of("wrong")));
    }


}
