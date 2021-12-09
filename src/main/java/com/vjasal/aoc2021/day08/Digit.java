package com.vjasal.aoc2021.day08;

public class Digit {

    private final String value;

    public Digit(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Digit digit = (Digit) o;
        return hashCode() == digit.hashCode();
    }

    @Override
    public int hashCode() {
        return value.chars().reduce(1, (a, b) -> a * b);
    }

    @Override
    public String toString() {
        return value;
    }
}
