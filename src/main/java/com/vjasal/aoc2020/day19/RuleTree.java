package com.vjasal.aoc2020.day19;

import java.util.*;

public class RuleTree {

    private final Map<Integer, RuleNode> rules = new HashMap<>();

    public RuleTree(List<String> ruleLines) {
        for (String line : ruleLines) {
            addRule(line);
        }
    }

    public boolean consume(String value) {
        if (!rules.containsKey(0))
            throw new IllegalStateException("No rule with id 0");
        return consume(0, value).contains(value.length());
    }

    private Set<Integer> consume(int ruleId, String value) {
        String ruleVal = rules.get(ruleId).getValue();
        if (ruleVal != null) {
            return value.startsWith(ruleVal) ? Collections.singleton(ruleVal.length()) : Collections.emptySet();
        } else {
            Set<Integer> ret = new HashSet<>();
            for (List<Integer> rules : rules.get(ruleId).getChildNodes()) {
                Set<Integer> accumulator = new HashSet<>();
                accumulator.add(0);
                for (int rule : rules) {
                    Set<Integer> next = new HashSet<>();
                    for (int acc : accumulator) {
                        Set<Integer> result = consume(rule, value.substring(acc));
                        for (int c : result) {
                            next.add(c + acc);
                        }
                    }
                    accumulator = next;
                }
                ret.addAll(accumulator);
            }
            return ret;
        }
    }

    public void replaceRule(String inputLine) {
        RuleNode node = new RuleNode(inputLine);
        if (!rules.containsKey(node.getId()))
            throw new IllegalStateException("Can't replace rule that doesn't exist!");
        rules.put(node.getId(), node);
    }

    private void addRule(String inputLine) {
        RuleNode node = new RuleNode(inputLine);
        rules.put(node.getId(), node);
    }


}
