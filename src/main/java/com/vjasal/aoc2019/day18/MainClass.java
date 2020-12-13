package com.vjasal.aoc2019.day18;

import com.vjasal.util.AocMainClass;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private final static Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2019, 18);
    }

    @Override
    public long solvePuzzle1(String input) {
        Maze maze = new Maze(input);
        MazeGraph graph = new MazeGraph(maze);
        int result = graph.findShortestRoute();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Maze maze = new Maze(input);
        maze.replaceStart();
        MazeGraph graph = new MazeGraph(maze);
        int result = graph.findShortestRoute2();
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