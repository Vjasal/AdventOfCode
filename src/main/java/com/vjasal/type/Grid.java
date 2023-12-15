package com.vjasal.type;

import com.vjasal.type.vector.Vector2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class Grid<T> {

    private final Map<Vector2<Integer>, T> grid = new HashMap<>();

    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;

    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;

    public Grid() {
        this(false);
    }

    public Grid(boolean zero) {
        if (zero) {
            minX = 0;
            minY = 0;
            maxX = 0;
            maxY = 0;
        }
    }

    public void put(int x, int y, T value) {
        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, y);

        minX = Math.min(minX, x);
        minY = Math.min(minY, y);

        grid.put(Vector2.valueOf(x, y), value);
    }

    public void put(Vector2<Integer> position, T value) {
        maxX = Math.max(maxX, position.val1());
        maxY = Math.max(maxY, position.val2());

        minX = Math.min(minX, position.val1());
        minY = Math.min(minY, position.val2());

        grid.put(position, value);
    }

    public void putIfAbsent(int x, int y, T value) {
        if (grid.putIfAbsent(Vector2.valueOf(x, y), value) == null) {
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);

            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
        }
    }

    public T get(int x, int y) {
        return grid.get(Vector2.valueOf(x, y));
    }

    public T get(int x, int y, T defaultValue) {
        T value = grid.get(Vector2.valueOf(x, y));
        return value == null ? defaultValue : value;
    }

    public T get(Vector2<Integer> position) {
        return grid.get(position);
    }

    public Set<Map.Entry<Vector2<Integer>, T>> entrySet() {
        return grid.entrySet();
    }

    public Set<Vector2<Integer>> keySet() {
        return grid.keySet();
    }

    public T remove(int x, int y) {
        return grid.remove(Vector2.valueOf(x, y));
    }

    public T remove(Vector2<Integer> position) {
        return grid.remove(position);
    }

    public boolean containsKey(int x, int y) {
        return grid.containsKey(Vector2.valueOf(x, y));
    }

    public boolean containsKey(Vector2<Integer> position) {
        return grid.containsKey(position);
    }

    public boolean isEmpty() {
        return grid.isEmpty();
    }

    public int width() {
        return maxX - minX + 1;
    }

    public int height() {
        return maxY - minY + 1;
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

    public String toString(String defaultValue, Function<T, String> mapper) {
        StringBuilder sb = new StringBuilder("\n");

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                if (containsKey(x, y))
                    sb.append(mapper.apply(get(x, y)));
                else
                    sb.append(defaultValue);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
