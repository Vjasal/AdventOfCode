package com.vjasal.aoc2022.day02;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 2);
    }

    @Override
    public long solvePuzzle1(String input) {
        Map<String, Integer> scores = new HashMap<>() {{
            // A - rock, B - paper, C - scissors
            // X - rock, Y - paper, Z - scissors

            // lose - 0p, draw - 3p, win - 6p
            // X - 1p, Y - 2p, Z - 3p

            put("A X", 3 + 1);
            put("A Y", 6 + 2);
            put("A Z", 0 + 3);

            put("B X", 0 + 1);
            put("B Y", 3 + 2);
            put("B Z", 6 + 3);

            put("C X", 6 + 1);
            put("C Y", 0 + 2);
            put("C Z", 3 + 3);
        }};

        int score = CollectionUtil.toLinkedList(input).stream().mapToInt(scores::get).sum();
        logger.info("" + score);

        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {
        Map<String, Integer> scores = new HashMap<>() {{
            // A - rock, B - paper, C - scissors
            // X - rock, Y - paper, Z - scissors

            // X - lose - 0p, Y - draw - 3p, Z - win - 6p

            put("A X", 0 + 3);
            put("A Y", 3 + 1);
            put("A Z", 6 + 2);

            put("B X", 0 + 1);
            put("B Y", 3 + 2);
            put("B Z", 6 + 3);

            put("C X", 0 + 2);
            put("C Y", 3 + 3);
            put("C Z", 6 + 1);
        }};

        int score = CollectionUtil.toLinkedList(input).stream().mapToInt(scores::get).sum();
        logger.info("" + score);

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
