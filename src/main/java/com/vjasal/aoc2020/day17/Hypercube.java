package com.vjasal.aoc2020.day17;

import com.vjasal.util.CollectionUtil;
import com.vjasal.util.vectors.Tuple4;

import java.util.HashMap;
import java.util.Map;

public class Hypercube {

    private final Map<Tuple4<Integer, Integer, Integer, Integer>, Character> values;

    private int minW = Integer.MAX_VALUE;
    private int minZ = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int minX = Integer.MAX_VALUE;

    private int maxW = Integer.MIN_VALUE;
    private int maxZ = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private int maxX = Integer.MIN_VALUE;

    Hypercube(String input) {
        values = new HashMap<>();

        int y = 0;
        for (String line : CollectionUtil.toLinkedList(input)) {
            int x = 0;
            for (char c : CollectionUtil.toCharList(line)) {
                put(x, y, 0, 0, c);
                x++;
            }
            y++;
        }
    }

    private Hypercube(Hypercube other) {
        values = new HashMap<>();

        for (int w = other.minW; w <= other.maxW; w++) {
            for (int z = other.minZ; z <= other.maxZ; z++) {
                for (int y = other.minY; y <= other.maxY; y++) {
                    for (int x = other.minX; x <= other.maxX; x++) {

                        if (other.get(x, y, z, w) == '#') {
                            put(x, y, z, w, '#');
                        }

                    }
                }
            }
        }
    }

    void cycle() {
        Hypercube copy = new Hypercube(this);
        clear();

        for (int w = copy.minW - 1; w <= copy.maxW + 1; w++) {
            for (int z = copy.minZ - 1; z <= copy.maxZ + 1; z++) {
                for (int y = copy.minY - 1; y <= copy.maxY + 1; y++) {
                    for (int x = copy.minX - 1; x <= copy.maxX + 1; x++) {

                        int nearby = copy.countNearby(x, y, z, w);
                        if (copy.get(x, y, z, w) == '#' && (nearby == 2 || nearby == 3)) {
                            put(x, y, z, w, '#');
                        }
                        if (copy.get(x, y, z, w) == '.' && nearby == 3) {
                            put(x, y, z, w, '#');
                        }

                    }
                }
            }
        }
    }

    int countActive() {
        int result = 0;

        for (int w = minW; w <= maxW; w++) {
            for (int z = minZ; z <= maxZ; z++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int x = minX; x <= maxX; x++) {

                        if (get(x, y, z, w) == '#') {
                            result += 1;
                        }

                    }
                }
            }
        }

        return result;
    }

    private void put(int x, int y, int z, int w, char value) {
        values.put(new Tuple4<>(x, y, z, w), value);

        if (maxW < w) maxW = w;
        if (maxZ < z) maxZ = z;
        if (maxY < y) maxY = y;
        if (maxX < x) maxX = x;

        if (minW > w) minW = w;
        if (minZ > z) minZ = z;
        if (minY > y) minY = y;
        if (minX > x) minX = x;
    }

    private char get(int x, int y, int z, int w) {
        return values.getOrDefault(new Tuple4<>(x, y, z, w), '.');
    }

    private void clear() {
        values.clear();

        minW = Integer.MAX_VALUE;
        minZ = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        minX = Integer.MAX_VALUE;

        maxW = Integer.MIN_VALUE;
        maxZ = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
        maxX = Integer.MIN_VALUE;
    }

    private int countNearby(int x, int y, int z, int w) {
        int[] dw = {-1, 0, 1};
        int[] dz = {-1, 0, 1};
        int[] dy = {-1, 0, 1};
        int[] dx = {-1, 0, 1};
        int result = 0;

        for (int wi = 0; wi < 3; wi++) {
            for (int zi = 0; zi < 3; zi++) {
                for (int yi = 0; yi < 3; yi++) {
                    for (int xi = 0; xi < 3; xi++) {

                        if (dx[xi] == 0 && dy[yi] == 0 && dz[zi] == 0 && dw[wi] == 0)
                            continue;
                        if (get(x + dx[xi], y + dy[yi], z + dz[zi], w + dw[wi]) == '#')
                            result += 1;

                    }
                }
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int w = minW; w <= maxW; w++) {
            for (int z = minZ; z <= maxZ; z++) {
                builder.append("z=").append(z).append(" w=").append(w).append("\n");

                for (int y = minY; y <= maxY; y++) {
                    for (int x = minX; x <= maxX; x++) {
                        builder.append(get(x, y, z, w));
                    }
                    builder.append("\n");
                }

                builder.append("\n");
            }
        }

        return builder.toString();
    }
}
