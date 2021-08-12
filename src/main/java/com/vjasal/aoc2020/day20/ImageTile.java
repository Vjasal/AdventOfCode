package com.vjasal.aoc2020.day20;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageTile {

    private static final Logger logger = Logger.getLogger(ImageTile.class.getSimpleName());

    private enum Direction {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }

    private static final Pattern idPattern = Pattern.compile("^Tile (\\d+):\n((.+\n?)*)$");

    private final char[][] values;
    private final int id;

    private final Map<Direction, Long> edgeIds        = new HashMap<>();
    private final Map<Direction, Long> flippedEdgeIds = new HashMap<>();

    private Direction direction = Direction.UP;
    private boolean   isFlipped = false;

    public ImageTile(String input) {
        Matcher matcher = idPattern.matcher(input);
        if (!matcher.find())
            throw new IllegalArgumentException();

        id = Integer.parseInt(matcher.group(1));
        values = Arrays.stream(matcher.group(2).split("\n"))
                .map(String::toCharArray)
                .toArray(char[][]::new);

        calculateEdges();
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return values.length;
    }

    public char get(int x, int y) {
        int d = values.length;
        switch (direction) {
            case UP:
                return isFlipped ? values[y][d - x - 1] : values[y][x];
            case DOWN:
                return isFlipped ? values[d - y - 1][x] : values[d - y - 1][d - x - 1];
            case LEFT:
                return isFlipped ? values[x][y] : values[x][d - y - 1];
            case RIGHT:
                return isFlipped ? values[d - x - 1][d - y - 1] : values[d - x - 1][y];
            default:
                throw new IllegalStateException();
        }
    }

    public long getEdgeId(int x, int y) {
        if (x == 1)       return edgeIds.get(Direction.RIGHT);
        else if (x == -1) return edgeIds.get(Direction.LEFT);
        else if (y == 1)  return edgeIds.get(Direction.DOWN);
        else if (y == -1) return edgeIds.get(Direction.UP);
        else              throw new IllegalArgumentException();
    }

    public boolean containsEdgeId(long id) {
        return edgeIds.containsValue(id) || flippedEdgeIds.containsValue(id);
    }

    public void orientateTile(int x, int y, long edgeId) {
        if (x == 1)       orientateTile(Direction.LEFT, edgeId);
        else if (x == -1) orientateTile(Direction.RIGHT, edgeId);
        else if (y == 1)  orientateTile(Direction.UP, edgeId);
        else if (y == -1) orientateTile(Direction.DOWN, edgeId);
        else              throw new IllegalArgumentException();
    }

    public void orientateTile(Direction direction, long edgeId) {
        while (edgeIds.get(direction) != edgeId && flippedEdgeIds.get(direction) != edgeId) {
            rotateTileClockwise();
        }
        if (flippedEdgeIds.containsValue(edgeId)) {
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                flipTileVertically();
            } else {
                flipTileHorizontally();
            }
        }
    }

    private void calculateEdges() {
        for (Direction direction : Direction.values()) {
            long id = calculateEdgeId(direction);
            edgeIds.put(direction, id);
            flippedEdgeIds.put(direction, flipEdgeId(id));
        }
    }

    private long calculateEdgeId(Direction direction) {
        long result = 0;
        int d = values.length;
        for (int i = 0; i < d; i++) {
            switch (direction) {
                case UP:
                    result = (result << 1) | (values[0][i] == '#' ? 1 : 0);
                    break;
                case DOWN:
                    result = (result << 1) | (values[d - 1][i] == '#' ? 1 : 0);
                    break;
                case LEFT:
                    result = (result << 1) | (values[i][0] == '#' ? 1 : 0);
                    break;
                case RIGHT:
                    result = (result << 1) | (values[i][d - 1] == '#' ? 1 : 0);
                    break;
            }
        }
        return result;
    }

    private long flipEdgeId(long edgeId) {
        long result = 0;
        for (int i = 0; i < values.length; i++) {
            result = (result << 1) | (edgeId & 1);
            edgeId = (edgeId >> 1);
        }
        return result;
    }

    private void rotateTileClockwise() {
        long tmp;

        tmp = edgeIds.get(Direction.UP);
        edgeIds.put(Direction.UP, flippedEdgeIds.get(Direction.LEFT));
        flippedEdgeIds.put(Direction.LEFT, flippedEdgeIds.get(Direction.DOWN));
        flippedEdgeIds.put(Direction.DOWN, edgeIds.get(Direction.RIGHT));
        edgeIds.put(Direction.RIGHT, tmp);

        tmp = flippedEdgeIds.get(Direction.UP);
        flippedEdgeIds.put(Direction.UP, edgeIds.get(Direction.LEFT));
        edgeIds.put(Direction.LEFT, edgeIds.get(Direction.DOWN));
        edgeIds.put(Direction.DOWN, flippedEdgeIds.get(Direction.RIGHT));
        flippedEdgeIds.put(Direction.RIGHT, tmp);

        switch (direction) {
            case UP:
                direction = Direction.RIGHT;
                break;
            case DOWN:
                direction = Direction.LEFT;
                break;
            case LEFT:
                direction = Direction.UP;
                break;
            case RIGHT:
                direction = Direction.DOWN;
                break;
        }
    }

    private void flipTileVertically() {
        long tmp;

        tmp = edgeIds.get(Direction.UP);
        edgeIds.put(Direction.UP, edgeIds.get(Direction.DOWN));
        edgeIds.put(Direction.DOWN, tmp);

        tmp = flippedEdgeIds.get(Direction.UP);
        flippedEdgeIds.put(Direction.UP, flippedEdgeIds.get(Direction.DOWN));
        flippedEdgeIds.put(Direction.DOWN, tmp);

        tmp = edgeIds.get(Direction.LEFT);
        edgeIds.put(Direction.LEFT, flippedEdgeIds.get(Direction.LEFT));
        flippedEdgeIds.put(Direction.LEFT, tmp);

        tmp = edgeIds.get(Direction.RIGHT);
        edgeIds.put(Direction.RIGHT, flippedEdgeIds.get(Direction.RIGHT));
        flippedEdgeIds.put(Direction.RIGHT, tmp);

        switch (direction) {
            case UP:
                direction = Direction.DOWN;
                break;
            case DOWN:
                direction = Direction.UP;
                break;
        }
        isFlipped = !isFlipped;
    }

    private void flipTileHorizontally() {
        long tmp;

        tmp = edgeIds.get(Direction.LEFT);
        edgeIds.put(Direction.LEFT, edgeIds.get(Direction.RIGHT));
        edgeIds.put(Direction.RIGHT, tmp);

        tmp = flippedEdgeIds.get(Direction.LEFT);
        flippedEdgeIds.put(Direction.LEFT, flippedEdgeIds.get(Direction.RIGHT));
        flippedEdgeIds.put(Direction.RIGHT, tmp);

        tmp = edgeIds.get(Direction.UP);
        edgeIds.put(Direction.UP, flippedEdgeIds.get(Direction.UP));
        flippedEdgeIds.put(Direction.UP, tmp);

        tmp = edgeIds.get(Direction.DOWN);
        edgeIds.put(Direction.DOWN, flippedEdgeIds.get(Direction.DOWN));
        flippedEdgeIds.put(Direction.DOWN, tmp);

        switch (direction) {
            case LEFT:
                direction = Direction.RIGHT;
                break;
            case RIGHT:
                direction = Direction.LEFT;
                break;
        }
        isFlipped = !isFlipped;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Tile ").append(id).append(":\n");
        for (int y = 0; y < values.length; y++) {
            for (int x = 0; x < values.length; x++) {
                builder.append(get(x, y));
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
