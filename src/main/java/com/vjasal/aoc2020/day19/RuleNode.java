package com.vjasal.aoc2020.day19;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleNode {

    private static final Logger logger = Logger.getLogger(RuleNode.class.getName());

    private final static Pattern pattern = Pattern.compile("^(\\d+): (.+)$");

    private final int id;
    private final List<List<Integer>> childNodes = new ArrayList<>(2);

    private String value = null;

    public RuleNode(String input) {
        Matcher matcher = pattern.matcher(input);

        if (!matcher.find())
            throw new IllegalArgumentException("Wrong input");

        this.id = Integer.parseInt(matcher.group(1));

        String val = matcher.group(2);
        if (val.contains("\"")) {
            this.value = val.replace("\"", "");
        } else {
            for (String alternativeRulesVal : val.split(" \\| ")) {
                List<Integer> alternativeRules = new ArrayList<>();
                for (String childRuleId : alternativeRulesVal.split(" ")) {
                    alternativeRules.add(Integer.parseInt(childRuleId));
                }
                this.childNodes.add(alternativeRules);
            }
        }
    }

    public int getId() {
        return this.id;
    }

    public String getValue() {
        return this.value;
    }

    public List<List<Integer>> getChildNodes() {
        return childNodes;
    }
}
