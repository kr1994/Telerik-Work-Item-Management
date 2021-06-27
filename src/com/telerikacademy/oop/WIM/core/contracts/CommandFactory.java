package com.telerikacademy.oop.WIM.core.contracts;

import com.telerikacademy.oop.WIM.commands._contracts.Command;

public interface CommandFactory {
    
    Command createCommand(String commandTypeAsString, WIMFactory WIMFactory,
                          WIMRepository WIMRepository);
    
}
