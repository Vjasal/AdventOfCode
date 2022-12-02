package com.vjasal.util.vectors;

public record Tuple4<A, B, C, D>(A val1, B val2, C val3, D val4) {

    @Override
    public String toString() {
        return "(" + val1 + "," + val2 + "," + val3 + "," + val4 + ")";
    }
}
