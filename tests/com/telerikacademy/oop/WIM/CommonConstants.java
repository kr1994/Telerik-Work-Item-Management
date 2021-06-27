package com.telerikacademy.oop.WIM;

import com.telerikacademy.oop.WIM.core.WIMRepositoryImpl;
import com.telerikacademy.oop.WIM.core.factories.WIMFactoryImpl;
import com.telerikacademy.oop.WIM.models.AdminImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.BoardImpl;
import com.telerikacademy.oop.WIM.models.ItemsImpl.work.BugImpl;
import com.telerikacademy.oop.WIM.models.PersonImpl;
import com.telerikacademy.oop.WIM.models.TeamImpl;
import com.telerikacademy.oop.WIM.models.common.enums.BugStatus;
import com.telerikacademy.oop.WIM.models.common.enums.Priority;
import com.telerikacademy.oop.WIM.models.common.enums.Severity;
import com.telerikacademy.oop.WIM.models.common.enums.StorySize;
import com.telerikacademy.oop.WIM.models.contracts.Admin;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;
import com.telerikacademy.oop.WIM.models.contracts.items.Bug;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CommonConstants {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss");
    public static final String LEN_10 = "1234567890";
    public static final String LEN_15 = "123456789012345";
    public static final String LEN_2 = "12";
    public static final String LEN_55 = LEN_10 + LEN_10 + LEN_10 + LEN_10 + LEN_15;
    public static final String LEN_550 = LEN_55 + LEN_55 + LEN_55 + LEN_55 + LEN_55
            + LEN_55 + LEN_55 + LEN_55 + LEN_55 + LEN_55;
    public static final Priority priority = Priority.MEDIUM;
    public static final Severity severity = Severity.MAJOR;
    public static final StorySize storySize = StorySize.MEDIUM;
    public static final int rating = 1;
    public static final List<String> steps = List.of("1", "2", "3");

    public static WIMFactoryImpl factory = new WIMFactoryImpl();
    public static WIMRepositoryImpl repository = new WIMRepositoryImpl();
    public static Person person = new PersonImpl(LEN_10);
    public static Admin admin = new AdminImpl(LEN_10,LEN_10);
    public static Board board = new BoardImpl(LEN_10);
    public static Team team = new TeamImpl(LEN_10);
}
