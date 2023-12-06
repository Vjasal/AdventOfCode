package com.vjasal.aoc2023.day05;

import com.vjasal.type.vector.Vector2;

import java.util.*;
import java.util.stream.IntStream;

public class Permutation {

    private final List<Long> destinationList = new ArrayList<>();
    private final List<Long> sourceList = new ArrayList<>();
    private final List<Long> rangeList = new ArrayList<>();

    public void add(long destination, long source, long range) {
        destinationList.add(destination);
        sourceList.add(source);
        rangeList.add(range);
    }

    public long map(long value) {
        for (int i = 0; i < sourceList.size(); i++) {
            if (isIntersecting(value, sourceList.get(i), rangeList.get(i))) {
                long ret = value - sourceList.get(i) + destinationList.get(i);
                return ret;
            }
        }
        return value;
    }

    public List<Vector2<Long>> mapRange(long start, long range) {
        List<Integer> intersecting = IntStream.range(0, sourceList.size())
                .filter(i -> isIntersecting(start, sourceList.get(i), rangeList.get(i)) ||
                        isIntersecting(sourceList.get(i), start, range))
                .boxed()
                .sorted((i1, i2) -> sourceList.get(i1).compareTo(sourceList.get(i2)))
                .toList();

        List<Vector2<Long>> result = new LinkedList<>();
        long current = start;
        for (int i : intersecting) {
            if (current < sourceList.get(i)) {
                result.add(Vector2.valueOf(current, sourceList.get(i) - current));
                current = sourceList.get(i);
            }
            long len = Math.min(rangeList.get(i) + sourceList.get(i), range + start) - current;
            if (len <= 0) continue;
            result.add(Vector2.valueOf(current - sourceList.get(i) + destinationList.get(i), len));
            current += len;
        }
        if (start + range - current > 0) {
            result.add(Vector2.valueOf(current, start + range - current));
        }
        return result;
    }

    private boolean isIntersecting(long value, long start, long range) {
        return value >= start && value <= start + range;
    }
}
