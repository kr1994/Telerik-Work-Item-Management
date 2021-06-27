package com.telerikacademy.oop.WIM.models.contracts.items;

import com.telerikacademy.oop.WIM.models.common.enums.Severity;

import java.util.List;

public interface Bug extends Task, Comparable {

    List<String> getSteps();

    Severity getSeverity();

    int compareTo(Bug bug);

}
