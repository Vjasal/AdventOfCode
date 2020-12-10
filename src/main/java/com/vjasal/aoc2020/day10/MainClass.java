package com.vjasal.aoc2020.day10;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.Util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    private MainClass() {
        super(2020, 10);
    }

    @Override
    public void solvePuzzle1(String input) {
        List<Integer> list = Util.toLinkedListOfLines(input).stream().mapToInt(Integer::parseInt).boxed()
                .collect(Collectors.toList());

        list.add(0);
        list.sort(Integer::compareTo);

        int threes = 1; // always one 3 from last adapter to built-in adapter
        int ones   = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i + 1) - list.get(i) == 1) ones++;
            if (list.get(i + 1) - list.get(i) == 3) threes++;
        }

        logger.info("Result: " + (ones * threes));
    }

    @Override
    public void solvePuzzle2(String input) {
        List<Integer> list = Util.toLinkedListOfLines(input).stream().mapToInt(Integer::parseInt).boxed()
                .collect(Collectors.toCollection(LinkedList::new));

        list.add(0);
        list.sort(Integer::compareTo);

        // get every element, that if it were to be removed from list, it would still be valid
        // for each such element, there are 2 distinct ways to arrange the adapters
        // however, if there are 3 consecutive elements that increment by 1, then all of them cannot be removed
        // then for such 3 elements there are 7 possible arrangements (2^3 - 1)
        List<Integer> x = new ArrayList<>();
        for (int i = 0; i < list.size() - 2; i++) {
            if (list.get(i + 2) - list.get(i) <= 3) {
                x.add(list.get(i + 1));
            }
        }

        long result = 1;
        int i = 0;
        while (i < x.size()) {
            if (i + 2 < x.size() && x.get(i + 2) - x.get(i + 1) == 1 && x.get(i + 1) - x.get(i) == 1) {
                result *= 7;
                i += 3;
            } else {
                result *= 2;
                i += 1;
            }
        }

        logger.info("Result: " + result);
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
