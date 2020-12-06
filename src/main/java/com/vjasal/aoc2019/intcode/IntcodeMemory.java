package com.vjasal.aoc2019.intcode;

import java.io.StringReader;
import java.util.*;

public class IntcodeMemory {

    private final Map<Integer, Integer> memory;

    IntcodeMemory(String program) {
        this.memory = new HashMap<>();
        try (Scanner scanner = new Scanner(new StringReader(program))) {
            scanner.useDelimiter("[,\n]");
            int i = 0;
            while (scanner.hasNextInt()) {
                this.memory.put(i++, scanner.nextInt());
            }
        }
    }

    int get(int index) {
        Integer ret = this.memory.get(index);
        return ret == null ? 0 : ret;
    }

    void set(int index, int value) {
        this.memory.put(index, value);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",");
        int maxIndex = Collections.max(this.memory.keySet());
        for (int i = 0; i <= maxIndex; i++) {
            joiner.add(String.valueOf(this.memory.get(i)));
        }
        return joiner.toString();
    }
}
