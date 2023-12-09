package com.vjasal.aoc2023.day07;

import com.vjasal.aoc2019.day18.MazeGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hand implements Comparable {
    private static final Pattern pattern = Pattern.compile("^(\\w{5}) (\\d+)$");

    enum Type {
        FIVE_OF_KIND(6),
        FOUR_OF_KIND(5),
        FULL_HOUSE(4),
        THREE_OF_KIND(3),
        TWO_PAIR(2),
        ONE_PAIR(1),
        HIGH_CARD(0);

        private final int value;
        Type(int value) {
            this.value = value;
        }
    }

    private final int[] hand = new int[5];
    private final Type type;
    private long value;

    Hand(String input) {
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find())
            throw new IllegalArgumentException("Wrong input format!");

        String handVal = matcher.group(1);
        value = Long.parseLong(matcher.group(2));

        int i = 0;
        for (String s : handVal.split("")) {
            s = s.replace("T", "10")
                    .replace("J", "11")
                    .replace("Q", "12")
                    .replace("K", "13")
                    .replace("A", "14");
            hand[i++] = Integer.parseInt(s);
        }

        Map<Integer, Integer> s = new HashMap<>();
        for (int x : hand) s.put(x, s.getOrDefault(x, 0) + 1);
        int maxCount = s.values().stream().max(Integer::compare).orElse(0);
        switch (s.size()) {
            case 1 -> type = Type.FIVE_OF_KIND;
            case 2 -> type = maxCount == 4 ? Type.FOUR_OF_KIND : Type.FULL_HOUSE;
            case 3 -> type = maxCount == 3 ? Type.THREE_OF_KIND : Type.TWO_PAIR;
            case 4 -> type = Type.ONE_PAIR;
            default -> type = Type.HIGH_CARD;
        }
    }

    public long getValue() {
        return value;
    }

    @Override
    public int compareTo(Object obj) {
        if (this == obj) return 0;
        if (!(obj instanceof Hand other))
            throw new ClassCastException();

        if (this.type != other.type) {
            return Integer.compare(this.type.value, other.type.value);
        }

        int i = 0;
        while (i < hand.length) {
            if (this.hand[i] != other.hand[i])
                return Integer.compare(this.hand[i], other.hand[i]);
            i++;
        }

        return 0;
    }
}
