package com.vjasal.aoc2021.day19;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;
import com.vjasal.util.vectors.Tuple2;
import com.vjasal.util.vectors.Tuple3;

import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 19);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<Scanner> scanners = CollectionUtil.toListOfSections(input).stream()
                .map(Scanner::new)
                .toList();

        Queue<Scanner> queue = new LinkedList<>(scanners.stream().skip(1).toList());
        Scanner origin = scanners.get(0);

        while (!queue.isEmpty()) {
            Scanner current = queue.poll();
            Tuple2<Integer, Tuple3<Integer, Integer, Integer>> rotationAndTransform = origin.getRotation(current);

            if (rotationAndTransform.val1() == -1) {
                queue.add(current);
                continue;
            }

            origin.addMissingBeacons(current, rotationAndTransform.val1(), rotationAndTransform.val2());
        }

        int result = origin.getBeacons().size();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<Scanner> scanners = CollectionUtil.toListOfSections(input).stream()
                .map(Scanner::new)
                .toList();

        Queue<Scanner> queue = new LinkedList<>(scanners.stream().skip(1).toList());
        Scanner origin = scanners.get(0);

        List<Tuple3<Integer, Integer, Integer>> positions = new ArrayList<>();
        positions.add(Tuple3.ZERO);

        while (!queue.isEmpty()) {
            Scanner current = queue.poll();
            Tuple2<Integer, Tuple3<Integer, Integer, Integer>> rotationAndTransform = origin.getRotation(current);

            if (rotationAndTransform.val1() == -1) {
                queue.add(current);
                continue;
            }

            positions.add(rotationAndTransform.val2());
            origin.addMissingBeacons(current, rotationAndTransform.val1(), rotationAndTransform.val2());
        }

        Set<Integer> distances = new HashSet<>();
        for (int i = 0; i < positions.size(); i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                int dx = Math.abs(positions.get(i).val1() - positions.get(j).val1());
                int dy = Math.abs(positions.get(i).val2() - positions.get(j).val2());
                int dz = Math.abs(positions.get(i).val3() - positions.get(j).val3());
                distances.add(dx + dy + dz);
            }
        }

        int result = distances.stream().max(Integer::compare).orElse(0);
        logger.info("Result: " + result);
        return result;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
