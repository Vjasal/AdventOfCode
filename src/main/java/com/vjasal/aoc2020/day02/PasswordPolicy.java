package com.vjasal.aoc2020.day02;

class PasswordPolicy {

    private final String password;
    private final char letter;
    private final int min;
    private final int max;

    PasswordPolicy(String password, String letter, String min, String max) {
        this.password = password;
        this.letter = letter.charAt(0);
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    String getPassword() {
        return password;
    }

    char getLetter() {
        return letter;
    }

    int getMin() {
        return min;
    }

    int getMax() {
        return max;
    }
}
