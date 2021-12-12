package com.vjasal.aoc2021.day12;

import com.vjasal.util.AocMainClass;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 12);
    }

    @Override
    public long solvePuzzle1(String input) {
        CaveSystem caveSystem = new CaveSystem(input);
        int result = caveSystem.countPaths(false);
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        CaveSystem caveSystem = new CaveSystem(input);
        int result = caveSystem.countPaths(true);
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
