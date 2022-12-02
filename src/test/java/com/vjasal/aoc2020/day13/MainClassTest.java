package com.vjasal.aoc2020.day13;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainClassTest {

    @Test
    public void solvePuzzle1() {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        Assertions.assertNotNull(input, "Input not found");
        Assertions.assertEquals(3035, mainClass.solvePuzzle1(input));
    }

    @Test
    public void solvePuzzle2() {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        Assertions.assertNotNull(input, "Input not found");
        Assertions.assertEquals(725169163285238L, mainClass.solvePuzzle2(input));
    }
}