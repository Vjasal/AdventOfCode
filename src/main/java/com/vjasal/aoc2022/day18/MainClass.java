package com.vjasal.aoc2022.day18;

import com.vjasal.type.vector.Vector2;
import com.vjasal.type.vector.Vector3;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 18);
    }

    @Override
    public long solvePuzzle1(String input) {
        Map<Vector2<Integer>, Set<Integer>> xs = new HashMap<>();
        Map<Vector2<Integer>, Set<Integer>> ys = new HashMap<>();
        Map<Vector2<Integer>, Set<Integer>> zs = new HashMap<>();

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxZ = Integer.MIN_VALUE;

        for (String line : CollectionUtil.toLinkedList(input)) {
            String[] coordinates = line.split(",");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            int z = Integer.parseInt(coordinates[2]);
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
            minZ = Math.min(minZ, z);
            maxZ = Math.max(maxZ, z);

            if (!xs.containsKey(Vector2.valueOf(y, z))) xs.put(Vector2.valueOf(y, z), new HashSet<>());
            if (!ys.containsKey(Vector2.valueOf(x, z))) ys.put(Vector2.valueOf(x, z), new HashSet<>());
            if (!zs.containsKey(Vector2.valueOf(x, y))) zs.put(Vector2.valueOf(x, y), new HashSet<>());

            xs.get(Vector2.valueOf(y, z)).add(x);
            ys.get(Vector2.valueOf(x, z)).add(y);
            zs.get(Vector2.valueOf(x, y)).add(z);
        }

        int finalMinX = minX;
        int finalMaxX = maxX;
        int finalMinY = minY;
        int finalMaxY = maxY;
        int finalMinZ = minZ;
        int finalMaxZ = maxZ;

        int sidesX = xs.values().stream().mapToInt(set -> mapToNumberOfSides(set, finalMinX, finalMaxX)).sum();
        int sidesY = ys.values().stream().mapToInt(set -> mapToNumberOfSides(set, finalMinY, finalMaxY)).sum();
        int sidesZ = zs.values().stream().mapToInt(set -> mapToNumberOfSides(set, finalMinZ, finalMaxZ)).sum();

        logger.info("x=" + sidesX + " y=" + sidesY + " z=" + sidesZ);


        int result = sidesX + sidesY + sidesZ;
        logger.info("Result: " + result);

        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {
        Set<Vector3<Integer>> cubes = new HashSet<>();

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxZ = Integer.MIN_VALUE;

        for (String line : CollectionUtil.toLinkedList(input)) {
            String[] coordinates = line.split(",");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            int z = Integer.parseInt(coordinates[2]);
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
            minZ = Math.min(minZ, z);
            maxZ = Math.max(maxZ, z);
            cubes.add(Vector3.valueOf(x, y, z));
        }

        Set<Vector3<Integer>> invertedCubes = new HashSet<>();
        Queue<Vector3<Integer>> queue = new LinkedList<>();
        queue.add(Vector3.valueOf(minX - 1, maxY - 1, minZ - 1));

        while (!queue.isEmpty()) {
            Vector3<Integer> v = queue.poll();

            if (cubes.contains(v)) continue;
            if (invertedCubes.contains(v)) continue;
            if (v.val1() < minX - 1 || v.val1() > maxX + 1) continue;
            if (v.val2() < minY - 1 || v.val2() > maxY + 1) continue;
            if (v.val3() < minZ - 1 || v.val3() > maxZ + 1) continue;

            invertedCubes.add(v);
            queue.addAll(Vector3.neighbours(v));
        }

        Map<Vector2<Integer>, Set<Integer>> xs = new HashMap<>();
        Map<Vector2<Integer>, Set<Integer>> ys = new HashMap<>();
        Map<Vector2<Integer>, Set<Integer>> zs = new HashMap<>();

        for (Vector3<Integer> v : invertedCubes) {
            int x = v.val1();
            int y = v.val2();
            int z = v.val3();

            if (!xs.containsKey(Vector2.valueOf(y, z))) xs.put(Vector2.valueOf(y, z), new HashSet<>());
            if (!ys.containsKey(Vector2.valueOf(x, z))) ys.put(Vector2.valueOf(x, z), new HashSet<>());
            if (!zs.containsKey(Vector2.valueOf(x, y))) zs.put(Vector2.valueOf(x, y), new HashSet<>());

            xs.get(Vector2.valueOf(y, z)).add(x);
            ys.get(Vector2.valueOf(x, z)).add(y);
            zs.get(Vector2.valueOf(x, y)).add(z);
        }

        int finalMinX = minX;
        int finalMaxX = maxX;
        int finalMinY = minY;
        int finalMaxY = maxY;
        int finalMinZ = minZ;
        int finalMaxZ = maxZ;
        int sidesX = xs.values().stream().mapToInt(set -> mapToNumberOfSides(set, finalMinX, finalMaxX)).sum();
        int sidesY = ys.values().stream().mapToInt(set -> mapToNumberOfSides(set, finalMinY, finalMaxY)).sum();
        int sidesZ = zs.values().stream().mapToInt(set -> mapToNumberOfSides(set, finalMinZ, finalMaxZ)).sum();
        logger.info("x=" + sidesX + " y=" + sidesY + " z=" + sidesZ);

        int result = sidesX + sidesY + sidesZ;
        logger.info("Result: " + result);


        return 0;
    }

    private int mapToNumberOfSides(Set<Integer> set, int min, int max) {
        if (set.isEmpty()) return 0;
        int sides = 0;
        for (int k = min - 1; k <= max; k++) {
            if (set.contains(k) && !set.contains(k + 1)) sides += 1;
            if (!set.contains(k) && set.contains(k + 1)) sides += 1;
        }
        return sides;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
//        input = "2,2,2\n" +
//                "1,2,2\n" +
//                "3,2,2\n" +
//                "2,1,2\n" +
//                "2,3,2\n" +
//                "2,2,1\n" +
//                "2,2,3\n" +
//                "2,2,4\n" +
//                "2,2,6\n" +
//                "1,2,5\n" +
//                "3,2,5\n" +
//                "2,1,5\n" +
//                "2,3,5";
        logger.info("Input:\n" + input);

//        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
