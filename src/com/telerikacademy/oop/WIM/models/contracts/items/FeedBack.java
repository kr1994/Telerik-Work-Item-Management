package com.telerikacademy.oop.WIM.models.contracts.items;

public interface FeedBack extends WorkItem, Comparable{

    int getRating();

    int compareTo(FeedBack fb);
}
