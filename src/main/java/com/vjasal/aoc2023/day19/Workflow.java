package com.vjasal.aoc2023.day19;

import com.vjasal.type.tuple.Tuple2;
import com.vjasal.type.vector.Vector2;
import com.vjasal.type.vector.Vector4;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Workflow {

    private static final Pattern pattern = Pattern.compile("^(([xmas])([<>])(\\d+):)?(\\w+)?$");

    enum Category {
        X, M, A, S, NONE;
    }

    enum Operator {
        LESS_THEN, GREATER_THAN, NONE;
    }

    private final Category category;
    private final Operator operator;
    private final int value;
    private final String next;

    public Workflow(String input) {
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find())
            throw new IllegalArgumentException("Wrong input: " + input);

        category = matcher.group(2) == null ? Category.NONE : switch (matcher.group(2)) {
            case "x" -> Category.X;
            case "m" -> Category.M;
            case "a" -> Category.A;
            case "s" -> Category.S;
            default -> Category.NONE;
        };
        operator = matcher.group(3) == null ? Operator.NONE : switch (matcher.group(3)) {
            case "<" -> Operator.LESS_THEN;
            case ">" -> Operator.GREATER_THAN;
            default -> Operator.NONE;
        };
        value = matcher.group(4) == null ? 0 : Integer.parseInt(matcher.group(4));
        next = matcher.group(5);
    }

    public String getNext(Vector4<Integer> input) {
        if (category == Category.NONE || operator == Operator.NONE)
            return next;
        int val = switch (category) {
            case X -> input.val1();
            case M -> input.val2();
            case A -> input.val3();
            case S -> input.val4();
            default -> throw new IllegalStateException();
        };
        return switch (operator) {
            case LESS_THEN -> val < value ? next : null;
            case GREATER_THAN -> val > value ? next : null;
            default -> throw new IllegalStateException();
        };
    }

    public LinkedList<Tuple2<Vector4<Vector2<Integer>>, String>> split(Vector4<Vector2<Integer>> input) {
        if (category == Category.NONE || operator == Operator.NONE) {
            return new LinkedList<>(Collections.singleton(Tuple2.valueOf(input, next)));
        }

        Vector2<Integer> val = switch (category) {
            case X -> input.val1();
            case M -> input.val2();
            case A -> input.val3();
            case S -> input.val4();
            default -> throw new IllegalStateException();
        };

        if (value < val.val1()) {
            Tuple2<Vector4<Vector2<Integer>>, String> vv = switch (operator) {
                case LESS_THEN -> Tuple2.valueOf(input, null);
                case GREATER_THAN -> Tuple2.valueOf(input, next);
                case NONE -> throw new IllegalStateException();
            };
            return new LinkedList<>(Collections.singleton(vv));
        }

        if (value > val.val2()) {
            Tuple2<Vector4<Vector2<Integer>>, String> vv = switch (operator) {
                case LESS_THEN -> Tuple2.valueOf(input, next);
                case GREATER_THAN -> Tuple2.valueOf(input, null);
                case NONE -> throw new IllegalStateException();
            };
            return new LinkedList<>(Collections.singleton(vv));
        }

        Vector2<Integer> a = switch (operator) {
            case LESS_THEN -> Vector2.valueOf(val.val1(), value - 1);
            case GREATER_THAN -> Vector2.valueOf(value + 1, val.val2());
            default -> throw new IllegalStateException();
        };

        Vector2<Integer> b = switch (operator) {
            case LESS_THEN -> Vector2.valueOf(value, val.val2());
            case GREATER_THAN -> Vector2.valueOf(val.val1(), value);
            default -> throw new IllegalStateException();
        };

        Vector4<Vector2<Integer>> val1 = switch (category) {
            case X -> Vector4.valueOf(a, input.val2(), input.val3(), input.val4());
            case M -> Vector4.valueOf(input.val1(), a, input.val3(), input.val4());
            case A -> Vector4.valueOf(input.val1(), input.val2(), a, input.val4());
            case S -> Vector4.valueOf(input.val1(), input.val2(), input.val3(), a);
            default -> throw new IllegalStateException();
        };

        Vector4<Vector2<Integer>> val2 = switch (category) {
            case X -> Vector4.valueOf(b, input.val2(), input.val3(), input.val4());
            case M -> Vector4.valueOf(input.val1(), b, input.val3(), input.val4());
            case A -> Vector4.valueOf(input.val1(), input.val2(), b, input.val4());
            case S -> Vector4.valueOf(input.val1(), input.val2(), input.val3(), b);
            default -> throw new IllegalStateException();
        };

        LinkedList<Tuple2<Vector4<Vector2<Integer>>, String>> result = new LinkedList<>();
        result.add(Tuple2.valueOf(val1, next));
        result.add(Tuple2.valueOf(val2, null));
        return result;
    }

    @Override
    public String toString() {
        return category.name() + " " + operator.name() + " " + value + " : " + next;
    }
}
