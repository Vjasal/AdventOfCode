package com.vjasal.aoc2021.day15;

import com.vjasal.type.tuple.Tuple2;

import java.util.Objects;

public class Path implements Comparable<Path> {

    private final Tuple2<Integer, Integer> position;
    private final int riskLevel;

    public Path(Tuple2<Integer, Integer> position, int riskLevel) {
        this.position = position;
        this.riskLevel = riskLevel;
    }

    public Tuple2<Integer, Integer> getPosition() {
        return position;
    }

    public int getRiskLevel() {
        return riskLevel;
    }

    @Override
    public int compareTo(Path path) {
        return Integer.compare(riskLevel, path.riskLevel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return Objects.equals(position, path.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
