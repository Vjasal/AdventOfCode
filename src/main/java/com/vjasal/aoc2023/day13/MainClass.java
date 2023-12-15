package com.vjasal.aoc2023.day13;

import com.vjasal.type.Grid;
import com.vjasal.type.vector.Vector2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.Objects;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2023, 13);
    }

    @Override
    public long solvePuzzle1(String input) {
        long result = 0;
        for (String map : CollectionUtil.toListOfSections(input)) {
            Grid<String> grid = CollectionUtil.toGrid(map, s -> Objects.equals(s, "#") ? s : null);
            for (int i = 1; i < grid.width(); i++) {
                int col = i;
                if (grid.entrySet().stream().allMatch(e -> {
                    int x = 2 * col - e.getKey().val1() - 1;
                    return x < 0 || x >= grid.width() || grid.containsKey(x, e.getKey().val2());
                })) {
                    result += col;
                    break;
                }
            }
            for (int i = 1; i < grid.height(); i++) {
                int row = i;
                if (grid.entrySet().stream().allMatch(e -> {
                    int y = 2 * row - e.getKey().val2() - 1;
                    return y < 0 || y >= grid.height() || grid.containsKey(e.getKey().val1(), y);
                })) {
                    result += 100L * row;
                    break;
                }
            }
        }
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        long result = 0;
        for (String map : CollectionUtil.toListOfSections(input)) {
            Grid<String> grid = CollectionUtil.toGrid(map, s -> Objects.equals(s, "#") ? s : null);
            for (int i = 1; i < grid.width(); i++) {
                int col = i;
                if (grid.entrySet().stream().filter(e -> {
                    int x = 2 * col - e.getKey().val1() - 1;
                    return x >= 0 && x < grid.width() && !grid.containsKey(x, e.getKey().val2());
                }).count() == 1) {
                    result += col;
                    break;
                }
            }
            for (int i = 1; i < grid.height(); i++) {
                int row = i;
                if (grid.entrySet().stream().filter(e -> {
                    int y = 2 * row - e.getKey().val2() - 1;
                    return y >= 0 && y < grid.height() && !grid.containsKey(e.getKey().val1(), y);
                }).count() == 1) {
                    result += 100L * row;
                    break;
                }
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
