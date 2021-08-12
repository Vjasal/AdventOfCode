package com.vjasal.aoc2020.day20;

import java.util.Objects;

public class Vector3<A, B, C> {

    private final A value1;
    private final B value2;
    private final C value3;

    Vector3(A value1, B value2, C value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public A getValue1() {
        return value1;
    }

    public B getValue2() {
        return value2;
    }

    public C getValue3() {
        return value3;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof Vector3) {
            Vector3<?, ?, ?> other = (Vector3<?, ?, ?>) obj;
            return Objects.equals(value1, other.value1) &&
                    Objects.equals(value2, other.value2) &&
                    Objects.equals(value3, other.value3);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2, value3);
    }

    @Override
    public String toString() {
        return "(" + value1 + "," + value2 + "," + value3 + ")";
    }
}
