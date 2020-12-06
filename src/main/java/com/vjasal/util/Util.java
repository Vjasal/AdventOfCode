package com.vjasal.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Util {

    public static Set<Character> toCharSet(String s) {
        return s.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
    }

    public static List<Character> toCharList(String s) {
        return s.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    }

}
