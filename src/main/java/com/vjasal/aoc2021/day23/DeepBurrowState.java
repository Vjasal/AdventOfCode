package com.vjasal.aoc2021.day23;

import com.vjasal.type.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

public class DeepBurrowState implements Comparable<DeepBurrowState> {

    private static final Map<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>() {{
        put(0x1, Collections.singletonList(0x2));
        put(0x2, Arrays.asList(0x1, 0x3, 0x8));
        put(0x3, Arrays.asList(0x2, 0x4, 0x8, 0x9));
        put(0x4, Arrays.asList(0x3, 0x5, 0x9, 0xA));
        put(0x5, Arrays.asList(0x4, 0x6, 0xA, 0xB));
        put(0x6, Arrays.asList(0x5, 0x7, 0xB));
        put(0x7, Collections.singletonList(0x6));

        put(0x8, Arrays.asList(0x2, 0x3, 0xC));
        put(0x9, Arrays.asList(0x3, 0x4, 0xD));
        put(0xA, Arrays.asList(0x4, 0x5, 0xE));
        put(0xB, Arrays.asList(0x5, 0x6, 0xF));

        put(0xC, Arrays.asList(0x8, 0x10));
        put(0xD, Arrays.asList(0x9, 0x11));
        put(0xE, Arrays.asList(0xA, 0x12));
        put(0xF, Arrays.asList(0xB, 0x13));

        put(0x10, Arrays.asList(0xC, 0x14));
        put(0x11, Arrays.asList(0xD, 0x15));
        put(0x12, Arrays.asList(0xE, 0x16));
        put(0x13, Arrays.asList(0xF, 0x17));

        put(0x14, Collections.singletonList(0x10));
        put(0x15, Collections.singletonList(0x11));
        put(0x16, Collections.singletonList(0x12));
        put(0x17, Collections.singletonList(0x13));
    }};

    private final Set<Amphipod> amphipods = new HashSet<>(8);
    private final int cost;

    public DeepBurrowState() {
        amphipods.add(new Amphipod("B", 0x8));
        amphipods.add(new Amphipod("B", 0x9));
        amphipods.add(new Amphipod("D", 0xA));
        amphipods.add(new Amphipod("D", 0xB));
        amphipods.add(new Amphipod("D", 0xC));
        amphipods.add(new Amphipod("C", 0xD));
        amphipods.add(new Amphipod("B", 0xE));
        amphipods.add(new Amphipod("A", 0xF));
        amphipods.add(new Amphipod("D", 0x10));
        amphipods.add(new Amphipod("B", 0x11));
        amphipods.add(new Amphipod("A", 0x12));
        amphipods.add(new Amphipod("C", 0x13));
        amphipods.add(new Amphipod("C", 0x14));
        amphipods.add(new Amphipod("C", 0x15));
        amphipods.add(new Amphipod("A", 0x16));
        amphipods.add(new Amphipod("A", 0x17));
        cost = 0;
    }

    private DeepBurrowState(DeepBurrowState other, Amphipod amphipod, Tuple2<Integer, Integer> move) {
        this.amphipods.addAll(other.amphipods.stream().filter(a -> a != amphipod).collect(Collectors.toSet()));
        this.amphipods.add(new Amphipod(amphipod.getType(), move.val1()));
        this.cost = other.cost + move.val2();
    }

    public int getCost() {
        return cost;
    }

    public boolean isFinalState() {
        return amphipods.stream().allMatch(Amphipod::isFinalRoom);
    }

    public List<DeepBurrowState> getLegalMoves() {
        List<DeepBurrowState> legalMoves = new LinkedList<>();
        for (Amphipod amphipod : amphipods) {
            for (Tuple2<Integer, Integer> move : amphipod.getLegalMoves(amphipods, graph)) {
                legalMoves.add(new DeepBurrowState(this, amphipod, move));
            }
        }
        return legalMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeepBurrowState other = (DeepBurrowState) o;
        return Objects.equals(amphipods, other.amphipods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amphipods);
    }

    @Override
    public int compareTo(DeepBurrowState other) {
        return Integer.compare(this.cost, other.cost);
    }

