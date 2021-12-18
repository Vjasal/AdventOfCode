package com.vjasal.aoc2021.day18;

import com.vjasal.util.vectors.Vector2;

public class SnailNumber {

    private Integer value = null;
    private SnailNumber left  = null;
    private SnailNumber right = null;

    public SnailNumber(String input) {
        if (input.startsWith("[")) {
            // TODO: There probably is a better way to do this, but it's too late
            int index = splitBrackets(input);
            left  = new SnailNumber(input.substring(1, index));
            right = new SnailNumber(input.substring(index + 1, input.length() - 1));
        } else {
            value = Integer.parseInt(input);
        }
    }

    private int splitBrackets(String input) {
        int i = 2;
        while (shouldContinueReading(input.substring(1, i))) i += 1;
        return i;
    }

    private boolean shouldContinueReading(String input) {
        long open  = input.chars().filter(c -> c == '[').count();
        long close = input.chars().filter(c -> c == ']').count();
        return open != close;
    }

    private SnailNumber(int value) {
        this.value = value;
    }

    private SnailNumber(SnailNumber left, SnailNumber right) {
        this.left  = new SnailNumber(left);
        this.right = new SnailNumber(right);
    }

    private SnailNumber(SnailNumber other) {
        if (other.value != null) {
            value = other.value;
        } else {
            left  = new SnailNumber(other.left);
            right = new SnailNumber(other.right);
        }
    }

    public SnailNumber add(SnailNumber number) {
        SnailNumber result = new SnailNumber(this, number);
        result.reduce();
        return result;
    }

    public long magnitude() {
        if (value != null) return value;
        return 3 * left.magnitude() + 2 * right.magnitude();
    }

    private void reduce() {
        while (true) {
            if (explode(0) != null) continue;
            if (split()) continue;
            break;
        }
    }

    private Vector2<Integer, Integer> explode(int depth) {
        if (value != null) {
            return null;
        }

        if (depth >= 4) {
            Vector2<Integer, Integer> ret = new Vector2<>(left.value, right.value);
            left  = null;
            right = null;
            value = 0;
            return ret;
        }

        Vector2<Integer, Integer> resultLeft = left.explode(depth + 1);
        if (resultLeft != null) {
            right.explodeLeft(resultLeft.getValue2());
            return new Vector2<>(resultLeft.getValue1(), 0);
        }

        Vector2<Integer, Integer> resultRight = right.explode(depth + 1);
        if (resultRight != null) {
            left.explodeRight(resultRight.getValue1());
            return new Vector2<>(0, resultRight.getValue2());
        }

        return null;
    }

    private void explodeLeft(int value) {
        if (this.value != null) {
            this.value += value;
        } else {
            left.explodeLeft(value);
        }
    }

    private void explodeRight(int value) {
        if (this.value != null) {
            this.value += value;
        } else {
            right.explodeRight(value);
        }
    }

    private boolean split() {
        if (value == null) {
            return left.split() || right.split();
        }

        if (value >= 10) {
            left  = new SnailNumber(value / 2);
            right = new SnailNumber(value - value / 2);
            value = null;
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        if (value != null) return value.toString();
        return "[" + left.toString() + "," + right.toString() + "]";
    }
}
