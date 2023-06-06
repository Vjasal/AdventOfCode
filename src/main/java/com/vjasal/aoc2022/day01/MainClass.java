package com.vjasal.aoc2022.day01;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.Arrays;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 1);
    }

    @Override
    public long solvePuzzle1(String input) {
        int max = 0;
        for (String list : CollectionUtil.toListOfSections(input)) {
            int sum = CollectionUtil.toLinkedList(list).stream().mapToInt(Integer::parseInt).sum();
            if (sum > max) max = sum;
        }
        logger.info("Result: " + max);
        return max;
    }

    @Override
    public long solvePuzzle2(String input) {
        int[] max = {0, 0, 0};
        for (String list : CollectionUtil.toListOfSections(input)) {
            int sum = CollectionUtil.toLinkedList(list).stream().mapToInt(Integer::parseInt).sum();
            if (max[0] < sum) max[0] = sum;
            Arrays.sort(max);
        }
        int result = Arrays.stream(max).sum();
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
