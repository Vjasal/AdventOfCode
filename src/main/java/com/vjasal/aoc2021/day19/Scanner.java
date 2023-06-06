package com.vjasal.aoc2021.day19;

import com.vjasal.util.CollectionUtil;
import com.vjasal.type.tuple.Tuple2;
import com.vjasal.type.tuple.Tuple3;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Scanner {

    private static final Logger logger = Logger.getLogger(Scanner.class.getName());

    private static final Pattern idPattern = Pattern.compile("^--- scanner (\\d+) ---$");
    private static final Pattern beaconPattern = Pattern.compile("^(-?\\d+),(-?\\d+),(-?\\d+)$");

    private static final int[][][] rotations = {
            {{ 1,  0,  0}, { 0,  1,  0}, { 0,  0,  1}},
            {{ 1,  0,  0}, { 0,  0, -1}, { 0,  1,  0}},
            {{ 1,  0,  0}, { 0, -1,  0}, { 0,  0, -1}},
            {{ 1,  0,  0}, { 0,  0,  1}, { 0, -1,  0}},

            {{ 0,  0,  1}, { 0,  1,  0}, {-1,  0,  0}},
            {{ 0,  0,  1}, { 1,  0,  0}, { 0,  1,  0}},
            {{ 0,  0,  1}, { 0, -1,  0}, { 1,  0,  0}},
            {{ 0,  0,  1}, {-1,  0,  0}, { 0, -1,  0}},

            {{-1,  0,  0}, { 0,  1,  0}, { 0,  0, -1}},
            {{-1,  0,  0}, { 0,  0, -1}, { 0, -1,  0}},
            {{-1,  0,  0}, { 0, -1,  0}, { 0,  0,  1}},
            {{-1,  0,  0}, { 0,  0,  1}, { 0,  1,  0}},

            {{ 0,  0, -1}, { 0,  1,  0}, { 1,  0,  0}},
            {{ 0,  0, -1}, { 1,  0,  0}, { 0, -1,  0}},
            {{ 0,  0, -1}, { 0, -1,  0}, {-1,  0,  0}},
            {{ 0,  0, -1}, {-1,  0,  0}, { 0,  1,  0}},

            {{ 0,  1,  0}, {-1,  0,  0}, { 0,  0,  1}},
            {{ 0,  1,  0}, { 0,  0, -1}, {-1,  0,  0}},
            {{ 0,  1,  0}, { 1,  0,  0}, { 0,  0, -1}},
            {{ 0,  1,  0}, { 0,  0,  1}, { 1,  0,  0}},

            {{ 0, -1,  0}, { 1,  0,  0}, { 0,  0,  1}},
            {{ 0, -1,  0}, { 0,  0,  1}, {-1,  0,  0}},
            {{ 0, -1,  0}, {-1,  0,  0}, { 0,  0, -1}},
            {{ 0, -1,  0}, { 0,  0, -1}, { 1,  0,  0}},
    };

    private final Set<Tuple3<Integer, Integer, Integer>> beacons = new HashSet<>();
    private final int id;

    public Scanner(String input) {
        List<String> lines = CollectionUtil.toLinkedList(input);

        Matcher idMatcher = idPattern.matcher(lines.get(0));
        if (!idMatcher.find()) throw new IllegalStateException();
        id = Integer.parseInt(idMatcher.group(1));

        lines.stream().skip(1).forEach(s -> {
            Matcher matcher = beaconPattern.matcher(s);
            if (!matcher.find()) throw new IllegalStateException();
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            int z = Integer.parseInt(matcher.group(3));
            beacons.add(new Tuple3<>(x, y, z));
        });
    }

    public List<Tuple3<Integer, Integer, Integer>> getRotatedBeacons(int rotation) {
        int[][] r = rotations[rotation];
        return beacons.stream().map(v ->  new Tuple3<>(
                v.val1() * r[0][0] + v.val2() * r[0][1] + v.val3() * r[0][2],
                v.val1() * r[1][0] + v.val2() * r[1][1] + v.val3() * r[1][2],
                v.val1() * r[2][0] + v.val2() * r[2][1] + v.val3() * r[2][2])
        ).collect(Collectors.toList());
    }

    public void addMissingBeacons(Scanner other, int rotation, Tuple3<Integer, Integer, Integer> position) {
        int[][] r = rotations[rotation];
        beacons.addAll(other.beacons.stream().map(v -> new Tuple3<>(
                v.val1() * r[0][0] + v.val2() * r[0][1] + v.val3() * r[0][2] + position.val1(),
                v.val1() * r[1][0] + v.val2() * r[1][1] + v.val3() * r[1][2] + position.val2(),
                v.val1() * r[2][0] + v.val2() * r[2][1] + v.val3() * r[2][2] + position.val3()))
                .toList());
    }

    public Set<Tuple3<Integer, Integer, Integer>> getBeacons() {
        return beacons;
    }

    /**
     * Try to match this scanner to other and return matching rotation and position of other scanner.
     * @param other scanner
     * @return Vector, containing rotation index as first parameter and a position of other scanner.
     * If other scanner is too far (less than 12 common beacons), first param will be -1, and position will be (0, 0, 0).
     */
    public Tuple2<Integer, Tuple3<Integer, Integer, Integer>> getRotation(Scanner other) {
        for (int rotation = 0; rotation < 24; rotation++) {
            Map<Tuple3<Integer, Integer, Integer>, Integer> seen = new HashMap<>(
                    beacons.size() * other.beacons.size());
            for (Tuple3<Integer, Integer, Integer> beacon : beacons) {
                for (Tuple3<Integer, Integer, Integer> otherBeacon : other.getRotatedBeacons(rotation)) {
                    Tuple3<Integer, Integer, Integer> v = Tuple3.subtract(beacon, otherBeacon);
                    seen.merge(v, 1, Integer::sum);
                }
            }

            Optional<Map.Entry<Tuple3<Integer, Integer, Integer>, Integer>> result = seen.entrySet().stream()
                    .filter(e -> e.getValue() >= 12).findAny();

            if (result.isPresent()) {
                return new Tuple2<>(rotation, result.get().getKey());
            }
        }

        return new Tuple2<>(-1, Tuple3.ZERO);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Scanner ").append(id).append(" ---\n");
        for (Tuple3<Integer, Integer, Integer> b : beacons) {
            sb.append(b.val1()).append(",").append(b.val2()).append(",").append(b.val3()).append("\n");
        }
        return sb.toString();
    }
}
