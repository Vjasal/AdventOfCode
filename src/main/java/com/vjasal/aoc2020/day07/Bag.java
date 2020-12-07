package com.vjasal.aoc2020.day07;

import javafx.util.Pair;

import java.util.HashMap;

class Bag {

    private final HashMap<String, Pair<Bag, Integer>> content = new HashMap<>();
    private final String name;

    Bag(String name) {
        this.name = name;
    }

    void addBag(Bag other, int count) {
        content.put(other.name, new Pair<>(other, count));
    }

    boolean containsBag(String name) {
        if (content.containsKey(name)) {
            return true;
        } else {
            return content.values().stream().anyMatch(pair -> pair.getKey().containsBag(name));
        }
    }

    int countBags() {
        return content.values().stream().mapToInt(pair -> pair.getKey().countBags() * pair.getValue()).sum() + 1;
    }
}
