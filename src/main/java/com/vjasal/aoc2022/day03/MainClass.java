package com.vjasal.aoc2022.day03;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 3);
    }

    @Override
    public long solvePuzzle1(String input) {
        int sum = 0;
        for (String rucksack : CollectionUtil.toLinkedList(input)) {
            Set<Integer> a = rucksack.chars().limit(rucksack.length() / 2).boxed().collect(Collectors.toSet());
            Set<Integer> b = rucksack.chars().skip(rucksack.length() / 2).boxed().collect(Collectors.toSet());

            a.retainAll(b);

            sum += a.stream().findAny().map(this::mapToPriority).orElse(0);
        }

        logger.info("" + sum);
        return sum;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> rucksacks = CollectionUtil.toArrayList(input);

        int sum = 0;
        for (int k = 0; k < rucksacks.size(); k += 3) {
            Set<Integer> a = rucksacks.get(k).chars().boxed().collect(Collectors.toSet());
            Set<Integer> b = rucksacks.get(k + 1).chars().boxed().collect(Collectors.toSet());
            Set<Integer> c = rucksacks.get(k + 2).chars().boxed().collect(Collectors.toSet());

            a.retainAll(b);
            a.retainAll(c);

            sum += a.stream().findAny().map(this::mapToPriority).orElse(0);
        }

        logger.info("" + sum);
        return sum;
    }

    private int mapToPriority(int c) {
        // Lowercase item types a through z have priorities 1 through 26
        // Uppercase item types A through Z have priorities 27 through 52
        if (c >= 'a' && c <= 'z') return c - 'a' + 1;
        if (c >= 'A' && c <= 'Z') return c - 'A' + 27;
        throw new IllegalArgumentException("Not a letter");
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
