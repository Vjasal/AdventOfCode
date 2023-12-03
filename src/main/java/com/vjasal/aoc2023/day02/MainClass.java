package com.vjasal.aoc2023.day02;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());
    private static final Pattern pattern = Pattern.compile("^Game (\\d+): (.+)$");

    public MainClass() {
        super(2023, 2);
    }

    @Override
    public long solvePuzzle1(String input) {
        Map<String, Integer> max = new HashMap<>() {{
            put("red", 12);
            put("green", 13);
            put("blue", 14);
        }};

        int sum = 0;
        for (String line : CollectionUtil.toLinkedList(input)) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find())
                throw new IllegalArgumentException("Wring input pattern");

            int id = Integer.parseInt(matcher.group(1));
            String game = matcher.group(2);

            boolean shouldAdd = CollectionUtil.toLinkedList(game, "; ").stream().allMatch(set ->
                    CollectionUtil.toLinkedList(set, ", ").stream().allMatch(roll -> {
                        String[] x = roll.split(" ");
                        return Integer.parseInt(x[0]) <= max.get(x[1]);
                    }));
            if (shouldAdd) {
                sum += id;
            }
        }

        logger.info("Result: " + sum);
        return sum;
    }

    @Override
    public long solvePuzzle2(String input) {
        long sum = 0;
        for (String line : CollectionUtil.toLinkedList(input)) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find())
                throw new IllegalArgumentException("Wring input pattern");

            Map<String, Integer> min = new HashMap<>();
            String game = matcher.group(2);

            for (String set : CollectionUtil.toLinkedList(game, "; ")) {
                for (String roll : CollectionUtil.toLinkedList(set, ", ")) {
                    String[] x = roll.split(" ");
                    min.merge(x[1], Integer.parseInt(x[0]), Integer::max);
                }
            }
            sum += min.values().stream().reduce(1, (a, b) -> a * b);
        }

        logger.info("Result: " + sum);
        return sum;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