    @Override
    public String toString() {
        Optional<Amphipod> a1 = amphipods.stream().filter(a -> a.getPosition() == 0x1).findFirst();
        Optional<Amphipod> a2 = amphipods.stream().filter(a -> a.getPosition() == 0x2).findFirst();
        Optional<Amphipod> a3 = amphipods.stream().filter(a -> a.getPosition() == 0x3).findFirst();
        Optional<Amphipod> a4 = amphipods.stream().filter(a -> a.getPosition() == 0x4).findFirst();
        Optional<Amphipod> a5 = amphipods.stream().filter(a -> a.getPosition() == 0x5).findFirst();
        Optional<Amphipod> a6 = amphipods.stream().filter(a -> a.getPosition() == 0x6).findFirst();
        Optional<Amphipod> a7 = amphipods.stream().filter(a -> a.getPosition() == 0x7).findFirst();
        Optional<Amphipod> a8 = amphipods.stream().filter(a -> a.getPosition() == 0x8).findFirst();
        Optional<Amphipod> a9 = amphipods.stream().filter(a -> a.getPosition() == 0x9).findFirst();
        Optional<Amphipod> aa = amphipods.stream().filter(a -> a.getPosition() == 0xA).findFirst();
        Optional<Amphipod> ab = amphipods.stream().filter(a -> a.getPosition() == 0xB).findFirst();
        Optional<Amphipod> ac = amphipods.stream().filter(a -> a.getPosition() == 0xC).findFirst();
        Optional<Amphipod> ad = amphipods.stream().filter(a -> a.getPosition() == 0xD).findFirst();
        Optional<Amphipod> ae = amphipods.stream().filter(a -> a.getPosition() == 0xE).findFirst();
        Optional<Amphipod> af = amphipods.stream().filter(a -> a.getPosition() == 0xF).findFirst();
        Optional<Amphipod> a10 = amphipods.stream().filter(a -> a.getPosition() == 0x10).findFirst();
        Optional<Amphipod> a11 = amphipods.stream().filter(a -> a.getPosition() == 0x11).findFirst();
        Optional<Amphipod> a12 = amphipods.stream().filter(a -> a.getPosition() == 0x12).findFirst();
        Optional<Amphipod> a13 = amphipods.stream().filter(a -> a.getPosition() == 0x13).findFirst();
        Optional<Amphipod> a14 = amphipods.stream().filter(a -> a.getPosition() == 0x14).findFirst();
        Optional<Amphipod> a15 = amphipods.stream().filter(a -> a.getPosition() == 0x15).findFirst();
        Optional<Amphipod> a16 = amphipods.stream().filter(a -> a.getPosition() == 0x16).findFirst();
        Optional<Amphipod> a17 = amphipods.stream().filter(a -> a.getPosition() == 0x17).findFirst();

        StringBuilder sb = new StringBuilder("\n#############\n#");
        sb.append(a1.isPresent() ? a1.get() : ".");
        sb.append(a2.isPresent() ? a2.get() : ".");
        sb.append(".");
        sb.append(a3.isPresent() ? a3.get() : ".");
        sb.append(".");
        sb.append(a4.isPresent() ? a4.get() : ".");
        sb.append(".");
        sb.append(a5.isPresent() ? a5.get() : ".");
        sb.append(".");
        sb.append(a6.isPresent() ? a6.get() : ".");
        sb.append(a7.isPresent() ? a7.get() : ".");
        sb.append("#\n###");
        sb.append(a8.isPresent() ? a8.get() : ".");
        sb.append("#");
        sb.append(a9.isPresent() ? a9.get() : ".");
        sb.append("#");
        sb.append(aa.isPresent() ? aa.get() : ".");
        sb.append("#");
        sb.append(ab.isPresent() ? ab.get() : ".");
        sb.append("###\n  #");
        sb.append(ac.isPresent() ? ac.get() : ".");
        sb.append("#");
        sb.append(ad.isPresent() ? ad.get() : ".");
        sb.append("#");
        sb.append(ae.isPresent() ? ae.get() : ".");
        sb.append("#");
        sb.append(af.isPresent() ? af.get() : ".");
        sb.append("#\n  #");
        sb.append(a10.isPresent() ? a10.get() : ".");
        sb.append("#");
        sb.append(a11.isPresent() ? a11.get() : ".");
        sb.append("#");
        sb.append(a12.isPresent() ? a12.get() : ".");
        sb.append("#");
        sb.append(a13.isPresent() ? a13.get() : ".");
        sb.append("#\n  #");
        sb.append(a14.isPresent() ? a14.get() : ".");
        sb.append("#");
        sb.append(a15.isPresent() ? a15.get() : ".");
        sb.append("#");
        sb.append(a16.isPresent() ? a16.get() : ".");
        sb.append("#");
        sb.append(a17.isPresent() ? a17.get() : ".");
        sb.append("#\n  #########");

        return sb.toString();
    }
}
