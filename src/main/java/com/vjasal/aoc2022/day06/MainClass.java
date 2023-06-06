package com.vjasal.aoc2022.day06;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 6);
    }

    @Override
    public long solvePuzzle1(String input) {
        int result = getStartOfMarker(input, 4);
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        int result = getStartOfMarker(input, 14);
        logger.info("Result: " + result);
        return result;
    }

    private int getStartOfMarker(String input, int windowSize) {
        List<String> data = CollectionUtil.toArrayList(input, "");
        Map<String, Integer> window = new HashMap<>();

        for (int i = 0; i < windowSize; i++) {
            String s = data.get(i);
            window.merge(s, 1, Integer::sum);
        }

        int k = windowSize;
        while (window.entrySet().stream().anyMatch(e -> e.getValue() > 1)) {
            String oldValue = data.get(k - windowSize);
            String newValue = data.get(k);

            window.put(oldValue, window.get(oldValue) - 1);
            window.remove(oldValue, 0);

            window.putIfAbsent(newValue, 0);
            window.put(newValue, window.get(newValue) + 1);

            k += 1;
        }

        return k;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
