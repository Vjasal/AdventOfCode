package com.vjasal.aoc2020.day07;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass extends AocMainClass {

    private final static Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2020, 7);
    }

    @Override
    public void solvePuzzle1(String input) {
        Map<String, Bag> bags = parseInput(input);
        long result = bags.values().stream().filter(bag -> bag.containsBag("shiny gold")).count();
        logger.info("Result: " + result);
    }

    @Override
    public void solvePuzzle2(String input) {
        Map<String, Bag> bags = parseInput(input);
        int res = bags.get("shiny gold").countBags() - 1;
        logger.info("Result: " + res);
    }

    private Map<String, Bag> parseInput(String input) {
        Pattern linePattern = Pattern.compile("^(\\w+ \\w+) bags contain ((\\d+ \\w+ \\w+ \\w+[,.] ?)*)");
        Pattern contentPattern = Pattern.compile("^(\\d+) (\\w+ \\w+) \\w+\\.?$");

        Map<String, Bag> bags = new HashMap<>();
        for (String line : Util.toLinkedListOfLines(input)) {
            Matcher lineMatcher = linePattern.matcher(line);
            if (lineMatcher.find()) {
                String name    = lineMatcher.group(1);
                String content = lineMatcher.group(2);

                if (!bags.containsKey(name))
                    bags.put(name, new Bag(name));
                if (content == null)
                    continue;

                Bag current = bags.get(name);
                for (String bundle : content.split(", ")) {
                    Matcher contentMatcher = contentPattern.matcher(bundle);

                    if (contentMatcher.find()) {
                        String contentCount = contentMatcher.group(1);
                        String contentName  = contentMatcher.group(2);

                        if (!bags.containsKey(contentName))
                            bags.put(contentName, new Bag(contentName));

                        current.addBag(bags.get(contentName), Integer.parseInt(contentCount));
                    }
                }
            }
        }

        return bags;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
