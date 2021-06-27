package com.telerikacademy.oop.WIM.commands.showingCommands.listing;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.commands._enums.FilterOptions;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.common.enums.BugStatus;
import com.telerikacademy.oop.WIM.models.contracts.actions.Assignable;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;
import static com.telerikacademy.oop.WIM.models.common.Utils.capitaliseFirst;

public class WIMFilterBy implements Command {

    private static final int PARAMETERS_MIN_SIZE = 1;


    private static String filterStatusValue = "";
    private static String filterAssigneeName = "";

    private final String PARAMETERS = "Parameters in " + "FilterBy";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;

    public WIMFilterBy(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
    }

    public static String filterStatusBy() {
        return filterStatusValue.equals("") ? OFF : filterStatusValue;
    }

    public static String filterAssigneeBy() {
        return filterAssigneeName.equals("") ? OFF : capitaliseFirst(filterAssigneeName);
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        for (int i = 0; i < parameters.size(); i++) {
            FilterOptions option = extractOption(parameters.get(i).trim());
            switch (option) {
                case OFF:
                    filterStatusValue = filterAssigneeName = "";
                    return result();
                case ASSIGNEE:
                    if (++i < parameters.size()) {
                        Validator.validatePerson(parameters.get(i), WIMRepository.getPeople());
                        filterAssigneeName = parameters.get(i).trim().toLowerCase();
                    } else
                        throw new IllegalArgumentException(UNSPECIFIED_ASSIGNEE);
                    break;
                case STATUS:
                    if (++i < parameters.size()) {
                        Validator.validateNotNull(parameters.get(i), PARAMETERS);
                        filterStatusValue = findStatus(parameters.get(i).trim());
                    } else
                        throw new IllegalArgumentException(UNSPECIFIED_STATUS);
                    break;
            }
        }
        return result();
    }

    //package
    static WIMListOf.ListOrder filter(WIMListOf.ListOrder orderToFilter) {
        if (!filterAssigneeName.equals(""))
            filterAllByName(orderToFilter);
        if (!filterStatusValue.equals(""))
            filterAllByStatus(orderToFilter);
        return WIMSortBy.sort(orderToFilter);
    }

    //private
    private String findStatus(String status) {
        return BugStatus.searchForFilter(status);
    }

    private FilterOptions extractOption(String parameter) {
        try {
            return FilterOptions.valueOf(parameter.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(UNSUPPORTED_FILTER_OPTION);
        }
    }

    private String result() {
        return String.format(FILTER_OPTIONS_SUCCESSFULLY,
                filterAssigneeBy(),
                filterStatusBy());
    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }

    private static void filterAllByStatus(WIMListOf.ListOrder orderToFilter) {
        orderToFilter.setTomi(filterByStatus(orderToFilter.getTomi()));
        orderToFilter.setTodor(filterByStatus(orderToFilter.getTodor()));
        orderToFilter.setVladimir(filterByStatus(orderToFilter.getVladimir()));
        orderToFilter.setTittle(orderToFilter.getTitle() + " filter by status: " + filterStatusBy());

    }

    private static void filterAllByName(WIMListOf.ListOrder orderToFilter) {
        orderToFilter.setTomi(new ArrayList<>());
        orderToFilter.setTodor(filterByName(orderToFilter.getTodor()));
        orderToFilter.setVladimir(filterByName(orderToFilter.getVladimir()));

        orderToFilter.setTittle(orderToFilter.getTitle() + " filter by assignee: " + filterAssigneeBy());
    }

    private static <E extends Assignable> List<E> filterByName(List<E> list) {
        return list.stream()
                .filter(b -> b.getAssignee().getName().equalsIgnoreCase(filterAssigneeName))
                .collect(Collectors.toList());
    }

    private static <E extends WorkItem> List<E> filterByStatus(List<E> list) {
        return list.stream()
                .filter(i -> i.getStatus().equalsIgnoreCase(filterStatusValue))
                .collect(Collectors.toList());
    }
}
