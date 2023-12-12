package com.vjasal.aoc2023.day12;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());
    private static final Pattern pattern = Pattern.compile("^([.#?]+) ([\\d,]+)$");

    public MainClass() {
        super(2023, 12);
    }

    @Override
    public long solvePuzzle1(String input) {
        long result = 0;
        for (String line : CollectionUtil.toLinkedList(input)) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find())
                throw new IllegalArgumentException("Wring input format: " + input);

            String ground = matcher.group(1) + ".";
            List<Integer> groups = Arrays.stream(matcher.group(2).split(",")).map(Integer::parseInt).toList();

            result += new State(ground.toCharArray(), groups).countPossibilities();
        }
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        long result = 0;
        for (String line : CollectionUtil.toLinkedList(input)) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find())
                throw new IllegalArgumentException("Wring input format: " + input);

            String ground = matcher.group(1) + "?" + matcher.group(1) + "?" + matcher.group(1) + "?" + matcher.group(1) + "?" + matcher.group(1) + ".";
            List<Integer> tmp = Arrays.stream(matcher.group(2).split(",")).map(Integer::parseInt).toList();
            List<Integer> groups = new ArrayList<>();
            groups.addAll(tmp);
            groups.addAll(tmp);
            groups.addAll(tmp);
            groups.addAll(tmp);
            groups.addAll(tmp);

            result += new State(ground.toCharArray(), groups).countPossibilities();
        }
        logger.info("Result: " + result);
        return result;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
