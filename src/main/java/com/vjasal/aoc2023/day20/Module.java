package com.vjasal.aoc2023.day20;

import com.vjasal.type.tuple.Tuple2;
import com.vjasal.type.tuple.Tuple3;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Module {
    private static final Pattern pattern = Pattern.compile("^([%&]?)(\\w+) -> ([\\w, ]+)$");

    enum Type { FLIP_FLOP, CONJUNCTION, BROADCASTER }
    enum Signal { HIGH, LOW }

    private final Map<String, Signal> rememberedSignal = new HashMap<>();
    private boolean flipFlopState = false;

    private List<String> inputs = new LinkedList<>();
    private List<String> outputs = new LinkedList<>();
    private final Type type;
    private final String name;

    public Module(String input) {
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find())
            throw new IllegalArgumentException(input);
        type = matcher.group(1) == null ? Type.BROADCASTER : switch (matcher.group(1)) {
            case "%" -> Type.FLIP_FLOP;
            case "&" -> Type.CONJUNCTION;
            default -> Type.BROADCASTER;
        };
        name = matcher.group(2);
        outputs.addAll(Arrays.asList(matcher.group(3).split(", ")));
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public List<String> getOutputs() {
        return outputs;
    }

    public List<String> getInputs() {
        return inputs;
    }

    public void setInputs(Map<String, Module> modules) {
        for (Map.Entry<String, Module> e : modules.entrySet()) {
            if (e.getValue().outputs.contains(name)) {
                inputs.add(e.getKey());
            }
        }
    }

    public List<Tuple3<String, String, Signal>> handleSignal(Signal signal, String source) {
        return switch (type) {
            case FLIP_FLOP -> {
                if (signal == Signal.HIGH) yield Collections.emptyList();
                flipFlopState = !flipFlopState;
                yield outputs.stream().map(s -> Tuple3.valueOf(name, s, flipFlopState ? Signal.HIGH : Signal.LOW)).toList();
            }
            case CONJUNCTION -> {
                rememberedSignal.put(source, signal);
                boolean allMatch = inputs.stream().allMatch(s -> rememberedSignal.getOrDefault(s, Signal.LOW) == Signal.HIGH);
                yield outputs.stream().map(s -> Tuple3.valueOf(name, s, allMatch ? Signal.LOW : Signal.HIGH)).toList();
            }
            case BROADCASTER -> outputs.stream().map(s -> Tuple3.valueOf(name, s, signal)).toList();
        };
    }
}
