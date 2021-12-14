package com.vjasal.aoc2021.day14;

import com.vjasal.util.CollectionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polymerization {

    private static final Pattern pattern = Pattern.compile("^(\\w+) -> (\\w)$");

    private final String polymerTemplate;
    private final Map<String, Character> insertionRules = new HashMap<>();

    public Polymerization(String input) {
        List<String> sections = CollectionUtil.toListOfSections(input);
        polymerTemplate = sections.get(0);

        for (String insertionRule : CollectionUtil.toLinkedList(sections.get(1))) {
            Matcher matcher = pattern.matcher(insertionRule);
            if (!matcher.find()) throw new IllegalArgumentException("Wrong input format!");
            insertionRules.put(matcher.group(1), matcher.group(2).charAt(0));
        }
    }

    public String getPolymerTemplate() {
        return polymerTemplate;
    }

    public Map<String, Character> getInsertionRules() {
        return insertionRules;
    }
}
