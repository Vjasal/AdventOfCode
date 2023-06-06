package com.vjasal.aoc2021.day04;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 4);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> a = CollectionUtil.toListOfSections(input);
        int last = 0;

        List<String> inp = Arrays.stream(a.remove(0).split(",")).collect(Collectors.toList());

        List<Board> boards = new ArrayList<>();

        for (String section : a) {
            boards.add(new Board(section));
        }

        for (String n : inp) {
            boards.forEach(board -> board.markNumber(Integer.parseInt(n)));
            logger.info("i=" + n);
            if (boards.stream().anyMatch(Board::isWin)) {
                last = Integer.parseInt(n);
                break;
            }
        }

        Board winning = boards.stream().filter(Board::isWin).findFirst().orElseThrow(IllegalStateException::new);

//        for (Board b : boards) {
//            b.markNumber(7);
//            logger.info(b.toString());
//        }
//
//        logger.info("w=" + winning.toString() + " " + winning.sumUnmarked() + " " + last);

        logger.info("Result: " + (winning.sumUnmarked() * last));



        return (winning.sumUnmarked() * last);
    }

    @Override
    public long solvePuzzle2(String input) {

        List<String> a = CollectionUtil.toListOfSections(input);
        int last = 0;

        List<String> inp = Arrays.stream(a.remove(0).split(",")).collect(Collectors.toList());

        List<Board> boards = new ArrayList<>();
        List<Board> prev = null;

        for (String section : a) {
            boards.add(new Board(section));
        }

        for (String n : inp) {
            prev = boards.stream().filter(board -> !board.isWin()).collect(Collectors.toList());
            boards.forEach(board -> board.markNumber(Integer.parseInt(n)));
            logger.info("i=" + n);
            if (boards.stream().allMatch(Board::isWin)) {
                last = Integer.parseInt(n);
                break;
            }
        }

        Board winning = prev.stream().filter(Board::isWin).findFirst().orElseThrow(IllegalStateException::new);

        logger.info("" + winning.toString());

        int k = boards.indexOf(winning);

        if (winning != null) {
            logger.info("Result: " + k + " | " + winning.sumUnmarked() + " " + last + " " + (winning.sumUnmarked() * last));
        }


        return (winning.sumUnmarked() * last);
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        String test = "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1\n" +
                "\n" +
                "22 13 17 11  0\n" +
                " 8  2 23  4 24\n" +
                "21  9 14 16  7\n" +
                " 6 10  3 18  5\n" +
                " 1 12 20 15 19\n" +
                "\n" +
                " 3 15  0  2 22\n" +
                " 9 18 13 17  5\n" +
                "19  8  7 25 23\n" +
                "20 11 10 24  4\n" +
                "14 21 16 12  6\n" +
                "\n" +
                "14 21 17 24  4\n" +
                "10 16 15  9 19\n" +
                "18  8 23 26 20\n" +
                "22 11 13  6  5\n" +
                " 2  0 12  3  7";

//        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
