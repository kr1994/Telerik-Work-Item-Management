package com.telerikacademy.oop.WIM.models.contracts.items;

import com.telerikacademy.oop.WIM.models.common.enums.StorySize;

public interface Story extends Task, Comparable {

    StorySize getSize();

    int compareTo(Story story);

}
