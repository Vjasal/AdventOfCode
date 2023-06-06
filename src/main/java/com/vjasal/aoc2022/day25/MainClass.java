package com.vjasal.aoc2022.day25;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;
import com.vjasal.util.MathUtil;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 25);
    }

    @Override
    public long solvePuzzle1(String input) {
        long sum = CollectionUtil.toLinkedList(input).stream().mapToLong(this::parseSnafu)
                .sum();
        String result = toSnafu(sum);
        logger.info("Result: " + result);
        return result.hashCode();
    }

    @Override
    public long solvePuzzle2(String input) {

        return 0;
    }

    private String toSnafu(long value) {
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            int a = (int) MathUtil.mod(value, 5);
            a = a > 2 ? a - 5 : a;
            sb.append(intToSnafuDigit(a));
            value = (value - a) / 5;
        }
        return sb.reverse().toString();
    }

    private long parseSnafu(String value) {
        long result = 0;
        for (char digit : value.toCharArray()) {
            result = result * 5 + snafuDigitToInt(digit);
        }
        return result;
    }

    private int snafuDigitToInt(char digit) {
        return switch (digit) {
            case '=' -> -2;
            case '-' -> -1;
            case '0' -> 0;
            case '1' -> 1;
            case '2' -> 2;
            default -> throw new IllegalArgumentException("" + digit);
        };
    }

    private char intToSnafuDigit(int digit) {
        return switch (digit) {
            case -2 -> '=';
            case -1 -> '-';
            case  0 -> '0';
            case  1 -> '1';
            case  2 ->'2';
            default -> throw new IllegalArgumentException("" + digit);
        };
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
