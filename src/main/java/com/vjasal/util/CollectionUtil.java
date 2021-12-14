package com.vjasal.util;

import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionUtil {

    @SafeVarargs
    public static <T> List<T> asLinkedList(T... a) {
        return new LinkedList<>(Arrays.asList(a));
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

    private static void readData(Collection<String> collection, String input, String delimiter) {
        try (Scanner scanner = new Scanner(new StringReader(input.trim()))) {
            scanner.useDelimiter(delimiter);
            while (scanner.hasNext()) {
                collection.add(scanner.next());
            }
        }
    }
}
