package com.vjasal.type.vector;

public record Vector4<T>(T val1, T val2, T val3, T val4) {

    public static Vector4<Integer> ZERO = Vector4.valueOf(0, 0, 0, 0);

    public static <T> Vector4<T> valueOf(T val1, T val2, T val3, T val4) {
        return new Vector4<>(val1, val2, val3, val4);
    }

    public static Vector4<Integer> add(Vector4<Integer> v1, Vector4<Integer> v2) {
        return new Vector4<>(v1.val1 + v2.val1, v1.val2 + v2.val2, v1.val3 + v2.val3, v1.val4 + v2.val4);
    }

    public static Vector4<Integer> subtract(Vector4<Integer> v1, Vector4<Integer> v2) {
        return new Vector4<>(v1.val1 - v2.val1, v1.val2 - v2.val2, v1.val3 - v2.val3, v1.val4 - v2.val4);
    }

    public static boolean lessEqualsThan(Vector4<Integer> v1, Vector4<Integer> v2) {
        return v1.val1 <= v2.val1 && v1.val2 <= v2.val2 && v1.val3 <= v2.val3 && v1.val4 <= v2.val4;
    }

    public Vector4<T> copy() {
        return Vector4.valueOf(val1, val2, val3, val4);
    }

    @Override
    public String toString() {
        return "(" + val1 + ", " + val2 + ", " + val3 + ", " + val4 + ")";
    }
}
