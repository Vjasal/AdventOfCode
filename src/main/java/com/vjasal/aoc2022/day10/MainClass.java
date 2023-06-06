package com.vjasal.aoc2022.day10;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 10);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String[]> instructions = CollectionUtil.toArrayList(input).stream().map(s -> s.split(" ")).toList();

        int x = 1, ip = 0, clock = 1;
        boolean isProcessing = false;

        int signalStrengthSum = 0;

        while (ip < instructions.size()) {
            if ((clock - 20) % 40 == 0) {
                signalStrengthSum += (clock * x);
            }

            switch (instructions.get(ip)[0]) {
                case "noop" -> ip += 1;
                case "addx" -> {
                    if (isProcessing) {
                        x += Integer.parseInt(instructions.get(ip)[1]);
                        ip += 1;
                    }
                    isProcessing = !isProcessing;
                }
            }

            clock += 1;
        }

        logger.info("" + signalStrengthSum);
        return signalStrengthSum;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String[]> instructions = CollectionUtil.toArrayList(input).stream().map(s -> s.split(" ")).toList();

        int x = 1, ip = 0, clock = 1, screenWidth = 40;
        boolean isProcessing = false;

        StringBuilder sb = new StringBuilder();

        while (ip < instructions.size()) {
            int drawPosition = (clock - 1) % screenWidth;

            if (drawPosition == x - 1 || drawPosition == x || drawPosition == x + 1) {
                sb.append("##");
            } else {
                sb.append("..");
            }

            if (drawPosition + 1 == screenWidth) {
                sb.append("\n");
            }

            switch (instructions.get(ip)[0]) {
                case "noop" -> ip += 1;
                case "addx" -> {
                    if (isProcessing) {
                        x += Integer.parseInt(instructions.get(ip)[1]);
                        ip += 1;
                    }
                    isProcessing = !isProcessing;
                }
            }

            clock += 1;
        }

        logger.info("Result: \n" + sb);
        return sb.toString().hashCode();
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
