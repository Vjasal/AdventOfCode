package com.vjasal.aoc2024.day02;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2024, 2);
    }

    private int isSafe(int[] report) {
        int x = 0;

        for (int i = 0; i < report.length - 1; i++) {
            int a = report[i];
            int b = report[i + 1];
            int diff;

            if (x == 0)
                x = Integer.compare(a, b);

            if (x > 0)
                diff = a - b;
            else
                diff = b - a;

            if (diff <= 0 || diff > 3)
                return i;
        }

        return -1;
    }

    private int[] removeElement(int[] report, int index) {
        int[] ret = new int[report.length - 1];
        int k = 0;

        for (int i = 0; i < report.length; i++) {
            if (i == index)
                continue;
            ret[k++] = report[i];
        }

        return ret;
    }

    private boolean isSafe2(int[] report) {
        int index = isSafe(report);

        if (index == -1)
            return true;

        if (index == 1 && isSafe(removeElement(report, 0)) == -1)
            return true;

        return isSafe(removeElement(report, index)) == -1 || isSafe(removeElement(report, index + 1)) == -1;
    }

    @Override
    public long solvePuzzle1(String input) {
        long result = CollectionUtil.toLinkedList(input).stream()
                .map(s -> CollectionUtil.toLinkedList(s, " ").stream().mapToInt(Integer::parseInt).toArray())
                .filter(r -> isSafe(r) == -1)
                .count();

        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        long result = CollectionUtil.toLinkedList(input).stream()
                .map(s -> CollectionUtil.toLinkedList(s, " ").stream().mapToInt(Integer::parseInt).toArray())
                .filter(this::isSafe2)
                .count();

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
