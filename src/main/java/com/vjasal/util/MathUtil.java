package com.vjasal.util;

import java.util.logging.Logger;

public class MathUtil {

    private static Logger logger = Logger.getLogger(MathUtil.class.getName());

    public static long mod(long a, long b) {
        long result = a % b;
        if (result < 0)
            result += b;
        return result;
    }

    public static long powMod(long a, long b, long m) {
        long result = 1;
        a = mod(a, m);
        while (b != 0) {
            result = (b & 1) == 0 ? result : mod(result * a, m);
            a = mod(a * a, m);
            b /= 2;
        }
        return result;
    }

}