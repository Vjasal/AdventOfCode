package com.vjasal.aoc2023.day20;

import com.vjasal.type.tuple.Tuple2;
import com.vjasal.type.tuple.Tuple3;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;
import com.vjasal.util.MathUtil;

import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2023, 20);
    }

    @Override
    public long solvePuzzle1(String input) {
        Map<String, Module> modules = new HashMap<>();
        for (String line : CollectionUtil.toLinkedList(input)) {
            Module module = new Module(line);
            modules.put(module.getName(), module);
        }
        for (Module module : modules.values()) {
            module.setInputs(modules);
        }

        long highs = 0;
        long lows = 0;
        Queue<Tuple3<String, String, Module.Signal>> queue = new LinkedList<>();

        for (int i = 0; i < 1000; i++) {
            queue.add(Tuple3.valueOf("button", "broadcaster", Module.Signal.LOW));
            while (!queue.isEmpty()) {
                Tuple3<String, String, Module.Signal> current = queue.poll();
                String source = current.val1();
                String destination = current.val2();
                Module.Signal signal = current.val3();
                switch (signal) {
                    case LOW -> lows++;
                    case HIGH -> highs++;
                }
                if (!modules.containsKey(destination))
                    continue;
                queue.addAll(modules.get(destination).handleSignal(signal, source));
            }
        }
        long result = lows * highs;
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Map<String, Module> modules = new HashMap<>();
        for (String line : CollectionUtil.toLinkedList(input)) {
            Module module = new Module(line);
            modules.put(module.getName(), module);
        }
        for (Module module : modules.values()) {
            module.setInputs(modules);
        }


        Module lastModule = modules.values().stream().filter(m -> m.getOutputs().contains("rx")).findFirst().orElseThrow();
        Map<String, Long> buttonPresses = new HashMap<>();

        if (lastModule.getType() != Module.Type.CONJUNCTION)
            throw new IllegalStateException();

        for (String last : lastModule.getInputs()) {
            boolean found = false;
            boolean done = false;
            long result = 0;

            Queue<Tuple3<String, String, Module.Signal>> queue = new LinkedList<>();

            while (!done) {
                queue.add(Tuple3.valueOf("button", "broadcaster", Module.Signal.LOW));
                if (found)
                    result += 1;
                while (!queue.isEmpty()) {
                    Tuple3<String, String, Module.Signal> current = queue.poll();
                    String source = current.val1();
                    String destination = current.val2();
                    Module.Signal signal = current.val3();
                    if (source.equals(last) && signal == Module.Signal.HIGH) {
                        if (found) {
                            done = true;
                            break;
                        }
                        found = true;
                    }
                    if (!modules.containsKey(destination))
                        continue;
                    queue.addAll(modules.get(destination).handleSignal(signal, source));
                }
            }
            buttonPresses.put(last, result);
        }

        long result = 1;
        for (long a : buttonPresses.values()) {
            result = MathUtil.lcm(result, a);
        }
        logger.info("Result: " + result);
        return result;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
