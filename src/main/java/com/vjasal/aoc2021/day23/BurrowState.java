package com.vjasal.aoc2021.day23;

import com.vjasal.type.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A state of burrow consists of 8 amphipods, each in its unique
 * position (int from 0 to F). Positions are as follows:
 *
 * #############
 * #12.3.4.5.67#
 * ###8#9#A#B###
 *   #C#D#E#F#
 *   #########
 *
 * If an amphipod is in a room, it can make a move to a position in a hallway
 * (for example: 8 -> 1, 8 -> 2, 8 -> 3 ... 8 -> 7), or it can move from
 * a hallway to its unique room, but only if that room is empty, or it only has
 * amphipod of the same type (for example amphipod A can move to position C or
 * to position 8, but only if C is occupied by amphipod A).
 * Amphipod cannot make a move to or through an already occupied space in
 * a hallway (for example if 3 is taken, then amphipod from a position 8 can
 * only move to position 1 or 2).
 *
 * State is final if all amphipods are in their unique rooms. Final state:
 *
 * #############
 * #...........#
 * ###A#B#C#D###
 *   #A#B#C#D#
 *   #########
 *
 */
public class BurrowState implements Comparable<BurrowState> {

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

        put(0xC, Collections.singletonList(0x8));
        put(0xD, Collections.singletonList(0x9));
        put(0xE, Collections.singletonList(0xA));
        put(0xF, Collections.singletonList(0xB));
    }};

    private final Set<Amphipod> amphipods = new HashSet<>(8);
    private final int cost;

    public BurrowState() {
        amphipods.add(new Amphipod("A", 0xE));
        amphipods.add(new Amphipod("A", 0xF));
        amphipods.add(new Amphipod("B", 0x8));
        amphipods.add(new Amphipod("B", 0x9));
        amphipods.add(new Amphipod("C", 0xC));
        amphipods.add(new Amphipod("C", 0xD));
        amphipods.add(new Amphipod("D", 0xA));
        amphipods.add(new Amphipod("D", 0xB));
        cost = 0;
    }

    private BurrowState(BurrowState other, Amphipod amphipod, Tuple2<Integer, Integer> move) {
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

    public List<BurrowState> getLegalMoves() {
        List<BurrowState> legalMoves = new LinkedList<>();
        for (Amphipod amphipod : amphipods) {
            for (Tuple2<Integer, Integer> move : amphipod.getLegalMoves(amphipods, graph)) {
                legalMoves.add(new BurrowState(this, amphipod, move));
            }
        }
        return legalMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BurrowState other = (BurrowState) o;
        return Objects.equals(amphipods, other.amphipods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amphipods);
    }

    @Override
    public int compareTo(BurrowState other) {
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
        sb.append("#\n  #########");

        return sb.toString();
    }
}
