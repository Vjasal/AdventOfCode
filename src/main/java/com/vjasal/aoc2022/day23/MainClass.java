package com.vjasal.aoc2022.day23;

import com.vjasal.type.Grid2;
import com.vjasal.type.vector.Vector2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 23);
    }

    @Override
    public long solvePuzzle1(String input) {
        Grid2 grid2 = new Grid2();

        Set<Vector2<Integer>> north = new HashSet<>() {{
            add(Vector2.valueOf(-1, -1));
            add(Vector2.valueOf( 0, -1));
            add(Vector2.valueOf( 1, -1));
        }};
        Set<Vector2<Integer>> south = new HashSet<>() {{
            add(Vector2.valueOf(-1,  1));
            add(Vector2.valueOf( 0,  1));
            add(Vector2.valueOf( 1,  1));
        }};
        Set<Vector2<Integer>> west = new HashSet<>() {{
            add(Vector2.valueOf(-1, -1));
            add(Vector2.valueOf(-1,  0));
            add(Vector2.valueOf(-1,  1));
        }};
        Set<Vector2<Integer>> east = new HashSet<>() {{
            add(Vector2.valueOf( 1, -1));
            add(Vector2.valueOf( 1,  0));
            add(Vector2.valueOf( 1,  1));
        }};
        List<Set<Vector2<Integer>>> directions = new ArrayList<>(Arrays.asList(north, south, west, east));
        int startDirection = 0;

        int y = 0;
        for (String line : CollectionUtil.toLinkedList(input)) {
            int x = 0;
            for (char c : CollectionUtil.toCharList(line)) {
                if (c == '#') grid2.add(x, y);
                x += 1;
            }
            y += 1;
        }

        logger.info("" + grid2);

        while(true) {
            Map<Vector2<Integer>, Vector2<Integer>> proposedMoves = new HashMap<>();
            for (Vector2<Integer> elf : grid2.values()) {
                if (Vector2.neighbours(elf, true).stream().noneMatch(grid2::contains)) {
                    continue;
                }

                for (int d = 0; d < 4; d++) {
                    if (directions.get((d + startDirection) % 4).stream().map(v -> Vector2.add(v, elf)).noneMatch(grid2::contains)) {
                        proposedMoves.put(elf, Vector2.add(elf, getDirection((d + startDirection) % 4)));
                        break;
                    }
                }
            }

            boolean didMove = false;
            Grid2 next = new Grid2();
            for (Vector2<Integer> elf : grid2.values()) {
                if (!proposedMoves.containsKey(elf)) {
                    next.add(elf);
                    continue;
                }

                Vector2<Integer> move = proposedMoves.remove(elf);
                Set<Vector2<Integer>> others = proposedMoves.entrySet().stream().filter(e -> e.getValue().equals(move)).map(Map.Entry::getKey).collect(Collectors.toSet());
                if (others.isEmpty()) {
                    next.add(move);
                    didMove = true;
                } else {
                    for (Vector2<Integer> e : others) {
                        proposedMoves.remove(e);
                    }
                    next.add(elf);
                }
            }
            grid2 = next;
            startDirection += 1;

            if (!didMove) break;
        }
        logger.info("" + startDirection);

//        int result = 0;
//        for (int dy = grid2.minY(); dy <= grid2.maxY(); dy++) {
//            for (int dx = grid2.minX(); dx <= grid2.maxX(); dx++) {
//                if (!grid2.contains(dx, dy)) result += 1;
//            }
//        }
//
//        logger.info("" + result);



        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {

        return 0;
    }

    public static Vector2<Integer> getDirection(int direction) {
        return switch (direction) {
            case 0 -> Vector2.DOWN;
            case 1 -> Vector2.UP;
            case 2 -> Vector2.LEFT;
            case 3 -> Vector2.RIGHT;
            default -> throw new IllegalArgumentException();
        };
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();

        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
