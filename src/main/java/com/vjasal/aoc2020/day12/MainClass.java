package com.vjasal.aoc2020.day12;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.Util;

import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    private MainClass() {
        super(2020, 12);
    }

    @Override
    public void solvePuzzle1(String input) {
        List<String> list = Util.toLinkedListOfLines(input);

        int x = 0;
        int y = 0;
        int d = 0;

        for (String line : list) {
            int value = Integer.parseInt(line.substring(1));
            switch (line.charAt(0)) {
                case 'N':
                    y += value;
                    break;
                case 'S':
                    y -= value;
                    break;
                case 'E':
                    x += value;
                    break;
                case 'W':
                    x -= value;
                    break;
                case 'L':
                    d = (d + value / 90) % 4;
                    break;
                case 'R':
                    d = (d - value / 90) % 4;
                    if (d < 0) d += 4;
                    break;
                case 'F':
                    switch (d) {
                        case 0:
                            x += value;
                            break;
                        case 1:
                            y += value;
                            break;
                        case 2:
                            x -= value;
                            break;
                        case 3:
                            y -= value;
                            break;
                    }
                    break;
            }
        }

        logger.info("Result: " + (Math.abs(x) + Math.abs(y)));
    }

    @Override
    public void solvePuzzle2(String input) {
        List<String> list = Util.toLinkedListOfLines(input);

        int x = 0;
        int y = 0;
        int wx = 10;
        int wy = 1;

        for (String line : list) {
            int value = Integer.parseInt(line.substring(1));
            switch (line.charAt(0)) {
                case 'N':
                    wy += value;
                    break;
                case 'S':
                    wy -= value;
                    break;
                case 'E':
                    wx += value;
                    break;
                case 'W':
                    wx -= value;
                    break;
                case 'L':
                    for (int k = value / 90; k > 0; k--) {
                        int tmp = wx;
                        wx = -wy;
                        wy = tmp;
                    }
                    break;
                case 'R':
                    for (int k = value / 90; k > 0; k--) {
                        int tmp = wx;
                        wx = wy;
                        wy = -tmp;
                    }
                    break;
                case 'F':
                    x += value * wx;
                    y += value * wy;
                    break;
            }
        }

        logger.info("Result: " + (Math.abs(x) + Math.abs(y)));
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}