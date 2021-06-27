package com.telerikacademy.oop.WIM.commands.showingCommands.listing;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.commands._enums.SortingOptions;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.contracts.items.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.*;

public class WIMSortBy implements Command {

    private static final int PARAMETERS_MIN_SIZE = 1;
    private static final SortFormat format = new SortFormat();

    private final String PARAMETERS = "Parameters in " + "WIMSort";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;

    public WIMSortBy(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        format.updateFormat(parameters.get(0));

        return String.format("Sort format: %s", format.showFormat());
    }

    public static String showFormat() {
        return format.showFormat();
    }

    //package
    static WIMListOf.ListOrder sort(WIMListOf.ListOrder orderToSort) {
        Validator.validateNotNull(orderToSort, ORDER_TO_SORT);
        sortByOption(format.getOrdering(), orderToSort);

        return orderToSort;
    }

    //private
    private static void sortByOption(SortingOptions sortingOption, WIMListOf.ListOrder orderToSort) {
        switch (sortingOption) {
            case TITLE:
                List<String> work = orderToSort.getWork().stream()
                        .filter(Objects::nonNull)
                        .sorted(WorkItem::compareTo)
                        .map(e -> e.toString().trim())
                        .collect(Collectors.toList());
                orderToSort.addToPrint(work);
                orderToSort.setTittle(orderToSort.getTitle() + SORTED_BY_TITLE);

                //sortByList(WorkItem::compareTo(), orderToSort.getWork(), orderToSort);
                break;
            case PRIORITY:
                List<String> task = orderToSort.getTasks().stream()
                        .filter(Objects::nonNull)
                        .sorted(Task::compareTo)
                        .map(e -> e.toString().trim())
                        .collect(Collectors.toList());
                orderToSort.addToPrint(task);
                orderToSort.setTittle(orderToSort.getTitle() + SORTED_BY_PRIORITY);

                //sortByList( Task::compareTo,orderToSort.getTasks(), orderToSort);
                break;
            case SEVERITY:
                List<String> bugs = orderToSort.getTodor().stream()
                        .filter(Objects::nonNull)
                        .sorted(Bug::compareTo)
                        .map(e -> e.toString().trim())
                        .collect(Collectors.toList());
                orderToSort.addToPrint(bugs);
                orderToSort.setTittle(orderToSort.getTitle() + SORTED_BY_SEVERITY);

                //sortByList(Bug::compareTo,orderToSort.getTodor(), orderToSort);
                break;
            case RATING:
                List<String> feedback = orderToSort.getTomi().stream()
                        .filter(Objects::nonNull)
                        .sorted(FeedBack::compareTo)
                        .map(e -> e.toString().trim())
                        .collect(Collectors.toList());
                orderToSort.addToPrint(feedback);
                orderToSort.setTittle(orderToSort.getTitle() + SORTED_BY_RATING);

                //sortByList(FeedBack::compareTo,orderToSort.getTomi(), orderToSort);
                break;
            case SIZE:
                List<String> story = orderToSort.getVladimir().stream()
                        .filter(Objects::nonNull)
                        .sorted(Story::compareTo)
                        .map(e -> e.toString().trim())
                        .collect(Collectors.toList());
                orderToSort.addToPrint(story);
                orderToSort.setTittle(orderToSort.getTitle() + SORTED_BY_SIZE);

                //sortByList(Story::compareTo,orderToSort.getVladimir(), orderToSort);
                break;
            default:
                List<String> defaultSort = orderToSort.getIdentifies().stream()
                        .filter(Objects::nonNull)
                        .sorted(Comparator.comparingInt(a -> Integer.parseInt(a.getIdentity())))
                        .map(e -> e.toString().trim())
                        .collect(Collectors.toList());
                orderToSort.addToPrint(defaultSort);
                orderToSort.setTittle(orderToSort.getTitle() + SORTED_BY_ID);

                //  sortByList(Comparator.comparingInt(a -> Integer.parseInt(a.getIdentity())),orderToSort.getWork(), orderToSort);
        }
    }

    //title/priority/severity/size/rating
    private static class SortFormat {

        private SortingOptions ordering;

        SortFormat() {
            this.ordering = SortingOptions.OFF;
        }

        String showFormat() {
            return ordering.toString().toLowerCase();
        }

        void updateFormat(String parameter) {
            Validator.validateNotNull(parameter, SORTING_OPTIONS);
            try {
                this.ordering = SortingOptions.valueOf(parameter.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(UNSUPPORTED_SORTING_OPTION);
            }
        }

        private SortingOptions getOrdering() {
            return (ordering);
        }
    }

    private void validateInput(List<String> parameters) {
        Validator.validateParameters(parameters, PARAMETERS, PARAMETERS_MIN_SIZE);
    }
}
   /* private static <E extends WorkItem> void sortByList(Comparator comparator, List<E> list, WIMListOf.ListOrder order) {
         List<String> result = new ArrayList<>();
         list.stream()
                 .filter(Objects::nonNull)
                 .sorted(comparator)
                 .map(Object::toString)
                 .forEach(e -> result.add(((String) e).trim()));
         order.addToPrint(result);
     }*/