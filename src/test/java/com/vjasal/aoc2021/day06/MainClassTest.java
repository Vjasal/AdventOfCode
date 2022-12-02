package com.vjasal.aoc2021.day06;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainClassTest {

    private final MainClass mainClass = new MainClass();

    @Test
    public void solvePuzzle1() {
        String input = mainClass.getInput();
        Assertions.assertNotNull(input, "Input not found");
        Assertions.assertEquals(363101, mainClass.solvePuzzle1(input));
    }

    @Test
    public void solvePuzzle2() {
        String input = mainClass.getInput();
        Assertions.assertNotNull(input, "Input not found");
        Assertions.assertEquals(1644286074024L, mainClass.solvePuzzle2(input));
    }
}