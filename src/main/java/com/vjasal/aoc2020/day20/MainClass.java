package com.vjasal.aoc2020.day20;

import com.vjasal.util.AocMainClass;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getSimpleName());

    public MainClass() {
        super(2020, 20);
    }

    @Override
    public long solvePuzzle1(String input) {
        Image image = new Image(input);

        long result = image.getMultipliedCorners();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Image image = new Image(input);
        ImageProcessor processor = new ImageProcessor(image.getCombinedImage());

        long result = processor.countWater();
        logger.info("Result: " + result);
        return result;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
