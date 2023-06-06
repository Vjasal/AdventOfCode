package com.vjasal.aoc2022.day24;

import com.vjasal.type.vector.Vector2;

public record Blizzard(Vector2<Integer> position, Vector2<Integer> direction) {

    public Blizzard next(int mapWidth, int mapHeight) {
        Vector2<Integer> nextPosition = Vector2.add(position, direction);
        if (nextPosition.val1() <= 0) {
            return new Blizzard(Vector2.valueOf(mapWidth - 2, position.val2()), direction);
        }
        if (nextPosition.val1() >= mapWidth - 1) {
            return new Blizzard(Vector2.valueOf(1, position.val2()), direction);
        }
        if (nextPosition.val2() <= 0) {
            return new Blizzard(Vector2.valueOf(position.val1(), mapHeight - 2), direction);
        }
        if (nextPosition.val2() >= mapHeight - 1) {
            return new Blizzard(Vector2.valueOf(position.val1(), 1), direction);
        }
        return new Blizzard(nextPosition, direction);
    }

    @Override
    public String toString() {
        return "Blizzard{" +
                "p=" + position +
                ", d=" + direction +
                '}';
    }
}
