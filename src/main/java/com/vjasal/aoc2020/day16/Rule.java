package com.vjasal.aoc2020.day16;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Rule {

    private final static Pattern pattern = Pattern.compile("^(\\w+\\s*\\w*): (\\d+)-(\\d+) or (\\d+)-(\\d+)$");

    private final String name;
    private final int value1;
    private final int value2;
    private final int value3;
    private final int value4;

    private final Set<Integer> satisfiedValueIndexes = new HashSet<>();

    Rule(String input) {
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) {
            throw new IllegalArgumentException();
        }
        name   = matcher.group(1);
        value1 = Integer.parseInt(matcher.group(2));
        value2 = Integer.parseInt(matcher.group(3));
        value3 = Integer.parseInt(matcher.group(4));
        value4 = Integer.parseInt(matcher.group(5));
    }

    public String getName() {
        return name;
    }

    boolean satisfies(int value) {
        return (value >= value1 && value <= value2) || (value >= value3 && value <= value4);
    }

    void markAllAsSatisfied(int size) {
        satisfiedValueIndexes.addAll(IntStream.range(0, size).boxed().collect(Collectors.toList()));
    }

    void removeNotSatisfied(Set<Integer> indexes) {
        satisfiedValueIndexes.removeAll(indexes);
    }

    Set<Integer> getSatisfiedValueIndexes() {
        return satisfiedValueIndexes;
    }

    int getRuleIndex() {
        if (satisfiedValueIndexes.size() > 1)
            throw new IllegalStateException();
        return satisfiedValueIndexes.iterator().next();
    }

    @Override
    public String toString() {
        return name + ": " + value1 + "-" + value2 + " or " + value3 + "-" + value4;
    }
}
