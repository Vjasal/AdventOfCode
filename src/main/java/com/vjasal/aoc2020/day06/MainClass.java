package com.vjasal.aoc2020.day06;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    private MainClass() {
        super(2020, 6);
    }

    @Override
    public void solvePuzzle1(String input) {
        List<String> lines = parseInput(input);

        int result = 0;
        for (String line : lines) {
            result += line.chars().filter(c -> c != ' ').distinct().count();
        }

        logger.info("Result: " + result);
    }

    @Override
    public void solvePuzzle2(String input) {
        List<String> lines = parseInput(input);

        int result = 0;
        for (String line : lines) {
            List<Set<Character>> answers = Arrays.stream(line.split(" "))
                    .collect(LinkedList::new, (list, s) -> list.add(Util.toCharSet(s)), LinkedList::addAll);

            Iterator<Set<Character>> iterator = answers.iterator();
            Set<Character> common = iterator.next();
            while (iterator.hasNext()) {
                common.retainAll(iterator.next());
            }

            result += common.size();
        }

        logger.info("Result: " + result);
    }

    private List<String> parseInput(String input) {
        List<String> lines = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(groupLines(input)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            logger.warning("Exception: " + e);
        }
        return lines;
    }

    private String groupLines(String input) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new StringReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("")) {
                    builder.append("\n");
                } else {
                    builder.append(line).append(" ");
                }
            }
        } catch (IOException e) {
            logger.warning("Exception: " + e);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
