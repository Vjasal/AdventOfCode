package com.vjasal.aoc2020.day16;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class Ticket {

    private final int[] values;

    Ticket(String input) {
        values = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    boolean isValid(List<Rule> rules) {
        for (int value : values) {
            if (rules.stream().noneMatch(rule -> rule.satisfies(value))) {
                return false;
            }
        }
        return true;
    }

    int getErrorRate(List<Rule> rules) {
        int errorRate = 0;
        for (int value : values) {
            if (rules.stream().noneMatch(rule -> rule.satisfies(value))) {
                errorRate += value;
            }
        }
        return errorRate;
    }

    int getValue(int index) {
        return values[index];
    }

    int getSize() {
        return values.length;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",");
        for (int value : values) {
            joiner.add(Integer.toString(value));
        }
        return joiner.toString();
    }
}
