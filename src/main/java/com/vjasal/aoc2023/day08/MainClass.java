package com.vjasal.aoc2023.day08;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;
import com.vjasal.util.MathUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2023, 8);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> sections = CollectionUtil.toListOfSections(input);

        Map<String, Node> nodes = new HashMap<>();
        for (String line : CollectionUtil.toLinkedList(sections.get(1))) {
            Node node = new Node(line);
            nodes.put(node.getName(), node);
        }

        Node current = nodes.get("AAA");
        List<String> directions = CollectionUtil.toArrayList(sections.get(0), "");
        int index = 0;
        while (!current.getName().equals("ZZZ")) {
            if (directions.get(index % directions.size()).equals("L")) {
                current = nodes.get(current.getLeft());
            } else {
                current = nodes.get(current.getRight());
            }
            index += 1;
        }
        logger.info("Result: " + index);
        return index;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> sections = CollectionUtil.toListOfSections(input);

        Map<String, Node> nodes = new HashMap<>();
        for (String line : CollectionUtil.toLinkedList(sections.get(1))) {
            Node node = new Node(line);
            nodes.put(node.getName(), node);
        }

        List<String> starts = nodes.keySet().stream().filter(name -> name.endsWith("A")).toList();
        List<String> directions = CollectionUtil.toArrayList(sections.get(0), "");
        Map<String, Long> distances = new HashMap<>();

        for (String start : starts) {
            Node current = nodes.get(start);
            int index = 0;
            while (!current.getName().endsWith("Z")) {
                if (directions.get(index % directions.size()).equals("L")) {
                    current = nodes.get(current.getLeft());
                } else {
                    current = nodes.get(current.getRight());
                }
                index += 1;
            }
            distances.put(start, (long)index);
        }

        long result = distances.values().stream().reduce(1L, MathUtil::lcm);
        logger.info("Result: " + result);
        return result;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        String test = """
                LR
                                
                11A = (11B, XXX)
                11B = (XXX, 11Z)
                11Z = (11B, XXX)
                22A = (22B, XXX)
                22B = (22C, 22C)
                22C = (22Z, 22Z)
                22Z = (22B, 22B)
                XXX = (XXX, XXX)""";

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
