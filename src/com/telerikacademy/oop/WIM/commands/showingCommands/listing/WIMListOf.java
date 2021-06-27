package com.telerikacademy.oop.WIM.commands.showingCommands.listing;

import com.telerikacademy.oop.WIM.commands._contracts.Command;
import com.telerikacademy.oop.WIM.core.contracts.WIMFactory;
import com.telerikacademy.oop.WIM.core.contracts.WIMRepository;
import com.telerikacademy.oop.WIM.models.common.Validator;
import com.telerikacademy.oop.WIM.models.common.enums.WIType;
import com.telerikacademy.oop.WIM.models.contracts.actions.Identify;
import com.telerikacademy.oop.WIM.models.contracts.items.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.telerikacademy.oop.WIM.commands._constants.CommandConstants.UNSUPPORTED_WORK_ITEM_TYPE;

public class WIMListOf implements Command {

    private static final int PARAMETERS_MIN_SIZE = 0;

    private final String PARAMETERS = "Parameters in " + "ListOf";
    private final WIMFactory WIMFactory;
    private final WIMRepository WIMRepository;

    public WIMListOf(WIMFactory WIMFactory, WIMRepository WIMRepository) {
        this.WIMFactory = WIMFactory;
        this.WIMRepository = WIMRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.validateNotNull(parameters, PARAMETERS);
        WIType sortBy;
        ListOrder order;
        try {
            if (!parameters.isEmpty() && !parameters.get(0).isBlank()) {
                sortBy = WIType.valueOf(parameters.get(0).trim().toUpperCase());
                order = implement(sortBy);
            } else
                order = implement();

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(UNSUPPORTED_WORK_ITEM_TYPE);
        }

        ListOrder orderToPrint = WIMFilterBy.filter(order);
        return orderToPrint.getTitle()
                + System.lineSeparator()
                + order.getToPrint();
    }

    private ListOrder implement() {
        String title = "List of all work items ";

        return new ListOrder(title, WIMRepository.getBugs(),
                WIMRepository.getStory(), WIMRepository.getFeedBack());
    }

    private ListOrder implement(WIType type) {
        String title = String.format("List of %s", type.toString().toLowerCase());
        switch (type) {
            case BUG:
                return new ListOrder(title, WIMRepository.getBugs(), new ArrayList<>(), new ArrayList<>());
            case STORY:
                return new ListOrder(title, new ArrayList<>(), WIMRepository.getStory(), new ArrayList<>());
            case FEEDBACK:
                return new ListOrder(title, new ArrayList<>(), new ArrayList<>(), WIMRepository.getFeedBack());
            default:
                throw new IllegalArgumentException(UNSUPPORTED_WORK_ITEM_TYPE);
        }
    }

    static class ListOrder {
        private String title;
        private final List<String> toPrint;
        private List<Bug> todor;
        private List<Story> vladimir;
        private List<FeedBack> tomi;

        private ListOrder(String title, List<Bug> todor, List<Story> vladimir, List<FeedBack> tomi) {
            this.toPrint = new ArrayList<>();
            this.title = title;
            this.todor = todor;
            this.vladimir = vladimir;
            this.tomi = tomi;
        }

        void setTittle(String title) {
            this.title = title;
        }

        void addToPrint(List<String> newPrint) {
            toPrint.addAll(newPrint);
        }

        void setTodor(List<Bug> todor) {
            this.todor = todor;
        }

        void setVladimir(List<Story> vladimir) {
            this.vladimir = vladimir;
        }

        void setTomi(List<FeedBack> tomi) {
            this.tomi = tomi;
        }

        String getTitle() {
            return title;
        }

        String getToPrint() {
            String result = toPrint.stream()
                    .reduce("", (a, b) -> a.trim() + System.lineSeparator() + b.trim());
            return result.trim();
        }

        List<Bug> getTodor() {
            return todor;
        }

        List<Story> getVladimir() {
            return vladimir;
        }

        List<FeedBack> getTomi() {
            return tomi;
        }

        List<Task> getTasks() {
            List<Task> result = new ArrayList<>(getTodor());
            result.addAll(getVladimir());
            result = result.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return result;
        }

        List<WorkItem> getWork() {
            List<WorkItem> result = new ArrayList<>(getTasks());
            result.addAll(getTomi());
            result = result.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return result;
        }

        List<Identify> getIdentifies(){
            List <Identify> identifies = new ArrayList<>(getTodor());
            identifies.addAll(getVladimir());
            identifies.addAll(getTomi());
            identifies = identifies.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return identifies;
        }
    }
}
