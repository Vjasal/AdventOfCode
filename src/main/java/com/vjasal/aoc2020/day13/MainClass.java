package com.vjasal.aoc2020.day13;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;
import com.vjasal.util.MathUtil;

import java.io.StringReader;
import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2020, 13);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> lines = CollectionUtil.toArrayList(input);

        int start = Integer.parseInt(lines.get(0));
        List<Integer> ids = parseInput1(lines.get(1));

        int timestamp = start;
        int result = -1;
        while (result < 0) {
            for (Integer id : ids) {
                if (timestamp % id == 0) {
                    result = id * (timestamp - start);
                }
            }
            timestamp++;
        }

        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> lines = CollectionUtil.toArrayList(input);
        Map<Long, Long> reminders = parseInput2(lines.get(1));

        long m = reminders.keySet().stream().reduce(1L, (a, b) -> a * b);
        long result = 0;
        for (Map.Entry<Long, Long> entry : reminders.entrySet()) {
            long mi = entry.getKey();
            long ci = entry.getValue();
            long ni = m / mi;

            result += ci * ni * MathUtil.powMod(ni, mi - 2, mi);
            result = MathUtil.mod(result, m);
        }

        logger.info("Result: " + result);
        return result;
    }

    private List<Integer> parseInput1(String input) {
        List<Integer> result = new ArrayList<>();

        try (Scanner scanner = new Scanner(new StringReader(input))) {
            scanner.useDelimiter(",");

            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    result.add(scanner.nextInt());
                } else {
                    scanner.next();
                }
            }
        }

        return result;
    }

    private Map<Long, Long> parseInput2(String input) {
        Map<Long, Long> result = new HashMap<>();

        try (Scanner scanner = new Scanner(new StringReader(input))) {
            scanner.useDelimiter(",");

            int index = 0;
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    long id = scanner.nextLong();
                    result.put(id, MathUtil.mod(-index, id));
                } else {
                    scanner.next();
                }
                index++;
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
