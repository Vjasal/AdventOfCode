package com.vjasal.aoc2021.day05;

import java.util.Objects;

public class Vector2<A, B> {

    private final A value1;
    private final B value2;

    Vector2(A value1, B value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    A getValue1() {
        return value1;
    }

    B getValue2() {
        return value2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof Vector2) {
            Vector2<?, ?> other = (Vector2<?, ?>) obj;
            return Objects.equals(value1, other.value1) &&
                    Objects.equals(value2, other.value2);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2);
    }

    @Override
    public String toString() {
        return "(" + value1 + "," + value2 + ")";
    }
}
