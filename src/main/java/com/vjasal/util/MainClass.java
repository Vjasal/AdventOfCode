package com.vjasal.util;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    private MainClass() {
        super(2020, 2);
    }

    @Override
    public void solvePuzzle1(String input) {

    }

    @Override
    public void solvePuzzle2(String input) {

    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }

}
