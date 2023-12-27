package com.vjasal.aoc2023.day17;

import com.vjasal.type.Grid;
import com.vjasal.type.tuple.Tuple3;
import com.vjasal.type.tuple.Tuple4;
import com.vjasal.type.vector.Vector2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2023, 17);
    }

    @Override
    public long solvePuzzle1(String input) {
        Grid<Integer> map = CollectionUtil.toGrid(input, Integer::parseInt);

        Queue<Tuple4<Vector2<Integer>, Vector2.Direction, Integer, Integer>> queue =
                new PriorityQueue<>(Comparator.comparing(Tuple4::val4));
        Set<Tuple3<Vector2<Integer>, Vector2.Direction, Integer>> seen = new HashSet<>();

        queue.add(Tuple4.valueOf(Vector2.ZERO, Vector2.Direction.RIGHT, -1, 0));
        int result = -1;

        while (!queue.isEmpty()) {
            Tuple4<Vector2<Integer>, Vector2.Direction, Integer, Integer> current = queue.poll();
            Vector2<Integer> position = current.val1();
            Vector2.Direction direction = current.val2();
            int distance = current.val3() + 1;
            int heatLoss = current.val4() + (distance > 0 ? map.get(position, 0) : 0);

            if (!map.containsKey(position))
                continue;
            if (distance > 3)
                continue;

            Tuple3<Vector2<Integer>, Vector2.Direction, Integer> state = Tuple3.valueOf(position, direction, distance);
            if (seen.contains(state))
                continue;
            seen.add(state);

            if (position.equals(Vector2.valueOf(map.width() - 1, map.height() - 1))) {
                result = heatLoss;
                break;
            }

            queue.add(Tuple4.valueOf(Vector2.add(position, direction), direction, distance, heatLoss));
            queue.add(Tuple4.valueOf(Vector2.add(position, direction.toLeft()), direction.toLeft(), 0, heatLoss));
            queue.add(Tuple4.valueOf(Vector2.add(position, direction.toRight()), direction.toRight(), 0, heatLoss));
        }

        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Grid<Integer> map = CollectionUtil.toGrid(input, Integer::parseInt);

        Queue<Tuple4<Vector2<Integer>, Vector2.Direction, Integer, Integer>> queue =
                new PriorityQueue<>(Comparator.comparing(Tuple4::val4));
        Set<Tuple3<Vector2<Integer>, Vector2.Direction, Integer>> seen = new HashSet<>();

        queue.add(Tuple4.valueOf(Vector2.ZERO, Vector2.Direction.RIGHT, -1, 0));
        int result = -1;

        while (!queue.isEmpty()) {
            Tuple4<Vector2<Integer>, Vector2.Direction, Integer, Integer> current = queue.poll();
            Vector2<Integer> position = current.val1();
            Vector2.Direction direction = current.val2();
            int distance = current.val3() + 1;
            int heatLoss = current.val4() + (distance > 0 ? map.get(position, 0) : 0);

            if (!map.containsKey(position))
                continue;
            if (distance > 10)
                continue;

            Tuple3<Vector2<Integer>, Vector2.Direction, Integer> state = Tuple3.valueOf(position, direction, distance);
            if (seen.contains(state))
                continue;
            seen.add(state);

            if (position.equals(Vector2.valueOf(map.width() - 1, map.height() - 1)) && distance >= 4) {
                result = heatLoss;
                break;
            }

            queue.add(Tuple4.valueOf(Vector2.add(position, direction), direction, distance, heatLoss));
            if (distance >= 4) {
                queue.add(Tuple4.valueOf(Vector2.add(position, direction.toLeft()), direction.toLeft(), 0, heatLoss));
                queue.add(Tuple4.valueOf(Vector2.add(position, direction.toRight()), direction.toRight(), 0, heatLoss));
            }
        }

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
