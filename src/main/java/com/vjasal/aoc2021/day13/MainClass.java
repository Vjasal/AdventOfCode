package com.vjasal.aoc2021.day13;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 13);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> sections = CollectionUtil.toListOfSections(input);
        Paper paper = new Paper(sections.get(0));
        paper.execFold(CollectionUtil.toLinkedList(sections.get(1)).get(0));
        int result = paper.countDots();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> sections = CollectionUtil.toListOfSections(input);
        Paper paper = new Paper(sections.get(0));
        for (String fold : CollectionUtil.toLinkedList(sections.get(1))) {
            paper.execFold(fold);
        }
        logger.info("Result: " + paper);
        return 0;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
