package com.vjasal.aoc2019.day18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Maze {

    private final static Logger logger = Logger.getLogger(Maze.class.getName());

    private final List<List<Character>> mazeGrid;
    private final int height;
    private final int width;

    public Maze(String mazeString) {
        this.mazeGrid = parseToGrid(mazeString);
        this.height = this.mazeGrid.size();
        this.width = this.mazeGrid.isEmpty() ? 0 : this.mazeGrid.get(0).size();
    }

    public void replaceStart() {
        for (int y = 0; y < mazeGrid.size(); y++) {
            for (int x = 0; x < mazeGrid.get(y).size(); x++) {
                if (mazeGrid.get(y).get(x) == '@') {
                    mazeGrid.get(y - 1).set(x - 1, '0');
                    mazeGrid.get(y - 1).set(x    , '#');
                    mazeGrid.get(y - 1).set(x + 1, '1');
                    mazeGrid.get(y    ).set(x - 1, '#');
                    mazeGrid.get(y    ).set(x    , '#');
                    mazeGrid.get(y    ).set(x + 1, '#');
                    mazeGrid.get(y + 1).set(x - 1, '2');
                    mazeGrid.get(y + 1).set(x    , '#');
                    mazeGrid.get(y + 1).set(x + 1, '3');
                    return;
                }
            }
        }
    }

    public char getMazeElement(int x, int y)  {
        if (y < 0 || y > this.mazeGrid.size()) {
            throw new IllegalArgumentException("Illegal y value: " + y);
        }
        if (x < 0 || x > this.mazeGrid.get(y).size()) {
            throw new IllegalArgumentException("Illegal x value: " + x);
        }
        return this.mazeGrid.get(y).get(x);
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    private List<List<Character>> parseToGrid(String mazeString) {
        List<List<Character>> maze = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(mazeString))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<Character> mazeLine = new ArrayList<>(line.length());
                line.chars().forEach(value -> mazeLine.add((char)value));
                maze.add(mazeLine);
            }
        } catch (IOException e) {
            logger.warning("Error while parsing mazeString to grid: " + e.getMessage());
        }
        return maze;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (List<Character> line : this.mazeGrid) {
            for (Character c : line) {
                builder.append(c);
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}