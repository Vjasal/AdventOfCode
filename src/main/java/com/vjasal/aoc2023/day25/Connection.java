package com.vjasal.aoc2023.day25;

import java.util.Objects;

public record Connection(String a, String b) {
    @Override
    public int hashCode() {
        return Objects.hashCode(a) + Objects.hashCode(b);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Connection other)) return false;

        return (Objects.equals(this.a, other.a) && Objects.equals(this.b, other.b)) ||
                (Objects.equals(this.a, other.b) && Objects.equals(this.b, other.a));
    }

    @Override
    public String toString() {
        return a + "-" + b;
    }
}
