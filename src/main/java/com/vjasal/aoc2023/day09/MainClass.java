package com.vjasal.aoc2023.day09;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2023, 9);
    }

    private IntStream revRange(int from, int to) {
        return IntStream.range(from, to)
                .map(i -> to - i + from - 1);
    }

    @Override
    public long solvePuzzle1(String input) {
        long result = 0;
        for (String line : CollectionUtil.toLinkedList(input)) {
            List<Integer> sequence = CollectionUtil.toLinkedList(line, " ").stream().map(Integer::parseInt).toList();

            if (sequence.size() < 2)
                throw new IllegalStateException("Too few elements!");

            LinkedList<Integer> lastValues = new LinkedList<>();
            LinkedList<Integer> firstValues = new LinkedList<>();
            lastValues.add(sequence.get(sequence.size() - 1));
            firstValues.add(sequence.get(sequence.size() - 1));
            int k = 1;

            while (k < sequence.size()) {
                LinkedList<Integer> next = new LinkedList<>();
                next.add(sequence.get(sequence.size() - k - 1));
                for (int val : firstValues) {
                    next.add(val - next.getLast());
                }
                lastValues.add(next.getLast());
                firstValues = next;
                k++;
            }

            result += lastValues.stream().reduce(0, Integer::sum);
        }
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        long result = 0;
        for (String line : CollectionUtil.toLinkedList(input)) {
            List<Integer> sequence = CollectionUtil.toLinkedList(line, " ").stream().map(Integer::parseInt).toList();

            if (sequence.size() < 2)
                throw new IllegalStateException("Too few elements!");

            LinkedList<Integer> lastValues = new LinkedList<>();
            LinkedList<Integer> firstValues = new LinkedList<>();
            lastValues.add(sequence.get(0));
            firstValues.add(sequence.get(0));
            int k = 1;

            while (k < sequence.size()) {
                LinkedList<Integer> next = new LinkedList<>();
                next.add(sequence.get(k));
                for (int val : firstValues) {
                    next.add(next.getLast() - val);
                }
                lastValues.add(next.getLast());
                firstValues = next;
                k++;
            }

            result += revRange(0, lastValues.size()).map(lastValues::get).reduce(0, (a, b) -> b - a);
        }
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
