package com.vjasal.aoc2020.day14;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.math.BigInteger;
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
        List<String> lines = CollectionUtil.toLinkedListOfLines(input);

        Pattern pattern = Pattern.compile("^mem\\[(\\d+)] = (\\d+)$");

        Map<Integer, BigInteger> mem = new HashMap<>();
        String mask = "";

        for (String line : lines) {
            if (line.startsWith("mask")) {
                mask = line.substring(7);
            } else {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    int address      = Integer.parseInt(matcher.group(1));
                    BigInteger value = new BigInteger(matcher.group(2));

                    mem.put(address, applyMask1(mask, value));
                } else {
                    throw new RuntimeException("Invalid input!");
                }
            }
        }

        BigInteger result = mem.values().stream().reduce(BigInteger.ZERO, BigInteger::add);
        logger.info("Result: " + result);
        return result.longValue();
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> lines = CollectionUtil.toLinkedListOfLines(input);

        Pattern pattern = Pattern.compile("^mem\\[(\\d+)] = (\\d+)$");

        Map<BigInteger, BigInteger> mem = new HashMap<>();
        String mask = "";

        for (String line : lines) {
            if (line.startsWith("mask")) {
                mask = line.substring(7);
            } else {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    BigInteger address = new BigInteger(matcher.group(1));
                    BigInteger value   = new BigInteger(matcher.group(2));

                    for (BigInteger maskedAddress : applyMask2(mask, address)) {
                        mem.put(maskedAddress, value);
                    }
                } else {
                    throw new RuntimeException("Invalid input!");
                }
            }
        }

        BigInteger result = mem.values().stream().reduce(BigInteger.ZERO, BigInteger::add);
        logger.info("Result: " + result);
        return result.longValue();
    }

    private BigInteger applyMask1(String mask, BigInteger value) {
        char[] array = mask.toCharArray();

        for (int i = 0; i < array.length; i++) {
            switch (array[array.length - 1 - i]) {
                case '0':
                    value = value.clearBit(i);
                    break;
                case '1':
                    value = value.setBit(i);
                    break;
            }
        }

        return value;
    }

    private List<BigInteger> applyMask2(String mask, BigInteger value) {
        char[] array = mask.toCharArray();

        List<BigInteger> result = new ArrayList<>();
        result.add(value);

        for (int i = 0; i < array.length; i++) {
            switch (array[array.length - 1 - i]) {
                case '1':
                    for (int k = 0; k < result.size(); k++) {
                        result.set(k, result.get(k).setBit(i));
                    }
                    break;
                case 'X':
                    int size = result.size();
                    for (int k = 0; k < size; k++) {
                        result.set(k, result.get(k).setBit(i));
                    }
                    for (int k = 0; k < size; k++) {
                        result.add(result.get(k).clearBit(i));
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
