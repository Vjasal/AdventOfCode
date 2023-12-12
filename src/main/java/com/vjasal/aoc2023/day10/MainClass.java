package com.vjasal.aoc2023.day10;

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
        super(2023, 10);
    }

    @Override
    public long solvePuzzle1(String input) {
        Grid<Pipe> pipeGrid = CollectionUtil.toGrid(input, Pipe::new);


        Set<Vector2<Integer>> seen = new HashSet<>();
        Queue<Tuple2<Vector2<Integer>, Integer>> queue = new LinkedList<>();

        Vector2<Integer> start = pipeGrid.entrySet().stream()
                .filter(e -> e.getValue().getLabel().equals("S"))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(Vector2.ZERO);
        queue.add(Tuple2.valueOf(start, 0));

        int maxDistance = 0;
        while (!queue.isEmpty()) {
            Tuple2<Vector2<Integer>, Integer> current = queue.poll();

            if (seen.contains(current.val1())) continue;
            seen.add(current.val1());

            maxDistance = Math.max(current.val2(), maxDistance);

            for (Vector2<Integer> direction : pipeGrid.get(current.val1()).getConnections()) {
                Vector2<Integer> next = Vector2.add(current.val1(), direction);

                if (current.val1() == start) {
                    Vector2<Integer>[] nextDirections = pipeGrid.get(next).getConnections();
                    if (Arrays.stream(nextDirections).map(v -> Vector2.add(v, direction)).anyMatch(v -> v.equals(Vector2.ZERO))) {
                        queue.add(Tuple2.valueOf(next, current.val2() + 1));
                    }
                    continue;
                }

                queue.add(Tuple2.valueOf(next, current.val2() + 1));
            }
        }


        logger.info("" + maxDistance);

        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {
        Grid<Pipe> pipeGrid = CollectionUtil.toGrid(input, Pipe::new);

        Set<Vector2<Integer>> toLeft = new HashSet<>();
        Set<Vector2<Integer>> toRight = new HashSet<>();



        Vector2<Integer> start = pipeGrid.entrySet().stream()
                .filter(e -> e.getValue().getLabel().equals("S"))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(Vector2.ZERO);

        Set<Vector2<Integer>> loop = new HashSet<>();
        loop.add(start);

        // FIXME: change based on input
        Vector2<Integer> x = Vector2.UP;

        Tuple2<Vector2<Integer>, Vector2<Integer>> current = Tuple2.valueOf(Vector2.add(start, x), x);
        while (!current.val1().equals(start)) {
            Vector2<Integer> position = current.val1();
            loop.add(position);

            pipeGrid.get(current.val1()).toLeft(current.val2()).stream()
                    .map(v -> Vector2.add(position, v))
                    .forEach(toLeft::add);
            pipeGrid.get(current.val1()).toRight(current.val2()).stream()
                    .map(v -> Vector2.add(position, v))
                    .forEach(toRight::add);

            Vector2<Integer> nextDirection = pipeGrid.get(position).nextDirection(current.val2());
            current = Tuple2.valueOf(Vector2.add(position, nextDirection), nextDirection);
        }

        toLeft.removeAll(loop);
        toRight.removeAll(loop);

        logger.info(toLeft.toString());
        logger.info(toRight.toString());

        logger.info("Result: " + toLeft.size() + " or " + toRight.size());

        Queue<Vector2<Integer>> queue1 = new LinkedList<>(toLeft);
        Set<Vector2<Integer>> seen1 = new HashSet<>();
        while (!queue1.isEmpty()) {
            Vector2<Integer> current1 = queue1.poll();
            if (seen1.contains(current1)) continue;
            if (loop.contains(current1)) continue;
            if (!pipeGrid.containsKey(current1)) continue;
            seen1.add(current1);
            queue1.addAll(Vector2.neighbours(current1, false));
        }

        Queue<Vector2<Integer>> queue2 = new LinkedList<>(toRight);
        Set<Vector2<Integer>> seen2 = new HashSet<>();
        while (!queue2.isEmpty()) {
            Vector2<Integer> current2 = queue2.poll();
            if (seen2.contains(current2)) continue;
            if (loop.contains(current2)) continue;
            seen2.add(current2);
            queue2.addAll(Vector2.neighbours(current2, false));
        }

        logger.info("Result: " + seen1.size() + " or " + seen2.size());

        return 0;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
//        logger.info("Input:\n" + input);

        String test = """
                ..........
                .S------7.
                .|F----7|.
                .||OOOO||.
                .||OOOO||.
                .|L-7F-J|.
                .|II||II|.
                .L--JL--J.
                ..........""";
        logger.info("Input:\n" + test);

//        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
