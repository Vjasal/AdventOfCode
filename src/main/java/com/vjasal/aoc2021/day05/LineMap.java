package com.vjasal.aoc2021.day05;

import com.vjasal.type.tuple.Tuple2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineMap {
    private static final Pattern linePattern = Pattern.compile("^(\\d+),(\\d+) -> (\\d+),(\\d+)$");

    private final Map<Tuple2<Integer, Integer>, Integer> values = new HashMap<>();

    public LineMap(List<String> lines, boolean vhOnly) {
        for (String line : lines) {
            Matcher lineMatcher = linePattern.matcher(line);
            if (lineMatcher.find()) {
                int x1 = Integer.parseInt(lineMatcher.group(1));
                int y1 = Integer.parseInt(lineMatcher.group(2));
                int x2 = Integer.parseInt(lineMatcher.group(3));
                int y2 = Integer.parseInt(lineMatcher.group(4));
                insetLine(x1, y1, x2, y2, vhOnly);
            }
        }
    }

    public long count() {
        return values.entrySet().stream().filter(e -> e.getValue() >= 2).count();
    }

    private void insetLine(int x1, int y1, int x2, int y2, boolean vhOnly) {
        if (x1 == x2) {
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                putValue(x1, y);
            }
        } else if (y1 == y2) {
            for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                putValue(x, y1);
            }
        } else if (!vhOnly) {
            // Part 2
            if (x1 > x2 && y1 > y2 || x1 < x2 && y1 < y2) {
                int originX = Math.min(x1, x2);
                int originY = Math.min(y1, y2);
                for (int i = 0; i <= Math.abs(x2 - x1); i++) {
                    putValue(originX + i, originY + i);
                }
            } else {
                int originX = Math.max(x1, x2);
                int originY = Math.min(y1, y2);
                for (int i = 0; i <= Math.abs(x2 - x1); i++) {
                    putValue(originX - i, originY + i);
                }
            }
        }
    }

    private void putValue(int x, int y) {
        Tuple2<Integer, Integer> v = new Tuple2<>(x, y);
        if (values.containsKey(v)) {
            values.put(v, values.get(v) + 1);
        } else {
            values.put(v, 1);
        }
    }
}
