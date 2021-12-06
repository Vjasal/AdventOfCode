package com.vjasal.aoc2021.day01;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 1);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<Integer> measurements = CollectionUtil.toArrayList(input).stream()
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());

        int result = 0;
        int previous = measurements.get(0);

        for (int measurement : measurements) {
            result += previous < measurement ? 1 : 0;
            previous = measurement;
        }

        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<Integer> measurements = CollectionUtil.toArrayList(input).stream()
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());

        int result = 0;
        int previous = measurements.get(0);

        // Because checking 'A + B + C < B + C + D' is the same as checking 'A < D'
        for (int i = 0; i < measurements.size() - 3; i++) {
            result += previous < measurements.get(i + 3) ? 1 : 0;
            previous = measurements.get(i + 1);
        }

        logger.info("Result: " + result);
        return result;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);
        String test = "199\n" +
                "200\n" +
                "208\n" +
                "210\n" +
                "200\n" +
                "207\n" +
                "240\n" +
                "269\n" +
                "260\n" +
                "263";

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
