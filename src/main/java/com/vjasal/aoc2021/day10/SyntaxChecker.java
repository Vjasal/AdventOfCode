package com.vjasal.aoc2021.day10;

import com.vjasal.util.CollectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SyntaxChecker {

    private static final Map<Character, Character> brackets = new HashMap<Character, Character>(4) {{
        put('(', ')');
        put('[', ']');
        put('{', '}');
        put('<', '>');
    }};

    private static final Map<Character, Long> syntaxErrorPoints = new HashMap<Character, Long>(4) {{
        put(')', 3L);
        put(']', 57L);
        put('}', 1197L);
        put('>', 25137L);
    }};

    private static final Map<Character, Long> autocompletePoints = new HashMap<Character, Long>(4) {{
        put(')', 1L);
        put(']', 2L);
        put('}', 3L);
        put('>', 4L);
    }};

    public static long syntaxErrorScore(String line) {
        Stack<Character> stack = new Stack<>();
        for (char bracket : CollectionUtil.toCharList(line)) {
            if (brackets.containsKey(bracket)) {
                stack.push(brackets.get(bracket));
            } else {
                char expectedBracket = stack.pop();
                if (bracket != expectedBracket) {
                    return syntaxErrorPoints.get(bracket);
                }
            }
        }
        return 0;
    }

    public static long autocompleteScore(String line) {
        Stack<Character> stack = new Stack<>();
        for (char bracket : CollectionUtil.toCharList(line)) {
            if (brackets.containsKey(bracket)) {
                stack.push(brackets.get(bracket));
            } else {
                char expectedBracket = stack.pop();
                if (bracket != expectedBracket) {
                    return -1;
                }
            }
        }
        long result = 0;
        while (!stack.isEmpty()) {
            result = result * 5 + autocompletePoints.get(stack.pop());
        }
        return result;
    }
}
