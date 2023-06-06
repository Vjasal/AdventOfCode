package com.vjasal.util;

public class MathUtil {

    public static long mod(long a, long n) {
        long result = a % n;
        if (result < 0)
            result += n;
        return result;
    }

    public static long powMod(long a, long b, long n) {
        long result = 1;
        a = mod(a, n);
        while (b != 0) {
            result = (b & 1) == 0 ? result : result * a % n;
            a = a * a % n;
            b /= 2;
        }
        return result;
    }

    public static long setBit(long a, int i) {
        if (i > Long.SIZE)
            throw new IllegalArgumentException();
        return a | (1L << i);
    }

    public static long clearBit(long a, int i) {
        if (i > Long.SIZE)
            throw new IllegalArgumentException();
        return a & (a ^ (1L << i));
    }

}
