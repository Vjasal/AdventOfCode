package com.vjasal.aoc2021.day06;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.ArrayList;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 6);
    }

    @Override
    public long solvePuzzle1(String input) {
        ArrayList<Long> fish = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            fish.add(0L);
        }

        for (String val : CollectionUtil.toLinkedList(input, ",")) {
            int i = Integer.parseInt(val);
            fish.set(i, fish.get(i) + 1);
        }

        for (int i = 0; i < 80; i++) {
            long tmp = fish.remove(0);
            fish.set(6, fish.get(6) + tmp);
            fish.add(tmp);
        }

        long result = fish.stream().reduce(0L, Long::sum);
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        ArrayList<Long> fish = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            fish.add(0L);
        }

        for (String val : CollectionUtil.toLinkedList(input, ",")) {
            int i = Integer.parseInt(val);
            fish.set(i, fish.get(i) + 1);
        }

        for (int i = 0; i < 256; i++) {
            long tmp = fish.remove(0);
            fish.set(6, fish.get(6) + tmp);
            fish.add(tmp);
        }

        long result = fish.stream().reduce(0L, Long::sum);
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
