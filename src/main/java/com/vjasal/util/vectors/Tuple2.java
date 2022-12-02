package com.vjasal.util.vectors;

import java.util.LinkedList;
import java.util.List;

public record Tuple2<A, B>(A val1, B val2) {

    public static List<Tuple2<Integer, Integer>> neighbours(Tuple2<Integer, Integer> position, boolean diagonal) {
        int[] dx = { 0,  1,  0, -1,  1,  1, -1, -1};
        int[] dy = { 1,  0, -1,  0,  1, -1,  1, -1};

        List<Tuple2<Integer, Integer>> neighbours = new LinkedList<>();
        for (int i = 0; i < (diagonal ? 8 : 4); i++) {
            int x = position.val1() + dx[i];
            int y = position.val2() + dy[i];
            neighbours.add(new Tuple2<>(x, y));
        }
        return neighbours;
    }

    @Override
    public String toString() {
        return "(" + val1 + "," + val2 + ")";
    }
}
