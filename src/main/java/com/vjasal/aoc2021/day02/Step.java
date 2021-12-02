package com.vjasal.aoc2021.day02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Step {
    private static final Pattern pattern = Pattern.compile("^(.+) (\\d)$");

    private final String direction;
    private final int x;

    public Step(String input) {
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find())
            throw new IllegalArgumentException();

        direction = matcher.group(1);
        x = Integer.parseInt(matcher.group(2));
    }

    public String getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }
}
