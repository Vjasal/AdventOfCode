package com.vjasal.aoc2023.day05;

import com.vjasal.type.vector.Vector2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());
    private static final Pattern seedPattern = Pattern.compile("^seeds: ((\\d+ ?)+)$");

    public MainClass() {
        super(2023, 5);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<Permutation> permutations = new LinkedList<>();
        List<Long> seeds = new LinkedList<>();
        for (String section : CollectionUtil.toListOfSections(input)) {
            if (seeds.isEmpty()) {
                Matcher matcher = seedPattern.matcher(section);
                if (!matcher.find())
                    throw new IllegalArgumentException("Wrong input format!");
                for (String num : matcher.group(1).split(" ")) {
                    seeds.add(Long.parseLong(num));
                }
            } else {
                Permutation permutation = new Permutation();
                CollectionUtil.toLinkedList(section).stream()
                        .skip(1)
                        .map(s -> s.split(" "))
                        .map(s -> Arrays.stream(s).mapToLong(Long::parseLong).toArray())
                        .forEach(x -> permutation.add(x[0], x[1], x[2]));
                permutations.add(permutation);
            }
        }

        long result = seeds.stream().mapToLong(seed -> {
            for (Permutation p : permutations) {
                seed = p.map(seed);
            }
            return seed;
        }).min().orElse(0);

        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<Permutation> permutations = new LinkedList<>();
        List<Vector2<Long>> seeds = new LinkedList<>();
        for (String section : CollectionUtil.toListOfSections(input)) {
            if (seeds.isEmpty()) {
                Matcher matcher = seedPattern.matcher(section);
                if (!matcher.find())
                    throw new IllegalArgumentException("Wrong input format!");
                String[] seedsVal = matcher.group(1).split(" ");
                for (int i = 0; i < seedsVal.length; i += 2) {
                    seeds.add(Vector2.valueOf(Long.parseLong(seedsVal[i]), Long.parseLong(seedsVal[i + 1])));
                }
            } else {
                Permutation permutation = new Permutation();
                CollectionUtil.toLinkedList(section).stream()
                        .skip(1)
                        .map(s -> s.split(" "))
                        .map(s -> Arrays.stream(s).mapToLong(Long::parseLong).toArray())
                        .forEach(x -> permutation.add(x[0], x[1], x[2]));
                permutations.add(permutation);
            }
        }

        for (Permutation permutation : permutations) {
            List<Vector2<Long>> next = new LinkedList<>();
            for (Vector2<Long> seed : seeds) {
                next.addAll(permutation.mapRange(seed.val1(), seed.val2()));
            }
            seeds = next;
        }

        long result = seeds.stream().mapToLong(Vector2::val1).min().orElse(0);
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
