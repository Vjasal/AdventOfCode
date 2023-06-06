package com.vjasal.aoc2021.day24;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2021, 24);
    }

    private void decreaseModelNumber(int[] modelNumber) {
        int k = 0;
        while (true) {
            int index = modelNumber.length - k - 1;
            modelNumber[index] -= 1;
            if (modelNumber[index] > 0)
                return;
            modelNumber[index] = 9;
            k += 1;
        }
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> lines = CollectionUtil.toArrayList(input);
        MonadSolver monadSolver = new MonadSolver();
        for (int i = 0; i < lines.size(); i++) {
            monadSolver.reverse(lines.get(lines.size() - 1 - i));
            logger.info(monadSolver.getValue());
        }


        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {

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
