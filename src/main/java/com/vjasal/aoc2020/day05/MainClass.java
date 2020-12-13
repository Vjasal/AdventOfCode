package com.vjasal.aoc2020.day05;

import com.vjasal.util.AocMainClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    private MainClass() {
        super(2020, 5);
    }

    @Override
    public void solvePuzzle1(String input) {
        Set<Integer> ids = parseInput(input);
        logger.info("Result: " + Collections.max(ids));
    }

    @Override
    public void solvePuzzle2(String input) {
        Set<Integer> ids = parseInput(input);

        int min = Collections.min(ids);
        int i = 0;

        while (i < ids.size() && ids.contains(i + min)) {
            i++;
        }

        logger.info("Result: " + (i + min));
    }

    private Set<Integer> parseInput(String input) {
        Set<Integer> ids = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String binaryVal = line.replaceAll("[FL]", "0")
                        .replaceAll("[BR]", "1");
                ids.add(Integer.valueOf(binaryVal, 2));
            }
        } catch (IOException e) {
            logger.warning("Exception: " + e);
        }
        return ids;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }

}
