package com.vjasal.aoc2023.day06;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());
    private static final Pattern pattern = Pattern.compile("^\\w+: +(( *\\d+)+)$");

    public MainClass() {
        super(2023, 6);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> lines = CollectionUtil.toLinkedList(input);
        Matcher timeMatcher = pattern.matcher(lines.get(0));
        Matcher distanceMatcher = pattern.matcher(lines.get(1));
        if (!timeMatcher.find() || !distanceMatcher.find())
            throw new IllegalArgumentException("Wring input format!");

        List<Integer> times = Arrays.stream(timeMatcher.group(1).split(" +"))
                .map(Integer::parseInt).toList();
        List<Integer> distances = Arrays.stream(distanceMatcher.group(1).split(" +"))
                .map(Integer::parseInt).toList();

        long result = 1;
        for (int ix = 0; ix < times.size(); ix++) {
            final int i = ix;
            long c = IntStream.range(0, times.get(i))
                    .map(t -> t * (times.get(i) - t))
                    .filter(d -> d > distances.get(i))
                    .count();
            result *= c;
        }
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> lines = CollectionUtil.toLinkedList(input);
        Matcher timeMatcher = pattern.matcher(lines.get(0));
        Matcher distanceMatcher = pattern.matcher(lines.get(1));
        if (!timeMatcher.find() || !distanceMatcher.find())
            throw new IllegalArgumentException("Wring input format!");

        long time = Long.parseLong(timeMatcher.group(1).replace(" ", ""));
        long distance = Long.parseLong(distanceMatcher.group(1).replace(" ", ""));

        long t = 0;
        while (t < time / 2 && t * (time - t) < distance) t++;

        long result = time - 2 * t + 1;


        logger.info("Result: " + result);
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
