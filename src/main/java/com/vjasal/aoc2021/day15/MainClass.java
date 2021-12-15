package com.vjasal.aoc2021.day15;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.vectors.Vector2;
import com.vjasal.util.vectors.VectorUtil;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 15);
    }

    @Override
    public long solvePuzzle1(String input) {
        long result = findRiskFactor(input, 1);
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        long result = findRiskFactor(input, 5);
        logger.info("Result: " + result);
        return result;
    }

    private long findRiskFactor(String input, int inputFactor) {
        CaveMap map = new CaveMap(input, inputFactor);
        Queue<Path> queue = new PriorityQueue<>();
        Set<Path> seen = new HashSet<>();

        queue.add(new Path(new Vector2<>(0, 0), 0));

        while (!queue.isEmpty()) {
            Path path = queue.poll();

            if (seen.contains(path)) continue;
            seen.add(path);

            if (path.getPosition().equals(map.getEndPosition())) {
                return path.getRiskLevel();
            }

            for (Vector2<Integer, Integer> neighbour : VectorUtil.neighbours(path.getPosition(), false)) {
                if (map.contains(neighbour)) {
                    queue.add(new Path(neighbour, path.getRiskLevel() + map.get(neighbour)));
                }
            }
        }

        throw new IllegalStateException("Error - could not find risk factor");
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
