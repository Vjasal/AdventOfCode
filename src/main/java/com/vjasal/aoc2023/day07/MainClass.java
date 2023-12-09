package com.vjasal.aoc2023.day07;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());


    public MainClass() {
        super(2023, 7);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<Hand> hands = CollectionUtil.toLinkedList(input).stream()
                .map(Hand::new)
                .sorted()
                .toList();

        long result = LongStream.range(0, hands.size()).map(i -> (i + 1) * hands.get((int) i).getValue()).sum();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<HandWithJoker> hands = CollectionUtil.toLinkedList(input).stream()
                .map(HandWithJoker::new)
                .sorted()
                .toList();

        long result = LongStream.range(0, hands.size()).map(i -> (i + 1) * hands.get((int) i).getValue()).sum();
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
