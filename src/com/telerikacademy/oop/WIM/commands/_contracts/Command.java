package com.telerikacademy.oop.WIM.commands._contracts;

import java.util.List;

public interface Command {

    String execute(List<String> parameters);

}