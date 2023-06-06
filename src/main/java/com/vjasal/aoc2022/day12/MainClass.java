package com.vjasal.aoc2022.day12;

import com.vjasal.type.Grid;
import com.vjasal.type.tuple.Tuple2;
import com.vjasal.type.vector.Vector2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 12);
    }

    @Override
    public long solvePuzzle1(String input) {
        Grid<Character> grid = CollectionUtil.toGrid(input, s -> s.charAt(0));

        Queue<Tuple2<Vector2<Integer>, Integer>> queue = new LinkedList<>();
        Set<Vector2<Integer>> seen = new HashSet<>();

        Vector2<Integer> start = grid.entrySet().stream().filter(v -> v.getValue() == 'S').findAny()
                .map(Map.Entry::getKey).orElse(Vector2.ZERO);
        queue.add(new Tuple2<>(start, 0));

        int result = 0;

        while (!queue.isEmpty()) {
            Tuple2<Vector2<Integer>, Integer> current = queue.poll();

            if (seen.contains(current.val1())) continue;
            seen.add(current.val1());

            if (grid.get(current.val1()) == 'E') {
                result = current.val2();
                break;
            }

            List<Tuple2<Vector2<Integer>, Integer>> next = Vector2.neighbours(current.val1(), false).stream()
                    .filter(v -> grid.containsKey(v) && isReachable(grid.get(current.val1()), grid.get(v)))
                    .map(v -> Tuple2.valueOf(v, current.val2() + 1))
                    .toList();
            queue.addAll(next);
        }

        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Grid<Character> grid = CollectionUtil.toGrid(input, s -> s.charAt(0));

        Queue<Tuple2<Vector2<Integer>, Integer>> queue = new LinkedList<>();
        Set<Vector2<Integer>> seen = new HashSet<>();

        List<Vector2<Integer>> start = grid.entrySet().stream().filter(v -> v.getValue() == 'S' || v.getValue() == 'a')
                .map(Map.Entry::getKey).toList();
        queue.addAll(start.stream().map(v -> Tuple2.valueOf(v, 0)).toList());

        int result = 0;

        while (!queue.isEmpty()) {
            Tuple2<Vector2<Integer>, Integer> current = queue.poll();

            if (seen.contains(current.val1())) continue;
            seen.add(current.val1());

            if (grid.get(current.val1()) == 'E') {
                result = current.val2();
                break;
            }

            List<Tuple2<Vector2<Integer>, Integer>> next = Vector2.neighbours(current.val1(), false).stream()
                    .filter(v -> grid.containsKey(v) && isReachable(grid.get(current.val1()), grid.get(v)))
                    .map(v -> Tuple2.valueOf(v, current.val2() + 1))
                    .toList();
            queue.addAll(next);
        }

        logger.info("Result: " + result);
        return result;
    }

    private boolean isReachable(char from, char to) {
        if (from == 'S') from = 'a';
        if (from == 'E') from = 'z';
        if (to == 'S') to = 'a';
        if (to == 'E') to = 'z';

        return to <= from + 1;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
