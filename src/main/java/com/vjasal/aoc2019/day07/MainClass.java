package com.vjasal.aoc2019.day07;

import com.vjasal.aoc2019.intcode.IllegalOpcodeException;
import com.vjasal.aoc2019.intcode.IntcodeComputer;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2019, 7);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<List<Integer>> permutations = CollectionUtil.permutations(Arrays.asList(0, 1, 2, 3, 4));
        long result = Long.MIN_VALUE;

        try {
            for (List<Integer> amplifiers : permutations) {
                List<IntcodeComputer> computers = new ArrayList<>(5);
                for (int i = 0; i < 5; i++) {
                    computers.add(new IntcodeComputer(input));
                    computers.get(i).writeInput(amplifiers.get(i));
                }

                int inputSignal = 0;
                for (int i = 0; i < 5; i++) {
                    IntcodeComputer computer = computers.get(i);
                    computer.writeInput(inputSignal);
                    while (computer.solve()) {
                        inputSignal = computer.readOutput();
                    }
                }

                result = Math.max(result, inputSignal);
            }
        } catch (IllegalOpcodeException | IOException e) {
            e.printStackTrace();
        }

        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<List<Integer>> permutations = CollectionUtil.permutations(Arrays.asList(5, 6, 7, 8, 9));
        long result = Long.MIN_VALUE;

        try {
            for (List<Integer> amplifiers : permutations) {
                List<IntcodeComputer> computers = new ArrayList<>(5);
                for (int i = 0; i < 5; i++) {
                    computers.add(new IntcodeComputer(input));
                    computers.get(i).writeInput(amplifiers.get(i));
                }

                int inputSignal = 0;
                int k = 0;
                boolean hasOutput = true;

                while (hasOutput || k < 4) {
                    computers.get(k).writeInput(inputSignal);
                    hasOutput = computers.get(k).solve();
                    if (hasOutput) {
                        inputSignal = computers.get(k).readOutput();
                    }
                    k = (k + 1) % 5;
                }

                result = Math.max(result, inputSignal);
            }
        } catch (IllegalOpcodeException | IOException e) {
            e.printStackTrace();
        }

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
