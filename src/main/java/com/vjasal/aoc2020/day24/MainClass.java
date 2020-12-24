package com.vjasal.aoc2020.day24;

import com.vjasal.util.AocMainClass;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2020, 24);
    }

    @Override
    public long solvePuzzle1(String input) {
        TileMap tileMap = new TileMap(input);
        int result = tileMap.countAll();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        TileMap tileMap = new TileMap(input);
        for (int i = 0; i < 100; i++) {
            tileMap.round();
        }
        int result = tileMap.countAll();
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
