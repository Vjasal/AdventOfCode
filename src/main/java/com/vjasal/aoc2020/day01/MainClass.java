package com.vjasal.aoc2020.day01;

import com.vjasal.util.AocMainClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2020, 1);
    }

    @Override
    public long solvePuzzle1(String input) {
        Set<Integer> report = parseInput(input);
        int target = 2020;
        int result = 0;

        for (int i : report) {
            if (report.contains(target - i)) {
                result = i * (target - i);
                break;
            }
        }

        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Set<Integer> report = parseInput(input);

        int target = 2020;
        int result = 0;

        Iterator<Integer> iterator1 = report.iterator();
        while(iterator1.hasNext() && result == 0) {
            int i = iterator1.next();

            Iterator<Integer> iterator2 = report.iterator();
            while (iterator2.hasNext() && result == 0) {
                int j = iterator2.next();
                if (report.contains(target - i - j)) {
                    result = i * j * (target - i - j);
                }
            }
        }
        logger.info("Result: " + result);
        return result;
    }

    private Set<Integer> parseInput(String input) {
        Set<Integer> parsedInput = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parsedInput.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            logger.warning("Error reading input:" + e);
        }
        return parsedInput;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
