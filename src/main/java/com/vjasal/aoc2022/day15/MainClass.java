package com.vjasal.aoc2022.day15;

import com.vjasal.type.Range;
import com.vjasal.type.vector.Vector2;
import com.vjasal.type.vector.Vector4;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 15);
    }

    private enum State {
        SCANNER, BEACON, EMPTY, UNKNOWN
    }

    @Override
    public long solvePuzzle1(String input) {
        Pattern pattern = Pattern.compile("^Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)$");

        int targetY = 2000000;

        List<Range<Integer>> ranges = new LinkedList<>();
        Set<Vector2<Integer>> notEmpty = new HashSet<>();

        for (String line : CollectionUtil.toLinkedList(input)) {
            int empty = 0;

            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) throw new IllegalArgumentException();

            int sx = Integer.parseInt(matcher.group(1));
            int sy = Integer.parseInt(matcher.group(2));
            int bx = Integer.parseInt(matcher.group(3));
            int by = Integer.parseInt(matcher.group(4));

            int dx = Math.abs(sx - bx) + Math.abs(sy - by) - Math.abs(sy - targetY);
            if (dx >= 0) {
                ranges.add(new Range<>(sx - dx, sx + dx));
                logger.info(sx + " " + sy + " -> " + new Range<>(sx - dx, sx + dx));
            } else {
                logger.info(sx + " " + sy + " -> null");
            }
            if (sy == targetY) notEmpty.add(Vector2.valueOf(sx, sy));
            if (by == targetY) notEmpty.add(Vector2.valueOf(bx, by));
        }

        int min = ranges.stream().mapToInt(Range::minimum).min().orElse(0);
        int max = ranges.stream().mapToInt(Range::maximum).max().orElse(0);

        logger.info(min + " " + max);

        long result = IntStream.range(min, max + 1).filter(i -> {
            return ranges.stream().anyMatch(r -> r.contains(i));
        }).count();



        // 4687825 - too low
        logger.info("" + result + " " + notEmpty.size());

        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {
        Pattern pattern = Pattern.compile("^Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)$");

        List<Vector4<Integer>> positions = new LinkedList<>();

        for (String line : CollectionUtil.toLinkedList(input)) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) throw new IllegalArgumentException();

            int sx = Integer.parseInt(matcher.group(1));
            int sy = Integer.parseInt(matcher.group(2));
            int bx = Integer.parseInt(matcher.group(3));
            int by = Integer.parseInt(matcher.group(4));

            positions.add(new Vector4<>(sx, sy, bx, by));
        }


        for (int targetY = 0; targetY < 4000000; targetY++) {
            List<Range<Integer>> ranges = new LinkedList<>();

            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (Vector4<Integer> position : positions) {
                int sx = position.val1();
                int sy = position.val2();
                int bx = position.val3();
                int by = position.val4();

                int dx = Math.abs(sx - bx) + Math.abs(sy - by) - Math.abs(sy - targetY);
                if (dx < 0) continue;

                ranges.add(new Range<>(sx - dx, sx + dx));

                min = Math.min(min, sx - dx);
                max = Math.max(max, sx + dx);
            }
            ranges.sort(Comparator.comparingInt(Range::minimum));

            for (Range<Integer> r1 : ranges) {
                for (Range<Integer> r2 : ranges) {
                    if (r1 == r2) continue;
                    if (r1.overlaps(r2)) continue;
                    if (r1.maximum() - r2.minimum() == 2) {
                        // y=3017867 x=3068581
                        logger.info("Found! y=" + targetY + " x=" + (r1.maximum() + 1) );
                    }
                }
            }

            if (targetY % 1000 == 0) {
                logger.info("" + targetY);
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();

//        input = "Sensor at x=2, y=18: closest beacon is at x=-2, y=15\n" +
//                "Sensor at x=9, y=16: closest beacon is at x=10, y=16\n" +
//                "Sensor at x=13, y=2: closest beacon is at x=15, y=3\n" +
//                "Sensor at x=12, y=14: closest beacon is at x=10, y=16\n" +
//                "Sensor at x=10, y=20: closest beacon is at x=10, y=16\n" +
//                "Sensor at x=14, y=17: closest beacon is at x=10, y=16\n" +
//                "Sensor at x=8, y=7: closest beacon is at x=2, y=10\n" +
//                "Sensor at x=2, y=0: closest beacon is at x=2, y=10\n" +
//                "Sensor at x=0, y=11: closest beacon is at x=2, y=10\n" +
//                "Sensor at x=20, y=14: closest beacon is at x=25, y=17\n" +
//                "Sensor at x=17, y=20: closest beacon is at x=21, y=22\n" +
//                "Sensor at x=16, y=7: closest beacon is at x=15, y=3\n" +
//                "Sensor at x=14, y=3: closest beacon is at x=15, y=3\n" +
//                "Sensor at x=20, y=1: closest beacon is at x=15, y=3";

        logger.info("Input:\n" + input);

//        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
