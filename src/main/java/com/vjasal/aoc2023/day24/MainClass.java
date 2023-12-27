package com.vjasal.aoc2023.day24;

import com.vjasal.type.tuple.Tuple2;
import com.vjasal.type.vector.Vector2;
import com.vjasal.type.vector.Vector4;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());
    private static final Pattern pattern = Pattern.compile("^(\\d+), (\\d+), (\\d+) @ +(-?\\d+), +(-?\\d+), +(-?\\d+)$");

    public MainClass() {
        super(2023, 24);
    }

    @Override
    public long solvePuzzle1(String input) {
        double minV = 200000000000000d;
        double maxV = 400000000000000d;

        List<Vector4<Long>> points = new LinkedList<>();
        for (String line : CollectionUtil.toLinkedList(input)) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find())
                throw new IllegalArgumentException(line);
            long x = Long.parseLong(matcher.group(1));
            long y = Long.parseLong(matcher.group(2));
            long dx = Long.parseLong(matcher.group(4));
            long dy = Long.parseLong(matcher.group(5));
            points.add(Vector4.valueOf(x, y, dx, dy));
        }

        List<Vector2<Double>> lines = points.stream().map(v4 -> {
            double a = (double) v4.val4() / v4.val3();
            double b = v4.val2() - a * v4.val1();
            return Vector2.valueOf(a, b);
        }).toList();

        int count = 0;
        for (int i = 0; i < lines.size(); i++) {
            double a1 = lines.get(i).val1();
            double b1 = lines.get(i).val2();

            for (int j = i + 1; j < lines.size(); j++) {
                double a2 = lines.get(j).val1();
                double b2 = lines.get(j).val2();

                double x = (b2 - b1) / (a1 - a2);
                double y = (a1 * b2 - b1 * a2) / (a1 - a2);

                if (Math.signum(x - points.get(i).val1()) != Math.signum(points.get(i).val3()))
                    continue;
                if (Math.signum(x - points.get(j).val1()) != Math.signum(points.get(j).val3()))
                    continue;
                if (Math.signum(y - points.get(i).val2()) != Math.signum(points.get(i).val4()))
                    continue;
                if (Math.signum(y - points.get(j).val2()) != Math.signum(points.get(j).val4()))
                    continue;

                if (x >= minV && x <= maxV && y >= minV && y <= maxV)
                    count += 1;
            }
        }

        logger.info("Result: " + count);

        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {

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
