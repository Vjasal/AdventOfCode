package com.vjasal.aoc2020.day16;

import com.vjasal.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

public class Notes {

    private final List<Rule>   rules  = new ArrayList<>();
    private final List<Ticket> nearby = new ArrayList<>();
    private final Ticket yours;

    Notes(String input) {
        List<String> sections = CollectionUtil.toListOfSections(input);
        addAllRules(sections.get(0));
        addAllNearbyTickets(sections.get(2));
        yours = new Ticket(sections.get(1).split("\n")[1]);
    }

    List<Rule> getRules() {
        return rules;
    }

    Ticket getYours() {
        return yours;
    }

    List<Ticket> getNearby() {
        return nearby;
    }

    private void addAllRules(String input) {
        for (String line : CollectionUtil.toLinkedListOfLines(input)) {
            rules.add(new Rule(line));
        }
    }

    private void addAllNearbyTickets(String input) {
        for (String line : CollectionUtil.toLinkedListOfLines(input)) {
            if (line.equals("nearby tickets:")) {
                continue;
            }
            nearby.add(new Ticket(line));
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Rule rule : rules) {
            builder.append(rule).append("\n");
        }
        builder.append("\nyour ticket:\n").append(yours).append("\n");
        builder.append("\nnearby tickets:\n");
        for (Ticket ticket : nearby) {
            builder.append(ticket).append("\n");
        }
        return builder.toString();
    }
}
