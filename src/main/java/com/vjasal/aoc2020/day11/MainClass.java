package com.vjasal.aoc2020.day11;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    private MainClass() {
        super(2020, 11);
    }

    @Override
    public void solvePuzzle1(String input) {
        List<List<Character>> map = new ArrayList<>();
        for (String line : Util.toLinkedListOfLines(input)) {
            map.add(Util.toCharList(line));
        }

        int result = 0;
        int taken;
        while ((taken = step1(map)) != result) {
            result = taken;
        }

        logger.info("Result: " + result);
    }

    @Override
    public void solvePuzzle2(String input) {
        List<List<Character>> map = new ArrayList<>();
        for (String line : Util.toLinkedListOfLines(input)) {
            map.add(Util.toCharList(line));
        }

        int result = 0;
        int taken;
        while ((taken = step2(map)) != result) {
            result = taken;
        }

        logger.info("Result: " + result);
    }

    private int step1(List<List<Character>> map) {
        List<List<Character>> copy = new ArrayList<>(map.size());
        for (List<Character> line : map) {
            copy.add(new ArrayList<>(line));
        }

        for (int i = 0; i < copy.size(); i++) {
            for (int j = 0; j < copy.get(i).size(); j++) {
                if (copy.get(i).get(j) == '.')
                    continue;

                int count = countAround1(copy, i, j);
                if (copy.get(i).get(j) == 'L' && count == 0)
                    map.get(i).set(j, '#');
                if (copy.get(i).get(j) == '#' && count >= 4)
                    map.get(i).set(j, 'L');
            }
        }

        return countTaken(map);
    }

    private int step2(List<List<Character>> map) {
        List<List<Character>> copy = new ArrayList<>(map.size());
        for (List<Character> line : map) {
            copy.add(new ArrayList<>(line));
        }

        for (int i = 0; i < copy.size(); i++) {
            for (int j = 0; j < copy.get(i).size(); j++) {
                if (copy.get(i).get(j) == '.')
                    continue;

                int count = countAround2(copy, i, j);
                if (copy.get(i).get(j) == 'L' && count == 0)
                    map.get(i).set(j, '#');
                if (copy.get(i).get(j) == '#' && count >= 5)
                    map.get(i).set(j, 'L');
            }
        }

        return countTaken(map);
    }

    private int countAround1(List<List<Character>> map, int i, int j) {
        int result = 0;

        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int d = 0; d < 8; d++) {
            int k = i + dx[d];
            int l = j + dy[d];

            if (k >= 0 && k < map.size() && l >= 0 && l < map.get(k).size() && map.get(k).get(l) == '#') {
                result++;
            }
        }

        return result;
    }

    private int countAround2(List<List<Character>> map, int i, int j) {
        int result = 0;

        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int d = 0; d < 8; d++) {
            int k = i + dx[d];
            int l = j + dy[d];

            while (k >= 0 && k < map.size() && l >= 0 && l < map.get(k).size() && map.get(k).get(l) == '.') {
                k += dx[d];
                l += dy[d];
            }

            if (k >= 0 && k < map.size() && l >= 0 && l < map.get(k).size()) {
                if (map.get(k).get(l) == '#')
                    result++;
            }
        }

        return result;
    }

    private int countTaken(List<List<Character>> map) {
        int result = 0;
        for (List<Character> row : map) {
            for (Character character : row) {
                if (character == '#') {
                    result++;
                }
            }
        }
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
