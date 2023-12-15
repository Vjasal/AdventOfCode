package com.vjasal.aoc2023.day14;

import com.vjasal.type.Grid;
import com.vjasal.type.vector.Vector2;
import com.vjasal.util.CollectionUtil;

import java.util.*;

public class Platform {
    private final Grid<Boolean> map;

    public Platform(String input) {
        map = CollectionUtil.toGrid(input, s -> switch (s) {
            case "#" -> false;
            case "O" -> true;
            default -> null;
        });
    }

    public void spin(int k) {
        Map<List<Vector2<Integer>>, Integer> seen = new HashMap<>();
        int remaining = 0;

        for (int i = 0; i < k; i++) {
            rollUp();
            rollLeft();
            rollDown();
            rollRight();

            // Collect rolling stone positions to a list, and check if we already saw this list
            List<Vector2<Integer>> state = map.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).toList();
            if (seen.containsKey(state)) {
                // We already saw this position, so we can skip some steps
                // First we did x steps and got some state, then after x+y steps, and we got the same state
                // So the following is true k = x + n * y + r, for natural number n
                // Therefore r = (k - x) % y
                //
                // .*******._____._____._____._____._____._____._____._____._____.+++.
                //     x      y     y     y     y     y     y     y     y     y    r
                remaining = (k - seen.get(state) - 1) % (i - seen.get(state));
                break;
            }
            seen.put(state, i);
        }

        for (int i = 0; i < remaining; i++) {
            rollUp();
            rollLeft();
            rollDown();
            rollRight();
        }
    }

    public void rollUp() {
        for (int y = 1; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                if (map.get(x, y, false)) {
                    int dy = 0;
                    while (y - dy > 0 && !map.containsKey(x, y - dy - 1)) dy++;
                    if (dy > 0) {
                        map.remove(x, y);
                        map.put(x, y - dy, true);
                    }
                }
            }
        }
    }

    public void rollDown() {
        for (int y = map.height() - 2; y >= 0; y--) {
            for (int x = 0; x < map.width(); x++) {
                if (map.get(x, y, false)) {
                    int dy = 0;
                    while (y + dy < map.height() - 1 && !map.containsKey(x, y + dy + 1)) dy++;
                    if (dy > 0) {
                        map.remove(x, y);
                        map.put(x, y + dy, true);
                    }
                }
            }
        }
    }

    public void rollLeft() {
        for (int x = 1; x < map.width(); x++) {
            for (int y = 0; y < map.height(); y++) {
                if (map.get(x, y, false)) {
                    int dx = 0;
                    while (x - dx > 0 && !map.containsKey(x - dx - 1, y)) dx++;
                    if (dx > 0) {
                        map.remove(x, y);
                        map.put(x - dx, y, true);
                    }
                }
            }
        }
    }

    public void rollRight() {
        for (int x = map.height() - 2; x >= 0; x--) {
            for (int y = 0; y < map.height(); y++) {
                if (map.get(x, y, false)) {
                    int dx = 0;
                    while (x + dx < map.height() - 1 && !map.containsKey(x + dx + 1, y)) dx++;
                    if (dx > 0) {
                        map.remove(x, y);
                        map.put(x + dx, y, true);
                    }
                }
            }
        }
    }

    public long calculateLoad() {
        return map.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .mapToLong(v -> map.height() - v.val2())
                .sum();
    }
}
