package com.vjasal.aoc2023.day14;

import com.vjasal.util.AocMainClass;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2023, 14);
    }

    @Override
    public long solvePuzzle1(String input) {
        Platform platform = new Platform(input);
        platform.rollUp();
        long result = platform.calculateLoad();

        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Platform platform = new Platform(input);
        platform.spin(1000000000);
        long result = platform.calculateLoad();

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
