package com.vjasal.aoc2020.day09;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.Util;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    private MainClass() {
        super(2020, 9);
    }

    @Override
    public void solvePuzzle1(String input) {
        List<Long> list = Util.toLinkedListOfLines(input).stream().mapToLong(Long::parseLong).boxed()
                .collect(Collectors.toCollection(LinkedList::new));

        PreambleSet preambleSet = new PreambleSet(25);

        Iterator<Long> iterator = list.iterator();
        for (int i = 0; i < preambleSet.getMaxSize(); i++) {
            preambleSet.add(iterator.next());
        }

        long current = 0;
        while (iterator.hasNext()) {
            current = iterator.next();
            if (!preambleSet.containsSum(current)) {
                break;
            }
            preambleSet.add(current);
        }

        logger.info("Result: " + current);
    }

    @Override
    public void solvePuzzle2(String input) {
        List<Long> list = Util.toLinkedListOfLines(input).stream().mapToLong(Long::parseLong).boxed()
                .collect(Collectors.toCollection(LinkedList::new));
        long target = 22477624;

        Set<Long> partialSums = new LinkedHashSet<>();

        // calculate partial sums, up until a set that sums up to target has been found
        long previousSum = 0;
        for (long next : list) {
            previousSum += next;
            partialSums.add(previousSum);
            if (partialSums.contains(previousSum - target)) {
                break;
            }
        }

        // get min and max value
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        boolean foundStart = false;
        Iterator<Long> iterator1 = list.iterator();
        Iterator<Long> iterator2 = partialSums.iterator();
        while (iterator1.hasNext() && iterator2.hasNext()) {
            long val = iterator1.next();
            long sum = iterator2.next();
            if (foundStart) {
                min = min < val ? min : val;
                max = max > val ? max : val;
            } else if (partialSums.contains(target + sum)) {
                foundStart = true;
            }
        }

        logger.info("Result: " + (min + max));
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
