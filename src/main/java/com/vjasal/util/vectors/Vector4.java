package com.vjasal.util.vectors;

import java.util.Objects;

public class Vector4<A, B, C, D> {

    private final A value1;
    private final B value2;
    private final C value3;
    private final D value4;

    public Vector4(A value1, B value2, C value3, D value4) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
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

    public D getValue4() {
        return value4;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof Vector4) {
            Vector4<?, ?, ?, ?> other = (Vector4<?, ?, ?, ?>) obj;
            return Objects.equals(value1, other.value1) &&
                    Objects.equals(value2, other.value2) &&
                    Objects.equals(value3, other.value3) &&
                    Objects.equals(value4, other.value4);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2, value3, value4);
    }

    @Override
    public String toString() {
        return "(" + value1 + "," + value2 + "," + value3 + "," + value4 + ")";
    }
}
