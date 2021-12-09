package com.vjasal.aoc2020.day20;

import com.vjasal.util.CollectionUtil;
import com.vjasal.util.vectors.Vector2;
import com.vjasal.util.vectors.Vector3;

import java.util.*;
import java.util.logging.Logger;

public class Image {

    private static final Logger logger = Logger.getLogger(Image.class.getSimpleName());

    private final List<ImageTile> tiles = new LinkedList<>();
    private final Map<Vector2<Integer, Integer>, ImageTile> tileMap = new HashMap<>();

    int maxX = Integer.MIN_VALUE;
    int maxY = Integer.MIN_VALUE;
    int minX = Integer.MAX_VALUE;
    int minY = Integer.MAX_VALUE;

    public Image(String input) {
        for (String tileString : CollectionUtil.toListOfSections(input)) {
            tiles.add(new ImageTile(tileString));
        }

        orderTiles();
    }

    public long getMultipliedCorners() {
        return (long) getTile(minX, minY).getId() * getTile(minX, maxY).getId() *
                getTile(maxX, minY).getId() * getTile(maxX, maxY).getId();
    }

    public char[][] getCombinedImage() {
        int tileSize = tiles.get(0).getSize() - 2;
        char[][] result = new char[(maxY - minY + 1) * tileSize][(maxX - minX + 1) * tileSize];
        for (int y = 0; y < result.length; y++) {
            for (int x = 0; x < result[y].length; x++) {
                result[y][x] = getTile(minX + (x / tileSize), minY + (y / tileSize))
                        .get(x % tileSize + 1, y % tileSize + 1);
            }
        }
        return result;
    }

    public char[][] getTestCombinedImage() {
        int tileSize = tiles.get(0).getSize();
        char[][] result = new char[(maxY - minY + 1) * tileSize][(maxX - minX + 1) * tileSize];
        for (int y = 0; y < result.length; y++) {
            for (int x = 0; x < result[y].length; x++) {
                result[y][x] = getTile(minX + (x / tileSize), minY + (y / tileSize))
                        .get(x % tileSize, y % tileSize);
            }
        }
        return result;
    }

    private void orderTiles() {
        Queue<Vector3<ImageTile, Integer, Integer>> queue = new LinkedList<>();
        queue.add(new Vector3<>(tiles.get(0), 0, 0));
        int[] dx = {  1,  0, -1,  0};
        int[] dy = {  0,  1,  0, -1};

        while (!queue.isEmpty()) {
            Vector3<ImageTile, Integer, Integer> current = queue.poll();
            ImageTile tile = current.getValue1();
            int x = current.getValue2();
            int y = current.getValue3();

            if (contains(x, y)) continue;
            put(tile, x, y);

            for (int k = 0; k < dx.length; k++) {
                int nextX = x + dx[k];
                int nextY = y + dy[k];

                if (contains(nextX, nextY)) continue;
                if (queue.stream().anyMatch(v -> v.getValue2() == nextX && v.getValue3() == nextY)) continue;

                long id = tile.getEdgeId(dx[k], dy[k]);

                // this can be done better by having a hashmap with edge ids and tiles
                Optional<ImageTile> nextTile = tiles.stream().filter(imageTile -> imageTile != tile)
                        .filter(imageTile -> imageTile.containsEdgeId(id))
                        .findFirst();

                if (!nextTile.isPresent()) continue;

                nextTile.get().orientateTile(dx[k], dy[k], id);
                queue.add(new Vector3<>(nextTile.get(), nextX, nextY));
            }
        }
    }

    private void put(ImageTile tile, int x, int y) {
        tileMap.put(new Vector2<>(x, y), tile);

        if (maxX < x) maxX = x;
        if (maxY < y) maxY = y;
        if (minX > x) minX = x;
        if (minY > y) minY = y;
    }

    private ImageTile getTile(int x, int y) {
        return tileMap.get(new Vector2<>(x, y));
    }

    private boolean contains(int x, int y) {
        return tileMap.containsKey(new Vector2<>(x, y));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("\n");
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                builder.append(getTile(x, y).getId()).append(" ");
            }
            builder.append("\n");
        }

        char[][] image = getTestCombinedImage();

        for (char[] line : image) {
            for (char c : line) {
                builder.append(c);
            }
            builder.append("\n");
        }


        return builder.toString();
    }
}
