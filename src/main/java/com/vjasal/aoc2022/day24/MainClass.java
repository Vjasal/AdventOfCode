package com.vjasal.aoc2022.day24;

import com.vjasal.type.Grid;
import com.vjasal.type.tuple.Tuple2;
import com.vjasal.type.vector.Vector2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 24);
    }

    @Override
    public long solvePuzzle1(String input) {
        Grid<Character> grid = CollectionUtil.toGrid(input, s -> s.charAt(0));
        Set<Blizzard> blizzards = grid.entrySet().stream()
                .filter(e -> e.getValue() != '#' && e.getValue() != '.')
                .map(e -> new Blizzard(e.getKey(), mapToDirection(e.getValue())))
                .collect(Collectors.toSet());

        Vector2<Integer> start = Vector2.valueOf(0, 1);
        Vector2<Integer> end   = Vector2.valueOf(grid.width() - 2, grid.height() - 1);

        Map<Integer, Set<Vector2<Integer>>> cache = new HashMap<>();

        Queue<Tuple2<Vector2<Integer>, Integer>> queue = new LinkedList<>();
        Set<Tuple2<Vector2<Integer>, Integer>> seen = new HashSet<>();
        queue.add(Tuple2.valueOf(start, 0));

        while (!queue.isEmpty()) {
            Tuple2<Vector2<Integer>, Integer> current = queue.poll();

            if (seen.contains(current)) continue;
            seen.add(current);

            if (Objects.equals(current.val1(), end)) {
                logger.info("Result: " + current.val2());
                break;
            }

            if (!cache.containsKey(current.val2())) {
                blizzards = blizzards.stream().map(b -> b.next(grid.width(), grid.height())).collect(Collectors.toSet());
//                logger.info("" + blizzards);

                Set<Vector2<Integer>> emptySpaces = new HashSet<>();
                emptySpaces.add(start);
                emptySpaces.add(end);
                for (int y = 1; y < grid.height() - 1; y++) {
                    for (int x = 1; x < grid.width() - 1; x++) {
                        Vector2<Integer> v = Vector2.valueOf(x, y);
                        if (blizzards.stream().noneMatch(b -> b.position().equals(v))) {
                            emptySpaces.add(v);
                        }
                    }
                }

                cache.put(current.val2(), emptySpaces);
            }

            Set<Vector2<Integer>> emptySpaces = cache.get(current.val2());
            List<Vector2<Integer>> next = Vector2.neighbours(current.val1(), false);
            next.add(current.val1());
            queue.addAll(next.stream().filter(emptySpaces::contains).map(v -> Tuple2.valueOf(v, current.val2() + 1)).collect(Collectors.toSet()));
        }

        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {

        Grid<Character> grid = CollectionUtil.toGrid(input, s -> s.charAt(0));
        Set<Blizzard> blizzards = grid.entrySet().stream()
                .filter(e -> e.getValue() != '#' && e.getValue() != '.')
                .map(e -> new Blizzard(e.getKey(), mapToDirection(e.getValue())))
                .collect(Collectors.toSet());

        Vector2<Integer> start = Vector2.valueOf(0, 1);
        Vector2<Integer> end   = Vector2.valueOf(grid.width() - 2, grid.height() - 1);

        Map<Integer, Set<Vector2<Integer>>> cache = new HashMap<>();

        Queue<Tuple2<Vector2<Integer>, Integer>> queue = new LinkedList<>();
        Set<Tuple2<Vector2<Integer>, Integer>> seen = new HashSet<>();
        queue.add(Tuple2.valueOf(start, 0));

        int trip = 0;

        while (!queue.isEmpty()) {
            Tuple2<Vector2<Integer>, Integer> current = queue.poll();

            if (seen.contains(current)) continue;
            seen.add(current);

            if (trip == 0 && Objects.equals(current.val1(), end)) {
                logger.info("1: " + current.val2());
                queue.clear();
                trip = 1;
            }

            if (trip == 1 && Objects.equals(current.val1(), start)) {
                logger.info("2: " + current.val2());
                queue.clear();
                trip = 2;
            }

            if (trip == 2 && Objects.equals(current.val1(), end)) {
                logger.info("3: " + current.val2());
                break;
            }

            if (!cache.containsKey(current.val2())) {
                blizzards = blizzards.stream().map(b -> b.next(grid.width(), grid.height())).collect(Collectors.toSet());
//                logger.info("" + blizzards);

                Set<Vector2<Integer>> emptySpaces = new HashSet<>();
                emptySpaces.add(start);
                emptySpaces.add(end);
                for (int y = 1; y < grid.height() - 1; y++) {
                    for (int x = 1; x < grid.width() - 1; x++) {
                        Vector2<Integer> v = Vector2.valueOf(x, y);
                        if (blizzards.stream().noneMatch(b -> b.position().equals(v))) {
                            emptySpaces.add(v);
                        }
                    }
                }

                cache.put(current.val2(), emptySpaces);
            }

            Set<Vector2<Integer>> emptySpaces = cache.get(current.val2());
            List<Vector2<Integer>> next = Vector2.neighbours(current.val1(), false);
            next.add(current.val1());
            queue.addAll(next.stream().filter(emptySpaces::contains).map(v -> Tuple2.valueOf(v, current.val2() + 1)).collect(Collectors.toSet()));
        }

        return 0;
    }

    private Vector2<Integer> mapToDirection(char value) {
        return switch (value) {
            case '>' -> Vector2.RIGHT;
            case '<' -> Vector2.LEFT;
            case '^' -> Vector2.UP;
            case 'v' -> Vector2.DOWN;
            default -> throw new IllegalArgumentException("" + value);
        };
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
//        input = "#.######\n" +
//                "#>>.<^<#\n" +
//                "#.<..<<#\n" +
//                "#>v.><>#\n" +
//                "#<^v^^>#\n" +
//                "######.#";
        logger.info("Input:\n" + input);

//        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
