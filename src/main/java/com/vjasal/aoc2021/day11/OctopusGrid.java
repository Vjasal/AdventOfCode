package com.vjasal.aoc2021.day11;

import com.vjasal.util.CollectionUtil;
import com.vjasal.util.vectors.Vector2;

import java.util.*;
import java.util.stream.Collectors;

public class OctopusGrid {

    private final Map<Vector2<Integer, Integer>, Integer> grid = new HashMap<>();

    public OctopusGrid(String input) {
        List<String> lines = CollectionUtil.toArrayList(input);
        for (int y = 0; y < 10; y++) {
            List<Integer> values = CollectionUtil.toIntList(lines.get(y));
            for (int x = 0; x < 10; x++) {
                grid.put(new Vector2<>(x, y), values.get(x));
            }
        }
    }

    public int step() {
        for (Map.Entry<Vector2<Integer, Integer>, Integer> entry : grid.entrySet()) {
            entry.setValue(entry.getValue() + 1);
        }

        Queue<Vector2<Integer, Integer>> queue = grid.entrySet().stream()
                .filter(e -> e.getValue() > 9)
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(LinkedList::new));
        Set<Vector2<Integer, Integer>> seen = new HashSet<>();

        while (!queue.isEmpty()) {
            Vector2<Integer, Integer> v = queue.poll();
            if (seen.contains(v)) continue;
            grid.put(v, grid.get(v) + 1);
            if (grid.get(v) > 9) {
                seen.add(v);
                queue.addAll(neighbours(v));
            }
        }

        int flashes = 0;
        for (Map.Entry<Vector2<Integer, Integer>, Integer> entry : grid.entrySet()) {
            if (entry.getValue() > 9) {
                entry.setValue(0);
                flashes += 1;
            }
        }

        return flashes;
    }

    private List<Vector2<Integer, Integer>> neighbours(Vector2<Integer, Integer> v) {
        List<Vector2<Integer, Integer>> neighbours = new LinkedList<>();
        int[] dx = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 0, 1, -1, 0, 1};

        for (int k = 0; k < 9; k++) {
            int x = v.getValue1() + dx[k];
            int y = v.getValue2() + dy[k];
            if (x < 0 || x >= 10) continue;
            if (y < 0 || y >= 10) continue;
            neighbours.add(new Vector2<>(x, y));
        }

        return neighbours;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Vector2<Integer, Integer> v = new Vector2<>(x, y);
                sb.append(grid.get(v) < 10 ? grid.get(v) : "#");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
