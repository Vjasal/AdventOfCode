package com.vjasal.aoc2022.day09;

import com.vjasal.type.vector.Vector2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 9);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String[]> instructions = CollectionUtil.toLinkedList(input).stream().map(s -> s.split(" ")).toList();

        SnakeSegment head = new SnakeSegment(2);
        SnakeSegment tail = head.getTail();

        Set<Vector2<Integer>> tailPositions = new HashSet<>();
        for (String[] in : instructions) {
            for (int k = 0; k < Integer.parseInt(in[1]); k++) {
                head.move(in[0]);
                tailPositions.add(tail.getPosition());
            }
        }

        logger.info("Result: " + tailPositions.size());
        return tailPositions.size();
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String[]> instructions = CollectionUtil.toLinkedList(input).stream().map(s -> s.split(" ")).toList();

        SnakeSegment head = new SnakeSegment(10);
        SnakeSegment tail = head.getTail();

        Set<Vector2<Integer>> tailPositions = new HashSet<>();
        for (String[] in : instructions) {
            for (int k = 0; k < Integer.parseInt(in[1]); k++) {
                head.move(in[0]);
                tailPositions.add(tail.getPosition());
            }
        }

        logger.info("Result: " + tailPositions.size());
        return tailPositions.size();
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
