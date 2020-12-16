package com.vjasal.aoc2020.day16;

import com.vjasal.util.AocMainClass;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2020, 16);
    }

    @Override
    public long solvePuzzle1(String input) {
        Notes notes = new Notes(input);

        int result = notes.getNearby().stream()
                .filter(ticket -> !ticket.isValid(notes.getRules()))
                .mapToInt(ticket -> ticket.getErrorRate(notes.getRules()))
                .sum();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Notes notes = new Notes(input);

        List<Ticket> validTickets = notes.getNearby().stream()
                .filter(ticket -> ticket.isValid(notes.getRules()))
                .collect(Collectors.toList());
        validTickets.add(notes.getYours());

        for (Rule rule : notes.getRules()) {
            rule.markAllAsSatisfied(notes.getRules().size());
        }

        for (Ticket ticket : validTickets) {
            for (Rule rule : notes.getRules()) {
                Set<Integer> indexes = IntStream.range(0, ticket.getSize()).boxed()
                        .filter(index -> !rule.satisfies(ticket.getValue(index)))
                        .collect(Collectors.toSet());
                rule.removeNotSatisfied(indexes);
            }
        }

        notes.getRules().sort(Comparator.comparing(rule -> rule.getSatisfiedValueIndexes().size()));
        for (int i = 0; i < notes.getRules().size() - 1; i++) {
            for (int j = i + 1; j < notes.getRules().size(); j++) {
                notes.getRules().get(j).removeNotSatisfied(notes.getRules().get(i).getSatisfiedValueIndexes());
            }
        }

        long result = notes.getRules().stream()
                .filter(rule -> rule.getName().startsWith("departure"))
                .mapToInt(Rule::getRuleIndex)
                .mapToLong(index -> notes.getYours().getValue(index))
                .reduce(1L, (a, b) -> a * b);

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
