package com.vjasal.aoc2018.day03;

import com.vjasal.util.CollectionUtil;
import com.vjasal.util.vectors.Vector2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Fabric {

    private static final Logger logger = Logger.getLogger(Fabric.class.getName());
    private static final Pattern pattern = Pattern.compile("^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)$");

    private final Map<Vector2<Integer, Integer>, List<Integer>> map = new HashMap<>();
    private final Map<Integer, Boolean> overlaps = new HashMap<>();

    public Fabric(String input) {
        for (String line : CollectionUtil.toLinkedList(input)) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) throw new IllegalArgumentException("Wring input format");
            int id = Integer.parseInt(matcher.group(1));
            int x  = Integer.parseInt(matcher.group(2));
            int y  = Integer.parseInt(matcher.group(3));
            int w  = Integer.parseInt(matcher.group(4));
            int h  = Integer.parseInt(matcher.group(5));
            markCut(x, y, w, h, id);
        }
    }

    public long countIntersections() {
        return map.values().stream().filter(list -> list.size() > 1).count();
    }

    public int getNotOverlappingId() {
        List<Integer> result = overlaps.entrySet().stream()
                .filter(Map.Entry::getValue)
                .mapToInt(Map.Entry::getKey)
                .boxed()
                .collect(Collectors.toList());

        if (result.size() != 1) throw new IllegalStateException("");
        return result.get(0);
    }

    private void markCut(int x, int y, int w, int h, int id) {
        overlaps.put(id, true);
        for (int j = y; j < y + h; j++) {
            for (int i = x; i < x + w; i++) {
                Vector2<Integer, Integer> v = new Vector2<>(i, j);
                if (map.containsKey(v)) {
                    map.get(v).add(id);
                    for (Integer overlappingId : map.get(v)) {
                        overlaps.put(overlappingId, false);
                    }
                } else {
                    map.put(v, CollectionUtil.asLinkedList(id));
                }
            }
        }
    }
}
