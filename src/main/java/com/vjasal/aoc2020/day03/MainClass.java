package com.vjasal.aoc2020.day03;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2020, 3);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<List<Character>> map = parseInput(input);
        int result = 0;
        int x = 0;

        for (List<Character> row : map) {
            if (row.get(x) == '#') {
                result++;
            }
            x = (x + 3) % row.size();
        }

        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<List<Character>> map = parseInput(input);
        int[] dx = {1, 3, 5, 7, 1};
        int[] dy = {1, 1, 1, 1, 2};
        int result = 1;

        for (int i = 0; i < dx.length; i++) {
            int trees = 0;
            int x = 0;

            for (int y = 0; y < map.size(); y += dy[i]) {
                List<Character> row = map.get(y);
                if (row.get(x) == '#') {
                    trees++;
                }
                x = (x + dx[i]) % row.size();
            }

            result *= trees;
        }

        logger.info("Result: " + result);
        return result;
    }

    private List<List<Character>> parseInput(String input) {
        List<List<Character>> map = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                map.add(CollectionUtil.toCharList(line));
            }
        } catch (IOException e) {
            logger.warning("Exception: " + e);
        }
        return map;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
