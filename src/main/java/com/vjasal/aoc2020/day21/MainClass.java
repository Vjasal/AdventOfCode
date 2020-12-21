package com.vjasal.aoc2020.day21;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2020, 21);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<Food> foods = parseInput(input);

        Map<String, Set<String>> allergenMap = new HashMap<>();
        for (Food food : foods) {
            for (String allergen : food.getAllergens()) {
                if (!allergenMap.containsKey(allergen)) {
                    allergenMap.put(allergen, new HashSet<>(food.getIngredients()));
                } else {
                    allergenMap.get(allergen).retainAll(food.getIngredients());
                }
            }
        }

        Set<String> ingredientsWithAllergens = allergenMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        long result = foods.stream()
                .flatMap(food -> food.getIngredients().stream())
                .filter(ingredient -> !ingredientsWithAllergens.contains(ingredient))
                .count();

        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<Food> foods = parseInput(input);

        Map<String, Set<String>> allergenMap = new HashMap<>();
        for (Food food : foods) {
            for (String allergen : food.getAllergens()) {
                if (!allergenMap.containsKey(allergen)) {
                    allergenMap.put(allergen, new HashSet<>(food.getIngredients()));
                } else {
                    allergenMap.get(allergen).retainAll(food.getIngredients());
                }
            }
        }

        List<String> allergens = new ArrayList<>(allergenMap.keySet());
        for (int i = 0; i < allergens.size(); i++) {
            if (allergenMap.get(allergens.get(i)).size() == 1) {

                // remove ingredient from all other sets
                for (String key : allergenMap.keySet()) {
                    if (key.equals(allergens.get(i)))
                        continue;
                    allergenMap.get(key).removeAll(allergenMap.get(allergens.get(i)));
                }

                // remove allergen from list and go back to beginning
                allergens.remove(i);
                i = -1;
            }
        }

        List<String> result = allergenMap.keySet().stream()
                .sorted()
                .map(a -> getFirst(allergenMap.get(a)))
                .collect(Collectors.toList());

        StringJoiner joiner = new StringJoiner(",");
        for (String ingredient : result) {
            joiner.add(ingredient);
        }
        logger.info("Result: " + joiner.toString());
        return 0;
    }

    private List<Food> parseInput(String input) {
        List<Food> foods = new LinkedList<>();
        for (String line : CollectionUtil.toLinkedListOfLines(input)) {
            foods.add(new Food(line));
        }
        return foods;
    }

    private String getFirst(Set<String> set) {
        if (set.size() != 1)
            throw new IllegalArgumentException();
        return set.iterator().next();
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
