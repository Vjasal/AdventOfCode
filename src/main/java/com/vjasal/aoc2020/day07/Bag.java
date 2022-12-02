package com.vjasal.aoc2020.day07;

import com.vjasal.util.vectors.Tuple2;

import java.util.HashMap;

class Bag {

    private final HashMap<String, Tuple2<Bag, Integer>> content = new HashMap<>();
    private final String name;

    Bag(String name) {
        this.name = name;
    }

    void addBag(Bag other, int count) {
        content.put(other.name, new Tuple2<>(other, count));
    }

    boolean containsBag(String name) {
        if (content.containsKey(name)) {
            return true;
        } else {
            return content.values().stream().anyMatch(pair -> pair.val1().containsBag(name));
        }
    }

    int countBags() {
        return content.values().stream().mapToInt(pair -> pair.val1().countBags() * pair.val2()).sum() + 1;
    }
}
