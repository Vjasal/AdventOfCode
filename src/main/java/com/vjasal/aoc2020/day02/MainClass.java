package com.vjasal.aoc2020.day02;

import com.vjasal.util.AocMainClass;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    private MainClass() {
        super(2020, 2);
    }

    @Override
    public void solvePuzzle1(String input) {
        List<PasswordPolicy> passwords = parseInput(input);
        long result = passwords.stream().filter(this::isValidPassword1).count();
        logger.info("Result: " + result);
    }

    @Override
    public void solvePuzzle2(String input) {
        List<PasswordPolicy> passwords = parseInput(input);
        long result = passwords.stream().filter(this::isValidPassword2).count();
        logger.info("Result: " + result);
    }

    private List<PasswordPolicy> parseInput(String input) {
        List<PasswordPolicy> passwords = new LinkedList<>();
        Pattern pattern = Pattern.compile("([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            passwords.add(new PasswordPolicy(matcher.group(4), matcher.group(3), matcher.group(1), matcher.group(2)));
        }
        return passwords;
    }

    private boolean isValidPassword1(PasswordPolicy policy) {
        long number = policy.getPassword().chars().filter(c -> c == policy.getLetter()).count();
        return number >= policy.getMin() && number <= policy.getMax();
    }

    private boolean isValidPassword2(PasswordPolicy policy) {
        boolean min = policy.getPassword().charAt(policy.getMin() - 1) == policy.getLetter();
        boolean max = policy.getPassword().charAt(policy.getMax() - 1) == policy.getLetter();
        return min ^ max;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
