package com.vjasal.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionUtil {

    public static Set<Character> toCharSet(String s) {
        return s.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
    }

    public static List<Character> toCharList(String s) {
        return s.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    }

    public static List<Integer> toIntList(String s) {
        return s.chars().boxed().filter(c -> c >= '0' && c <= '9').map(c -> c - 48).collect(Collectors.toList());
    }

    public static List<String> toLinkedListOfLines(String s) {
        List<String> lines = new LinkedList<>();
        try (Scanner scanner = new Scanner(new StringReader(s))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        }
        return lines;
    }

    public static List<String> toArrayListOfLines(String s) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(s))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ignored) {}
        return lines;
    }

}
