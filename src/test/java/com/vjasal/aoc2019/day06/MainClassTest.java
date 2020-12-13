package com.vjasal.aoc2019.day06;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MainClassTest {

    @Test
    public void solvePuzzle1() {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        assertNotNull("Input not found", input);
        assertEquals(322508, mainClass.solvePuzzle1(input));
    }

    @Test
    public void solvePuzzle2() {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        assertNotNull("Input not found", input);
        assertEquals(496, mainClass.solvePuzzle2(input));
    }
}