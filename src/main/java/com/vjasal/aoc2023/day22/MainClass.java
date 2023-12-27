package com.vjasal.aoc2023.day22;

import com.vjasal.type.vector.Vector3;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2023, 22);
    }

    @Override
    public long solvePuzzle1(String input) {
        Map<Integer, Brick> brickMap = new HashMap<>();
        int id = 0;
        for (String line : CollectionUtil.toLinkedList(input)) {
            brickMap.put(id, new Brick(line, id));
            id += 1;
        }

        Map<Vector3<Integer>, Integer> map = new HashMap<>();
        brickMap.values().stream().sorted(Comparator.comparing(Brick::lowestZ)).forEach(brick -> {
            List<Vector3<Integer>> segments = brick.getSegments(0);
            int k = 1;
            while (true) {
                List<Vector3<Integer>> next = brick.getSegments(k);
                if (next.stream().anyMatch(map::containsKey)) {
                    next.stream().filter(map::containsKey).map(map::get).forEach(brick::addSupporter);
                    break;
                }
                int min = next.stream().mapToInt(Vector3::val3).min().orElseThrow();
                if (min <= 0)
                    break;
                segments = next;
                k += 1;
            }
            for (Vector3<Integer> segment : segments) {
                map.put(segment, brick.getId());
            }
        });


        Map<Integer, Set<Integer>> supporterMap = new HashMap<>();
        brickMap.keySet().forEach(key -> supporterMap.put(key, new HashSet<>()));
        for (Brick brick : brickMap.values()) {
            brick.getSupporters().forEach(x -> supporterMap.get(x).add(brick.getId()));
        }
        logger.info(supporterMap.toString());
        int result = 0;
        for (Map.Entry<Integer, Set<Integer>> entry : supporterMap.entrySet()) {
            if (entry.getValue().isEmpty()) {
                result += 1;
                continue;
            }
            boolean flag = entry.getValue().stream().allMatch(other -> supporterMap.entrySet().stream()
                    .filter(e -> !Objects.equals(e.getKey(), entry.getKey()))
                    .map(Map.Entry::getValue)
                    .anyMatch(set -> set.contains(other)));
            if (flag)
                result += 1;
        }

        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Map<Integer, Brick> brickMap = new HashMap<>();
        int id = 0;
        for (String line : CollectionUtil.toLinkedList(input)) {
            brickMap.put(id, new Brick(line, id));
            id += 1;
        }

        Map<Vector3<Integer>, Integer> map = new HashMap<>();
        brickMap.values().stream().sorted(Comparator.comparing(Brick::lowestZ)).forEach(brick -> {
            List<Vector3<Integer>> segments = brick.getSegments(0);
            int k = 1;
            while (true) {
                List<Vector3<Integer>> next = brick.getSegments(k);
                if (next.stream().anyMatch(map::containsKey)) {
                    next.stream().filter(map::containsKey).map(map::get).forEach(brick::addSupporter);
                    break;
                }
                int min = next.stream().mapToInt(Vector3::val3).min().orElseThrow();
                if (min <= 0) {
                    brick.addSupporter(-1);
                    break;
                }
                segments = next;
                k += 1;
            }
            for (Vector3<Integer> segment : segments) {
                map.put(segment, brick.getId());
            }
        });
        for (Brick brick : brickMap.values()) {
            brick.getSupporters().stream()
                    .filter(brickMap::containsKey)
                    .forEach(x -> brickMap.get(x).addSupporting(brick.getId()));
        }

        int result = 0;
        for (Brick brick : brickMap.values()) {
            Set<Integer> removed = new HashSet<>();
            removed.add(brick.getId());
            boolean flag = true;
            while (flag) {
                Set<Integer> next = brickMap.entrySet().stream()
                        .filter(e -> !removed.contains(e.getKey()))
                        .map(Map.Entry::getValue)
                        .filter(b -> removed.containsAll(b.getSupporters()))
                        .map(Brick::getId)
                        .collect(Collectors.toSet());
                removed.addAll(next);
                flag = !next.isEmpty();
            }
            result += removed.size() - 1;
        }
        logger.info("Result: " + result);
        return 0;
    }

    private void countFalling(Map<Integer, Brick> brickMap, Set<Integer> removed) {

    }


    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        String test = """
                1,0,1~1,2,1
                0,0,2~2,0,2
                0,2,3~2,2,3
                0,0,4~0,2,4
                2,0,5~2,2,5
                0,1,6~2,1,6
                1,1,8~1,1,9""";

//        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
