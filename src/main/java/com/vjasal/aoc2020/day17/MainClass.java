package com.vjasal.aoc2020.day17;

import com.vjasal.util.AocMainClass;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2020, 17);
    }

    @Override
    public long solvePuzzle1(String input) {
        Cube cube = new Cube(input);
        for (int i = 0; i < 6; i++) {
            cube.cycle();
        }

        int result = cube.countActive();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Hypercube cube = new Hypercube(input);
        for (int i = 0; i < 6; i++) {
            cube.cycle();
        }

        int result = cube.countActive();
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
