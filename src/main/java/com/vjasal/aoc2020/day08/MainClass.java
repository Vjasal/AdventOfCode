package com.vjasal.aoc2020.day08;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;
import com.vjasal.type.tuple.Tuple2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private final static Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2020, 8);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> program = CollectionUtil.toArrayList(input);
        Tuple2<Boolean, Integer> result = simulateProgram(program);
        logger.info("Result: " + result.val2());
        return result.val2();
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> program = CollectionUtil.toArrayList(input);

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

            Tuple2<Boolean, Integer> result = simulateProgram(copy);
            programHalts = result.val1();
            accumulator  = result.val2();
        }

        logger.info("Result: " + accumulator);
        return accumulator;
    }

    private Tuple2<Boolean, Integer> simulateProgram(List<String> program) {
        int programCounter = 0;
        int accumulator    = 0;

        Set<Integer> seen = new HashSet<>();

        while (true) {
            if (seen.contains(programCounter))
                return new Tuple2<>(false, accumulator);
            if (program.size() <= programCounter)
                return new Tuple2<>(true, accumulator);

            seen.add(programCounter);

            String command = program.get(programCounter).split(" ")[0];
            String value   = program.get(programCounter).split(" ")[1];
            switch (command) {
                case "acc" -> {
                    accumulator += Integer.parseInt(value);
                    programCounter += 1;
                }
                case "jmp" -> programCounter += Integer.parseInt(value);
                case "nop" -> programCounter += 1;
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
