package com.telerikacademy.oop.WIM.core.providers;

import com.telerikacademy.oop.WIM.core.contracts.CommandParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CommandParserTests {

    private final CommandParser parser = new CommandParserImpl();
    private final String inputWord = " command ";
    private final String endCommentSeparator = "}}";
    private final String startCommentSeparator = "{{";
    private final String outputWord = inputWord.trim();
    private final String outputComment = "comment to add";
    private final String inputComment = startCommentSeparator + outputComment + endCommentSeparator;

    private List<String> actualOutput;
    private List<String> expectedOutput;

    private String input;
    private String actualString;
    private String expectedString;

    @BeforeEach
    public void bootUp() {
        actualString = "";
        expectedString = "";
        input = "";
        expectedOutput = new ArrayList();
        actualOutput = new ArrayList<>();
    }

    @Test
    public void ParseParameters_Should_ParseCorrectly_Whit_EmptyInput() {
        input = "";
        expectedOutput = List.of();

        actualOutput = parser.parseParameters(input);

        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void ParseParameters_Should_ParseWord_Whit_CorrectInput() {
        input = inputWord;
        expectedOutput = List.of();

        actualOutput = parser.parseParameters(input);

        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void ParseParameters_Should_ParseComment_Whit_CorrectInput() {
        input = inputWord + inputComment;
        expectedOutput.add(outputComment);

        actualOutput = parser.parseParameters(input);

        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void ParseParameters_Should_ParseCommentAndWords_Whit_CorrectInput() {
        input = inputWord + inputComment + inputComment + inputWord;
        expectedOutput.add(outputComment);
        expectedOutput.add(outputComment);
        expectedOutput.add(outputWord);

        actualOutput = parser.parseParameters(input);

        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void ParseParameters_Should_Throw_Whit_UnclosedComments() {
        input = inputWord + startCommentSeparator + inputWord;

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> parser.parseParameters(input));
    }

    @Test
    public void ParseCommand_Should_ParseCorrectly_Whit_CorrectInput() {
        input = inputWord;
        expectedString = outputWord;

        actualString = parser.parseCommand(input);

        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void ParseCommand_Should_ParseCorrectly_Whit_CorrectInput2() {
        input = inputWord + inputWord + inputComment + inputWord;
        expectedString = outputWord;

        actualString = parser.parseCommand(input);

        Assertions.assertEquals(expectedString, actualString);
    }
}
