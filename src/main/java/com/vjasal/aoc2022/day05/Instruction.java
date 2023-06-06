package com.vjasal.aoc2022.day05;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Instruction(int count, int origin, int destination) {

    private static final Pattern pattern = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)$");

    public static Instruction parseInstruction(String input) {
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) throw new IllegalArgumentException();

        int count = Integer.parseInt(matcher.group(1));
        int origin = Integer.parseInt(matcher.group(2));
        int destination = Integer.parseInt(matcher.group(3));

        return new Instruction(count, origin, destination);
    }
}
