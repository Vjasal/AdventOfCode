package com.vjasal.aoc2021.day14;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Polymer {

    private Map<String, Long> bucket;

    public Polymer(String template) {
        bucket = new LinkedHashMap<>();
        for (int i = 0; i < template.length() - 1; i++) {
            String pair = template.substring(i, i + 2);
            if (bucket.containsKey(pair)) {
                bucket.put(pair, bucket.get(pair) + 1);
            } else {
                bucket.put(pair, 1L);
            }
        }
    }

    public void step(Map<String, Character> insertionRules) {
        Map<String, Long> newBucket = new LinkedHashMap<>();

        for (String key : bucket.keySet()) {
            char c = insertionRules.get(key);
            String first  = "" + key.charAt(0) + c;
            String second = "" + c + key.charAt(1);

            if (newBucket.containsKey(first)) {
                newBucket.put(first, newBucket.get(first) + bucket.get(key));
            } else {
                newBucket.put(first, bucket.get(key));
            }

            if (newBucket.containsKey(second)) {
                newBucket.put(second, newBucket.get(second) + bucket.get(key));
            } else {
                newBucket.put(second, bucket.get(key));
            }
        }

        bucket = newBucket;
    }

    public Map<Character, Long> countElements() {
        Map<Character, Long> elementCounts = new HashMap<>();

        for (Map.Entry<String, Long> entry : bucket.entrySet()) {
            char[] chars = entry.getKey().toCharArray();

            if (elementCounts.isEmpty()) {
                elementCounts.put(chars[0], 1L);
            }

            if (elementCounts.containsKey(chars[1])) {
                elementCounts.put(chars[1], elementCounts.get(chars[1]) + entry.getValue());
            } else {
                elementCounts.put(chars[1], entry.getValue());
            }
        }

        return elementCounts;
    }
}
