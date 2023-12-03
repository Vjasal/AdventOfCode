package com.vjasal.aoc2023.day03;

import com.vjasal.type.Grid;
import com.vjasal.type.tuple.Tuple2;
import com.vjasal.type.vector.Vector2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());
    private static final Pattern pattern = Pattern.compile("\\d+");

    public MainClass() {
        super(2023, 3);
    }

    private int getPartNumber(Grid<String> grid, List<Vector2<Integer>> coordinates, String number) {
        Set<Vector2<Integer>> neighbours = new HashSet<>();
        coordinates.forEach(v -> neighbours.addAll(Vector2.neighbours(v, true)));
        coordinates.forEach(neighbours::remove);

        if (neighbours.stream()
                .filter(grid::containsKey)
                .map(grid::get)
                .anyMatch(s -> !s.equals("."))) {
            return Integer.parseInt(number);
        } else {
            return 0;
        }
    }

    private Optional<Tuple2<Integer, Vector2<Integer>>> getGearPartNumber(Grid<String> grid,
                                                                          List<Vector2<Integer>> coordinates,
                                                                          String number) {
        Set<Vector2<Integer>> neighbours = new HashSet<>();
        coordinates.forEach(v -> neighbours.addAll(Vector2.neighbours(v, true)));
        coordinates.forEach(neighbours::remove);

        Optional<Vector2<Integer>> vector = neighbours.stream()
                .filter(v -> Objects.equals(grid.get(v), "*"))
                .findFirst();

        return vector.map(v -> Tuple2.valueOf(Integer.parseInt(number), v));
    }

    @Override
    public long solvePuzzle1(String input) {
        Grid<String> grid = CollectionUtil.toGrid(input);
        int sum = 0;
        for (int y = 0; y < grid.height(); y++) {
            StringBuilder number = new StringBuilder();
            List<Vector2<Integer>> coordinates = new LinkedList<>();
            for (int x = 0; x < grid.width(); x++) {
                String val = grid.get(x, y);
                if (pattern.matcher(val).matches()) {
                    number.append(val);
                    coordinates.add(Vector2.valueOf(x, y));
                } else if (number.length() > 0) {
                    sum += getPartNumber(grid, coordinates, number.toString());
                    number = new StringBuilder();
                    coordinates.clear();
                }
            }
            if (number.length() > 0) {
                sum += getPartNumber(grid, coordinates, number.toString());
            }
        }
        logger.info("Result: " + sum);
        return sum;
    }

    @Override
    public long solvePuzzle2(String input) {
        Grid<String> grid = CollectionUtil.toGrid(input);
        Map<Vector2<Integer>, List<Integer>> res = new HashMap<>();
        for (int y = 0; y < grid.height(); y++) {
            StringBuilder number = new StringBuilder();
            List<Vector2<Integer>> coordinates = new LinkedList<>();
            for (int x = 0; x < grid.width(); x++) {
                String val = grid.get(x, y);
                if (pattern.matcher(val).matches()) {
                    number.append(val);
                    coordinates.add(Vector2.valueOf(x, y));
                } else if (number.length() > 0) {
                    Optional<Tuple2<Integer, Vector2<Integer>>> vx = getGearPartNumber(grid, coordinates, number.toString());
                    if (vx.isPresent()) {
                        res.putIfAbsent(vx.get().val2(), new LinkedList<>());
                        res.get(vx.get().val2()).add(vx.get().val1());
                    }
                    number = new StringBuilder();
                    coordinates.clear();
                }
            }
            if (number.length() > 0) {
                Optional<Tuple2<Integer, Vector2<Integer>>> vx = getGearPartNumber(grid, coordinates, number.toString());
                if (vx.isPresent()) {
                    res.putIfAbsent(vx.get().val2(), new LinkedList<>());
                    res.get(vx.get().val2()).add(vx.get().val1());
                }
            }
        }

        long result = res.values().stream()
                .filter(ll -> ll.size() == 2)
                .mapToInt(ll -> ll.stream().reduce(1, (a, b) -> a* b))
                .sum();

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
