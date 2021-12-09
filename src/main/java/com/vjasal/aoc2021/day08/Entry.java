package com.vjasal.aoc2021.day08;

import com.vjasal.util.CollectionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Entry {

    private static final Pattern pattern = Pattern.compile("^(.+) \\| (.+)$");

    private static final Map<Integer, Integer> sumMap;
    static {
        sumMap = new HashMap<>(10);
        sumMap.put(42, 0);
        sumMap.put(17, 1);
        sumMap.put(34, 2);
        sumMap.put(39, 3);
        sumMap.put(30, 4);
        sumMap.put(37, 5);
        sumMap.put(41, 6);
        sumMap.put(25, 7);
        sumMap.put(49, 8);
        sumMap.put(45, 9);
    }

    private final List<String> input;
    private final List<String> output;

    public Entry(String line) {
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Bad input");
        }

        input  = CollectionUtil.toLinkedList(matcher.group(1), " ");
        output = CollectionUtil.toLinkedList(matcher.group(2), " ");
    }

    public long countSimpleOutputDigits() {
        // 1, 4, 7, 8 have 2, 4, 3, 7 segments
        return output.stream()
                .filter(s -> s.length() == 2 || s.length() == 4 || s.length() == 3 || s.length() == 7)
                .count();
    }

    public long value() {
        Map<Digit, Integer> decodedDigits = decodeInput();
        return output.stream().map(Digit::new).mapToInt(decodedDigits::get).reduce(0, (a, b) -> a * 10 + b);
    }

    // Credit goes to
    // https://www.reddit.com/r/adventofcode/comments/rc5s3z/2021_day_8_part_2_a_simple_fast_and_deterministic/
    public Map<Digit, Integer> decodeInput() {
        // Count the number of times each character occurs
        Map<Character, Integer> segmentValues = new HashMap<>(7);
        for (String encodedDigit : input) {
            for (char encodedSegment : encodedDigit.toCharArray()) {
                if (!segmentValues.containsKey(encodedSegment)) {
                    segmentValues.put(encodedSegment, 1);
                } else {
                    segmentValues.put(encodedSegment, segmentValues.get(encodedSegment) + 1);
                }
            }
        }

        // For each digit in the output, add up the scores for each character contained in that digit
        Map<Digit, Integer> digitValues = new HashMap<>();
        for (String encodedDigit : input) {
            int value = 0;
            for (char encodedSegment : encodedDigit.toCharArray()) {
                value += segmentValues.get(encodedSegment);
            }
            digitValues.put(new Digit(encodedDigit), value);
        }

        // Look up the sum in a map of the sums you calculated earlier to find out which digit yields that sum
        Map<Digit, Integer> decodedDigits = new HashMap<>();
        for (Map.Entry<Digit, Integer> digitValue : digitValues.entrySet()) {
            decodedDigits.put(digitValue.getKey(), sumMap.get(digitValue.getValue()));
        }

        return decodedDigits;
    }
}
