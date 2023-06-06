package com.vjasal.aoc2022.day19;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 19);
    }

    @Override
    public long solvePuzzle1(String input) {
        int result = 0;
        for (String line : CollectionUtil.toLinkedList(input)) {
            Blueprint blueprint = Blueprint.parse(line);
            int val = blueprint.simulate(24);
            result += blueprint.id() * val;
            logger.info("r=" + val);
        }

        logger.info("Result: " + result);

        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> in = CollectionUtil.toArrayList(input);

        int result = 1;
        for (int i = 0; i < 3; i++) {
            Blueprint blueprint = Blueprint.parse(in.get(i));
            int val = blueprint.simulate(32);
            result *= val;
            logger.info("r=" + val);
        }

        logger.info("Result: " + result);

        return 0;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
//        input = "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.\n" +
//                "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.";
        logger.info("Input:\n" + input);

//        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
