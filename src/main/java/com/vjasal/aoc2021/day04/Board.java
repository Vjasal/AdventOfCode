package com.vjasal.aoc2021.day04;

import com.vjasal.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Board {
    private static final Logger logger = Logger.getLogger(Board.class.getName());

    private List<List<Integer>> numbers = new ArrayList<>();

    public Board(String input) {
        for (String line : CollectionUtil.toLinkedList(input)) {

            List<Integer> l = new ArrayList<>();
            for (String x : line.trim().split(" +"))
                l.add(Integer.parseInt(x));
            numbers.add(l);
        }
    }

    public void markNumber(int number) {
        for (List<Integer> row : numbers) {
            for (int i = 0; i < row.size(); i++) {
                if (row.get(i) == number)
                    row.set(i, -1);
            }
        }
    }

    public boolean isWin() {
        for (List<Integer> row : numbers) {
            if (row.stream().allMatch(i -> i == -1)) {
                return true;
            }
        }

        for (int i = 0; i < 5; i++) {
            int k = i;
            if (numbers.stream().allMatch(list -> list.get(k) == -1)) {
                return true;
            }
        }

        return false;
    }

    public int sumUnmarked() {
        int ret = 0;
        for (List<Integer> row : numbers) {
            for (int x : row) {
                if (x != -1)
                    ret += x;
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        return "Board{" +
                "numbers=" + numbers +
                '}';
    }
}
