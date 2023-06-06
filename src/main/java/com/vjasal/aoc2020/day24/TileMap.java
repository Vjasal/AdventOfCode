package com.vjasal.aoc2020.day24;

import com.vjasal.util.CollectionUtil;
import com.vjasal.type.tuple.Tuple2;

import java.io.StringReader;
import java.util.*;

public class TileMap {

    private final Set<Tuple2<Integer, Integer>> flippedTiles = new HashSet<>();

    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;

    TileMap(String input) {
        Set<Tuple2<Integer, Integer>> flippedTiles = new HashSet<>();
        for (String line : CollectionUtil.toLinkedList(input)) {
            List<String> directions = parseLine(line);
            Tuple2<Integer, Integer> tile = getTile(directions);
            if (flippedTiles.contains(tile)) {
                flippedTiles.remove(tile);
            } else {
                flippedTiles.add(tile);
            }
        }
        for (Tuple2<Integer, Integer> tile : flippedTiles) {
            add(tile);
        }
    }

    private void add(Tuple2<Integer, Integer> tile) {
        flippedTiles.add(tile);

        if (maxX < tile.val1()) maxX = tile.val1();
        if (minX > tile.val1()) minX = tile.val1();
        if (maxY < tile.val2()) maxY = tile.val2();
        if (minY > tile.val2()) minY = tile.val2();
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
        Set<Tuple2<Integer, Integer>> copy = new HashSet<>(this.flippedTiles);
        int maxX = this.maxX;
        int maxY = this.maxY;
        int minX = this.minX;
        int minY = this.minY;

        clear();

        for (int y = minY - 2; y < maxY + 2; y++) {
            for (int x = minX - 2; x < maxX + 2; x++) {
                Tuple2<Integer, Integer> next = new Tuple2<>(x, y);
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

    private static Tuple2<Integer, Integer> getTile(List<String> directions) {
        int x = 0;
        int y = 0;
        for (String direction : directions) {
            switch (direction) {
                case "e" -> x += 1;
                case "w" -> x -= 1;
                case "ne" -> {
                    x += 1;
                    y += 1;
                }
                case "nw" -> y += 1;
                case "se" -> y -= 1;
                case "sw" -> {
                    x -= 1;
                    y -= 1;
                }
            }
        }
        return new Tuple2<>(x, y);
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

    private static int countFlippedTiles(Tuple2<Integer, Integer> tile, Set<Tuple2<Integer, Integer>> flippedTiles) {
        int[] dx = {1,  1,  0, -1, -1,  0};
        int[] dy = {0,  1,  1,  0, -1, -1};

        int result = 0;
        for (int k = 0; k < dx.length; k++) {
            Tuple2<Integer, Integer> next = new Tuple2<>(tile.val1() + dx[k], tile.val2() + dy[k]);
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
