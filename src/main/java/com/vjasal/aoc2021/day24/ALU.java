package com.vjasal.aoc2021.day24;

import com.vjasal.util.CollectionUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ALU {

    private static final Pattern pattern = Pattern.compile("^(\\w{3}) (\\w) ?(-?\\w+)?$");

    private final List<String> program;

    private final Queue<Integer> input = new LinkedList<>();
    private final int[] registers = new int[4];

    public ALU(String input) {
        this.program = CollectionUtil.toLinkedList(input);
    }

    public void writeInput(int value) {
        input.add(value);
    }

    public int readRegister(int index) {
        if (index < 0 || index >= registers.length)
            throw new IndexOutOfBoundsException();
        return registers[index];
    }

    private int readInput() {
        if (input.isEmpty())
            throw new IllegalStateException("Input queue is empty");
        return input.poll();
    }

    public void runProgram() {
        for (int i = 0; i < 4; i++)
            registers[i] = 0;

        for (String instruction : program) {
            executeInstruction(instruction);
        }

        input.clear();
    }

    private void executeInstruction(String instruction) {
        Matcher matcher = pattern.matcher(instruction);
        if (!matcher.find()) throw new IllegalArgumentException();

        switch (matcher.group(1)) {
            case "inp":
                inp(matcher.group(2));
                break;
            case "add":
                add(matcher.group(2), matcher.group(3));
                break;
            case "mul":
                mul(matcher.group(2), matcher.group(3));
                break;
            case "div":
                div(matcher.group(2), matcher.group(3));
                break;
            case "mod":
                mod(matcher.group(2), matcher.group(3));
                break;
            case "eql":
                eql(matcher.group(2), matcher.group(3));
                break;
        }
    }

    private void inp(String val1) {
        int i1 = mapToIndex(val1);
        if (i1 < 0) throw new IllegalArgumentException();
        registers[i1] = readInput();
    }

    private void add(String val1, String val2) {
        int i1 = mapToIndex(val1);
        if (i1 < 0) throw new IllegalArgumentException();

        int i2 = mapToIndex(val2);
        int v2 = i2 < 0 ? Integer.parseInt(val2) : registers[i2];

        registers[i1] += v2;
    }

    private void mul(String val1, String val2) {
        int i1 = mapToIndex(val1);
        if (i1 < 0) throw new IllegalArgumentException();

        int i2 = mapToIndex(val2);
        int v2 = i2 < 0 ? Integer.parseInt(val2) : registers[i2];

        registers[i1] *= v2;
    }

    private void div(String val1, String val2) {
        int i1 = mapToIndex(val1);
        if (i1 < 0) throw new IllegalArgumentException();

        int i2 = mapToIndex(val2);
        int v2 = i2 < 0 ? Integer.parseInt(val2) : registers[i2];

        registers[i1] /= v2;
    }

    private void mod(String val1, String val2) {
        int i1 = mapToIndex(val1);
        if (i1 < 0) throw new IllegalArgumentException();

        int i2 = mapToIndex(val2);
        int v2 = i2 < 0 ? Integer.parseInt(val2) : registers[i2];

        registers[i1] = registers[i1] % v2;
    }

    private void eql(String val1, String val2) {
        int i1 = mapToIndex(val1);
        if (i1 < 0) throw new IllegalArgumentException();

        int i2 = mapToIndex(val2);
        int v2 = i2 < 0 ? Integer.parseInt(val2) : registers[i2];

        registers[i1] = registers[i1] == v2 ? 1 : 0;
    }

    private int mapToIndex(String val) {
        switch (val) {
            case "w":
                return 0;
            case "x":
                return 1;
            case "y":
                return 2;
            case "z":
                return 3;
            default:
                return -1;
        }
    }
}
