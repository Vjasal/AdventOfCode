package com.vjasal.type;

public record Range<T extends Comparable<T>>(T minimum, T maximum) {

    public Range(T minimum, T maximum) {
        if (minimum.compareTo(maximum) <= 0) {
            this.minimum = minimum;
            this.maximum = maximum;
        } else {
            this.minimum = maximum;
            this.maximum = minimum;
        }
    }

    public boolean contains(T value) {
        if (value == null)
            return false;
        return minimum.compareTo(value) <= 0 && maximum.compareTo(value) >= 0;
    }

    public boolean contains(Range<T> range) {
        if (range == null)
            return false;
        return contains(range.minimum) && contains(range.maximum);
    }

    public boolean overlaps(Range<T> range) {
        if (range == null)
            return false;
        return contains(range.minimum) || contains(range.maximum) || range.contains(minimum);
    }

    @Override
    public String toString() {
        return "[" + minimum + "," + maximum + "]";
    }
}
