package com.vjasal.aoc2022.day20;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 20);
    }

    @Override
    public long solvePuzzle1(String input) {
        SpecialList list = new SpecialList();
        for (String line : CollectionUtil.toLinkedList(input)) {
            list.add(Integer.parseInt(line));
        }

        list.mix(1);
        int i = list.indexOf(0);
        long result = list.get(i + 1000) + list.get(i + 2000) + list.get(i + 3000);

        logger.info("Result: " + result);
        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {
        SpecialList list = new SpecialList();
        for (String line : CollectionUtil.toLinkedList(input)) {
            list.add(Long.parseLong(line) * 811589153);
        }

        list.mix(10);
        int i = list.indexOf(0);
        long result = list.get(i + 1000) + list.get(i + 2000) + list.get(i + 3000);

        logger.info("Result: " + result);
        return 0;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
