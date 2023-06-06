package com.vjasal.aoc2022.day11;

import com.vjasal.type.vector.Vector2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 11);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<Monkey> monkeys = new ArrayList<>();

        for (String section : CollectionUtil.toListOfSections(input)) {
            Monkey monkey = new Monkey(section);
            monkeys.add(monkey);
        }

        for (int i = 0; i < 20 ; i++) {
            for (Monkey monkey : monkeys) {
                Vector2<List<Integer>> thrownItems = monkey.round();
                monkeys.get(monkey.getFirstId()).addItems(thrownItems.val1());
                monkeys.get(monkey.getSecondId()).addItems(thrownItems.val2());
            }
        }

        long monkeyBusiness = monkeys.stream()
                .mapToLong(Monkey::getInspections)
                .sorted()
                .skip(monkeys.size() - 2)
                .reduce(1, (a, b) -> a * b);
        logger.info("Result: " + monkeyBusiness);
        return monkeyBusiness;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<Monkey> monkeys = new ArrayList<>();

        for (String section : CollectionUtil.toListOfSections(input)) {
            Monkey monkey = new Monkey(section);
            monkeys.add(monkey);
        }

        int mod = monkeys.stream().mapToInt(Monkey::getTestValue).reduce(1, (a, b) -> a * b);
        for (int i = 0; i < 10000 ; i++) {
            for (Monkey monkey : monkeys) {
                Vector2<List<Integer>> thrownItems = monkey.round(mod);
                monkeys.get(monkey.getFirstId()).addItems(thrownItems.val1());
                monkeys.get(monkey.getSecondId()).addItems(thrownItems.val2());
            }
        }

        long monkeyBusiness = monkeys.stream()
                .mapToLong(Monkey::getInspections)
                .sorted()
                .skip(monkeys.size() - 2)
                .reduce(1, (a, b) -> a * b);
        logger.info("Result: " + monkeyBusiness);
        return monkeyBusiness;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
