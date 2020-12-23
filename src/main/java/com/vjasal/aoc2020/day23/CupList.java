package com.vjasal.aoc2020.day23;

import com.vjasal.util.CollectionUtil;

import java.util.*;

public class CupList {

    private Map<Integer, Cup> cupMap = new HashMap<>();
    private Cup head = null;

    CupList(String input) {
        for (int value : CollectionUtil.toIntList(input)) {
            add(value);
        }
    }

    CupList(String input, int size) {
        for (int value : CollectionUtil.toIntList(input)) {
            add(value);
        }
        while (cupMap.size() < size) {
            add(cupMap.size() + 1);
        }
    }

    private void add(int value) {
        Cup newCup = new Cup(value);
        cupMap.put(value, newCup);
        if (head == null) {
            newCup.next = newCup;
            newCup.prev = newCup;
            head = newCup;
        } else {
            newCup.next = head;
            newCup.prev = head.prev;
            head.prev.next = newCup;
            head.prev = newCup;
        }
    }

    long getResult1() {
        Cup current = cupMap.get(1).next;
        StringBuilder builder = new StringBuilder();
        while (current != cupMap.get(1)) {
            builder.append(current.value);
            current = current.next;
        }
        return Long.parseLong(builder.toString());
    }

    long getResult2() {
        Cup current = cupMap.get(1).next;
        long result = 1;
        for (int i = 0; i < 2; i++) {
            result *= current.value;
            current = current.next;
        }
        return result;
    }

    void round() {
        // pick up 3 cups that are clockwise (next) to head
        Set<Cup> pickedUp = new LinkedHashSet<>(3);
        for (int i = 0; i < 3; i++) {
            pickedUp.add(removeNextFrom(head));
        }

        // get destination cup
        int destinationValue = head.value;
        do {
            destinationValue -= 1;
            if (destinationValue == 0) destinationValue += cupMap.size();
        } while (pickedUp.contains(cupMap.get(destinationValue)));

        // put picked up cups clockwise (next) from destination
        Cup destination = cupMap.get(destinationValue);
        for (Cup cup : pickedUp) {
            insertNextTo(destination, cup);
            destination = destination.next;
        }

        // move clockwise (next)
        head = head.next;
    }

    private Cup removeNextFrom(Cup current) {
        Cup result = current.next;

        current.next = result.next;
        current.next.prev = current;

        result.next = null;
        result.prev = null;

        return result;
    }

    private void insertNextTo(Cup current, Cup toInsert) {
        toInsert.next = current.next;
        toInsert.prev = current;

        current.next.prev = toInsert;
        current.next = toInsert;
    }

    private class Cup {
        private final int value;
        private Cup prev;
        private Cup next;

        private Cup(int value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Cup)) return false;
            Cup other = (Cup) obj;
            return value == other.value;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Cup current = head;
        do {
            builder.append(current.value).append(" ");
            current = current.next;
        } while (current != head);
        return builder.toString();
    }
}
