package com.vjasal.aoc2023.day16;

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
        super(2023, 16);
    }

    private long countIlluminated(Grid<String> grid, Tuple2<Vector2<Integer>, Vector2.Direction> start) {
        Queue<Tuple2<Vector2<Integer>, Vector2.Direction>> queue = new LinkedList<>();
        queue.add(start);

        Set<Tuple2<Vector2<Integer>, Vector2.Direction>> seen = new HashSet<>();

        while (!queue.isEmpty()) {
            Tuple2<Vector2<Integer>, Vector2.Direction> current = queue.poll();
            Vector2<Integer> position = current.val1();
            Vector2.Direction direction = current.val2();

            if (!grid.containsKey(position))
                continue;

            if (seen.contains(current))
                continue;
            seen.add(current);

            switch (grid.get(position)) {
                case "." -> queue.add(Tuple2.valueOf(Vector2.add(position, direction), direction));
                case "\\" -> {
                    Vector2.Direction next = switch (direction) {
                        case UP -> Vector2.Direction.LEFT;
                        case LEFT -> Vector2.Direction.UP;
                        case DOWN -> Vector2.Direction.RIGHT;
                        case RIGHT -> Vector2.Direction.DOWN;
                    };
                    queue.add(Tuple2.valueOf(Vector2.add(position, next), next));
                }
                case "/" -> {
                    Vector2.Direction next = switch (direction) {
                        case UP -> Vector2.Direction.RIGHT;
                        case RIGHT -> Vector2.Direction.UP;
                        case DOWN -> Vector2.Direction.LEFT;
                        case LEFT -> Vector2.Direction.DOWN;
                    };
                    queue.add(Tuple2.valueOf(Vector2.add(position, next), next));
                }
                case "-" -> {
                    Vector2.Direction d1 = Vector2.Direction.LEFT;
                    Vector2.Direction d2 = Vector2.Direction.RIGHT;

                    if (direction == d1 || direction == d2) {
                        queue.add(Tuple2.valueOf(Vector2.add(position, direction), direction));
                    } else {
                        queue.add(Tuple2.valueOf(Vector2.add(position, d1), d1));
                        queue.add(Tuple2.valueOf(Vector2.add(position, d2), d2));
                    }
                }
                case "|" -> {
                    Vector2.Direction d1 = Vector2.Direction.UP;
                    Vector2.Direction d2 = Vector2.Direction.DOWN;

                    if (direction == d1 || direction == d2) {
                        queue.add(Tuple2.valueOf(Vector2.add(position, direction), direction));
                    } else {
                        queue.add(Tuple2.valueOf(Vector2.add(position, d1), d1));
                        queue.add(Tuple2.valueOf(Vector2.add(position, d2), d2));
                    }
                }
            }
        }

        return seen.stream().map(Tuple2::val1).distinct().count();
    }

    @Override
    public long solvePuzzle1(String input) {
        Grid<String> grid = CollectionUtil.toGrid(input);
        long result = countIlluminated(grid, Tuple2.valueOf(Vector2.ZERO, Vector2.Direction.RIGHT));
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Grid<String> grid = CollectionUtil.toGrid(input);
        List<Tuple2<Vector2<Integer>, Vector2.Direction>> startingPositions = new LinkedList<>();

        for (int x = 0; x < grid.width(); x++) {
            Vector2<Integer> p1 = Vector2.valueOf(x, 0);
            Vector2<Integer> p2 = Vector2.valueOf(x, grid.height() - 1);
            startingPositions.add(Tuple2.valueOf(p1, Vector2.Direction.DOWN));
            startingPositions.add(Tuple2.valueOf(p2, Vector2.Direction.UP));
        }

        for (int y = 0; y < grid.height(); y++) {
            Vector2<Integer> p1 = Vector2.valueOf(0, y);
            Vector2<Integer> p2 = Vector2.valueOf(grid.width() - 1, y);
            startingPositions.add(Tuple2.valueOf(p1, Vector2.Direction.RIGHT));
            startingPositions.add(Tuple2.valueOf(p2, Vector2.Direction.LEFT));
        }

        long result = startingPositions.stream().mapToLong(a -> countIlluminated(grid, a)).max().orElse(0);
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
