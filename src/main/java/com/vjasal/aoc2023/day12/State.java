package com.vjasal.aoc2023.day12;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class State {
    private final char[] ground;
    private final int groundIdx;

    private final List<Integer> groups;
    private final int groupIdx;

    private final int count;

    private final Map<State, Long> cache;

    public State(char[] ground, List<Integer> groups) {
        this(ground, 0, groups, 0, 0, new HashMap<>());
    }

    private State(char[] ground, int groundIdx, List<Integer> groups, int groupIdx, int count, Map<State, Long> cache) {
        this.ground = ground;
        this.groundIdx = groundIdx;
        this.groups = groups;
        this.groupIdx = groupIdx;
        this.count = count;
        this.cache = cache;
    }

    public long countPossibilities() {
        if (cache.containsKey(this))
            return cache.get(this);

        long ret = -1;
        if (ground.length == groundIdx) {
            if (count == 0 && groups.size() == groupIdx) {
                // valid state - all groups match
                ret = 1;
            } else {
                ret = 0;
            }
        } else {
            if (ground[groundIdx] == '.') {
                ret = stepEmpty();
            } else if (ground[groundIdx] == '#') {
                ret = stepSpring();
            } else if (ground[groundIdx] == '?') {
                ret = stepEmpty() + stepSpring();
            }
        }
        if (ret < 0) {
            throw new IllegalStateException();
        }
        cache.put(this, ret);
        return ret;
    }

    private long stepSpring() {
        if (groups.size() == groupIdx || groups.get(groupIdx) <= count) {
            // Got a spring, but there are no more groups or current group is full
            return 0;
        } else {
            return new State(ground, groundIdx + 1, groups, groupIdx, count + 1, cache).countPossibilities();
        }
    }

    private long stepEmpty() {
        if (count > 0) {
            if (groups.get(groupIdx) == count) {
                // Got empty and got correct number of springs in a group
                return new State(ground, groundIdx + 1, groups, groupIdx + 1, 0, cache).countPossibilities();
            } else {
                // Incorrect number of springs in group
                return 0;
            }
        } else {
            return new State(ground, groundIdx + 1, groups, groupIdx, 0, cache).countPossibilities();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof State other)) return false;

        return this.groundIdx == other.groundIdx &&
                this.groupIdx == other.groupIdx &&
                this.count == other.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groundIdx, groupIdx, count);
    }
}
