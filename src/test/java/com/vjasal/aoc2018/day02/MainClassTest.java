package com.vjasal.aoc2018.day02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainClassTest {

    @Test
    public void solvePuzzle1() {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        Assertions.assertNotNull(input, "Input not found");
        Assertions.assertEquals(6474, mainClass.solvePuzzle1(input));
    }

    @Test
    public void solvePuzzle2() {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        Assertions.assertNotNull(input, "Input not found");
        Assertions.assertEquals(0, mainClass.solvePuzzle2(input));
    }
}