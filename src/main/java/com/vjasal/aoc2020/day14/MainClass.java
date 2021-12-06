package com.vjasal.aoc2020.day14;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;
import com.vjasal.util.MathUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2020, 14);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> lines = CollectionUtil.toLinkedList(input);

        Pattern pattern = Pattern.compile("^mem\\[(\\d+)] = (\\d+)$");

        Map<Long, Long> mem = new HashMap<>();
        String mask = "";

        for (String line : lines) {
            if (line.startsWith("mask")) {
                mask = line.substring(7);
            } else {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    long address = Long.parseLong(matcher.group(1));
                    long value   = Long.parseLong(matcher.group(2));

                    mem.put(address, applyMask1(mask, value));
                } else {
                    throw new RuntimeException("Invalid input!");
                }
            }
        }

        long result = mem.values().stream().reduce(0L, Long::sum);
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> lines = CollectionUtil.toLinkedList(input);

        Pattern pattern = Pattern.compile("^mem\\[(\\d+)] = (\\d+)$");

        Map<Long, Long> mem = new HashMap<>();
        String mask = "";

        for (String line : lines) {
            if (line.startsWith("mask")) {
                mask = line.substring(7);
            } else {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    long address = Long.parseLong(matcher.group(1));
                    long value   = Long.parseLong(matcher.group(2));

                    for (long maskedAddress : applyMask2(mask, address)) {
                        mem.put(maskedAddress, value);
                    }
                } else {
                    throw new RuntimeException("Invalid input!");
                }
            }
        }

        long result = mem.values().stream().reduce(0L, Long::sum);
        logger.info("Result: " + result);
        return result;
    }

    private long applyMask1(String mask, long value) {
        char[] array = mask.toCharArray();

        for (int i = 0; i < array.length; i++) {
            switch (array[array.length - 1 - i]) {
                case '0':
                    value = MathUtil.clearBit(value, i);
                    break;
                case '1':
                    value = MathUtil.setBit(value, i);
                    break;
            }
        }

        return value;
    }

    private List<Long> applyMask2(String mask, long value) {
        char[] array = mask.toCharArray();

        List<Long> result = new ArrayList<>();
        result.add(value);

        for (int i = 0; i < array.length; i++) {
            switch (array[array.length - 1 - i]) {
                case '1':
                    for (int k = 0; k < result.size(); k++) {
                        long val = MathUtil.setBit(result.get(k), i);
                        result.set(k, val);
                    }
                    break;
                case 'X':
                    int size = result.size();
                    for (int k = 0; k < size; k++) {
                        long val = MathUtil.setBit(result.get(k), i);
                        result.set(k, val);
                    }
                    for (int k = 0; k < size; k++) {
                        long val = MathUtil.clearBit(result.get(k), i);
                        result.add(val);
                    }
                    break;
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
