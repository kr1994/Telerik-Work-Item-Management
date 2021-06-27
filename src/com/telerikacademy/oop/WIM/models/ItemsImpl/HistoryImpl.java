package com.telerikacademy.oop.WIM.models.ItemsImpl;

import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.items.History;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryImpl implements History {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss");
    private final LocalDateTime timestamp;
    private final String description;
    private final String authorName;

    public HistoryImpl(Admin author, String description) {
        this.timestamp = LocalDateTime.now();
        this.description = description;
        authorName = author.toString();
    }

    // [data] Author effect placeOfChange form oldState to newState.
    public String toString() {
        return String.format("[%s] %s %s%n",
                timestamp.format(formatter),
                authorName,
                description);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(History history) {
        return getTimestamp().isAfter(history.getTimestamp()) ? 1 : -1;
    }

    @Override
    public int compareTo(Object o) {
        History history = (History) o;
        return compareTo(history);
    }
}
