package com.vjasal.aoc2021.day20;

import com.vjasal.util.CollectionUtil;
import com.vjasal.util.vectors.Vector2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageEnhancer {

    public final Map<Vector2<Integer, Integer>, Boolean> image = new HashMap<>();

    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;

    public ImageEnhancer(String input) {
        List<String> lines = CollectionUtil.toArrayList(input);
        for (int y = 0; y < lines.size(); y++) {
            List<Character> values = CollectionUtil.toCharList(lines.get(y));
            for (int x = 0; x < values.size(); x++) {
                put(x, y, values.get(x) == '#');
            }
        }
    }

    public void enhanceImage(List<Character> algorithm, int times) {
        for (int i = 0; i < times; i++) {
            step(algorithm, i % 2 == 1);
        }
    }

    public long countLightPixels() {
        return image.values().stream().filter(val -> val).count();
    }

    private void step(List<Character> algorithm, boolean defaultValue) {
        Map<Vector2<Integer, Integer>, Boolean> tmp = new HashMap<>(image);
        int tmpMinX = minX;
        int tmpMinY = minY;
        int tmpMaxX = maxX;
        int tmpMaxY = maxY;

        clear();
        for (int y = tmpMinY - 1; y <= tmpMaxY + 1; y++) {
            for (int x = tmpMinX - 1; x <= tmpMaxX + 1; x++) {
                put(x, y, algorithm.get(getWindowValue(tmp, x, y, defaultValue)) == '#');
            }
        }
    }

    private int getWindowValue(Map<Vector2<Integer, Integer>, Boolean> map, int x, int y, boolean defaultValue) {
        int[] dy = {-1, -1, -1,  0,  0,  0,  1,  1,  1};
        int[] dx = {-1,  0,  1, -1,  0,  1, -1,  0,  1};

        int result = 0;
        for (int i = 0; i < 9; i++) {
            Vector2<Integer, Integer> v = new Vector2<>(x + dx[i], y + dy[i]);
            result = (result << 1) | (map.getOrDefault(v, defaultValue) ? 1 : 0);
        }
        return result;
    }

    private void put(int x, int y, boolean value) {
        image.put(new Vector2<>(x, y), value);
        minX = Math.min(minX, x);
        minY = Math.min(minY, y);
        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, x);
    }

    private void clear() {
        image.clear();
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                sb.append(image.get(new Vector2<>(x, y)) ? '#' : '.');
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
