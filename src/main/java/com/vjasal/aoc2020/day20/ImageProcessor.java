package com.vjasal.aoc2020.day20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ImageProcessor {

    private static final Logger logger = Logger.getLogger(ImageProcessor.class.getSimpleName());

    private enum Direction {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }

    private static final String seaMonsterPattern =
            "                  # \n" +
            "#    ##    ##    ###\n" +
            " #  #  #  #  #  #   ";

    private final char[][] image;
    private final List<List<Vector2<Integer, Integer>>> patterns;

    public ImageProcessor(char[][] image) {
        this.image = image;
        this.patterns = new ArrayList<>(8);
        fillPatterns();
    }

    public long countWater() {
        for (List<Vector2<Integer, Integer>> pattern : patterns) {
            if (removeSeaMonsterTiles(pattern) > 0) {
                break;
            }
        }
        return Stream.of(image).mapToLong(line -> IntStream.range(0, line.length)
                .mapToObj(i -> line[i]).filter(c -> c == '#').count())
                .sum();
    }

    private int removeSeaMonsterTiles(List<Vector2<Integer, Integer>> pattern) {
        int removed = 0;
        for (int y = 0; y < image.length; y++) {
            for (int x = 0; x < image[y].length; x++) {
                int finalX = x;
                int finalY = y;
                boolean match = pattern.stream().allMatch(v -> {
                    int tmpX = finalX + v.getValue1();
                    int tmpY = finalY + v.getValue2();

                    if (tmpY >= image.length) return false;
                    if (tmpX >= image[tmpY].length) return false;
                    return image[tmpY][tmpX] == '#';
                });
                if (match) {
                    for (Vector2<Integer, Integer> v : pattern) {
                        image[y + v.getValue2()][x + v.getValue1()] = 'O';
                    }
                    removed++;
                }
            }
        }
        return removed;
    }

    private void fillPatterns() {
        char[][] pattern = Arrays.stream(seaMonsterPattern.split("\n"))
                .map(String::toCharArray)
                .toArray(char[][]::new);

        for (Direction direction : Direction.values()) {
            patterns.add(getPatternCoordinates(pattern, direction, false));
            patterns.add(getPatternCoordinates(pattern, direction, true));
        }
    }

    private List<Vector2<Integer, Integer>> getPatternCoordinates(char[][] pattern, Direction direction, boolean isFlipped) {
        List<Vector2<Integer, Integer>> result = new LinkedList<>();
        for (int y = 0; y < getHeight(pattern, direction); y++) {
            for (int x = 0; x < getWidth(pattern, direction); x++) {
                if (get(pattern, x, y, direction, isFlipped) == '#') {
                    result.add(new Vector2<>(x, y));
                }
            }
        }
        return result;
    }

    private char get(char[][] pattern, int x, int y, Direction direction, boolean isFlipped) {
        int w = getWidth(pattern, direction);
        int h = getHeight(pattern, direction);

        if (x < 0 || x >= w)  throw new IllegalArgumentException("Illegal x arg");
        if (y < 0 || y >= h) throw new IllegalArgumentException("Illegal y arg");

        switch (direction) {
            case UP:
                return isFlipped ? pattern[y][w - x - 1] : pattern[y][x];
            case DOWN:
                return isFlipped ? pattern[pattern.length - y - 1][x] : pattern[h - y - 1][w - x - 1];
            case LEFT:
                return isFlipped ? pattern[x][y] : pattern[w - x - 1][y];
            case RIGHT:
                return isFlipped ? pattern[w - x - 1][h - y - 1] : pattern[x][h - y - 1];
            default:
                throw new IllegalStateException();
        }
    }

    private int getWidth(char[][] pattern, Direction direction) {
        if (direction == Direction.UP || direction == Direction.DOWN) {
            return pattern[0].length;
        } else {
            return pattern.length;
        }
    }

    private int getHeight(char[][] pattern, Direction direction) {
        if (direction == Direction.UP || direction == Direction.DOWN) {
            return pattern.length;
        } else {
            return pattern[0].length;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("\n");
        for (char[] line : image) {
            for (char c : line) {
                builder.append(c);
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
