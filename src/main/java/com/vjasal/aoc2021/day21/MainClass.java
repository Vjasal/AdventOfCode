package com.vjasal.aoc2021.day21;

import com.vjasal.util.AocMainClass;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 21);
    }

    @Override
    public long solvePuzzle1(String input) {
        DiceGame game = new DiceGame(input);
        int result = game.play();
        logger.info("Result: " + result);

        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {

        DiceGameSimulator gameSimulator = new DiceGameSimulator(input);
        gameSimulator.simulate();

        // 274291038026362
        // 135620348907779
        logger.info("" + DiceGameSimulator.day21pt2(2));

        return 0;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        String test = "Player 1 starting position: 4\n" +
                "Player 2 starting position: 8";

//        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
