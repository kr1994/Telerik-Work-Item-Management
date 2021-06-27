package com.telerikacademy.oop.WIM.models.ItemsImpl;

import com.telerikacademy.oop.WIM.models.contracts.items.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommentImplTest {
    private Comment com;
    private final String CORRECT_INPUT = "14  characters";
    private final String SMALL_INPUT = "low";
    private final String LARGE_INPUT = "1234567890" +
            "1234567890" +
            "1234567890" +
            "1234567890" +
            "1234567890000";

    @Test
    public void Constructor_Should_CreateComment_Whit_CorrectInput() {
        com = new CommentImpl(CORRECT_INPUT, CORRECT_INPUT);

        Assertions.assertEquals(CORRECT_INPUT, com.getAuthor());
        Assertions.assertEquals(CORRECT_INPUT, com.getContent());

    }

    @Test
    public void Constructor_Should_Throw_Whit_IncorrectInput() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CommentImpl(SMALL_INPUT, CORRECT_INPUT));
    }

    @Test
    public void Constructor_Should_Throw_Whit_IncorrectInput2() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CommentImpl(CORRECT_INPUT, SMALL_INPUT));
    }

    @Test
    public void Constructor_Should_Throw_Whit_IncorrectInput3() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CommentImpl(LARGE_INPUT, CORRECT_INPUT));
    }

    @Test
    public void Constructor_Should_Throw_Whit_IncorrectInput4() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CommentImpl(CORRECT_INPUT, LARGE_INPUT));
    }

    @Test
    public void Constructor_Should_Throw_Whit_nullInput() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CommentImpl(null, CORRECT_INPUT));
    }

    @Test
    public void Constructor_Should_Throw_Whit_nullInput2() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CommentImpl(CORRECT_INPUT, null));
    }

}
