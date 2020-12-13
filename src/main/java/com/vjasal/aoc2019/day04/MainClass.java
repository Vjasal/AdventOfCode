package com.vjasal.aoc2019.day04;

import com.vjasal.util.AocMainClass;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class MainClass extends AocMainClass {

    private final static Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2019, 4);
    }

    @Override
    public long solvePuzzle1(String input) {
        int min = Integer.parseInt(input.trim().split("-")[0]);
        int max = Integer.parseInt(input.trim().split("-")[1]);

        long result = IntStream.range(min, max + 1).filter(i -> isValidPassword1(Integer.toString(i))).count();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        int min = Integer.parseInt(input.trim().split("-")[0]);
        int max = Integer.parseInt(input.trim().split("-")[1]);

        long result = IntStream.range(min, max + 1).filter(i -> isValidPassword2(Integer.toString(i))).count();
        logger.info("Result: " + result);
        return result;
    }

    private boolean isValidPassword1(String password) {
        return hasDouble1(password) && neverDecreases(password);
    }

    private boolean isValidPassword2(String password) {
        return hasDouble2(password) && neverDecreases(password);
    }

    private boolean hasDouble1(String password) {
        char[] chars = password.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == chars[i + 1]) {
                return true;
            }
        }
        return false;
    }

    private boolean hasDouble2(String password) {
        Map<Character, Integer> chars = new HashMap<>();
        for (char c : password.toCharArray()) {
            if (!chars.containsKey(c))
                chars.put(c, 0);
            chars.put(c, chars.get(c) + 1);
        }
        return chars.containsValue(2);
    }

    private boolean neverDecreases(String password) {
        char[] chars = password.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] > chars[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}