package com.vjasal.aoc2024.day01;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2024, 1);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        CollectionUtil.toLinkedList(input).forEach(s -> {
            String[] inp = s.split(" +");
            list1.add(Integer.parseInt(inp[0]));
            list2.add(Integer.parseInt(inp[1]));
        });

        Collections.sort(list1);
        Collections.sort(list2);

        int result = 0;
        for (int i = 0; i < list1.size() && i < list2.size(); i++) {
            result += Math.abs(list1.get(i) - list2.get(i));
        }

        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        CollectionUtil.toLinkedList(input).forEach(s -> {
            String[] inp = s.split(" +");
            list1.add(Integer.parseInt(inp[0]));
            list2.add(Integer.parseInt(inp[1]));
        });

        Map<Integer, Integer> mem = new HashMap<>();

        int result = 0;
        for (int x : list1) {
            int count = mem.containsKey(x) ? mem.get(x) : (int) list2.stream().filter(a -> a == x).count();
            mem.put(x, count);
            result += x * count;
        }

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
