package com.vjasal.type.vector;

import java.util.LinkedList;
import java.util.List;

public record Vector3<T>(T val1, T val2, T val3) {

    public static Vector3<Integer> ZERO = Vector3.valueOf(0, 0, 0);

    public static <T> Vector3<T> valueOf(T val1, T val2, T val3) {
        return new Vector3<>(val1, val2, val3);
    }

    public static Vector3<Integer> add(Vector3<Integer> v1, Vector3<Integer> v2) {
        return new Vector3<>(v1.val1 + v2.val1, v1.val2 + v2.val2, v1.val3 + v2.val3);
    }

    public static Vector3<Integer> subtract(Vector3<Integer> v1, Vector3<Integer> v2) {
        return new Vector3<>(v1.val1 - v2.val1, v1.val2 - v2.val2, v1.val3 - v2.val3);
    }

    public static boolean lessEqualsThan(Vector3<Integer> v1, Vector3<Integer> v2) {
        return v1.val1 <= v2.val1 && v1.val2 <= v2.val2 && v1.val3 <= v2.val3;
    }

    public static List<Vector3<Integer>> neighbours(Vector3<Integer> position) {
        int[] dx = { 0,  1,  0, -1,  0,  0};
        int[] dy = { 1,  0, -1,  0,  0,  0};
        int[] dz = { 0,  0,  0,  0,  1, -1};

        List<Vector3<Integer>> neighbours = new LinkedList<>();
        for (int i = 0; i < 6; i++) {
            int x = position.val1() + dx[i];
            int y = position.val2() + dy[i];
            int z = position.val3() + dz[i];
            neighbours.add(Vector3.valueOf(x, y, z));
        }
        return neighbours;
    }

    @Override
    public String toString() {
        return "(" + val1 + ", " + val2 + ", " + val3 + ")";
    }
}
