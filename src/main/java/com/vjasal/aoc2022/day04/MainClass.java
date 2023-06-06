package com.vjasal.aoc2022.day04;

import com.vjasal.type.Range;
import com.vjasal.type.vector.Vector2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 4);
    }

    @Override
    public long solvePuzzle1(String input) {
        long result = CollectionUtil.toLinkedList(input).stream()
                .map(this::mapToRanges)
                .filter(pair -> pair.val1().contains(pair.val2()) || pair.val2().contains(pair.val1()))
                .count();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        long result = CollectionUtil.toLinkedList(input).stream()
                .map(this::mapToRanges)
                .filter(pair -> pair.val1().overlaps(pair.val2()))
                .count();
        logger.info("Result: " + result);
        return result;
    }

    private Vector2<Range<Integer>> mapToRanges(String input) {
        String[] in = input.split(",");
        return new Vector2<>(mapToRange(in[0]), mapToRange(in[1]));
    }

    private Range<Integer> mapToRange(String input) {
        String[] in = input.split("-");
        return new Range<>(Integer.parseInt(in[0]), Integer.parseInt(in[1]));
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
