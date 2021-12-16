package com.vjasal.aoc2021.day16;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.LongStream;

public class Packet {

    private final int version;
    private final int id;

    private final List<Packet> packets = new LinkedList<>();
    private long value = 0;

    private int length = 0;

    public Packet(int version, int id) {
        this.version = version;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void addPacket(Packet packet) {
        packets.add(packet);
    }

    public int sumVersions() {
        return version + packets.stream().mapToInt(Packet::sumVersions).sum();
    }

    public long getValue() {
        LongStream stream = packets.stream().mapToLong(Packet::getValue);
        switch (id) {
            case 0:
                return stream.sum();
            case 1:
                return stream.reduce(1, (a, b) -> a * b);
            case 2:
                return stream.min().orElseThrow(IllegalStateException::new);
            case 3:
                return stream.max().orElseThrow(IllegalStateException::new);
            case 4:
                return value;
            case 5:
                return packets.get(0).getValue() > packets.get(1).getValue() ? 1 : 0;
            case 6:
                return packets.get(0).getValue() < packets.get(1).getValue() ? 1 : 0;
            case 7:
                return packets.get(0).getValue() == packets.get(1).getValue() ? 1 : 0;
            default:
                throw new IllegalStateException();
        }
    }
}
