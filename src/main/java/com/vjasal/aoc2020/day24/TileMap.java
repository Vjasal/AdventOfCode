package com.vjasal.aoc2020.day24;

import com.vjasal.util.CollectionUtil;
import com.vjasal.util.vectors.Vector2;

import java.io.StringReader;
import java.util.*;

public class TileMap {

    private final Set<Vector2<Integer, Integer>> flippedTiles = new HashSet<>();

    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;

    TileMap(String input) {
        Set<Vector2<Integer, Integer>> flippedTiles = new HashSet<>();
        for (String line : CollectionUtil.toLinkedList(input)) {
            List<String> directions = parseLine(line);
            Vector2<Integer, Integer> tile = getTile(directions);
            if (flippedTiles.contains(tile)) {
                flippedTiles.remove(tile);
            } else {
                flippedTiles.add(tile);
            }
        }
        for (Vector2<Integer, Integer> tile : flippedTiles) {
            add(tile);
        }
    }

    private void add(Vector2<Integer, Integer> tile) {
        flippedTiles.add(tile);

        if (maxX < tile.getValue1()) maxX = tile.getValue1();
        if (minX > tile.getValue1()) minX = tile.getValue1();
        if (maxY < tile.getValue2()) maxY = tile.getValue2();
        if (minY > tile.getValue2()) minY = tile.getValue2();
    }

    private void clear() {
        flippedTiles.clear();

        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
    }

    int countAll() {
        return flippedTiles.size();
    }

    void round() {
        Set<Vector2<Integer, Integer>> copy = new HashSet<>(this.flippedTiles);
        int maxX = this.maxX;
        int maxY = this.maxY;
        int minX = this.minX;
        int minY = this.minY;

        clear();

        for (int y = minY - 2; y < maxY + 2; y++) {
            for (int x = minX - 2; x < maxX + 2; x++) {
                Vector2<Integer, Integer> next = new Vector2<>(x, y);
                int count = countFlippedTiles(next, copy);
                if (copy.contains(next) && count > 0 && count <= 2) {
                    add(next);
                }
                if (!copy.contains(next) && count == 2) {
                    add(next);
                }
            }
        }
    }

    private static Vector2<Integer, Integer> getTile(List<String> directions) {
        int x = 0;
        int y = 0;
        for (String direction : directions) {
            switch (direction) {
                case "e":
                    x += 1;
                    break;
                case "w":
                    x -= 1;
                    break;
                case "ne":
                    x += 1;
                    y += 1;
                    break;
                case "nw":
                    y += 1;
                    break;
                case "se":
                    y -= 1;
                    break;
                case "sw":
                    x -= 1;
                    y -= 1;
                    break;
            }
        }
        return new Vector2<>(x, y);
    }

    private static List<String> parseLine(String input) {
        List<String> result = new LinkedList<>();
        try (Scanner scanner = new Scanner(new StringReader(input))) {
            scanner.useDelimiter("");
            while (scanner.hasNext()) {
                String d = scanner.next();
                if (d.equals("s") || d.equals("n")) {
                    result.add(d + scanner.next());
                } else {
                    result.add(d);
                }
            }
        }
        return result;
    }

    private static int countFlippedTiles(Vector2<Integer, Integer> tile, Set<Vector2<Integer, Integer>> flippedTiles) {
        int[] dx = {1,  1,  0, -1, -1,  0};
        int[] dy = {0,  1,  1,  0, -1, -1};

        int result = 0;
        for (int k = 0; k < dx.length; k++) {
            Vector2<Integer, Integer> next = new Vector2<>(tile.getValue1() + dx[k], tile.getValue2() + dy[k]);
            if (flippedTiles.contains(next))
                result += 1;
        }
        return result;
    }

    @Override
    public String toString() {
        return flippedTiles.toString();
    }
}
