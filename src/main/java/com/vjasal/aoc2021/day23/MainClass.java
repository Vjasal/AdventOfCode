package com.vjasal.aoc2021.day23;

import com.vjasal.util.AocMainClass;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 23);
    }

    @Override
    public long solvePuzzle1(String input) {
        PriorityQueue<BurrowState> queue = new PriorityQueue<>();
        Set<BurrowState> seen = new HashSet<>();

        BurrowState start = new BurrowState();
        logger.info(start.toString());
        queue.addAll(start.getLegalMoves());
        while (!queue.isEmpty()) {
            BurrowState current = queue.poll();

            if (seen.contains(current)) continue;
            seen.add(current);

            if (current.isFinalState()) {
                logger.info("Result: " + current.getCost());
                break;
            }

            queue.addAll(current.getLegalMoves());
        }

        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {
        PriorityQueue<DeepBurrowState> queue = new PriorityQueue<>();
        Set<DeepBurrowState> seen = new HashSet<>();

        DeepBurrowState start = new DeepBurrowState();
        logger.info(start.toString());
        queue.addAll(start.getLegalMoves());
        while (!queue.isEmpty()) {
            DeepBurrowState current = queue.poll();

            if (seen.contains(current)) continue;
            seen.add(current);

//            logger.info(current.getCost() + current.toString());

            if (current.isFinalState()) {
                logger.info("Result: " + current.getCost() + current);
                break;
            }

            for (DeepBurrowState next : current.getLegalMoves()) {
//                if (next.getCost() > 4328) continue;
                queue.add(next);
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

//        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
