package com.vjasal.aoc2023.day04;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());
    private static final Pattern pattern = Pattern.compile("^Card +\\d+: +([\\d ]+) \\| +([\\d ]+)$");

    public MainClass() {
        super(2023, 4);
    }

    @Override
    public long solvePuzzle1(String input) {
        long result = 0;
        for (String line : CollectionUtil.toLinkedList(input)) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find())
                throw new IllegalArgumentException("Wrong input format!");
            Set<Integer> winningNumbers = Arrays.stream(matcher.group(1).split(" +"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());
            long count = Arrays.stream(matcher.group(2).split(" +"))
                    .mapToInt(Integer::parseInt)
                    .filter(winningNumbers::contains)
                    .count();
            if (count == 0) continue;
            result += Math.pow(2, count - 1);
        }
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> lines = CollectionUtil.toLinkedList(input);
        long[] cards = new long[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            Matcher matcher = pattern.matcher(lines.get(i));
            if (!matcher.find())
                throw new IllegalArgumentException("Wrong input format!");
            Set<Integer> winningNumbers = Arrays.stream(matcher.group(1).split(" +"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());
            long count = Arrays.stream(matcher.group(2).split(" +"))
                    .mapToInt(Integer::parseInt)
                    .filter(winningNumbers::contains)
                    .count();
            cards[i] += 1;
            for (int k = 0; k < count && i + k + 1 < lines.size(); k++)
                cards[i + k + 1] += cards[i];
        }
        long result = Arrays.stream(cards).sum();
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
