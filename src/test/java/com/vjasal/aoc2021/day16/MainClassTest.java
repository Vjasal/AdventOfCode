package com.vjasal.aoc2021.day16;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MainClassTest {

    private final MainClass mainClass = new MainClass();

    @Test
    public void solvePuzzle1() {
        String input = mainClass.getInput();
        assertNotNull("Input not found", input);
        assertEquals(1014, mainClass.solvePuzzle1(input));
    }

    @Test
    public void solvePuzzle2() {
        String input = mainClass.getInput();
        assertNotNull("Input not found", input);
        assertEquals(1922490999789L, mainClass.solvePuzzle2(input));
    }
}