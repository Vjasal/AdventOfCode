package com.vjasal.aoc2022.day14;

import com.vjasal.type.Grid;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 14);
    }

    private enum Material {
        AIR, ROCK, SAND
    }

    @Override
    public long solvePuzzle1(String input) {
        Grid<Material> grid = new Grid<>();

        // put rock
        for (String line : CollectionUtil.toLinkedList(input)) {
            int x = -1;
            int y = -1;
            for (String coordinates : line.split(" -> ")) {
                String[] c = coordinates.split(",");
                int nx = Integer.parseInt(c[0]);
                int ny = Integer.parseInt(c[1]);
                if (!(x == -1 && y == -1)) {
                    int sign = x < nx || y < ny ? 1 : -1;
                    for (int d = 0; d <= Math.abs(x - nx) + Math.abs(y - ny); d++) {
                        grid.put(x == nx ? x : x + sign * d, y == ny ? y : y + sign * d, Material.ROCK);
                    }
                }
                x = nx;
                y = ny;
            }
        }

        int maxY = grid.maxY();

        int k = 0;
        while(!grid.containsKey(500, 0)) {
            int sandX = 500;
            int sandY = 0;
            boolean canMove = true;
            k += 1;

            while (canMove) {
                if (sandY > maxY) {
                    canMove = false;
                    grid.put(sandX, sandY, Material.SAND);
                    continue;
                }
                if (!grid.containsKey(sandX, sandY + 1)) {
                    sandY += 1;
                    continue;
                }
                if (!grid.containsKey(sandX - 1, sandY + 1)) {
                    sandY += 1;
                    sandX -= 1;
                    continue;
                }
                if (!grid.containsKey(sandX + 1, sandY + 1)) {
                    sandY += 1;
                    sandX += 1;
                    continue;
                }
                canMove = false;
                grid.put(sandX, sandY, Material.SAND);
            }
        }

        logger.info("" + grid.toString(Material.AIR, m -> switch (m) {
            case AIR -> ".";
            case ROCK -> "#";
            case SAND -> "o";
        }));

        logger.info("Result: " + (k));

        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {

        return 0;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
//        input = "498,4 -> 498,6 -> 496,6\n" +
//                "503,4 -> 502,4 -> 502,9 -> 494,9";

        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
