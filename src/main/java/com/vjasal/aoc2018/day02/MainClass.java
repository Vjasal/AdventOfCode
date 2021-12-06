package com.vjasal.aoc2018.day02;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2018, 2);
    }

    @Override
    public long solvePuzzle1(String input) {
        long twos   = 0;
        long threes = 0;

        for (String line : CollectionUtil.toLinkedList(input)) {
            Map<Character, Integer> count = countLetters(line);
            if (count.containsValue(2)) twos++;
            if (count.containsValue(3)) threes++;
        }

        long result = twos * threes;
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> seenLines = new LinkedList<>();
        for (String line : CollectionUtil.toLinkedList(input)) {
            for (String seen : seenLines) {
                if (stringsAreAlmostEqual(line, seen)) {
                    logger.info("Result: " + getIdString(line, seen));
                    return 0;
                }
            }
            seenLines.add(line);
        }
        return 0;
    }

    private Map<Character, Integer> countLetters(String value) {
        Map<Character, Integer> result = new HashMap<>();
        for (char c : value.toCharArray()) {
            if (!result.containsKey(c)) {
                result.put(c, 0);
            }
            result.put(c, result.get(c) + 1);
        }
        return result;
    }

    private boolean stringsAreAlmostEqual(String val1, String val2) {
        int diff = 0;
        for (int i = 0; i < val1.length(); i++) {
            if (val1.charAt(i) != val2.charAt(i)) diff++;
        }
        return diff == 1;
    }

    private String getIdString(String val1, String val2) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < val1.length(); i++) {
            if (val1.charAt(i) == val2.charAt(i))
                builder.append(val1.charAt(i));
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

//        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
