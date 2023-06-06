package com.vjasal.aoc2022.day09;

import com.vjasal.type.vector.Vector2;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class SnakeSegment {


    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    enum Direction {
        RIGHT, LEFT, UP, DOWN
    }

    private Vector2<Integer> position = Vector2.ZERO;
    private SnakeSegment next;

    public SnakeSegment(int length) {
        if (length > 1)
            next = new SnakeSegment(length - 1);
    }

    public void move(String direction) {
        switch (direction) {
            case "U" -> move(Direction.UP);
            case "D" -> move(Direction.DOWN);
            case "L" -> move(Direction.LEFT);
            case "R" -> move(Direction.RIGHT);
            default -> throw new IllegalArgumentException();
        }
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP -> move(0, 1);
            case DOWN -> move(0, -1);
            case LEFT -> move(-1, 0);
            case RIGHT -> move(1, 0);
        }
    }

    public void move(int dx, int dy) {
        if (next != null) next.onParentMove(position, dx, dy);
        position = Vector2.valueOf(position.val1() + dx, position.val2() + dy);
    }

    private void onParentMove(Vector2<Integer> from, int dx, int dy) {
        int diffX = from.val1() + dx - position.val1();
        int diffY = from.val2() + dy - position.val2();
        if (Math.abs(diffX) > 1 || Math.abs(diffY) > 1) {
            move(Integer.signum(diffX), Integer.signum(diffY));
        }
    }

    public Vector2<Integer> getPosition() {
        return position;
    }

    public SnakeSegment getTail() {
        return next == null ? this : next.getTail();
    }

    public List<Vector2<Integer>> getPositions() {
        List<Vector2<Integer>> positions = next == null ? new LinkedList<>() : next.getPositions();
        positions.add(position);
        return positions;
    }
}
