package com.vjasal.aoc2022.day05;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 5);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> sections = CollectionUtil.toListOfSections(input);

        Cargo cargo = new Cargo(sections.get(0));

        CollectionUtil.toLinkedList(sections.get(1)).stream()
                .map(Instruction::parseInstruction)
                .forEach(cargo::moveCrates);

        String result = cargo.getTopValues();
        logger.info("Result: " + result);
        return result.hashCode();
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> sections = CollectionUtil.toListOfSections(input);

        Cargo cargo = new Cargo(sections.get(0));

        CollectionUtil.toLinkedList(sections.get(1)).stream()
                .map(Instruction::parseInstruction)
                .forEach(cargo::moveMultipleCrates);

        String result = cargo.getTopValues();
        logger.info("Result: " + result);
        return result.hashCode();
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
//        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
