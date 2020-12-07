package com.vjasal.aoc2019.day03;

import com.vjasal.util.AocMainClass;

import java.io.StringReader;
import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    private MainClass() {
        super(2019, 3);
    }

    @Override
    public void solvePuzzle1(String input) {
        String[] wires = input.split("\n");
        List<String> path1 = parseInput(wires[0]);
        List<String> path2 = parseInput(wires[1]);

        int result = Integer.MAX_VALUE;
        for (Point point : getIntersections(path1, path2).keySet()) {
            if (result > point.getDistanceToStart()) {
                result = point.getDistanceToStart();
            }
        }

        logger.info("Result: " + result);
    }

    @Override
    public void solvePuzzle2(String input) {
        String[] wires = input.split("\n");
        List<String> path1 = parseInput(wires[0]);
        List<String> path2 = parseInput(wires[1]);

        int result = Integer.MAX_VALUE;
        for (int distance : getIntersections(path1, path2).values()) {
            if (result > distance) {
                result = distance;
            }
        }

        logger.info("Result: " + result);

    }

    private Map<Point, Integer> getIntersections(List<String> path1, List<String> path2) {
        Map<Point, Integer> pointsPath1   = getPoints(path1);
        Map<Point, Integer> pointsPath2   = getPoints(path2);
        Map<Point, Integer> intersections = new HashMap<>();

        for (Map.Entry<Point, Integer> entry : pointsPath1.entrySet()) {
            if (pointsPath2.containsKey(entry.getKey())) {
                intersections.put(entry.getKey(), entry.getValue() + pointsPath2.get(entry.getKey()));
            }
        }

        return intersections;
    }

    private Map<Point, Integer> getPoints(List<String> path) {
        Map<Point, Integer> points = new HashMap<>();
        int x = 0;
        int y = 0;
        int d = 0;

        for (String move : path) {
            int dx = move.charAt(0) != 'L' ? move.charAt(0) != 'R' ? 0 : 1 : -1;
            int dy = move.charAt(0) != 'D' ? move.charAt(0) != 'U' ? 0 : 1 : -1;
            for (int i = 0; i < Integer.parseInt(move.substring(1)); i++) {
                x += dx;
                y += dy;
                d += 1;
                points.put(new Point(x, y), d);
            }
        }
        return points;
    }

    private List<String> parseInput(String input) {
        List<String> path = new LinkedList<>();
        try (Scanner scanner = new Scanner(new StringReader(input))) {
            scanner.useDelimiter("[,\n]");
            while (scanner.hasNext()) {
                path.add(scanner.next());
            }
        }
        return path;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
