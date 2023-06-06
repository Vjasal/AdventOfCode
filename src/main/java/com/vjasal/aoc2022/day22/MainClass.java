package com.vjasal.aoc2022.day22;

import com.vjasal.type.Grid;
import com.vjasal.type.vector.Vector2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;
import com.vjasal.util.MathUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 22);
    }

    @Override
    public long solvePuzzle1(String input) {
        String[] in = input.split("\n\n");
        Grid<Character> tiles = parseTiles(in[0]);
        List<String> commands = parseCommands(in[1]);

        List<Vector2<Integer>> directions = new ArrayList<>() {{
            add(Vector2.RIGHT);
            add(Vector2.UP);
            add(Vector2.LEFT);
            add(Vector2.DOWN);
        }};

        Vector2<Integer> position = tiles.keySet().stream().filter(v -> v.val2() == 1).min(Comparator.comparingInt(Vector2::val1)).orElseThrow();
        int d = 0;

        for (String command : commands) {
            if (command.equals("R")) {
                d = (int) MathUtil.mod(d + 1, 4);
                continue;
            }
            if (command.equals("L")) {
                d = (int) MathUtil.mod(d - 1, 4);
                continue;
            }
            int distance = Integer.parseInt(command);
            while (distance > 0) {
                Vector2<Integer> next = Vector2.add(position, directions.get(d));
                if (!tiles.containsKey(next)) {
                    int x = position.val1();
                    int y = position.val2();
                    next = switch (d) {
                        case 0 -> tiles.keySet().stream().filter(v -> v.val2() == y).min(Comparator.comparingInt(Vector2::val1)).orElseThrow();
                        case 2 -> tiles.keySet().stream().filter(v -> v.val2() == y).max(Comparator.comparingInt(Vector2::val1)).orElseThrow();
                        case 1 -> tiles.keySet().stream().filter(v -> v.val1() == x).min(Comparator.comparingInt(Vector2::val2)).orElseThrow();
                        case 3 -> tiles.keySet().stream().filter(v -> v.val1() == x).max(Comparator.comparingInt(Vector2::val2)).orElseThrow();
                        default -> throw new IllegalStateException();
                    };
                }
                if (tiles.get(next) == '#') break;
                position = next;
                distance -= 1;
            }
        }

        int result = position.val2() * 1000 + position.val1() * 4 + d;
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        String[] in = input.split("\n\n");
        int size = 50;

        List<Grid<Character>> cube = parseCube(in[0], size);

        int[][][] connections = {
                {{1, 0}, {2, 0}, {3, 2}, {5, 1}},
                {{4, 2}, {2, 1}, {0, 0}, {5, 0}},
                {{1, 3}, {4, 0}, {3, 3}, {0, 0}},
                {{4, 0}, {5, 0}, {0, 2}, {2, 1}},
                {{1, 2}, {5, 1}, {3, 0}, {2, 0}},
                {{4, 3}, {1, 0}, {0, 3}, {3, 0}}
        };

        int[][] offset = {
                {50, 0},
                {100, 0},
                {50, 50},
                {0, 100},
                {50, 100},
                {0, 150}
        };

        List<String> commands = parseCommands(in[1]);

        List<Vector2<Integer>> directions = new ArrayList<>() {{
            add(Vector2.RIGHT);
            add(Vector2.UP);
            add(Vector2.LEFT);
            add(Vector2.DOWN);
        }};

        Vector2<Integer> position = cube.get(0).keySet().stream().filter(v -> v.val2() == 1).min(Comparator.comparingInt(Vector2::val1)).orElseThrow();
        int side = 0;
        int direction = 0;

        for (String command : commands) {
            if (command.equals("R")) {
                direction = (int) MathUtil.mod(direction + 1, 4);
                continue;
            }
            if (command.equals("L")) {
                direction = (int) MathUtil.mod(direction - 1, 4);
                continue;
            }
            int distance = Integer.parseInt(command);
            while (distance > 0) {
                Vector2<Integer> next = Vector2.add(position, directions.get(direction));
                int nextDirection = direction;
                int nextSide = side;
                if (!cube.get(side).containsKey(next)) {
                    int[] connection = connections[side][direction];

                    // wrap coordinates
                    int x = (int) MathUtil.mod(next.val1() - 1, size) + 1;
                    int y = (int) MathUtil.mod(next.val2() - 1, size) + 1;

                    // rotate coordinates
                    next = switch (connection[1]) {
                        case 0 -> Vector2.valueOf(x, y);
                        case 1 -> Vector2.valueOf(size - y + 1, x);
                        case 2 -> Vector2.valueOf(size - x + 1, size - y + 1);
                        case 3 -> Vector2.valueOf(y, size - x + 1);
                        default -> throw new IllegalStateException();
                    };

                    nextSide = connection[0];
                    nextDirection = (direction + connection[1]) % 4;
                }
                if (cube.get(nextSide).get(next) == '#') break;
                position = next;
                side = nextSide;
                direction = nextDirection;
                distance -= 1;
            }
        }

        int result = (offset[side][1] + position.val2()) * 1000 + (offset[side][0] + position.val1()) * 4 + direction;
        logger.info("Result: " + result);
        return result;
    }

    private Grid<Character> parseTiles(String input) {
        Grid<Character> tiles = new Grid<>();
        int y = 1;
        for (String line : input.split("\n")) {
            int x = 1;
            for (char c : CollectionUtil.toCharList(line)) {
                if (c != ' ') tiles.put(x, y, c);
                x += 1;
            }
            y += 1;
        }
        return tiles;
    }

    private List<Grid<Character>> parseCube(String input, int size) {
        List<Grid<Character>> cube = new ArrayList<>();

        int y = 1;
        int l = 0;
        for (String line : input.split("\n")) {
            int x = 1;
            int k = 0;
            for (char c : CollectionUtil.toCharList(line)) {
                if (c != ' ') {
                    if (cube.size() < k / size + l + 1) cube.add(new Grid<>());
                    cube.get(k / size + l).put(x, y, c);
                    k += 1;
                    x = x % 50 + 1;
                }
            }
            if (y % size == 0) {
                l = cube.size();
                y = 0;
            }
            y += 1;
        }
        return cube;
    }

    private List<String> parseCommands(String input) {
        List<String> commands = new ArrayList<>();
        boolean seenNumber = false;
        for (char command : input.trim().toCharArray()) {
            switch (command) {
                case 'R' -> {
                    commands.add("R");
                    seenNumber = false;
                }
                case 'L' -> {
                    commands.add("L");
                    seenNumber = false;
                }
                default -> {
                    if (!seenNumber) commands.add("");
                    commands.set(commands.size() - 1, commands.get(commands.size() - 1) + command);
                    seenNumber = true;
                }
            }
        }
        return commands;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
