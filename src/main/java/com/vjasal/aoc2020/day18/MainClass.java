package com.vjasal.aoc2020.day18;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    private static final Pattern pattern    = Pattern.compile("^(\\d+) ([+*]) (\\d+)");
    private static final Pattern addPattern = Pattern.compile("(\\d+) \\+ (\\d+)");
    private static final Pattern mulPattern = Pattern.compile("(\\d+) \\* (\\d+)");

    MainClass() {
        super(2020, 18);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> lines = CollectionUtil.toLinkedListOfLines(input);
        long result = lines.stream().mapToLong(this::evaluateLine).sum();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> lines = CollectionUtil.toLinkedListOfLines(input);
        long result = lines.stream().mapToLong(this::evaluateLine2).sum();
        logger.info("Result: " + result);
        return result;
    }

    private long evaluateLine(String line) {
        int openParenthesesIndex;
        while ((openParenthesesIndex = line.lastIndexOf('(')) >= 0) {
            int closeParenthesesIndex = line.indexOf(')', openParenthesesIndex);

            // this will ensure, that evaluated line will be without parentheses,
            // because this is the inner most expression
            long result = evaluateLine(line.substring(openParenthesesIndex + 1, closeParenthesesIndex));
            line = line.substring(0, openParenthesesIndex) + result + line.substring(closeParenthesesIndex + 1);
        }

        Matcher matcher;
        while ((matcher = pattern.matcher(line)).find()) {
            long val1 = Long.parseLong(matcher.group(1));
            long val2 = Long.parseLong(matcher.group(3));
            String operator = matcher.group(2);

            long result = operator.equals("+") ? val1 + val2 : val1 * val2;
            line = result + line.substring(matcher.end());
        }

        return Long.parseLong(line);
    }

    private long evaluateLine2(String line) {
        int openParenthesesIndex;
        while ((openParenthesesIndex = line.lastIndexOf('(')) >= 0) {
            int closeParenthesesIndex = line.indexOf(')', openParenthesesIndex);

            // this will ensure, that evaluated line will be without parentheses,
            // because this is the inner most expression
            long result = evaluateLine2(line.substring(openParenthesesIndex + 1, closeParenthesesIndex));
            line = line.substring(0, openParenthesesIndex) + result + line.substring(closeParenthesesIndex + 1);
        }

        Matcher matcher;
        while ((matcher = addPattern.matcher(line)).find()) {
            long val1 = Long.parseLong(matcher.group(1));
            long val2 = Long.parseLong(matcher.group(2));
            long result = val1 + val2;

            line = line.substring(0, matcher.start()) + result + line.substring(matcher.end());
        }

        while ((matcher = mulPattern.matcher(line)).find()) {
            long val1 = Long.parseLong(matcher.group(1));
            long val2 = Long.parseLong(matcher.group(2));
            long result = val1 * val2;

            line = line.substring(0, matcher.start()) + result + line.substring(matcher.end());
        }

        return Long.parseLong(line);
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
