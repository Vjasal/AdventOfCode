package com.vjasal.aoc2021.day14;

import com.vjasal.util.AocMainClass;

import java.util.Map;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 14);
    }

    @Override
    public long solvePuzzle1(String input) {
        long result = polymerize(input, 10);
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        long result = polymerize(input, 40);
        logger.info("Result: " + result);
        return result;
    }

    private Long polymerize(String input, int rounds) {
        Polymerization polymerization = new Polymerization(input);
        Polymer polymer = new Polymer(polymerization.getPolymerTemplate());

        for (int i = 0; i < rounds; i++) {
            polymer.step(polymerization.getInsertionRules());
        }

        Map<Character, Long> count = polymer.countElements();
        long max = count.values().stream().max(Long::compare).orElseThrow(IllegalStateException::new);
        long min = count.values().stream().min(Long::compare).orElseThrow(IllegalStateException::new);
        return max - min;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
