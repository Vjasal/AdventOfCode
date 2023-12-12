package com.vjasal.aoc2023.day10;

import com.vjasal.type.vector.Vector2;

import java.util.Arrays;
import java.util.List;

public class Pipe {
    private final String label;
    private final Vector2<Integer>[] connections;
    private final Vector2<Integer>[] toLeft;
    private final Vector2<Integer>[] toRight;
    private final Vector2<Integer> normal;

    public Pipe(String input) {
        label = input;
        switch (input) {
            case "|" -> {
                connections = new Vector2[] {Vector2.UP, Vector2.DOWN};
                toLeft = new Vector2[] {Vector2.LEFT};
                toRight = new Vector2[] {Vector2.RIGHT};
                normal = Vector2.UP;
            }
            case "-" -> {
                connections = new Vector2[] {Vector2.LEFT, Vector2.RIGHT};
                toLeft = new Vector2[] {Vector2.UP};
                toRight = new Vector2[] {Vector2.DOWN};
                normal = Vector2.RIGHT;
            }
            case "L" -> {
                connections = new Vector2[] {Vector2.RIGHT, Vector2.UP};
                toLeft = new Vector2[] {Vector2.LEFT, Vector2.DOWN, Vector2.add(Vector2.LEFT, Vector2.DOWN)};
                toRight = new Vector2[0];
                normal = Vector2.LEFT;
            }
            case "J" -> {
                connections = new Vector2[] {Vector2.LEFT, Vector2.UP};
                toLeft = new Vector2[0];
                toRight = new Vector2[] {Vector2.RIGHT, Vector2.DOWN, Vector2.add(Vector2.RIGHT, Vector2.DOWN)};
                normal = Vector2.RIGHT;
            }
            case "7" -> {
                connections = new Vector2[] {Vector2.LEFT, Vector2.DOWN};
                toLeft = new Vector2[0];
                toRight = new Vector2[] {Vector2.RIGHT, Vector2.UP, Vector2.add(Vector2.RIGHT, Vector2.UP)};
                normal = Vector2.UP;
            }
            case "F" -> {
                connections = new Vector2[] {Vector2.RIGHT, Vector2.DOWN};
                toLeft = new Vector2[] {Vector2.LEFT, Vector2.UP, Vector2.add(Vector2.LEFT, Vector2.UP)};
                toRight = new Vector2[0];
                normal = Vector2.UP;
            }
            case "S" -> {
                connections = new Vector2[] {Vector2.UP, Vector2.DOWN, Vector2.LEFT, Vector2.RIGHT};
                toLeft = new Vector2[0];
                toRight = new Vector2[0];
                normal = Vector2.ZERO;
            }
            default -> {
                connections = new Vector2[0];
                toLeft = new Vector2[0];
                toRight = new Vector2[0];
                normal = Vector2.ZERO;
            }
        }
    }

    public String getLabel() {
        return label;
    }

    public Vector2<Integer>[] getConnections() {
        return connections;
    }

    public Vector2<Integer> nextDirection(Vector2<Integer> currentDirection) {
        return Arrays.stream(connections)
                .filter(v -> !Vector2.add(v, currentDirection).equals(Vector2.ZERO))
                .findFirst()
                .orElse(Vector2.ZERO);
    }

    public List<Vector2<Integer>> toLeft(Vector2<Integer> normal) {
        if (normal.equals(this.normal)) {
            return List.of(toLeft);
        } else {
            return List.of(toRight);
        }
    }

    public List<Vector2<Integer>> toRight(Vector2<Integer> normal) {
        if (normal.equals(this.normal)) {
            return List.of(toRight);
        } else {
            return List.of(toLeft);
        }
    }
}
