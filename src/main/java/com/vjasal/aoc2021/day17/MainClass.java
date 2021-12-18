package com.vjasal.aoc2021.day17;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.vectors.Vector2;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 17);
    }

    @Override
    public long solvePuzzle1(String input) {
        Probe probe = new Probe(input);

        List<Vector2<Integer, Integer>> hits = new LinkedList<>();
        for (int y = probe.getLowerYBound(); y <= probe.getUpperYBound(); y++) {
            for (int x = probe.getLowerXBound(); x <= probe.getUpperXBound(); x++) {
                if (probe.simulate(x, y)) {
                    hits.add(new Vector2<>(x, y));
                }
            }
        }

        int bestY = hits.stream().mapToInt(Vector2::getValue2).max().orElse(0);
        int result = probe.getHighestPositionForY(bestY);
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Probe probe = new Probe(input);

        List<Vector2<Integer, Integer>> hits = new LinkedList<>();
        for (int y = probe.getLowerYBound(); y <= probe.getUpperYBound(); y++) {
            for (int x = probe.getLowerXBound(); x <= probe.getUpperXBound(); x++) {
                if (probe.simulate(x, y)) {
                    hits.add(new Vector2<>(x, y));
                }
            }
        }

        int result = hits.size();
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
