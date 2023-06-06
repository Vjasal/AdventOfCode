package com.vjasal.aoc2022.day20;

import com.vjasal.util.MathUtil;

import java.util.LinkedList;
import java.util.List;

public class SpecialList {

    private Element head = null;
    private int size = 0;

    // add element to the end
    public void add(long value) {
        Element e = new Element(value);
        if (head == null) {
            e.next = e;
            e.prev = e;
            head = e;
        } else {
            e.next = head;
            e.prev = head.prev;
            head.prev.next = e;
            head.prev = e;
        }
        size += 1;
    }

    public void setHead(int value) {
        int index = 0;
        while (index < size && head.value != value) {
            head = head.next;
            index += 1;
        }
    }

    // get value at index
    public long get(int index) {
        if (head == null) throw new NullPointerException();

        Element p = head;
        index = index % size;
        while (--index >= 0) p = p.next;
        return p.value;
    }

    public int indexOf(int value) {
        if (head == null) throw new NullPointerException();

        Element p = head;
        int index = 0;
        while (index < size) {
            if (p.value == value) break;
            p = p.next;
            index += 1;
        }

        return index < size ? index : -1;
    }

    public void mix(int times) {
        if (head == null) throw new NullPointerException();

        List<Element> tmp = new LinkedList<>();
        Element p = head;
        for (int i = 0; i < size; i++) {
            tmp.add(p);
            p = p.next;
        }

        for (int k = 0; k < times; k++) {
            for (Element e : tmp) {
                long val = MathUtil.mod(e.value, size - 1);
                if (val == 0) continue;

                Element n = e;
                while (--val >= 0) n = n.next;

                e.next.prev = e.prev;
                e.prev.next = e.next;

                e.prev = n;
                e.next = n.next;

                n.next.prev = e;
                n.next = e;
            }
        }
    }

    @Override
    public String toString() {
        if (head == null) return "";

        StringBuilder sb = new StringBuilder("[");
        sb.append(head.value);

        Element p = head.next;
        for (int i = 1; i < size; i++) {
            sb.append(", ").append(p.value);
            p = p.next;
        }

        sb.append("]");
        return sb.toString();
    }

    private static class Element {
        private final long value;

        private Element next;
        private Element prev;

        private Element(long value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "E{" + value + "}";
        }
    }
}
