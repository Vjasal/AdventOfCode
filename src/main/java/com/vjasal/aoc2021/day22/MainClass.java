package com.vjasal.aoc2021.day22;

import com.vjasal.util.AocMainClass;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 22);
    }

    @Override
    public long solvePuzzle1(String input) {
        Reactor reactor = new Reactor(input);
        long result = reactor.count(50);
        logger.info("Result: " + result);
        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {
        Reactor reactor = new Reactor(input);
        long result = reactor.count();
        logger.info("Result: " + result);
        return 0;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
