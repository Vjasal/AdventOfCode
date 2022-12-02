package com.vjasal.aoc2021.day09;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;
import com.vjasal.util.vectors.Tuple2;

import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 9);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<List<Integer>> map = new ArrayList<>();
        for (String line : CollectionUtil.toLinkedList(input)) {
            map.add(CollectionUtil.toIntList(line));
        }

        int[] dx = { 0, -1, 0, 1};
        int[] dy = {-1,  0, 1, 0};

        long result = 0;
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                int val = map.get(y).get(x);
                boolean isLowPoint = true;

                for (int k = 0; k < 4; k++) {
                    int nx = x + dx[k];
                    int ny = y + dy[k];
                    if (ny < 0 || ny >= map.size()) continue;
                    if (nx < 0 || nx >= map.get(ny).size()) continue;

                    if (map.get(ny).get(nx) <= val) {
                        isLowPoint = false;
                        break;
                    }
                }

                if (isLowPoint) {
                    result += val + 1;
                }
            }
        }
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<List<Integer>> map = new ArrayList<>();
        for (String line : CollectionUtil.toLinkedList(input)) {
            map.add(CollectionUtil.toIntList(line));
        }

        int[] dx = { 0, -1, 0, 1};
        int[] dy = {-1,  0, 1, 0};

        Queue<Tuple2<Integer, Integer>> queue = new LinkedList<>();
        List<Integer> basins = new ArrayList<>();

        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                if (map.get(y).get(x) < 9) {
                    int size = 0;
                    queue.add(new Tuple2<>(x, y));

                    while (!queue.isEmpty()) {
                        Tuple2<Integer, Integer> v = queue.poll();
                        int vx = v.val1();
                        int vy = v.val2();
                        if (vy < 0 || vy >= map.size()) continue;
                        if (vx < 0 || vx >= map.get(vy).size()) continue;
                        if (map.get(vy).get(vx) == 9) continue;

                        for (int k = 0; k < 4; k++) {
                            queue.add(new Tuple2<>(vx + dx[k], vy + dy[k]));
                        }

                        map.get(vy).set(vx, 9);
                        size += 1;
                    }

                    basins.add(size);
                }
            }
        }

        long result = basins.stream().sorted(Comparator.reverseOrder()).limit(3).reduce(1, (a, b) -> a * b);
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
