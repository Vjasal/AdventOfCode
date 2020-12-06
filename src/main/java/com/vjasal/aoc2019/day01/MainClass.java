package com.vjasal.aoc2019.day01;

import com.vjasal.util.AocMainClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    private MainClass() {
        super(2019, 1);
    }

    @Override
    public void solvePuzzle1(String input) {
        List<Integer> masses = parseInput(input);
        long result = 0;
        for (int mass : masses) {
            result += mass / 3 - 2;
        }
        logger.info("Result: " + result);
    }

    @Override
    public void solvePuzzle2(String input) {
        List<Integer> masses = parseInput(input);
        long result = 0;
        for (int mass : masses) {
            int fuelMass = mass;
            while ((fuelMass = fuelMass / 3 - 2) > 0) {
                result += fuelMass;
            }
        }
        logger.info("Result: " + result);
    }

    private List<Integer> parseInput(String input) {
        List<Integer> lines = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(Integer.valueOf(line));
            }
        } catch (IOException e) {
            logger.warning("Exception: " + e);
        }
        return lines;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
