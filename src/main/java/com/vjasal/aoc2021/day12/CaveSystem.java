package com.vjasal.aoc2021.day12;

import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaveSystem {

    private static final Pattern pattern = Pattern.compile("^(\\w+)-(\\w+)$");

    private final Map<String, Cave> caves = new HashMap<>();

    public CaveSystem(String input) {
        for (String line : CollectionUtil.toLinkedList(input)) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) throw new IllegalArgumentException("Wrong input format");
            addCaveConnection(matcher.group(1), matcher.group(2));
        }
    }

    public int countPaths(boolean withRepetition) {
        Queue<LinkedList<Cave>> queue = new LinkedList<>();

        LinkedList<Cave> startPath = new LinkedList<>();
        startPath.add(caves.get("start"));
        queue.add(startPath);

        int result = 0;
        while (!queue.isEmpty()) {
            LinkedList<Cave> currentPath = queue.poll();
            Cave currentCave = currentPath.getLast();

            if (currentCave.getName().equals("end")) {
                result += 1;
                continue;
            }

            for (Cave next : currentCave.getReachable()) {
                if (next.getName().equals("start")) continue;

                if (next.isSmall() && currentPath.contains(next)) {
                    if (!withRepetition) {
                        // Part 1 - small caves can be visited only once
                        continue;
                    } else {
                        // Part 2 - a single small cave can be visited twice
                        Set<Cave> seen = new HashSet<>();
                        if (currentPath.stream().filter(Cave::isSmall).anyMatch(c -> !seen.add(c))) {
                            continue;
                        }
                    }
                }

                LinkedList<Cave> nextPath = new LinkedList<>(currentPath);
                nextPath.add(next);
                queue.add(nextPath);
            }
        }

        return result;
    }

    private void addCaveConnection(String cave1, String cave2) {
        if (!caves.containsKey(cave1)) {
            caves.put(cave1, new Cave(cave1));
        }
        if (!caves.containsKey(cave2)) {
            caves.put(cave2, new Cave(cave2));
        }
        caves.get(cave1).addReachable(caves.get(cave2));
        caves.get(cave2).addReachable(caves.get(cave1));
    }
}
