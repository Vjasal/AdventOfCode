package com.vjasal.aoc2021.day24;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonadSolver {

    private static final Pattern pattern = Pattern.compile("^(\\w{3}) (\\w) ?(-?\\w+)?$");

    private String value = "z=0";
    private int k = 0;

    public void reverse(String command) {
        Matcher matcher = pattern.matcher(command);
        if (!matcher.find()) throw new IllegalArgumentException();

        switch (matcher.group(1)) {
            case "inp":
                reverseInp(matcher.group(2));
                break;
            case "add":
                reverseAdd(matcher.group(2), matcher.group(3));
                break;
            case "mul":
                reverseMul(matcher.group(2), matcher.group(3));
                break;
            case "div":
                reverseDiv(matcher.group(2), matcher.group(3));
                break;
            case "mod":
                reverseMod(matcher.group(2), matcher.group(3));
                break;
            case "eql":
                reverseEql(matcher.group(2), matcher.group(3));
                break;
        }
    }

    public String getValue() {
        return value;
    }

    private void reverseInp(String val) {
        String x = "" + ('A' + 14 - k);
        value = value.replace(val, x);
        k += 1;
    }

    private void reverseAdd(String val1, String val2) {
        value = value.replace(val1, "(" + val1 + "+" + val2 + ")");
    }

    private void reverseMul(String val1, String val2) {
        value = value.replace(val1, "(" + val1 + "*" + val2 + ")");
    }

    private void reverseDiv(String val1, String val2) {
        value = value.replace(val1, "(" + val1 + "/" + val2 + ")");
    }

    private void reverseMod(String val1, String val2) {
        value = value.replace(val1, "(" + val1 + "%" + val2 + ")");
    }

    private void reverseEql(String val1, String val2) {
        value = value.replace(val1, "(" + val1 + "==" + val2 + ")");
    }


}
