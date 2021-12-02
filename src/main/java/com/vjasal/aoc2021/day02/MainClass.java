package com.vjasal.aoc2021.day02;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 2);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<Step> steps = new LinkedList<>();
        for (String line : CollectionUtil.toLinkedListOfLines(input)) {
            steps.add(new Step(line));
        }

        int position = 0;
        int depth = 0;

        for (Step step : steps) {
            switch (step.getDirection()) {
                case "forward":
                    position += step.getX();
                    break;
                case "up":
                    depth -= step.getX();
                    break;
                case "down":
                    depth += step.getX();
                    break;
            }
        }

        logger.info("Result: " + (position * depth));
        return (long) position * depth;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<Step> steps = new LinkedList<>();
        for (String line : CollectionUtil.toLinkedListOfLines(input)) {
            steps.add(new Step(line));
        }

        int position = 0;
        int depth = 0;
        int aim = 0;

        for (Step step : steps) {
            switch (step.getDirection()) {
                case "forward":
                    position += step.getX();
                    depth += aim * step.getX();
                    break;
                case "up":
                    aim -= step.getX();
                    break;
                case "down":
                    aim += step.getX();
                    break;
            }
        }

        logger.info("Result: " + (position * depth));
        return (long) position * depth;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        String test = "forward 5\n" +
                "down 5\n" +
                "forward 8\n" +
                "up 3\n" +
                "down 8\n" +
                "forward 2";

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
