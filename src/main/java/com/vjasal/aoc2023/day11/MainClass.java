package com.vjasal.aoc2023.day11;

import com.vjasal.type.Grid;
import com.vjasal.type.vector.Vector2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2023, 11);
    }

    @Override
    public long solvePuzzle1(String input) {
        Grid<Character> grid = CollectionUtil.toGrid(input, s -> s.charAt(0));
        List<Vector2<Integer>> galaxies = grid.entrySet().stream()
                .filter(e -> e.getValue() == '#')
                .map(Map.Entry::getKey)
                .toList();

        long[] emptyColumnsForRow = new long[grid.width()];
        long[] emptyRowForColumn = new long[grid.height()];

        for (int tx = 1; tx < grid.width(); tx++) {
            int x = tx;
            boolean shouldAdd = galaxies.stream().noneMatch(v -> v.val1() == x - 1);
            emptyColumnsForRow[x] = emptyColumnsForRow[x - 1] + (shouldAdd ? 1 : 0);
        }

        for (int ty = 1; ty < grid.width(); ty++) {
            int y = ty;
            boolean shouldAdd = galaxies.stream().noneMatch(v -> v.val2() == y - 1);
            emptyRowForColumn[y] = emptyRowForColumn[y - 1] + (shouldAdd ? 1 : 0);
        }

        long result = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                result += Math.abs(galaxies.get(i).val1() - galaxies.get(j).val1());
                result += Math.abs(emptyColumnsForRow[galaxies.get(i).val1()] - emptyColumnsForRow[galaxies.get(j).val1()]);
                result += Math.abs(galaxies.get(i).val2() - galaxies.get(j).val2());
                result += Math.abs(emptyRowForColumn[galaxies.get(i).val2()] - emptyRowForColumn[galaxies.get(j).val2()]);
            }
        }

        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Grid<Character> grid = CollectionUtil.toGrid(input, s -> s.charAt(0));
        List<Vector2<Integer>> galaxies = grid.entrySet().stream()
                .filter(e -> e.getValue() == '#')
                .map(Map.Entry::getKey)
                .toList();

        long[] emptyColumnsForRow = new long[grid.width()];
        long[] emptyRowForColumn = new long[grid.height()];

        for (int tx = 1; tx < grid.width(); tx++) {
            int x = tx;
            boolean shouldAdd = galaxies.stream().noneMatch(v -> v.val1() == x - 1);
            emptyColumnsForRow[x] = emptyColumnsForRow[x - 1] + (shouldAdd ? 1 : 0);
        }

        for (int ty = 1; ty < grid.width(); ty++) {
            int y = ty;
            boolean shouldAdd = galaxies.stream().noneMatch(v -> v.val2() == y - 1);
            emptyRowForColumn[y] = emptyRowForColumn[y - 1] + (shouldAdd ? 1 : 0);
        }

        long result = 0;
        long mul = 1000000 - 1;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                result += Math.abs(galaxies.get(i).val1() - galaxies.get(j).val1());
                result += mul * Math.abs(emptyColumnsForRow[galaxies.get(i).val1()] - emptyColumnsForRow[galaxies.get(j).val1()]);
                result += Math.abs(galaxies.get(i).val2() - galaxies.get(j).val2());
                result += mul * Math.abs(emptyRowForColumn[galaxies.get(i).val2()] - emptyRowForColumn[galaxies.get(j).val2()]);
            }
        }

        logger.info("Result: " + result);
        return 0;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
