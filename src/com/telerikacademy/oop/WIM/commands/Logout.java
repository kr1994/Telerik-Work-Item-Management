package com.telerikacademy.oop.WIM.commands;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.common.Validator;

import java.util.List;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.LOGGED_OUT;

public class Logout implements Command {

    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;


    public Logout(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        //WIMRepository.getAuthor().logOut();
        //WIMRepository.setAuthor(null);
        //logout is done after taking history in HistoryLogger.takeLogPerson
        return LOGGED_OUT;
    }
}
