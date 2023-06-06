package com.vjasal.aoc2022.day08;

import com.vjasal.type.Grid;
import com.vjasal.type.vector.Vector2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 8);
    }

    @Override
    public long solvePuzzle1(String input) {
        Grid<Integer> grid = CollectionUtil.toGrid(input, Integer::parseInt);
        int visibleTrees = 0;

        for (Map.Entry<Vector2<Integer>, Integer> tree : grid.entrySet()) {
            int x = tree.getKey().val1();
            int y = tree.getKey().val2();

            boolean visibleFromTop = IntStream.rangeClosed(1, y)
                    .allMatch(dy -> tree.getValue() > grid.get(x, y - dy));

            boolean visibleFromBottom = IntStream.rangeClosed(1, grid.height() - y - 1)
                    .allMatch(dy -> tree.getValue() > grid.get(x, y + dy));

            boolean visibleFromLeft = IntStream.rangeClosed(1, x)
                    .allMatch(dx -> tree.getValue() > grid.get(x - dx, y));

            boolean visibleFromRight = IntStream.rangeClosed(1, grid.width() - x - 1)
                    .allMatch(dx -> tree.getValue() > grid.get(x + dx, y));

            visibleTrees += visibleFromTop || visibleFromBottom || visibleFromLeft || visibleFromRight ? 1 : 0;
        }

        logger.info("Result: " + visibleTrees);
        return visibleTrees;
    }

    @Override
    public long solvePuzzle2(String input) {
        Grid<Integer> grid = CollectionUtil.toGrid(input, Integer::parseInt);
        int bestScore = 0;

        for (int tx = 1; tx < grid.width() - 1; tx++) {
            for (int ty = 1; ty < grid.height() - 1; ty++) {
                int x = tx, y = ty;
                int score = 1;

                // look down
                score *= IntStream.rangeClosed(1, grid.height() - y - 1)
                        .filter(dy -> grid.get(x, y) <= grid.get(x, y + dy))
                        .findFirst()
                        .orElse(grid.height() - y - 1);

                // look up
                score *= IntStream.rangeClosed(1, y)
                        .filter(dy -> grid.get(x, y) <= grid.get(x, y - dy))
                        .findFirst()
                        .orElse(y);

                // look right
                score *= IntStream.rangeClosed(1, grid.width() - x - 1)
                        .filter(dx -> grid.get(x, y) <= grid.get(x + dx, y))
                        .findFirst()
                        .orElse(grid.width() - x - 1);

                // look left
                score *= IntStream.rangeClosed(1, x)
                        .filter(dx -> grid.get(x, y) <= grid.get(x - dx, y))
                        .findFirst()
                        .orElse(x);

                bestScore = Math.max(bestScore, score);
            }
        }

        logger.info("Result: " + bestScore);
        return bestScore;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();

        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
