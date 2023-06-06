package com.vjasal.type;

import com.vjasal.type.vector.Vector2;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Grid2 {

    private final Set<Vector2<Integer>> grid = new HashSet<>();

    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;

    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;

    public void add(int x, int y) {
        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, y);

        minX = Math.min(minX, x);
        minY = Math.min(minY, y);

        grid.add(Vector2.valueOf(x, y));
    }

    public void add(Vector2<Integer> position) {
        maxX = Math.max(maxX, position.val1());
        maxY = Math.max(maxY, position.val2());

        minX = Math.min(minX, position.val1());
        minY = Math.min(minY, position.val2());

        grid.add(position);
    }

    public boolean contains(int x, int y) {
        return grid.contains(Vector2.valueOf(x, y));
    }

    public boolean contains(Vector2<Integer> position) {
        return grid.contains(position);
    }

    public int minX() {
        return minX;
    }

    public int minY() {
        return minY;
    }

    public int maxX() {
        return maxX;
    }

    public int maxY() {
        return maxY;
    }

    public Stream<Vector2<Integer>> stream() {
        return grid.stream();
    }

    public Set<Vector2<Integer>> values() {
        return grid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");

        for (int y = minY - 1; y <= maxY + 1; y++) {
            for (int x = minX - 1; x <= maxX + 1; x++) {
                sb.append(contains(x, y) ? "#" : ".");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
