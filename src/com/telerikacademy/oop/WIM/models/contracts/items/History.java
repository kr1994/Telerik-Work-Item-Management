package com.telerikacademy.oop.WIM.models.contracts.items;


import java.time.LocalDateTime;

public interface History extends Comparable {

    LocalDateTime getTimestamp();

    int compareTo(History history);
}
