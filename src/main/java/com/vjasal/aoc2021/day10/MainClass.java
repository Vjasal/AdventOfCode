package com.vjasal.aoc2021.day10;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 10);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> lines = CollectionUtil.toLinkedList(input);
        long result = lines.stream().mapToLong(SyntaxChecker::syntaxErrorScore).sum();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> lines = CollectionUtil.toLinkedList(input);
        List<Long> scores = lines.stream()
                .mapToLong(SyntaxChecker::autocompleteScore)
                .filter(value -> value != -1)
                .sorted()
                .boxed()
                .collect(Collectors.toList());
        if (scores.size() % 2 == 0) {
            throw new IllegalStateException("Even number of scores - no middle");
        }
        logger.info("Result: " + scores.get(scores.size() / 2));
        return scores.get(scores.size() / 2);
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
