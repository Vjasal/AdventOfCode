package com.vjasal.type.tuple;

public record Tuple5<A, B, C, D, E>(A val1, B val2, C val3, D val4, E val5) {

    public static <A, B, C, D, E> Tuple5<A, B, C, D, E> valueOf(A val1, B val2, C val3, D val4, E val5) {
        return new Tuple5<>(val1, val2, val3, val4, val5);
    }

    @Override
    public String toString() {
        return "(" + val1 + "," + val2 + "," + val3 + "," + val4 + "," + val5 + ")";
    }
}
