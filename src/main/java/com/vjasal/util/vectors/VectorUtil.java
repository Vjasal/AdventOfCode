package com.vjasal.util.vectors;

import java.util.LinkedList;
import java.util.List;

public class VectorUtil {

    public static List<Vector2<Integer, Integer>> neighbours(Vector2<Integer, Integer> position, boolean diagonal) {
        int[] dx = { 0,  1,  0, -1,  1,  1, -1, -1};
        int[] dy = { 1,  0, -1,  0,  1, -1,  1, -1};

        List<Vector2<Integer, Integer>> neighbours = new LinkedList<>();
        for (int i = 0; i < (diagonal ? 8 : 4); i++) {
            int x = position.getValue1() + dx[i];
            int y = position.getValue2() + dy[i];
            neighbours.add(new Vector2<>(x, y));
        }
        return neighbours;
    }

    public static List<Vector3<Integer, Integer, Integer>> neighbours(
            Vector3<Integer, Integer, Integer> position, boolean diagonal) {
        int[] dx = { 0,  0,  1,  0,  0, -1,  1,  1, -1, -1,  0,  1,  0, -1,  1,  1, -1, -1,  0,  1,  0, -1,  1,  1, -1, -1};
        int[] dy = { 0,  1,  0,  0, -1,  0,  1, -1,  1, -1,  1,  0, -1,  0,  1, -1,  1, -1,  1,  0, -1,  0,  1, -1,  1, -1};
        int[] dz = { 1,  0,  0, -1,  0,  0,  0,  0,  0,  0,  1,  1,  1,  1,  1,  1,  1,  1, -1, -1, -1, -1, -1, -1, -1, -1};

        List<Vector3<Integer, Integer, Integer>> neighbours = new LinkedList<>();
        for (int i = 0; i < (diagonal ? 26 : 6); i++) {
            int x = position.getValue1() + dx[i];
            int y = position.getValue2() + dy[i];
            int z = position.getValue3() + dz[i];
            neighbours.add(new Vector3<>(x, y, z));
        }
        return neighbours;
    }

}
