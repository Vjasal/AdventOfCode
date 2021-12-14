package com.vjasal.aoc2018.day03;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MainClassTest {

    private final MainClass mainClass = new MainClass();

    @Test
    public void solvePuzzle1() {
        String input = mainClass.getInput();
        assertNotNull("Input not found", input);
        assertEquals(104241, mainClass.solvePuzzle1(input));
    }

    @Test
    public void solvePuzzle2() {
        String input = mainClass.getInput();
        assertNotNull("Input not found", input);
        assertEquals(806, mainClass.solvePuzzle2(input));
    }
}