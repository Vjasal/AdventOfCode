package com.vjasal.aoc2019.day16;

import com.vjasal.util.AocMainClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private final static Logger logger = Logger.getLogger(MainClass.class.getName());

    private MainClass() {
        super(2019, 16);
    }

    @Override
    public void solvePuzzle1(String input) {
        int[] pattern = { 0, 1, 0, -1 };
        List<Integer> signal = parseInput(input);


        for (int i = 0; i < 100; i++) {
            fft(signal, pattern);
        }

        int result = signal.stream().limit(8).reduce(0, (a, b) -> a * 10 + b);
        logger.info("Result: " + String.format("%08d", result));
    }

    @Override
    public void solvePuzzle2(String input) {
        if (Integer.MAX_VALUE / 10000 < input.length()) {
            logger.info("Input size too big");
            return;
        }

        List<Integer> signal = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            signal.addAll(parseInput(input));
        }

        int address = signal.stream().limit(7).reduce(0, (a, b) -> 10 * a + b);
        if (address > signal.size() || address < signal.size() / 2) {
            logger.info("Unable to solve puzzle");
            return;
        }

        for (int i = 0; i < 100; i++) {
            fftFast(signal);
        }

        int result = signal.stream().skip(address).limit(8).reduce(0, (a, b) -> 10 * a + b);
        logger.info("Result: " + String.format("%08d", result));
    }

    private void fft(List<Integer> input, int[] pattern) {
        List<Integer> tmp = new ArrayList<>(input);
        for (int j = 0; j < input.size(); j++) {
            int sum = 0;
            for (int i = 0; i < tmp.size(); i++) {
                int index = ((i + 1) / (j + 1)) % pattern.length;
                sum += tmp.get(i) * pattern[index];
            }
            input.set(j, sum > 0 ? sum % 10 : - (sum % 10));
        }
    }

    private void fftFast(List<Integer> input) {
        for (int i = input.size() - 2; i > input.size() / 2; i--) {
            input.set(i, (input.get(i) + input.get(i + 1)) % 10);
        }
    }

    private List<Integer> parseInput(String input) {
        List<Integer> signal = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(input))) {
            int digit;
            while ((digit = reader.read() - 48) >= 0) {
                signal.add(digit);
            }
        } catch (IOException e) {
            logger.warning("Exception" + e);
        }
        return signal;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
