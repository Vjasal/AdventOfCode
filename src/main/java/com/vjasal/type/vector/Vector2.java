package com.vjasal.type.vector;

import java.util.LinkedList;
import java.util.List;

public record Vector2<T>(T val1, T val2) {

    public static Vector2<Integer> ZERO  = Vector2.valueOf( 0,  0);
    public static Vector2<Integer> LEFT  = Vector2.valueOf(-1,  0);
    public static Vector2<Integer> RIGHT = Vector2.valueOf( 1,  0);
    public static Vector2<Integer> UP    = Vector2.valueOf( 0, -1);
    public static Vector2<Integer> DOWN  = Vector2.valueOf( 0,  1);

    public enum Direction {
        UP(Vector2.UP), DOWN(Vector2.DOWN), LEFT(Vector2.LEFT), RIGHT(Vector2.RIGHT);

        private final Vector2<Integer> value;

        Direction(Vector2<Integer> value) {
            this.value = value;
        }

        public Vector2<Integer> getValue() {
            return value;
        }

        public Direction toLeft() {
            return switch (this) {
                case UP -> LEFT;
                case DOWN -> RIGHT;
                case LEFT -> DOWN;
                case RIGHT -> UP;
            };
        }

        public Direction toRight() {
            return switch (this) {
                case UP -> RIGHT;
                case DOWN -> LEFT;
                case LEFT -> UP;
                case RIGHT -> DOWN;
            };
        }

        public Direction back() {
            return switch (this) {
                case UP -> DOWN;
                case DOWN -> UP;
                case LEFT -> RIGHT;
                case RIGHT -> LEFT;
            };
        }
    }


    public static <T> Vector2<T> valueOf(T val1, T val2) {
        return new Vector2<>(val1, val2);
    }

    public static Vector2<Integer> add(Vector2<Integer> v1, Vector2<Integer> v2) {
        return new Vector2<>(v1.val1 + v2.val1, v1.val2 + v2.val2);
    }

    public static Vector2<Integer> add(Vector2<Integer> v1, Vector2.Direction direction) {
        return new Vector2<>(v1.val1 + direction.value.val1, v1.val2 + direction.value.val2);
    }

    public static Vector2<Integer> add(Vector2<Integer> v1, Vector2.Direction direction, int length) {
        return new Vector2<>(v1.val1 + length * direction.value.val1, v1.val2 + length * direction.value.val2);
    }

    public static Vector2<Integer> subtract(Vector2<Integer> v1, Vector2<Integer> v2) {
        return new Vector2<>(v1.val1 - v2.val1, v1.val2 - v2.val2);
    }

    public static List<Vector2<Integer>> neighbours(Vector2<Integer> position, boolean diagonal) {
        int[] dx = { 0,  1,  0, -1,  1,  1, -1, -1};
        int[] dy = { 1,  0, -1,  0,  1, -1,  1, -1};

        List<Vector2<Integer>> neighbours = new LinkedList<>();
        for (int i = 0; i < (diagonal ? 8 : 4); i++) {
            int x = position.val1() + dx[i];
            int y = position.val2() + dy[i];
            neighbours.add(Vector2.valueOf(x, y));
        }
        return neighbours;
    }

    @Override
    public String toString() {
        return "(" + val1 + ", " + val2 + ")";
    }
}
