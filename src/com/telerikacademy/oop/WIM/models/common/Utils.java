package com.telerikacademy.oop.WIM.models.common;

import com.telerikacademy.oop.WIM.core.contracts.HistoryLogger;
import com.telerikacademy.oop.WIM.models.common.enums.OpenField;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;

import java.util.List;

import static com.telerikacademy.oop.WIM.models.common.Constants.*;

public class Utils {

    public static String historyChangeFromTo(
            String effect, String placeOfChange, String oldState, String newState) {
        return String.format(HISTORY_EFFECT_PLACE_FROM_OLD_TO_NEW,
                effect,
                placeOfChange,
                oldState,
                newState
        );
    }

    public static String historyAction(String action, String placeOfAction) {
        return String.format(HISTORY_ACTED_OBJECT,
                action,
                placeOfAction
        );
    }

    public static String intInBoundsErrMessage(String inputName, int min, int max) {
        return String.format(INT_IN_BOUNDS,
                inputName,
                min,
                max);
    }

    public static String IsMoreThanErrMessage(String inputName, int min) {
        return String.format(IS_MORE_THAN,
                inputName,
                min);
    }

    public static String cantAssign(String value, OpenField place) {
        return String.format(ERR_CANT_ASSIGN,
                value.toUpperCase(),
                place.toString().toLowerCase());
    }

    public static String alreadyDone(String value, OpenField place) {
        return String.format(ERR_ALREADY_AT_IT,
                place,
                value);
    }

    public static String wrongField(String className, OpenField place) {
        return String.format(WRONG_FIELD,
                place.toString().toLowerCase(),
                className);
    }

    public static String abbreviate(String str) {
        int maxAbbreviateSize = 12;
        if (str.length() < maxAbbreviateSize)
            return str;
        String abbreviateEnd = "...";
        return str.substring(0, maxAbbreviateSize) + abbreviateEnd;
    }

    public static String capitaliseFirst(String text){
        return text.substring(0,1).toUpperCase() + text.substring(1).toLowerCase();
    }

    public static void addHistoryToAll(List<Board> boards, String report, HistoryLogger logger) {
        boards.forEach(b-> logger.takeLog(b,report));
    }

}
