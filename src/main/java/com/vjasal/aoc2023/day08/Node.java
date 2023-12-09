package com.vjasal.aoc2023.day08;

import com.vjasal.aoc2023.day07.HandWithJoker;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Node {
    private static final Pattern pattern = Pattern.compile("^(\\w+) = \\((\\w+), (\\w+)\\)$");

    private final String name;
    private final String left;
    private final String right;

    public Node(String input) {
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find())
            throw new IllegalArgumentException("Wrong input format!");
        this.name = matcher.group(1);
        this.left = matcher.group(2);
        this.right = matcher.group(3);
    }

    public String getName() {
        return name;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Node other))
            throw new ClassCastException();
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
