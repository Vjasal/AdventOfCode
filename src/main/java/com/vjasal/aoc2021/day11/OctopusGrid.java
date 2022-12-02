package com.vjasal.aoc2021.day11;

import com.vjasal.util.CollectionUtil;
import com.vjasal.util.vectors.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

public class OctopusGrid {

    private final Map<Tuple2<Integer, Integer>, Integer> grid = new HashMap<>();

    public OctopusGrid(String input) {
        List<String> lines = CollectionUtil.toArrayList(input);
        for (int y = 0; y < 10; y++) {
            List<Integer> values = CollectionUtil.toIntList(lines.get(y));
            for (int x = 0; x < 10; x++) {
                grid.put(new Tuple2<>(x, y), values.get(x));
            }
        }
    }

    public int step() {
        for (Map.Entry<Tuple2<Integer, Integer>, Integer> entry : grid.entrySet()) {
            entry.setValue(entry.getValue() + 1);
        }

        Queue<Tuple2<Integer, Integer>> queue = grid.entrySet().stream()
                .filter(e -> e.getValue() > 9)
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(LinkedList::new));
        Set<Tuple2<Integer, Integer>> seen = new HashSet<>();

        int flashes = 0;

        while (!queue.isEmpty()) {
            Tuple2<Integer, Integer> v = queue.poll();
            if (seen.contains(v)) continue;

            grid.put(v, grid.get(v) + 1);

            if (grid.get(v) > 9) {
                seen.add(v);

                grid.put(v, 0);
                flashes += 1;

                for (Tuple2<Integer, Integer> neighbour : Tuple2.neighbours(v, true)) {
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
                Tuple2<Integer, Integer> v = new Tuple2<>(x, y);
                sb.append(grid.get(v) < 10 ? grid.get(v) : "#");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
