package com.vjasal.aoc2023.day01;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    private static final Pattern firstPattern = Pattern.compile("^.*?(\\d)");
    private static final Pattern secondPattern = Pattern.compile("^.*(\\d)");

    private static final Pattern firstPattern2 = Pattern
            .compile("^.*?(\\d|one|two|three|four|five|six|seven|eight|nine|ten)");
    private static final Pattern secondPattern2 = Pattern
            .compile("^.*(\\d|one|two|three|four|five|six|seven|eight|nine|ten)");

    private static final String[] numNames = {
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "ten"};

    public MainClass() {
        super(2023, 1);
    }

    @Override
    public long solvePuzzle1(String input) {
        int result = CollectionUtil.toLinkedList(input).stream()
                .map(s -> {
                    Matcher firstMatcher = firstPattern.matcher(s);
                    Matcher secondMatcher = secondPattern.matcher(s);
                    if (!firstMatcher.find() || !secondMatcher.find())
                        throw new IllegalArgumentException("Wring input format");
                    return firstMatcher.group(1) + secondMatcher.group(1);
                })
                .mapToInt(Integer::parseInt)
                .sum();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        int result = CollectionUtil.toLinkedList(input).stream()
                .map(s -> {
                    Matcher firstMatcher = firstPattern2.matcher(s);
                    Matcher secondMatcher = secondPattern2.matcher(s);
                    if (!firstMatcher.find() || !secondMatcher.find())
                        throw new IllegalArgumentException("Wring input format");
                    return firstMatcher.group(1) + secondMatcher.group(1);
                })
                .map(s -> {
                    for(int i = 0; i < numNames.length; i++) {
                        s = s.replace(numNames[i], String.valueOf(i + 1));
                    }
                    return s;
                })
                .mapToInt(Integer::parseInt)
                .sum();
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
