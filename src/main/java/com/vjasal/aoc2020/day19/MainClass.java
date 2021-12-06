package com.vjasal.aoc2020.day19;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.List;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getSimpleName());

    public MainClass() {
        super(2020, 19);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> sections = CollectionUtil.toListOfSections(input);
        List<String> ruleLines  = CollectionUtil.toLinkedList(sections.get(0));
        List<String> valueLines = CollectionUtil.toLinkedList(sections.get(1));

        RuleTree ruleTree = new RuleTree(ruleLines);

        long result = valueLines.stream().filter(ruleTree::consume).count();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> sections = CollectionUtil.toListOfSections(input);
        List<String> ruleLines  = CollectionUtil.toLinkedList(sections.get(0));
        List<String> valueLines = CollectionUtil.toLinkedList(sections.get(1));

        RuleTree ruleTree = new RuleTree(ruleLines);

        ruleTree.replaceRule("8: 42 | 42 8");
        ruleTree.replaceRule("11: 42 31 | 42 11 31");

        long result = valueLines.stream().filter(ruleTree::consume).count();
        logger.info("Result: " + result);
        return result;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
//        logger.info("Input:\n" + input);

        String test = "0: 4 1 5\n" +
                "1: 2 3 | 3 2\n" +
                "2: 4 4 | 5 5\n" +
                "3: 4 5 | 5 4\n" +
                "4: \"a\"\n" +
                "5: \"b\"\n" +
                "\n" +
                "ababbb\n" +
                "bababa\n" +
                "abbbab\n" +
                "aaabbb\n" +
                "aaaabbb";

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
