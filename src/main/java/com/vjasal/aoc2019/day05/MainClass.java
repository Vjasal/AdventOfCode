package com.vjasal.aoc2019.day05;

import com.vjasal.aoc2019.intcode.IllegalOpcodeException;
import com.vjasal.aoc2019.intcode.IntcodeComputer;
import com.vjasal.util.AocMainClass;

import java.io.IOException;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private final static Logger logger = Logger.getLogger(MainClass.class.getName());

    private MainClass() {
        super(2019, 5);
    }

    @Override
    public void solvePuzzle1(String input) {
        try {
            IntcodeComputer computer = new IntcodeComputer(input);
            computer.writeInput(1);
            while (computer.solve()) {
                logger.info("out = " + computer.readOutput());
            }
        } catch (IllegalOpcodeException | IOException e) {
            logger.warning("Exception: " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void solvePuzzle2(String input) {

    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
