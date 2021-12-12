package com.vjasal.aoc2021.day12;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Cave {

    private final String name;
    private final Set<Cave> reachable = new HashSet<>();

    public Cave(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isSmall() {
        return name.chars().allMatch(Character::isLowerCase);
    }

    public void addReachable(Cave cave) {
        reachable.add(cave);
    }

    public Set<Cave> getReachable() {
        return reachable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cave cave = (Cave) o;
        return Objects.equals(name, cave.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
