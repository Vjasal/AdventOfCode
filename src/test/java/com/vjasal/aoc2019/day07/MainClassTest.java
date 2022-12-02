package com.vjasal.aoc2019.day07;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MainClassTest {

    private final MainClass mainClass = new MainClass();

    @Test
    public void solvePuzzle1() {
        String input = mainClass.getInput();
        Assertions.assertNotNull(input, "Input not found");
        Assertions.assertEquals(101490, mainClass.solvePuzzle1(input));
    }

    @Test
    public void solvePuzzle2() {
        String input = mainClass.getInput();
        Assertions.assertNotNull(input, "Input not found");
        Assertions.assertEquals(61019896, mainClass.solvePuzzle2(input));
    }
}