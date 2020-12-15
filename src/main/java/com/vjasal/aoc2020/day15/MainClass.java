package com.vjasal.aoc2020.day15;

import com.vjasal.util.AocMainClass;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2020, 15);
    }

    @Override
    public long solvePuzzle1(String input) {
        int result = runGame(input, 2020);
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        int result = runGame(input, 30000000);
        logger.info("Result: " + result);
        return result;
    }

    private int runGame(String input, int iterations) {
        HashMap<Integer, Integer> played = new HashMap<>();
        List<Integer> start = parseInput(input);

        int last = start.get(start.size() - 1);
        for (int i = 0; i < start.size() - 1; i++) {
            played.put(start.get(i), i);
        }

        int next;
        for (int i = start.size() - 1; i < iterations - 1; i++) {
            next = played.containsKey(last) ? i - played.get(last) : 0;
            played.put(last, i);
            last = next;
        }

        return last;
    }

    private List<Integer> parseInput(String input) {
        List<Integer> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(new StringReader(input))) {
            scanner.useDelimiter(",");
            while (scanner.hasNextInt()) {
                result.add(scanner.nextInt());
            }
        }

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
