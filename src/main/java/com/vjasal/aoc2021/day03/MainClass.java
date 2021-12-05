package com.vjasal.aoc2021.day03;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 3);
    }

    private int countSetBits(Collection<String> collection, int position) {
        return collection.stream().mapToInt(s -> Character.getNumericValue(s.charAt(position))).sum();
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> values = CollectionUtil.toLinkedListOfLines(input);
        int n = values.stream().findFirst().orElse("").length();

        int epsilon = 0;
        int gamma = 0;
        for (int i = 0; i < n; i++) {
            int k = countSetBits(values, i);
            epsilon = (epsilon << 1) | (k * 2 > values.size() ? 1 : 0);
            gamma   = (gamma << 1)   | (k * 2 > values.size() ? 0 : 1);
        }

        logger.info("Result: " + (epsilon * gamma));
        return (long) epsilon * gamma;
    }

    private void filterNumbers(Set<String> set, int strongerValue) {
        char val1 = (char) ('1' - strongerValue);
        char val2 = (char) ('0' + strongerValue);

        int n = set.stream().findFirst().orElse("").length();

        for (int i = 0; i < n; i++) {
            if (set.size() <= 1)
                break;
            int k = countSetBits(set, i);
            int position = i;
            if (2 * k >= set.size()) {
                set.removeIf(s -> s.charAt(position) == val1);
            } else {
                set.removeIf(s -> s.charAt(position) == val2);
            }
        }
    }

    @Override
    public long solvePuzzle2(String input) {
        Set<String> genSet = CollectionUtil.toHashSetOfLines(input);
        Set<String> scrSet = new HashSet<>(genSet);

        filterNumbers(genSet, 1);
        filterNumbers(scrSet, 0);

        int gen = Integer.parseInt(genSet.stream().findFirst().orElse("0"), 2);
        int scr = Integer.parseInt(scrSet.stream().findFirst().orElse("0"), 2);

        logger.info("Result: " + (gen * scr));
        return (long) gen * scr;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);
        String test = "00100\n" +
                "11110\n" +
                "10110\n" +
                "10111\n" +
                "10101\n" +
                "01111\n" +
                "00111\n" +
                "11100\n" +
                "10000\n" +
                "11001\n" +
                "00010\n" +
                "01010";

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
