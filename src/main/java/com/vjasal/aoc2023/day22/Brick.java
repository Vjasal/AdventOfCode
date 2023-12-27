package com.vjasal.aoc2023.day22;

import com.vjasal.type.vector.Vector3;

import java.util.*;
import java.util.stream.IntStream;

public class Brick {
    enum Direction {
        DIRECTION_X, DIRECTION_Y, DIRECTION_Z
    }

    private final Vector3<Integer> start;
    private final Vector3<Integer> end;
    private final Direction direction;
    private final int id;
    private final Set<Integer> supporters = new HashSet<>();
    private final Set<Integer> supporting = new HashSet<>();

    public Brick(String input, int id) {
        int[] values = Arrays.stream(input.split("[,~]")).mapToInt(Integer::parseInt).toArray();
        start = Vector3.valueOf(values[0], values[1], values[2]);
        end = Vector3.valueOf(values[3], values[4], values[5]);
        if (!Objects.equals(start.val1(), end.val1()))
            direction = Direction.DIRECTION_X;
        else if (!Objects.equals(start.val2(), end.val2()))
            direction = Direction.DIRECTION_Y;
        else
            direction = Direction.DIRECTION_Z;
        this.id = id;
    }

    public int lowestZ() {
        return Math.min(start.val3(), end.val3());
    }

    public int getId() {
        return id;
    }

    public Set<Integer> getSupporters() {
        return supporters;
    }

    public Set<Integer> getSupporting() {
        return supporting;
    }

    public List<Vector3<Integer>> getSegments(int k) {
        return switch (direction) {
            case DIRECTION_X -> {
                int min = Math.min(start.val1(), end.val1());
                int max = Math.max(start.val1(), end.val1());
                yield IntStream.range(min, max + 1)
                        .boxed()
                        .map(x -> Vector3.valueOf(x, start.val2(), start.val3() - k))
                        .toList();
            }
            case DIRECTION_Y -> {
                int min = Math.min(start.val2(), end.val2());
                int max = Math.max(start.val2(), end.val2());
                yield IntStream.range(min, max + 1)
                        .boxed()
                        .map(x -> Vector3.valueOf(start.val1(), x, start.val3() - k))
                        .toList();
            }
            case DIRECTION_Z -> {
                int min = Math.min(start.val3(), end.val3());
                int max = Math.max(start.val3(), end.val3());
                yield IntStream.range(min, max + 1)
                        .boxed()
                        .map(x -> Vector3.valueOf(start.val1(), start.val2(), x - k))
                        .toList();
            }
        };
    }

    public void addSupporter(int id) {
        supporters.add(id);
    }

    public void addSupporting(int id) {
        supporting.add(id);
    }
}
