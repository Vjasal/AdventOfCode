package com.vjasal.aoc2018.day01;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2018, 1);
    }

    @Override
    public long solvePuzzle1(String input) {
        long result = 0;
        for (String s : CollectionUtil.toLinkedListOfLines(input)) {
            result += Long.parseLong(s);
        }

        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> frequencyChanges = CollectionUtil.toArrayListOfLines(input);
        int index = 0;

        Set<Long> seen = new HashSet<>();
        long currentFrequency = 0;

        while (true) {
            currentFrequency += Long.parseLong(frequencyChanges.get(index));
            index = (index + 1) % frequencyChanges.size();
            if (seen.contains(currentFrequency)) {
                break;
            }
            seen.add(currentFrequency);
        }

        logger.info("Result: " + currentFrequency);
        return currentFrequency;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
