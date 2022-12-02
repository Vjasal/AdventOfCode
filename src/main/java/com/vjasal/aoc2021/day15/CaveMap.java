package com.vjasal.aoc2021.day15;

import com.vjasal.util.CollectionUtil;
import com.vjasal.util.vectors.Tuple2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaveMap {

    private final Map<Tuple2<Integer, Integer>, Integer> map = new HashMap<>();

    private final int width;
    private final int height;

    public CaveMap(String input, int factor) {
        Map<Tuple2<Integer, Integer>, Integer> tmp = new HashMap<>();
        List<String> lines = CollectionUtil.toArrayList(input);

        int width = 0;
        int height = lines.size();

        for (int y = 0; y < lines.size(); y++) {
            List<Integer> values = CollectionUtil.toIntList(lines.get(y));
            width = values.size();

            for (int x = 0; x < values.size(); x++) {
                Tuple2<Integer, Integer> v = new Tuple2<>(x, y);
                tmp.put(v, values.get(x));
            }
        }

        for (int fy = 0; fy < factor; fy++) {
            for (int fx = 0; fx < factor; fx++) {
                for (Map.Entry<Tuple2<Integer, Integer>, Integer> entry : tmp.entrySet()) {
                    int x = entry.getKey().val1() + fx * width;
                    int y = entry.getKey().val2() + fy * height;
                    int value = (entry.getValue() + fx + fy - 1) % 9 + 1;
                    map.put(new Tuple2<>(x, y), value);
                }
            }
        }

        this.width = width * factor;
        this.height = height * factor;
    }

    public Tuple2<Integer, Integer> getEndPosition() {
        return new Tuple2<>(height - 1, width - 1);
    }

    public int get(Tuple2<Integer, Integer> vector) {
        return map.get(vector);
    }

    public boolean contains(Tuple2<Integer, Integer> vector) {
        return map.containsKey(vector);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(map.get(new Tuple2<>(x, y)));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
