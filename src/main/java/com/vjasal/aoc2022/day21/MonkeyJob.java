package com.vjasal.aoc2022.day21;

import com.vjasal.type.vector.Vector2;

import java.util.Map;

public class MonkeyJob {

    public enum Operation {
        NOOP, ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    private final String name;
    private final long value;
    private final Vector2<String> childMonkeys;
    private final Operation operation;

    public MonkeyJob(String name, long value) {
        this.name = name;
        this.value = value;
        this.childMonkeys = null;
        this.operation = Operation.NOOP;
    }

    public MonkeyJob(String name, String m1, String m2, Operation operation) {
        this.name = name;
        this.value = 0;
        this.childMonkeys = Vector2.valueOf(m1, m2);
        this.operation = operation;
    }

    public static Operation getOp(String in) {
        return switch (in) {
            case "+" -> Operation.ADD;
            case "-" -> Operation.SUBTRACT;
            case "*" -> Operation.MULTIPLY;
            case "/" -> Operation.DIVIDE;
            default -> Operation.NOOP;
        };
    }

    public String first() {
        return childMonkeys.val1();
    }

    public String second() {
        return childMonkeys.val2();
    }

    public boolean contains(String name) {
        if (childMonkeys == null) return false;
        return childMonkeys.val1().equals(name) || childMonkeys.val2().equals(name);
    }

    public long test(Map<String, MonkeyJob> map, String child) {
        if (this.name.equals("root")) {
            if (childMonkeys.val1().equals(child)) return map.get(childMonkeys.val2()).getValue(map);
            return map.get(childMonkeys.val1()).getValue(map);
        }

        MonkeyJob parent = map.values().stream().filter(m -> m.contains(name)).findFirst().orElseThrow();
        if (childMonkeys == null) return parent.test(map, name);

        long val = parent.test(map, name);
        String other = childMonkeys.val1().equals(child) ? childMonkeys.val2() : childMonkeys.val1();
        long otherValue = map.get(other).getValue(map);

        return switch (operation) {
            case NOOP -> throw new IllegalStateException();
            case ADD -> val - otherValue;
            case SUBTRACT -> child.equals(first()) ? val + otherValue : otherValue - val;
            case MULTIPLY -> val / otherValue;
            case DIVIDE -> child.equals(first()) ? val * otherValue : otherValue / val;
        };
    }

    public long getValue(Map<String, MonkeyJob> map) {
        if (childMonkeys == null) return value;
        return switch (operation) {
            case NOOP -> throw new IllegalStateException();
            case ADD -> map.get(childMonkeys.val1()).getValue(map) + map.get(childMonkeys.val2()).getValue(map);
            case SUBTRACT -> map.get(childMonkeys.val1()).getValue(map) - map.get(childMonkeys.val2()).getValue(map);
            case MULTIPLY -> map.get(childMonkeys.val1()).getValue(map) * map.get(childMonkeys.val2()).getValue(map);
            case DIVIDE -> map.get(childMonkeys.val1()).getValue(map) / map.get(childMonkeys.val2()).getValue(map);
        };
    }
}
