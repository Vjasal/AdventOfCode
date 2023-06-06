package com.vjasal.aoc2022.day17;

import com.vjasal.type.vector.Vector2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 17);
    }

    private List<Set<Vector2<Integer>>> getRocks() {
        Set<Vector2<Integer>> rock1 = new HashSet<>() {{
            add(Vector2.valueOf(0, 0));
            add(Vector2.valueOf(1, 0));
            add(Vector2.valueOf(2, 0));
            add(Vector2.valueOf(3, 0));
        }};
        Set<Vector2<Integer>> rock2 = new HashSet<>() {{
            add(Vector2.valueOf(0, 1));
            add(Vector2.valueOf(1, 0));
            add(Vector2.valueOf(1, 1));
            add(Vector2.valueOf(1, 2));
            add(Vector2.valueOf(2, 1));
        }};
        Set<Vector2<Integer>> rock3 = new HashSet<>() {{
            add(Vector2.valueOf(0, 0));
            add(Vector2.valueOf(1, 0));
            add(Vector2.valueOf(2, 0));
            add(Vector2.valueOf(2, 1));
            add(Vector2.valueOf(2, 2));
        }};
        Set<Vector2<Integer>> rock4 = new HashSet<>() {{
            add(Vector2.valueOf(0, 0));
            add(Vector2.valueOf(0, 1));
            add(Vector2.valueOf(0, 2));
            add(Vector2.valueOf(0, 3));
        }};
        Set<Vector2<Integer>> rock5 = new HashSet<>() {{
            add(Vector2.valueOf(0, 0));
            add(Vector2.valueOf(0, 1));
            add(Vector2.valueOf(1, 0));
            add(Vector2.valueOf(1, 1));
        }};
        return new ArrayList<>(Arrays.asList(rock1, rock2, rock3, rock4, rock5));
    }

    private long simulate(long numberOfSteps, String input) {
        List<Set<Vector2<Integer>>> rocks = getRocks();

        List<Character> jetPattern = CollectionUtil.toCharList(input);
        int currentJet = 0;

        Set<Vector2<Integer>> grid = new HashSet<>();
        grid.addAll(IntStream.range(0, 7).mapToObj(i -> Vector2.valueOf(i, -1)).toList());

        Map<Set<Vector2<Integer>>, Vector2<Integer>> cache = new HashMap<>();

        boolean cycleFound = false;
        long heightFromCycles = 0;

        long index = 0;
        while (index < numberOfSteps) {
            Set<Vector2<Integer>> currentRock = rocks.get((int) (index % rocks.size()));
            int maxY = grid.stream().mapToInt(Vector2::val2).max().orElseThrow();
            currentRock = currentRock.stream().map(v -> Vector2.valueOf(v.val1() + 2, v.val2() + maxY + 4)).collect(Collectors.toSet());

            while (true) {
                if (jetPattern.get(currentJet) == '>') {
                    Set<Vector2<Integer>> movedRock = currentRock.stream().map(v -> Vector2.add(v, Vector2.RIGHT)).collect(Collectors.toSet());
                    if (movedRock.stream().allMatch(v -> v.val1() <= 6 && !grid.contains(v))) {
                        currentRock = movedRock;
                    }
                } else {
                    Set<Vector2<Integer>> movedRock = currentRock.stream().map(v -> Vector2.add(v, Vector2.LEFT)).collect(Collectors.toSet());
                    if (movedRock.stream().allMatch(v -> v.val1() >= 0 && !grid.contains(v))) {
                        currentRock = movedRock;
                    }
                }
                currentJet = (currentJet + 1) % jetPattern.size();

                Set<Vector2<Integer>> movedRock = currentRock.stream().map(v -> Vector2.add(v, Vector2.DOWN)).collect(Collectors.toSet());
                if (movedRock.stream().anyMatch(grid::contains)) {
                    break;
                }
                currentRock = movedRock;
            }
            grid.addAll(currentRock);
            index += 1;

            if (cycleFound) continue;

            Set<Vector2<Integer>> stateHash = grid.stream().filter(v -> maxY - v.val2() <= 30).map(v -> Vector2.valueOf(v.val1(), maxY - v.val2())).collect(Collectors.toSet());
            if (cache.containsKey(stateHash)) {
                Vector2<Integer> info = cache.get(stateHash);
                long cycleLength = index - info.val1();
                long numberOfCycles = (numberOfSteps - index) / cycleLength;

                int cycleHeight = maxY - info.val2();
                heightFromCycles = numberOfCycles * cycleHeight;

                index += numberOfCycles * cycleLength;
                cycleFound = true;
            } else {
                Vector2<Integer> info = Vector2.valueOf((int) index, maxY);
                cache.put(stateHash, info);
            }
        }

       return grid.stream().mapToInt(Vector2::val2).max().orElseThrow() + heightFromCycles + 1;
    }

    @Override
    public long solvePuzzle1(String input) {
        long result = simulate(2022, input);
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        long result = simulate(1_000_000_000_000L, input);
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
