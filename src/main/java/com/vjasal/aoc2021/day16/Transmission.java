package com.vjasal.aoc2021.day16;

public class Transmission {

    private final String signal;

    public Transmission(String signal) {
        this.signal = signal;
    }

    public Packet readPacket() {
        return readPacket(0);
    }

    private Packet readPacket(int index) {
        Packet packet = new Packet(read(index, 3), read(index + 3, 3));

        if (packet.getId() == 4) {
            long value = 0;
            int i = index + 6;
            int x;
            do {
                x = read(i, 5);
                i += 5;
                value = (value << 4) | (x & 0xf);
            } while (x >> 4 == 1);
            packet.setValue(value);
            packet.setLength(i - index);
        } else {
            if (read(index + 6, 1) == 0) {
                int length = read(index + 7, 15);
                int len = 0;
                while (length > len) {
                    Packet child = readPacket(index + 22 + len);
                    packet.addPacket(child);
                    len += child.getLength();
                }
                packet.setLength(22 + len);
            } else {
                int length = read(index + 7, 11);
                int len = 0;
                for (int k = 0; k < length; k++) {
                    Packet child = readPacket(index + 18 + len);
                    packet.addPacket(child);
                    len += child.getLength();
                }
                packet.setLength(18 + len);
            }
        }

        return packet;
    }

    private int read(int index, int length) {
        int result = 0;
        for (int i = 0; i < length; i++) {
            result = (result << 1) | getBit(index + i);
        }
        return result;
    }

    private int getBit(int index) {
        int i = index / 4;
        int x = Integer.parseInt(signal.substring(i, i + 1), 16);
        return (x >> (3 - (index % 4))) & 1;
    }
}
