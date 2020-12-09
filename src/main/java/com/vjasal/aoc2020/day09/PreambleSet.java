package com.vjasal.aoc2020.day09;

import java.util.Iterator;
import java.util.LinkedHashSet;

public class PreambleSet extends LinkedHashSet<Long> {

    private final int maxSize;

    PreambleSet(int maxSize) {
        this.maxSize = maxSize;
    }

    int getMaxSize() {
        return maxSize;
    }

    @Override
    public boolean add(Long value) {
        if (size() == maxSize) {
            removeFirst();
        }
        return super.add(value);
    }

    boolean containsSum(Long sum) {
        for (long element : this) {
            if (element != sum - element && contains(sum - element)) {
                return true;
            }
        }
        return false;
    }

    private void removeFirst() {
        Iterator<Long> iterator = iterator();
        iterator.next();
        iterator.remove();
    }
}
