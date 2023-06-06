package com.vjasal.aoc2022.day13;

import java.util.ArrayList;
import java.util.List;

public record Packet(int rawValue, List<Packet> values) {

    public static Packet parse(String input) {
        List<Packet> values = new ArrayList<>();

        // strip braces
        input = input.substring(1, input.length() - 1);

        int position = 0;
        while (position < input.length()) {
            int n = position + 1;
            if (input.charAt(position) == '[') {
                // current value is a list -> get index of ',' after closing bracket (ignore nested brackets)
                int k = 1;
                while (n < input.length() && k > 0) {
                    if (input.charAt(n) == '[') k += 1;
                    if (input.charAt(n) == ']') k -= 1;
                    n += 1;
                }
                // convert list to packet
                String value = input.substring(position, n);
                values.add(Packet.parse(value));
            } else {
                // current value is a number -> get index of ',' after this number
                while (n < input.length() && input.charAt(n) != ',') {
                    n += 1;
                }
                // convert integer to packet
                int value = Integer.parseInt(input.substring(position, n));
                values.add(Packet.valueOf(value));
            }
            position = n + 1;
        }

        return new Packet(0, values);
    }

    private static Packet valueOf(int rawValue) {
        return new Packet(rawValue, null);
    }

    public static int compare(Packet p1, Packet p2) {
        // both values are integers
        if (p1.values == null && p2.values == null) {
            return Integer.compare(p1.rawValue, p2.rawValue);
        }

        // convert both values to lists if needed
        p1 = p1.values != null ? p1 : new Packet(0, List.of(p1));
        p2 = p2.values != null ? p2 : new Packet(0, List.of(p2));

        // assert values != null to disable warnings
        if (p1.values == null || p2.values == null) throw new IllegalStateException("Assertion error");

        // compare values in lists
        for (int i = 0; i < p1.values.size() && i < p2.values.size(); i++) {
            int status = compare(p1.values.get(i), p2.values.get(i));
            if (status != 0) return status;
        }

        // check whether one list run out of items
        return Integer.compare(p1.values.size(), p2.values.size());
    }

    @Override
    public String toString() {
        if (values == null) return String.valueOf(rawValue);
        return values.toString();
    }
}
