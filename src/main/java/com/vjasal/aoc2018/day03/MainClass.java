package com.vjasal.aoc2018.day03;

import com.vjasal.util.AocMainClass;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2018, 3);
    }

    @Override
    public long solvePuzzle1(String input) {
        Fabric fabric = new Fabric(input);
        long result = fabric.countIntersections();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Fabric fabric = new Fabric(input);
        int result = fabric.getNotOverlappingId();
        logger.info("Result: " + result);
        return result;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        String test = "#1 @ 1,3: 4x4\n" +
                "#2 @ 3,1: 4x4\n" +
                "#3 @ 5,5: 2x2";

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
