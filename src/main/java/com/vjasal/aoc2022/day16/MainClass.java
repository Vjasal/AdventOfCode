package com.vjasal.aoc2022.day16;

import com.vjasal.type.tuple.Tuple2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2022, 16);
    }

    private final Map<String, Integer> seen = new HashMap<>();

    @Override
    public long solvePuzzle1(String input) {
        // Create graph
        Map<String, Valve> valves = new HashMap<>();
        int index = 0;
        for (String line : CollectionUtil.toLinkedList(input)) {
            Valve valve = new Valve(line, index);
            valves.put(valve.getId(), valve);
            index++;
        }

        // Setup connections for each graph (the cost to go to each graph)
        for (Valve valve : valves.values()) {
            Queue<Tuple2<String, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(Tuple2::val2));
            Set<String> seen = new HashSet<>();
            queue.add(new Tuple2<>(valve.getId(), 0));

            while (!queue.isEmpty()) {
                Tuple2<String, Integer> current = queue.poll();

                if (seen.contains(current.val1())) continue;
                seen.add(current.val1());

                valve.setRoute(current.val1(), current.val2());

                for (Map.Entry<String, Integer> next : valves.get(current.val1()).getRoutes().entrySet()) {
                    queue.add(new Tuple2<>(next.getKey(), current.val2() + next.getValue()));
                }
            }
        }

        int result = dfs("AA", 0, 30, 0, valves);
        logger.info("" + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        // Create graph
        Map<String, Valve> valves = new HashMap<>();
        int index = 0;
        for (String line : CollectionUtil.toLinkedList(input)) {
            Valve valve = new Valve(line, index);
            valves.put(valve.getId(), valve);
            index++;
        }

        // Setup connections for each graph (the cost to go to each graph)
        for (Valve valve : valves.values()) {
            Queue<Tuple2<String, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(Tuple2::val2));
            Set<String> seen = new HashSet<>();
            queue.add(new Tuple2<>(valve.getId(), 0));

            while (!queue.isEmpty()) {
                Tuple2<String, Integer> current = queue.poll();

                if (seen.contains(current.val1())) continue;
                seen.add(current.val1());

                valve.setRoute(current.val1(), current.val2());

                for (Map.Entry<String, Integer> next : valves.get(current.val1()).getRoutes().entrySet()) {
                    queue.add(new Tuple2<>(next.getKey(), current.val2() + next.getValue()));
                }
            }
        }

        int result = dfs("AA", 0, 26, 1, valves);
        logger.info("" + result);
        return result;
    }

    private int dfs(String position, long openValves, int time, int players, Map<String, Valve> valves) {
        if (time == 0) return 0;

        String stateHash = position + openValves + time + players;
        if (seen.containsKey(stateHash)) return seen.get(stateHash);

        int answer = players == 0 ? 0 : dfs("AA", openValves, 26, players - 1, valves);
        for (String next : valves.get(position).getRoutes().keySet()) {
            Valve valve = valves.get(next);

            if (valve.getRate() == 0) continue;
            if ((openValves & valve.getBitmask()) != 0) continue;

            int timeLeft = time - valves.get(position).getRoutes().get(next) - 1;
            if (timeLeft < 0) continue;

            answer = Math.max(answer, timeLeft * valve.getRate() + dfs(next, openValves ^ valve.getBitmask(), timeLeft, players, valves));
        }

        seen.put(stateHash, answer);
        return answer;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
