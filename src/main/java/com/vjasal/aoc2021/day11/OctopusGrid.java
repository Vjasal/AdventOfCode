package com.vjasal.aoc2021.day11;

import com.vjasal.util.CollectionUtil;
import com.vjasal.util.vectors.Vector2;
import com.vjasal.util.vectors.VectorUtil;

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

        int flashes = 0;

        while (!queue.isEmpty()) {
            Vector2<Integer, Integer> v = queue.poll();
            if (seen.contains(v)) continue;

            grid.put(v, grid.get(v) + 1);

            if (grid.get(v) > 9) {
                seen.add(v);

                grid.put(v, 0);
                flashes += 1;

                for (Vector2<Integer, Integer> neighbour : VectorUtil.neighbours(v, true)) {
                    if (grid.containsKey(neighbour)) {
                        queue.add(neighbour);
                    }
                }
            }
        }

        return flashes;
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
