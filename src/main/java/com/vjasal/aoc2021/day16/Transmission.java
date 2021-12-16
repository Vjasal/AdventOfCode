package com.vjasal.aoc2021.day16;

import com.vjasal.util.CollectionUtil;

import java.util.LinkedList;
import java.util.Queue;

public class Transmission {

    private final Queue<Boolean> bitQueue = new LinkedList<>();

    public Transmission(String input) {
        for (char c : CollectionUtil.toCharList(input)) {
            int x = Character.getNumericValue(c);
            for (int i = 0; i < 4; i++) {
                bitQueue.add((x >> (3 - i % 4) & 1) == 1);
            }
        }
    }

    public Packet readPacket() {
        Packet packet = new Packet(read(3), read(3));

        if (packet.getId() == 4) {
            long value = 0;
            int len = 0;
            int x;
            do {
                x = read(5);
                value = (value << 4) | (x & 0xf);
                len += 5;
            } while (((x >> 4) & 1) == 1);
            packet.setValue(value);
            packet.setLength(3 + 3 + len);
        } else {
            if (read(1) == 0) {
                int length = read(15);
                int len = 0;
                while (length > len) {
                    Packet child = readPacket();
                    packet.addPacket(child);
                    len += child.getLength();
                }
                packet.setLength(3 + 3 + 1 + 15 + len);
            } else {
                int length = read(11);
                int len = 0;
                for (int k = 0; k < length; k++) {
                    Packet child = readPacket();
                    packet.addPacket(child);
                    len += child.getLength();
                }
                packet.setLength(3 + 3 + 1 + 11 + len);
            }
        }

        return packet;
    }

    private int read(int length) {
        int result = 0;
        for (int i = 0; i < length; i++) {
            if (bitQueue.isEmpty()) throw new IllegalStateException("Missing data");
            result = (result << 1) | (bitQueue.poll() ? 1 : 0);
        }
        return result;
    }
}
