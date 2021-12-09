package com.vjasal.aoc2021.day07;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 7);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<Integer> values = CollectionUtil.toArrayList(input, ",").stream()
                .mapToInt(Integer::parseInt).sorted().boxed().collect(Collectors.toList());

        if (values.size() % 2 != 0) {
            logger.warning("Error - even number of values");
            return 0;
        }

        int median = values.get(values.size() / 2);
        long result = values.stream().reduce(0, (a, b) -> a + Math.abs(b - median));
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<Integer> values = CollectionUtil.toArrayList(input, ",").stream()
                .mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

        double mean = values.stream().mapToDouble(Integer::doubleValue).average().orElse(-1);

        long result1 = values.stream().reduce(0, (a, b) -> {
            int x = Math.abs(b - (int) Math.floor(mean));
            return a + x * (x + 1) / 2;
        });
        long result2 = values.stream().reduce(0, (a, b) -> {
            int x = Math.abs(b - (int) Math.ceil(mean));
            return a + x * (x + 1) / 2;
        });

        long result = Math.min(result1, result2);
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
