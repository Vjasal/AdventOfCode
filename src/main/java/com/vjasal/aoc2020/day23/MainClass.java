package com.vjasal.aoc2020.day23;

import com.vjasal.util.AocMainClass;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2020, 23);
    }

    @Override
    public long solvePuzzle1(String input) {
        CupList cups = new CupList(input);
        for (int i = 0; i < 100; i++) {
            cups.round();
        }
        long result = cups.getResult1();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        CupList cups = new CupList(input, 1000000);
        for (int i = 0; i < 10000000; i++) {
            cups.round();
        }
        long result = cups.getResult2();
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
