package com.vjasal.aoc2023.day18;

import com.vjasal.type.Grid;
import com.vjasal.type.vector.Vector2;
import com.vjasal.type.vector.Vector4;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;
import com.vjasal.util.MathUtil;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());
    private static final Pattern pattern = Pattern.compile("^(\\w) (\\d+) \\(#(\\w{5})(\\w)\\)$");

    public MainClass() {
        super(2023, 18);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<Vector2<Integer>> points = new ArrayList<>();
        Vector2<Integer> current = Vector2.ZERO;
        points.add(current);
        long b = 0;

        for (String line : CollectionUtil.toLinkedList(input)) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find())
                throw new IllegalArgumentException();

            Vector2.Direction direction = switch (matcher.group(1)) {
                case "U" -> Vector2.Direction.UP;
                case "D" -> Vector2.Direction.DOWN;
                case "L" -> Vector2.Direction.LEFT;
                case "R" -> Vector2.Direction.RIGHT;
                default -> throw new IllegalArgumentException();
            };
            int distance = Integer.parseInt(matcher.group(2));
            current = Vector2.add(current, direction, distance);
            points.add(current);
            b += distance;
        }

        long sum = 0;
        for (int i = 0; i < points.size(); i++) {
            int mi = (int) MathUtil.mod(i - 1, points.size());
            int pi = (int) MathUtil.mod(i + 1, points.size());
            sum += (long) points.get(i).val1() * (points.get(mi).val2() - points.get(pi).val2());
        }

        long result = (Math.abs(sum) + b) / 2 + 1;
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<Vector2<Integer>> points = new ArrayList<>();
        Vector2<Integer> current = Vector2.ZERO;
        points.add(current);
        long b = 0;

        for (String line : CollectionUtil.toLinkedList(input)) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find())
                throw new IllegalArgumentException();

            Vector2.Direction direction = switch (matcher.group(4)) {
                case "0" -> Vector2.Direction.RIGHT;
                case "1" -> Vector2.Direction.DOWN;
                case "2" -> Vector2.Direction.LEFT;
                case "3" -> Vector2.Direction.UP;
                default -> throw new IllegalArgumentException();
            };
            int distance = Integer.parseInt(matcher.group(3), 16);
            current = Vector2.add(current, direction, distance);
            points.add(current);
            b += distance;
        }

        long sum = 0;
        for (int i = 0; i < points.size(); i++) {
            int mi = (int) MathUtil.mod(i - 1, points.size());
            int pi = (int) MathUtil.mod(i + 1, points.size());
            sum += (long) points.get(i).val1() * (points.get(mi).val2() - points.get(pi).val2());
        }

        long result = (Math.abs(sum) + b) / 2 + 1;
        logger.info("Result: " + result);
        return result;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        String test = """
                R 6 (#70c710)
                D 5 (#0dc571)
                L 2 (#5713f0)
                D 2 (#d2c081)
                R 2 (#59c680)
                D 2 (#411b91)
                L 5 (#8ceee2)
                U 2 (#caa173)
                L 1 (#1b58a2)
                U 2 (#caa171)
                R 2 (#7807d2)
                U 3 (#a77fa3)
                L 2 (#015232)
                U 2 (#7a21e3)""";

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
