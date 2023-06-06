package com.vjasal.aoc2022.day05;

import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Cargo {

    private final List<Stack<String>> stacks = new ArrayList<>();

    public Cargo(String input) {
        LinkedList<String> lines = CollectionUtil.toLinkedList(input);
        Iterator<String> iterator = lines.descendingIterator();

        // Skip last line in string
        if (!iterator.hasNext()) throw new IllegalArgumentException();
        iterator.next();

        // Replace unnecessary characters ("[", "]" and white spaces) and add all values to stacks
        while (iterator.hasNext()) {
            String line = iterator.next().replaceAll("[\\[| ]([A-Z| ])[]| ]\\s?", "$1");
            String[] values = line.split("");
            for (int i = 0; i < values.length; i++) {
                if (stacks.size() <= i)
                    stacks.add(new Stack<>());
                if (!values[i].equals(" "))
                    stacks.get(i).add(values[i]);
            }
        }
    }

    public void moveCrates(Instruction instruction) {
        Stack<String> origin = stacks.get(instruction.origin() - 1);
        Stack<String> destination = stacks.get(instruction.destination() - 1);

        for (int i = 0; i < instruction.count(); i++) {
            destination.add(origin.pop());
        }
    }

    public void moveMultipleCrates(Instruction instruction) {
        Stack<String> origin = stacks.get(instruction.origin() - 1);
        Stack<String> destination = stacks.get(instruction.destination() - 1);
        Stack<String> tmp = new Stack<>();

        for (int i = 0; i < instruction.count(); i++) {
            tmp.add(origin.pop());
        }
        for (int i = 0; i < instruction.count(); i++) {
            destination.add(tmp.pop());
        }
    }

    public String getTopValues() {
        return stacks.stream().map(Stack::pop).collect(Collectors.joining());
    }
}
