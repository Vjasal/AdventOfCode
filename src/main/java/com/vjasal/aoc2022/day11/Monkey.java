package com.vjasal.aoc2022.day11;

import com.vjasal.type.vector.Vector2;
import com.vjasal.util.CollectionUtil;
import com.vjasal.util.MathUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Monkey {

    private enum Operation {
        ADD, MULTIPLY, SQUARE
    }

    private final static Pattern idPattern = Pattern.compile("^Monkey (\\d+):$");
    private final static Pattern itemPattern = Pattern.compile("^ *Starting items: ((\\d+,? ?)*)$");
    private final static Pattern operationPattern = Pattern.compile("^ *Operation: new = old ([+*]) (old|\\d+)$");
    private final static Pattern testPattern = Pattern.compile("^ *Test: divisible by (\\d+)$");
    private final static Pattern testTruePattern = Pattern.compile("^ *If true: throw to monkey (\\d+)$");
    private final static Pattern testFalsePattern = Pattern.compile("^ *If false: throw to monkey (\\d+)$");

    private final int id;
    private final Operation operation;
    private final int operand;
    private final int testValue;
    private final int firstId;
    private final int secondId;

    private final List<Integer> items = new LinkedList<>();

    private int inspections = 0;

    public Monkey(String input) {

        List<String> lines = CollectionUtil.toArrayList(input);
        Matcher idMatcher = idPattern.matcher(lines.get(0));
        Matcher itemMatcher = itemPattern.matcher(lines.get(1));
        Matcher operationMatcher = operationPattern.matcher(lines.get(2));
        Matcher testMatcher = testPattern.matcher(lines.get(3));
        Matcher testTrueMatcher = testTruePattern.matcher(lines.get(4));
        Matcher testFalseMatcher = testFalsePattern.matcher(lines.get(5));

        if (!(idMatcher.find() && itemMatcher.find() && operationMatcher.find() && testMatcher.find() && testTrueMatcher.find() && testFalseMatcher.find())) {
            throw new IllegalArgumentException();
        }

        this.id = Integer.parseInt(idMatcher.group(1));
        this.testValue = Integer.parseInt(testMatcher.group(1));
        this.firstId = Integer.parseInt(testTrueMatcher.group(1));
        this.secondId = Integer.parseInt(testFalseMatcher.group(1));

        if ("old".equals(operationMatcher.group(2))) {
            operation = "+".equals(operationMatcher.group(1)) ? Operation.MULTIPLY : Operation.SQUARE;
            operand = 2;
        } else {
            operation = "+".equals(operationMatcher.group(1)) ? Operation.ADD : Operation.MULTIPLY;
            operand = Integer.parseInt(operationMatcher.group(2));
        }

        items.addAll(Arrays.stream(itemMatcher.group(1).split(", ")).mapToInt(Integer::parseInt).boxed().toList());
    }

    public Vector2<List<Integer>> round() {
        return round(0);
    }

    public Vector2<List<Integer>> round(int mod) {
        Vector2<List<Integer>> thrownItems = Vector2.valueOf(new LinkedList<>(), new LinkedList<>());

        for (int worryLevel : items) {
            int value = mod == 0 ? inspectItem1(worryLevel) : inspectItem2(worryLevel, mod);
            if (value % testValue == 0) {
                thrownItems.val1().add(value);
            } else {
                thrownItems.val2().add(value);
            }
        }

        inspections += items.size();
        items.clear();
        return thrownItems;
    }

    public int getFirstId() {
        return firstId;
    }

    public int getSecondId() {
        return secondId;
    }

    public void addItems(List<Integer> items) {
        this.items.addAll(items);
    }

    public long getInspections() {
        return inspections;
    }

    public int getTestValue() {
        return testValue;
    }

    private int inspectItem1(int worryLevel) {
        return switch (operation) {
            case ADD -> (worryLevel + operand) / 3;
            case MULTIPLY -> (worryLevel * operand) / 3;
            case SQUARE -> (worryLevel * worryLevel) / 3;
        };
    }

    private int inspectItem2(int worryLevel, int mod) {
        return switch (operation) {
            case ADD -> worryLevel + operand % mod;
            case MULTIPLY -> worryLevel * operand % mod;
            case SQUARE -> (int) MathUtil.powMod(worryLevel, 2, mod);
        };
    }

    @Override
    public String toString() {
        return "Monkey{" +
                "id=" + id +
                ", items=" + items +
                ", inspections=" + inspections +
                '}';
    }
}
