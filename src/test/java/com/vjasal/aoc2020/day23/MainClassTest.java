package com.vjasal.aoc2020.day23;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MainClassTest {

    @Test
    public void solvePuzzle1() {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        assertNotNull("Input not found", input);
        assertEquals(49576328, mainClass.solvePuzzle1(input));
    }

    @Test
    public void solvePuzzle2() {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        assertNotNull("Input not found", input);
        assertEquals(511780369955L, mainClass.solvePuzzle2(input));
    }
}