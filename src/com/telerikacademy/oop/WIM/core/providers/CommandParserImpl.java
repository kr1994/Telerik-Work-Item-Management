package com.telerikacademy.oop.WIM.core.providers;

import com.telerikacademy.oop.WIM.core.contracts.CommandParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.UNCLOSED_STATEMENT_IN_COMMAND_LINE;

public class CommandParserImpl implements CommandParser {


    private static final String MAIN_SPLIT_SYMBOL = " ";
    private static final String COMMENT_OPEN_SYMBOL = "{{";
    private static final String COMMENT_CLOSE_SYMBOL = "}}";
    private int indexOfFirstSeparator;

    public String parseCommand(String fullCommand) {
        return fullCommand.trim().split(" ")[0];
    }

    public List<String> parseParameters(String fullCommand) {
        String trimmedFullCommand = fullCommand.trim();
        indexOfFirstSeparator = trimmedFullCommand.indexOf(MAIN_SPLIT_SYMBOL);

        String reducedInput = skipCommand(trimmedFullCommand);

        List<String> parameters = getParameters(reducedInput);
        parameters.removeAll(Arrays.asList(" ", "", null));
        return new ArrayList<>(parameters);
    }

    private String skipCommand(String trimmedFullCommand) {
        return  indexOfFirstSeparator < 0 ? ""
                : trimmedFullCommand.substring(indexOfFirstSeparator).trim();
    }

    private List<String> getParameters(String reducedInput) {
        List<String> result = new ArrayList<>();
        while (!reducedInput.isEmpty()) {
            if (reducedInput.startsWith(COMMENT_OPEN_SYMBOL)) {
                int indexOfOpenComment = reducedInput.indexOf(COMMENT_OPEN_SYMBOL);
                int indexOfCloseComment = reducedInput.indexOf(COMMENT_CLOSE_SYMBOL);
                if (indexOfCloseComment < 0)
                    throw new IllegalArgumentException(UNCLOSED_STATEMENT_IN_COMMAND_LINE);
                //inclusive start
                result.add(reducedInput.substring(indexOfOpenComment + COMMENT_OPEN_SYMBOL.length(),
                        indexOfCloseComment).trim());
                reducedInput = reducedInput.substring(indexOfCloseComment + COMMENT_CLOSE_SYMBOL.length());
            } else {
                indexOfFirstSeparator = reducedInput.indexOf(MAIN_SPLIT_SYMBOL);
                if (indexOfFirstSeparator < 0) {
                    result.add(reducedInput.trim());
                    break;
                } else {
                    result.add(reducedInput.substring(0, indexOfFirstSeparator).trim());
                    reducedInput = reducedInput.substring(indexOfFirstSeparator + MAIN_SPLIT_SYMBOL.length()).trim();
                }
            }
        }
        return new ArrayList<>(result);
    }
}
