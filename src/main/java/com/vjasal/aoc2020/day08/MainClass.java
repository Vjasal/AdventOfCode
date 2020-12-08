package com.vjasal.aoc2020.day08;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.Util;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private final static Logger logger = Logger.getLogger(MainClass.class.getName());

    private MainClass() {
        super(2020, 8);
    }

    @Override
    public void solvePuzzle1(String input) {
        List<String> program = Util.toArrayListOfLines(input);
        Pair<Boolean, Integer> result = simulateProgram(program);
        logger.info("Result: " + result.getValue());
    }


    @Override
    public void solvePuzzle2(String input) {
        List<String> program = Util.toArrayListOfLines(input);

        boolean programHalts = false;
        int accumulator = 0;
        int index = -1;

        while (!programHalts) {
            List<String> copy = new ArrayList<>(program);

            index += 1;
            String instruction = copy.get(index);
            if (instruction.startsWith("jmp")) {
                copy.set(index, instruction.replace("jmp", "nop"));
            } else if (instruction.startsWith("nop")) {
                copy.set(index, instruction.replace("nop", "jmp"));
            } else {
                continue;
            }

            Pair<Boolean, Integer> result = simulateProgram(copy);
            programHalts = result.getKey();
            accumulator  = result.getValue();
        }

        logger.info("Result: " + accumulator);
    }

    private Pair<Boolean, Integer> simulateProgram(List<String> program) {
        int programCounter = 0;
        int accumulator    = 0;

        Set<Integer> seen = new HashSet<>();

        while (true) {
            if (seen.contains(programCounter))
                return new Pair<>(false, accumulator);
            if (program.size() <= programCounter)
                return new Pair<>(true, accumulator);

            seen.add(programCounter);

            String command = program.get(programCounter).split(" ")[0];
            String value   = program.get(programCounter).split(" ")[1];
            switch (command) {
                case "acc":
                    accumulator += Integer.valueOf(value);
                    programCounter += 1;
                    break;
                case "jmp":
                    programCounter += Integer.valueOf(value);
                    break;
                case "nop":
                    programCounter += 1;
                    break;
            }
        }
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
