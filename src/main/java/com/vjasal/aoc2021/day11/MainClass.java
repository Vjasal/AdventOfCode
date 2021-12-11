package com.vjasal.aoc2021.day11;

import com.vjasal.util.AocMainClass;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 11);
    }

    @Override
    public long solvePuzzle1(String input) {
        OctopusGrid grid = new OctopusGrid(input);
        long result = 0;
        for (int i = 0; i < 100; i++) {
            result += grid.step();
        }
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        OctopusGrid grid = new OctopusGrid(input);
        long result = 1;
        while (grid.step() < 100) result++;
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
