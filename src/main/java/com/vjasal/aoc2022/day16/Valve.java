package com.vjasal.aoc2022.day16;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Valve {

    private static final Pattern pattern = Pattern.compile("^Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? ([\\w, ]+)$");

    private final String id;
    private final int rate;
    private final int index;

    private final Map<String, Integer> routes = new HashMap<>();

    public Valve(String input, int index) {
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) throw new IllegalArgumentException();

        this.id = matcher.group(1);
        this.rate = Integer.parseInt(matcher.group(2));
        this.index = index;

        this.routes.put(id, 0);
        for (String next : matcher.group(3).split(", ")) {
            this.routes.put(next, 1);
        }
    }

    public void setRoute(String next, int value) {
        routes.put(next, value);
    }

    public Map<String, Integer> getRoutes() {
        return routes;
    }

    public String getId() {
        return id;
    }

    public int getRate() {
        return rate;
    }

    public int getIndex() {
        return index;
    }

    public long getBitmask() {
        return 1L << index;
    }

    @Override
    public String toString() {
        return "{" + id + ", " + routes + "}";
    }
}
