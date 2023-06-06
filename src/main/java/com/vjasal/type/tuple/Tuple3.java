package com.vjasal.type.tuple;

import java.util.LinkedList;
import java.util.List;

public record Tuple3<A, B, C>(A val1, B val2, C val3) {

    public static Tuple3<Integer, Integer, Integer> ZERO = new Tuple3<>(0, 0, 0);

    public static Tuple3<Integer, Integer, Integer> add(Tuple3<Integer, Integer, Integer> v1,
                                                        Tuple3<Integer, Integer, Integer> v2)
    {
        return new Tuple3<>(v1.val1() + v2.val1(), v1.val2() + v2.val2(), v1.val3() + v2.val3());
    }

    public static Tuple3<Integer, Integer, Integer> subtract(Tuple3<Integer, Integer, Integer> v1,
                                                             Tuple3<Integer, Integer, Integer> v2)
    {
        return new Tuple3<>(v1.val1() - v2.val1(), v1.val2() - v2.val2(), v1.val3() - v2.val3());
    }

    public static List<Tuple3<Integer, Integer, Integer>> neighbours(
            Tuple3<Integer, Integer, Integer> position,
            boolean diagonal)
    {
        int[] dx = { 0,  0,  1,  0,  0, -1,  1,  1, -1, -1,  0,  1,  0, -1,  1,  1, -1, -1,  0,  1,  0, -1,  1,  1, -1, -1};
        int[] dy = { 0,  1,  0,  0, -1,  0,  1, -1,  1, -1,  1,  0, -1,  0,  1, -1,  1, -1,  1,  0, -1,  0,  1, -1,  1, -1};
        int[] dz = { 1,  0,  0, -1,  0,  0,  0,  0,  0,  0,  1,  1,  1,  1,  1,  1,  1,  1, -1, -1, -1, -1, -1, -1, -1, -1};

        List<Tuple3<Integer, Integer, Integer>> neighbours = new LinkedList<>();
        for (int i = 0; i < (diagonal ? 26 : 6); i++) {
            int x = position.val1() + dx[i];
            int y = position.val2() + dy[i];
            int z = position.val3() + dz[i];
            neighbours.add(new Tuple3<>(x, y, z));
        }
        return neighbours;
    }

    public static <A, B, C> Tuple3<A, B, C> valueOf(A val1, B val2, C val3) {
        return new Tuple3<>(val1, val2, val3);
    }

    @Override
    public String toString() {
        return "(" + val1 + "," + val2 + "," + val3 + ")";
    }
}
