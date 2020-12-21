package com.vjasal.aoc2020.day21;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Food {

    private final static Pattern pattern = Pattern.compile("^((\\w+[ ]?)+) \\(contains ((\\w+[, ]*)+)\\)$");

    private final Set<String> ingredients;
    private final Set<String> allergens;

    Food(String input) {
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find())
            throw new IllegalArgumentException();

        ingredients = Arrays.stream(matcher.group(1).split(" ")).collect(Collectors.toSet());
        allergens   = Arrays.stream(matcher.group(3).split(", ")).collect(Collectors.toSet());
    }

    Set<String> getIngredients() {
        return ingredients;
    }

    Set<String> getAllergens() {
        return allergens;
    }

    @Override
    public String toString() {
        return ingredients.toString() + " contains " + allergens.toString();
    }
}
