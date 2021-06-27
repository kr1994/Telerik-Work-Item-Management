package com.telerikacademy.oop.WIM.models.common;


import com.telerikacademy.oop.WIM.models.ItemsImpl.work.WorkItemBase;
import com.telerikacademy.oop.WIM.models.contracts.Person;
import com.telerikacademy.oop.WIM.models.contracts.Team;
import com.telerikacademy.oop.WIM.models.contracts.actions.Identify;
import com.telerikacademy.oop.WIM.models.contracts.items.Board;
import com.telerikacademy.oop.WIM.models.contracts.items.Task;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;
import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.PERSON_NAME;
import static com.telerikacademy.oop.WIM.models.common.Constants.*;
import static com.telerikacademy.oop.WIM.models.common.Utils.IsMoreThanErrMessage;
import static com.telerikacademy.oop.WIM.models.common.Utils.intInBoundsErrMessage;

public class Validator {


    public static void validateNotNull(Object arg, String objectTypeName) {
        if (arg == null) {
            throw new IllegalArgumentException(String.format(NULL_ERR, objectTypeName));
        }
    }

    public static String trimmedStringInBounds(String stringToChek,
                                               String stringType, int minLength, int maxLength) {
        String temp = stringToChek.trim();
        validateIntRange(temp.length(), minLength, maxLength, String.format(
                STRING_IN_BOUNDS_ERR,
                stringType,
                minLength,
                maxLength));
        return temp;
    }

    public static void validateIntRange(int value, String valueName, int min, int max) {
        String msg = intInBoundsErrMessage(valueName, min, max);
        validateIntRange(value, min, max, msg);
    }

    public static void validateIntRange(int value, int min, int max, String message) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validateIntIsNotLessThan(int value, String valueName, int min) {
        if (value < min)
            throw new IllegalArgumentException(IsMoreThanErrMessage(valueName, min));

    }

    public static <E> void ValidateExistsInMap(E input, String inputName, Map<E, ?> repo) {
        if (repo.get(input) == null)
            throw new IllegalArgumentException(String.format(NOT_FOUND_IN_MAP, inputName));
    }

    public static <E> void notEmpty(Collection<E> collection, String name) {
        if (collection.size() == 0)
            throw new IllegalArgumentException(String.format(ARE_NO_FOUND, name));
    }

    public static void validateWorkID(int index, List<WorkItem> work) {
        notEmpty(work, WORK_ITEMS);
        validateIntRange(index, WORK_ITEM_ID, 0, WorkItemBase.getNumberOfItems());
    }

    public static void validatePerson(String personNameInput, Map<String, Person> people) {
        validateFromRepo(personNameInput, PEOPLE, PERSON_NAME, people);
    }

    public static void validateParameters(List<String> parameters, String name, int minSize) {
        validateNotNull(parameters, name);
        if (minSize > 0)
            validateIntIsNotLessThan(parameters.size(), name, minSize);
    }

    public static void validateTeam(String teamNameInput, Map<String, Team> teams) {
        validateFromRepo(teamNameInput, TEAMS, TEAM_NAME, teams);
    }

    public static void validateBoard(String boardName) {
        validateNotNull(boardName, TEAM_BOARD);
    }

    //input - Maria, type people, typeName PERSON_NAME,
    private static <E> void validateFromRepo(String input, String type, String typeName,
                                             Map<String, E> map) {
        notEmpty(map.keySet(), type);
        validateNotNull(input, typeName);
        ValidateExistsInMap(input.toLowerCase(), typeName, map);
    }

    public static <E extends Identify> boolean isInCollection(String myIdentifier, Collection<E> list) {
        return list.stream()
                .anyMatch(o -> o.getIdentity().equalsIgnoreCase(myIdentifier));
    }

    public static boolean isWorkAssignable(int workID, List<Task> list) {
        return list.stream()
                .anyMatch(t -> t.getID() == workID);
    }
}

/*

    public static <E> void uniqueInMap(E input, String inputName, Map<E, ?> repo) {
        if (repo.get(input) != null)
            throw new IllegalArgumentException(String.format(UNIQUE_IN_THE_APP_ERR, inputName));
    }

    public static <E> boolean isInMap(E input, Map<E, ?> repo) {
        return repo.get(input) != null;
    }
*/
