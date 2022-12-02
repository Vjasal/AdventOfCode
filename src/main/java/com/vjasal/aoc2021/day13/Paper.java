package com.vjasal.aoc2021.day13;

import com.vjasal.util.CollectionUtil;
import com.vjasal.util.vectors.Tuple2;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Paper {

    private static final Pattern dotPattern = Pattern.compile("^(\\d+),(\\d+)$");
    private static final Pattern foldPattern = Pattern.compile("^fold along (\\w)=(\\d+)$");

    private final Set<Tuple2<Integer, Integer>> dots = new HashSet<>();

    public Paper(String input) {
        for (String coordinates : CollectionUtil.toLinkedList(input)) {
            Matcher matcher = dotPattern.matcher(coordinates);
            if (!matcher.find()) throw new IllegalArgumentException("Wrong input format!");
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            dots.add(new Tuple2<>(x, y));
        }
    }

    public void execFold(String fold) {
        Matcher matcher = foldPattern.matcher(fold);
        if (!matcher.find()) throw new IllegalArgumentException("Wrong fold format!");

        int line = Integer.parseInt(matcher.group(2));
        switch (matcher.group(1)) {
            case "x" -> foldX(line);
            case "y" -> foldY(line);
            default -> throw new IllegalArgumentException("Wrong fold format!");
        }
    }

    public int countDots() {
        return dots.size();
    }

    private void foldX(int line) {
        Set<Tuple2<Integer, Integer>> foldedDots = dots.stream()
                .filter(v -> v.val1() > line)
                .collect(Collectors.toSet());

        dots.removeAll(foldedDots);
        for (Tuple2<Integer, Integer> dot : foldedDots) {
            dots.add(new Tuple2<>(2 * line - dot.val1(), dot.val2()));
        }
    }

    private void foldY(int line) {
        Set<Tuple2<Integer, Integer>> foldedDots = dots.stream()
                .filter(v -> v.val2() > line)
                .collect(Collectors.toSet());

        dots.removeAll(foldedDots);
        for (Tuple2<Integer, Integer> dot : foldedDots) {
            dots.add(new Tuple2<>(dot.val1(), 2 * line - dot.val2()));
        }
    }

    @Override
    public String toString() {
        int maxX = dots.stream().mapToInt(Tuple2::val1).max().orElseThrow(IllegalStateException::new);
        int maxY = dots.stream().mapToInt(Tuple2::val2).max().orElseThrow(IllegalStateException::new);

        StringBuilder sb = new StringBuilder("\n");
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                sb.append(dots.contains(new Tuple2<>(x, y)) ? "##" : "  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
