package com.vjasal.aoc2023.day15;

import com.vjasal.type.tuple.Tuple2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());
    private static final Pattern pattern = Pattern.compile("^(\\w+)([=|-])(\\d*)$");

    public MainClass() {
        super(2023, 15);
    }

    @Override
    public long solvePuzzle1(String input) {
        long result = CollectionUtil.toArrayList(input, ",").stream().mapToLong(this::hashString).sum();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Map<Integer, Map<String, Integer>> boxes = new HashMap<>();
        for (String instruction : CollectionUtil.toLinkedList(input, ",")) {
            Matcher matcher = pattern.matcher(instruction);
            if (!matcher.find())
                throw new IllegalArgumentException();

            String label = matcher.group(1);
            int index = hashString(label);

            if (Objects.equals("=", matcher.group(2))) {
                int lens = Integer.parseInt(matcher.group(3));
                if (!boxes.containsKey(index)) boxes.put(index, new LinkedHashMap<>());
                boxes.get(index).put(label, lens);
            } else if (boxes.containsKey(index)) {
                boxes.get(index).remove(label);
            }
        }

        int result = 0;
        for (Map.Entry<Integer, Map<String, Integer>> e : boxes.entrySet()) {
            int k = 1;
            for (int val : e.getValue().values()) {
                result += (e.getKey() + 1) * val * k;
                k += 1;
            }
        }
        logger.info("Result: " + result);
        return result;
    }

    private int hashString(String value) {
        return value.chars().reduce(0, (a, b) -> (a + b) * 17 % 256);
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
