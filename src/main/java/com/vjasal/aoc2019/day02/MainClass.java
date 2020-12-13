package com.vjasal.aoc2019.day02;

import com.vjasal.aoc2019.intcode.IllegalOpcodeException;
import com.vjasal.aoc2019.intcode.IntcodeComputer;
import com.vjasal.util.AocMainClass;

import java.io.IOException;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2019, 2);
    }

    @Override
    public long solvePuzzle1(String input) {
        try {
            IntcodeComputer computer = new IntcodeComputer(input);
            computer.writeMemory(1, 12);
            computer.writeMemory(2, 2);
            computer.solve();
            logger.info("Result: " + computer.readMemory(0));
            return computer.readMemory(0);
        } catch (IllegalOpcodeException | IOException e) {
            logger.warning("Exception: " + e);
        }
        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {
        int target = 19690720;
        int result = -1;

        for (int noun = 0; noun <= 99 && result == -1; noun++) {
            for (int verb = 0; verb <= 99 && result == -1; verb++) {
                try {
                    IntcodeComputer computer = new IntcodeComputer(input);
                    computer.writeMemory(1, noun);
                    computer.writeMemory(2, verb);
                    computer.solve();
                    if (computer.readMemory(0) == target) {
                        result = 100 * noun + verb;
                    }
                } catch (IllegalOpcodeException | IOException e) {
                    logger.warning("Exception: " + e);
                }
            }
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
