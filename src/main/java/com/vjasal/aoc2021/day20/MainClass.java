package com.vjasal.aoc2021.day20;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 20);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> sections = CollectionUtil.toListOfSections(input);
        List<Character> algorithm = CollectionUtil.toCharList(sections.get(0));

        ImageEnhancer enhancer = new ImageEnhancer(sections.get(1));
        enhancer.enhanceImage(algorithm, 2);

        long result = enhancer.countLightPixels();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> sections = CollectionUtil.toListOfSections(input);
        List<Character> algorithm = CollectionUtil.toCharList(sections.get(0));

        ImageEnhancer enhancer = new ImageEnhancer(sections.get(1));
        enhancer.enhanceImage(algorithm, 50);

        long result = enhancer.countLightPixels();
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
