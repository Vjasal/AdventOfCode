package com.vjasal.aoc2023.day21;

import com.vjasal.type.Grid;
import com.vjasal.type.tuple.Tuple2;
import com.vjasal.type.tuple.Tuple3;
import com.vjasal.type.tuple.Tuple4;
import com.vjasal.type.vector.Vector2;
import com.vjasal.type.vector.Vector4;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2023, 21);
    }

    private long countSteps(Vector2<Integer> start, Grid<String> map, long steps) {
        Queue<Tuple2<Vector2<Integer>, Long>> queue = new LinkedList<>();
        Set<Vector2<Integer>> seen = new HashSet<>();
        Set<Vector2<Integer>> ans = new HashSet<>();

        queue.add(Tuple2.valueOf(start, steps));

        while (!queue.isEmpty()) {
            Tuple2<Vector2<Integer>, Long> current = queue.poll();
            Vector2<Integer> position = current.val1();
            long remaining = current.val2();

            if (!map.containsKey(position))
                continue;
            if (map.get(position).equals("#"))
                continue;

            if (seen.contains(position))
                continue;
            seen.add(position);

            if ((remaining % 2 == 0)) {
                ans.add(position);
            }

            if (remaining == 0)
                continue;

            for (Vector2<Integer> next : Vector2.neighbours(position, false)) {
                queue.add(Tuple2.valueOf(next, remaining - 1));
            }
        }

        return ans.size();
    }

    @Override
    public long solvePuzzle1(String input) {
        Grid<String> map = CollectionUtil.toGrid(input);
        Vector2<Integer> start = map.entrySet().stream()
                .filter(e -> e.getValue().equals("S"))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
        long result = countSteps(start, map, 64);
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Grid<String> map = CollectionUtil.toGrid(input);
        Vector2<Integer> start = map.entrySet().stream()
                .filter(e -> e.getValue().equals("S"))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();

        int steps = 26501365;
        int d = map.width();
        if (map.width() != map.height())
            throw new IllegalStateException();
        if (steps % d != d / 2)
            throw new IllegalStateException();
        long k = steps / d;

        long countEven = countSteps(start, map, d * 2L);
        long countOdd = countSteps(start, map, d * 2L - 1);

        long countCornerR = countSteps(Vector2.valueOf(start.val1(), 0), map, d - 1);
        long countCornerL = countSteps(Vector2.valueOf(start.val1(), d - 1), map, d - 1);
        long countCornerU = countSteps(Vector2.valueOf(0, start.val2()), map, d - 1);
        long countCornerD = countSteps(Vector2.valueOf(d - 1, start.val2()), map, d - 1);

        long countEdgeSmallUR = countSteps(Vector2.valueOf(0, 0), map, d / 2 - 1);
        long countEdgeSmallUL = countSteps(Vector2.valueOf(d - 1, 0), map, d / 2 - 1);
        long countEdgeSmallDR = countSteps(Vector2.valueOf(0, d - 1), map, d / 2 - 1);
        long countEdgeSmallDL = countSteps(Vector2.valueOf(d - 1, d - 1), map, d / 2 - 1);

        long countEdgeBigUR = countSteps(Vector2.valueOf(0, 0), map, d * 3L / 2 - 1);
        long countEdgeBigUL = countSteps(Vector2.valueOf(d - 1, 0), map, d * 3L / 2 - 1);
        long countEdgeBigDR = countSteps(Vector2.valueOf(0, d - 1), map, d * 3L / 2 - 1);
        long countEdgeBigDL = countSteps(Vector2.valueOf(d - 1, d - 1), map, d * 3L / 2 - 1);

        long result = countEven * k * k +
                countOdd * (k - 1) * (k - 1) +
                countCornerR + countCornerL + countCornerU + countCornerD +
                (countEdgeSmallUR + countEdgeSmallUL + countEdgeSmallDR + countEdgeSmallDL) * k +
                (countEdgeBigUR + countEdgeBigUL + countEdgeBigDR + countEdgeBigDL) * (k - 1);
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
