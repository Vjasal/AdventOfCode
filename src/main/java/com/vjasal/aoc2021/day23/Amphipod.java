package com.vjasal.aoc2021.day23;

import com.vjasal.type.tuple.Tuple2;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Amphipod {

    private static final Logger logger = Logger.getLogger(Amphipod.class.getName());

    private static final Set<Integer> singleCostNodes = new HashSet<Integer>() {{
        add(0x1);
        add(0x7);
        add(0xC);
        add(0xD);
        add(0xE);
        add(0xF);
        add(0x10);
        add(0x11);
        add(0x12);
        add(0x13);
        add(0x14);
        add(0x15);
        add(0x16);
        add(0x17);
    }};

    private final String type;
    private final int position;

    public Amphipod(String type, int position) {
        this.type = type;
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public int getPosition() {
        return position;
    }

    private int getRoomEntrance() {
        switch (type) {
            case "A": return 0x8;
            case "B": return 0x9;
            case "C": return 0xA;
            case "D": return 0xB;
            default:
                throw new IllegalStateException();
        }
    }

    private Set<Integer> getRoomPositions() {
        switch (type) {
            case "A": return new HashSet<>(Arrays.asList(0x8, 0xC, 0x10, 0x14));
            case "B": return new HashSet<>(Arrays.asList(0x9, 0xD, 0x11, 0x15));
            case "C": return new HashSet<>(Arrays.asList(0xA, 0xE, 0x12, 0x16));
            case "D": return new HashSet<>(Arrays.asList(0xB, 0xF, 0x13, 0x17));
            default:
                throw new IllegalStateException();
        }
    }

    public boolean isFinalRoom() {
        return isFinalRoom(position);
    }

    private boolean isFinalRoom(int position) {
        return getRoomPositions().contains(position);
    }

    public Optional<Tuple2<Integer, Integer>> getReachableRoom(
            Set<Amphipod> amphipods, Map<Integer, List<Integer>> graph)
    {
        Set<Integer> occupiedPositions = amphipods.stream()
                .filter(a -> a != this)
                .mapToInt(Amphipod::getPosition)
                .boxed()
                .collect(Collectors.toSet());

        Queue<Tuple2<Integer, Integer>> queue = new LinkedList<>();
        Set<Integer> seen = new HashSet<>();

        queue.add(new Tuple2<>(position, 0));
        while (!queue.isEmpty()) {
            Tuple2<Integer, Integer> v = queue.poll();
            int current = v.val1();
            int cost = v.val2();

            if (occupiedPositions.contains(current)) continue;
            if (seen.contains(current)) continue;

            if (current > 7) {
                if (current == getRoomEntrance()) {
                    int result = getRoomPositions().stream()
                            .filter(i -> !occupiedPositions.contains(i))
                            .max(Integer::compare)
                            .orElseThrow(IllegalStateException::new);
                    int resultCost = cost + (result - current) / 4 * getCostWeight();
                    return Optional.of(new Tuple2<>(result, resultCost));
                } else {
                    continue;
                }
            }

            seen.add(current);
            for (int next : graph.get(current)) {
                int nextCost = singleCostNodes.contains(current) || singleCostNodes.contains(next) ? 1 : 2;
                queue.add(new Tuple2<>(next, cost + nextCost * getCostWeight()));
            }
        }

        return Optional.empty();
    }

    public Set<Tuple2<Integer, Integer>> getReachableHallwayPositions(
            Set<Amphipod> amphipods, Map<Integer, List<Integer>> graph) {
        Set<Integer> occupiedPositions = amphipods.stream()
                .filter(a -> a != this)
                .mapToInt(Amphipod::getPosition)
                .boxed()
                .collect(Collectors.toSet());

        Set<Tuple2<Integer, Integer>> result = new HashSet<>();

        Queue<Tuple2<Integer, Integer>> queue = new LinkedList<>();
        Set<Integer> seen = new HashSet<>();

        queue.add(new Tuple2<>(position, 0));
        while (!queue.isEmpty()) {
            Tuple2<Integer, Integer> v = queue.poll();
            int current = v.val1();
            int cost = v.val2();

            if (occupiedPositions.contains(current)) continue;
            if (seen.contains(current)) continue;

            if (current <= 7) {
                result.add(v);
            }

            seen.add(current);
            for (int next : graph.get(current)) {
                if (next > 7 && next > current) continue;
                int nextCost = singleCostNodes.contains(current) || singleCostNodes.contains(next) ? 1 : 2;
                queue.add(new Tuple2<>(next, cost + nextCost * getCostWeight()));
            }
        }

        return result;
    }

    public Set<Tuple2<Integer, Integer>> getLegalMoves(Set<Amphipod> amphipods, Map<Integer, List<Integer>> graph) {
        if (position <= 7) {
            boolean canMove = amphipods.stream().filter(a -> !Objects.equals(a.type, type))
                    .noneMatch(a -> getRoomPositions().contains(a.position));

            if (canMove) {
                Optional<Tuple2<Integer, Integer>> nextPosition = getReachableRoom(amphipods, graph);
                return nextPosition.map(Collections::singleton).orElse(Collections.emptySet());
            } else {
                return Collections.emptySet();
            }
        } else {
            boolean canMove = amphipods.stream().filter(a -> !Objects.equals(a.type, type)).
                    anyMatch(a -> getRoomPositions().contains(a.position)) || !isFinalRoom();

            if (canMove) {
                return getReachableHallwayPositions(amphipods, graph);
            } else {
                return Collections.emptySet();
            }
        }
    }

    private int getCostWeight() {
        switch (type) {
            case "A": return 1;
            case "B": return 10;
            case "C": return 100;
            case "D": return 1000;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amphipod other = (Amphipod) o;
        return position == other.position && Objects.equals(type, other.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, position);
    }

    @Override
    public String toString() {
        return type;
    }
}
