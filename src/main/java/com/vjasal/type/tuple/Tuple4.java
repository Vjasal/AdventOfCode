package com.vjasal.type.tuple;

public record Tuple4<A, B, C, D>(A val1, B val2, C val3, D val4) {

    public static <A, B, C, D> Tuple4<A, B, C, D> valueOf(A val1, B val2, C val3, D val4) {
        return new Tuple4<>(val1, val2, val3, val4);
    }

    @Override
    public String toString() {
        return "(" + val1 + "," + val2 + "," + val3 + "," + val4 + ")";
    }
}
