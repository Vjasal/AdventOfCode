package com.vjasal.aoc2021.day18;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 18);
    }

    @Override
    public long solvePuzzle1(String input) {
        Queue<SnailNumber> numbers = CollectionUtil.toLinkedList(input).stream()
                .map(SnailNumber::new).collect(Collectors.toCollection(LinkedList::new));

        if (numbers.isEmpty()) throw new IllegalStateException();

        SnailNumber sum = numbers.poll();
        while (!numbers.isEmpty()) {
            sum = sum.add(numbers.poll());
        }

        long result = sum.magnitude();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<SnailNumber> numbers = CollectionUtil.toLinkedList(input).stream()
                .map(SnailNumber::new).collect(Collectors.toCollection(LinkedList::new));

        long result = Long.MIN_VALUE;
        for (SnailNumber number1 : numbers) {
            for (SnailNumber number2 : numbers) {
                long magnitude = number1.add(number2).magnitude();
                result = Math.max(result, magnitude);
            }
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
