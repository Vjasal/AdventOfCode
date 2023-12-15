package com.vjasal.util;

import com.vjasal.type.Grid;

import java.io.StringReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionUtil {

    @SafeVarargs
    public static <T> List<T> asLinkedList(T... a) {
        return new LinkedList<>(Arrays.asList(a));
    }

    @SafeVarargs
    public static <T> List<T> asArrayList(T... a) {
        return new ArrayList<>(Arrays.asList(a));
    }

    public static <T> List<List<T>> permutations(List<T> list) {
        List<List<T>> permutations = new LinkedList<>();
        if (list.size() <= 1) {
            permutations.add(list);
        } else {
            for (T val : list) {
                List<T> tmp = new LinkedList<>(list);
                tmp.remove(val);
                for (List<T> next : permutations(tmp)) {
                    next.add(val);
                    permutations.add(next);
                }
            }
        }
        return permutations;
    }

    public static Set<Character> toCharSet(String s) {
        return s.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
    }

    public static List<Character> toCharList(String s) {
        return s.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    }

    public static List<Integer> toIntList(String s) {
        return s.chars().boxed().filter(c -> c >= '0' && c <= '9').map(c -> c - 48).collect(Collectors.toList());
    }

    public static ArrayList<String> toListOfSections(String s) {
        return toArrayList(s, "\n\n");
    }

    public static ArrayList<String> toArrayList(String input) {
        return toArrayList(input, "\n");
    }

    public static ArrayList<String> toArrayList(String input, String delimiter) {
        ArrayList<String> arrayList = new ArrayList<>();
        readData(arrayList, input, delimiter);
        return arrayList;
    }

    public static LinkedList<String> toLinkedList(String input) {
        return toLinkedList(input, "\n");
    }

    public static LinkedList<String> toLinkedList(String input, String delimiter) {
        LinkedList<String> linkedList = new LinkedList<>();
        readData(linkedList, input, delimiter);
        return linkedList;
    }

    public static HashSet<String> toHashSet(String input) {
        return toHashSet(input, "\n");
    }

    public static HashSet<String> toHashSet(String input, String delimiter) {
        HashSet<String> hashSet = new HashSet<>();
        readData(hashSet, input, delimiter);
        return hashSet;
    }

    public static Grid<String> toGrid(String input) {
        return toGrid(input, "", String::new);
    }

    public static <T> Grid<T> toGrid(String input, Function<String, T> mapper) {
        return toGrid(input, "", mapper);
    }

    public static <T> Grid<T> toGrid(String input, String lineDelimiter, Function<String, T> mapper) {
        Grid<T> grid = new Grid<>();

        List<String> lines = toArrayList(input);
        for (int y = 0; y < lines.size(); y++) {
            List<String> line = toArrayList(lines.get(y), lineDelimiter);
            for (int x = 0; x < line.size(); x++) {
                T val = mapper.apply(line.get(x));
                if (val != null)
                    grid.put(x, y, val);
            }
        }

        return grid;
    }

    private static void readData(Collection<String> collection, String input, String delimiter) {
        try (Scanner scanner = new Scanner(new StringReader(input.trim()))) {
            scanner.useDelimiter(delimiter);
            while (scanner.hasNext()) {
                collection.add(scanner.next());
            }
        }
    }
}
