package com.vjasal.aoc2022.day19;

import com.vjasal.type.tuple.Tuple2;
import com.vjasal.type.tuple.Tuple4;
import com.vjasal.type.vector.Vector2;
import com.vjasal.type.vector.Vector3;
import com.vjasal.type.vector.Vector4;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Blueprint(int id, int oreRobotCost, int clayRobotCost, Vector2<Integer> obsidianRobotCost, Vector2<Integer> geodeRobotCost) {

    private static final Logger logger = Logger.getLogger(Blueprint.class.getName());

    private static final Pattern pattern = Pattern.compile("^Blueprint (\\d+): " +
            "Each ore robot costs (\\d+) ore\\. " +
            "Each clay robot costs (\\d+) ore\\. " +
            "Each obsidian robot costs (\\d+) ore and (\\d+) clay\\. " +
            "Each geode robot costs (\\d+) ore and (\\d+) obsidian\\.$");

    public static Blueprint parse(String input) {
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) throw new IllegalArgumentException();
        int id            = Integer.parseInt(matcher.group(1));
        int oreRobotCost  = Integer.parseInt(matcher.group(2));
        int clayRobotCost = Integer.parseInt(matcher.group(3));
        Vector2<Integer> obsidianRobotCost = Vector2.valueOf(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)));
        Vector2<Integer> geodeRobotCost    = Vector2.valueOf(Integer.parseInt(matcher.group(6)), Integer.parseInt(matcher.group(7)));
        return new Blueprint(id, oreRobotCost, clayRobotCost, obsidianRobotCost, geodeRobotCost);
    }

    public int simulate(int time) {
        logger.info("Simulate " + id);

        Queue<Tuple4<Vector3<Integer>, Vector4<Integer>, Integer, Integer>> queue = new PriorityQueue<>((a, b) -> Integer.compare(b.val3(), a.val3()));
        Set<Tuple2<Vector3<Integer>, Vector4<Integer>>> seen = new HashSet<>();
        queue.add(new Tuple4<>(Vector3.ZERO, Vector4.valueOf(1, 0, 0, 0), time, 0));

        Vector3<Integer> oreRobotCost      = Vector3.valueOf(this.oreRobotCost, 0, 0);
        Vector3<Integer> clayRobotCost     = Vector3.valueOf(this.clayRobotCost, 0, 0);
        Vector3<Integer> obsidianRobotCost = Vector3.valueOf(this.obsidianRobotCost.val1(), this.obsidianRobotCost.val2(), 0);
        Vector3<Integer> geodeRobotCost    = Vector3.valueOf(this.geodeRobotCost.val1(), 0, this.geodeRobotCost.val2());

        Vector4<Integer> oreRobot      = Vector4.valueOf(1, 0, 0, 0);
        Vector4<Integer> clayRobot     = Vector4.valueOf(0, 1, 0, 0);
        Vector4<Integer> obsidianRobot = Vector4.valueOf(0, 0, 1, 0);
        Vector4<Integer> geodeRobot    = Vector4.valueOf(0, 0, 0, 1);

        int maxDemandOre = Math.max(Math.max(this.oreRobotCost, this.clayRobotCost), Math.max(this.obsidianRobotCost.val1(), this.geodeRobotCost.val1()));
        int maxDemandClay = this.obsidianRobotCost.val2();
        int maxDemandObsidian = this.geodeRobotCost.val2();

        int maxGeode = 0;

        while (!queue.isEmpty()) {
            Tuple4<Vector3<Integer>, Vector4<Integer>, Integer, Integer> state = queue.poll();
            Tuple2<Vector3<Integer>, Vector4<Integer>> s = Tuple2.valueOf(state.val1(), state.val2());

            if (seen.contains(s)) continue;
            seen.add(s);

            if (state.val3() == 0) {
                maxGeode = Math.max(maxGeode, state.val4());
                continue;
            }

            Vector3<Integer> materials  = state.val1();
            Vector4<Integer> robots = state.val2();
            Vector3<Integer> prodRobots = Vector3.valueOf(robots.val1(), robots.val2(), robots.val3());
            int timeLeft = state.val3();

            if (Vector3.lessEqualsThan(geodeRobotCost, materials)) {
                Tuple4<Vector3<Integer>, Vector4<Integer>, Integer, Integer> next = new Tuple4<>(
                        Vector3.subtract(Vector3.add(materials, prodRobots), geodeRobotCost),
                        Vector4.add(robots, geodeRobot),
                        timeLeft - 1,
                        state.val4() + robots.val4());
                queue.add(next);
                continue;
            }

            if (Vector3.lessEqualsThan(oreRobotCost, materials) && maxDemandOre > prodRobots.val1()) {
                Tuple4<Vector3<Integer>, Vector4<Integer>, Integer, Integer> next = new Tuple4<>(
                        Vector3.subtract(Vector3.add(materials, prodRobots), oreRobotCost),
                        Vector4.add(robots, oreRobot),
                        timeLeft - 1,
                        state.val4() + robots.val4());
                queue.add(next);
            }

            if (Vector3.lessEqualsThan(clayRobotCost, materials) && maxDemandClay > prodRobots.val2()) {
                Tuple4<Vector3<Integer>, Vector4<Integer>, Integer, Integer> next = new Tuple4<>(
                        Vector3.subtract(Vector3.add(materials, prodRobots), clayRobotCost),
                        Vector4.add(robots, clayRobot),
                        timeLeft - 1,
                        state.val4() + robots.val4());
                queue.add(next);
            }

            if (Vector3.lessEqualsThan(obsidianRobotCost, materials) && maxDemandObsidian > prodRobots.val3()) {
                Tuple4<Vector3<Integer>, Vector4<Integer>, Integer, Integer> next = new Tuple4<>(
                        Vector3.subtract(Vector3.add(materials, prodRobots), obsidianRobotCost),
                        Vector4.add(robots, obsidianRobot),
                        timeLeft - 1,
                        state.val4() + robots.val4());
                queue.add(next);
            }

            Tuple4<Vector3<Integer>, Vector4<Integer>, Integer, Integer> n = new Tuple4<>(Vector3.add(materials, prodRobots), state.val2(), timeLeft - 1, state.val4() + robots.val4());
            queue.add(n);
        }

        return maxGeode;
    }
}
