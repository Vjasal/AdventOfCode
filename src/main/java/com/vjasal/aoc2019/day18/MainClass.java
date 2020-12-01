package com.vjasal.aoc2019.day18;

import com.vjasal.util.AocMainClass;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private final static Logger logger = Logger.getLogger(MainClass.class.getName());

    private MainClass() {
        super(2019, 18);
    }

    @Override
    public void solvePuzzle1(String input) {
        Maze maze = new Maze(input);
        MazeGraph graph = new MazeGraph(maze);
        logger.info("Found all keys, best dist = " + graph.findShortestRoute());
    }

    @Override
    public void solvePuzzle2(String input) {
        Maze maze = new Maze(input);
        maze.replaceStart();
        MazeGraph graph = new MazeGraph(maze);
        logger.info("Found all keys, best dist = " + graph.findShortestRoute2());
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();

        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}