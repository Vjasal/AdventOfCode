package com.vjasal.aoc2021.day17;

import com.vjasal.type.tuple.Tuple2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Probe {

    private static final Pattern pattern = Pattern.compile(
            "^target area: x=(-?\\d+)\\.\\.(-?\\d+), y=(-?\\d+)\\.\\.(-?\\d+)$");

    private final Tuple2<Integer, Integer> startPosition;
    private final Tuple2<Integer, Integer> t1;
    private final Tuple2<Integer, Integer> t2;

    public Probe(String target) {
        Matcher matcher = pattern.matcher(target);
        if (!matcher.find()) throw new IllegalArgumentException("Wrong target format!");
        int x1 = Integer.parseInt(matcher.group(1));
        int x2 = Integer.parseInt(matcher.group(2));
        int y1 = Integer.parseInt(matcher.group(3));
        int y2 = Integer.parseInt(matcher.group(4));

        startPosition = new Tuple2<>(0, 0);
        t1 = new Tuple2<>(x1, y1);
        t2 = new Tuple2<>(x2, y2);
    }

    public int getLowerXBound() {
        return Math.min(0, t1.val1());
    }

    public int getUpperXBound() {
        return Math.max(0, t2.val1());
    }

    public int getLowerYBound() {
        return Math.abs(Math.min(t1.val2(), t2.val2())) * -1;
    }

    public int getUpperYBound() {
        return Math.abs(Math.min(t1.val2(), t2.val2()));
    }

    public boolean simulate(int vx, int vy) {
        int x = startPosition.val1();
        int y = startPosition.val2();

        while ((x < t1.val1() && y >= t1.val2()) || (y > t2.val2() && x <= t2.val1())) {
            x += vx;
            y += vy;
            vx = stepX(vx);
            vy = stepY(vy);
        }

        return x >= t1.val1() && x <= t2.val1() && y >= t1.val2() && y <= t2.val2();
    }

    public int getHighestPositionForY(int vy) {
        int y = 0;
        while (vy > 0) {
            y += vy;
            vy = stepY(vy);
        }
        return y;
    }

    private int stepX(int x) {
        if (x > 0) return x - 1;
        if (x < 0) return x + 1;
        return 0;
    }

    private int stepY(int y) {
        return y - 1;
    }
}
