package com.vjasal.aoc2023.day23;

import com.vjasal.type.Grid;
import com.vjasal.type.tuple.Tuple2;
import com.vjasal.type.tuple.Tuple3;
import com.vjasal.type.tuple.Tuple4;
import com.vjasal.type.vector.Vector2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public MainClass() {
        super(2023, 23);
    }

    @Override
    public long solvePuzzle1(String input) {
        Grid<String> map = CollectionUtil.toGrid(input);

        Vector2<Integer> start = Vector2.valueOf(1, 0);
        Vector2<Integer> end = Vector2.valueOf(map.maxX() - 1, map.maxY());

        Map<Vector2<Integer>, Node> nodes = new HashMap<>();
        nodes.put(start, new Node(start));
        nodes.put(end, new Node(end));

        Queue<Tuple4<Vector2<Integer>, Vector2.Direction, Integer, Node>> queue = new LinkedList<>();
        Set<Tuple2<Vector2<Integer>, Vector2.Direction>> seen = new HashSet<>();

        queue.add(Tuple4.valueOf(Vector2.add(start, Vector2.Direction.DOWN), Vector2.Direction.DOWN, 1, nodes.get(start)));
        seen.add(Tuple2.valueOf(start, Vector2.Direction.DOWN));

        while (!queue.isEmpty()) {
            Tuple4<Vector2<Integer>, Vector2.Direction, Integer, Node> current = queue.poll();
            Vector2<Integer> position = current.val1();
            Vector2.Direction direction = current.val2();
            int distance = current.val3();
            Node prevNode = current.val4();

            if (nodes.containsKey(position)) {
                prevNode.addNextNode(nodes.get(position), distance);
                continue;
            }

            Tuple2<Vector2<Integer>, Vector2.Direction> posAndDir = Tuple2.valueOf(position, direction);
            if (seen.contains(posAndDir))
                continue;
            seen.add(posAndDir);

            List<Tuple2<Vector2<Integer>, Vector2.Direction>> next = Arrays.stream(Vector2.Direction.values())
                    .filter(d -> d != direction.back())
                    .map(d -> Tuple2.valueOf(Vector2.add(position, d), d))
                    .filter(t -> !map.get(t.val1(), "#").equals("#"))
                    .toList();
            if (next.size() > 1) {
                Node nextNode = new Node(position);
                nodes.put(position, nextNode);
                prevNode.addNextNode(nodes.get(position), distance);
                next.stream().filter(t -> {
                    String expected = switch (t.val2()) {
                        case LEFT -> "<";
                        case RIGHT -> ">";
                        case UP -> "^";
                        case DOWN -> "v";
                    };
                    return map.get(t.val1(), "#").equals(".") ||
                            map.get(t.val1(), "#").equals(expected);
                }).forEach(t -> queue.add(Tuple4.valueOf(t.val1(), t.val2(), 1, nextNode)));
            } else {
                for (Tuple2<Vector2<Integer>, Vector2.Direction> nextPos : next) {
                    queue.add(Tuple4.valueOf(nextPos.val1(), nextPos.val2(), distance + 1, prevNode));
                }
            }
        }

        int result = distance(nodes.get(start), nodes.get(end), new HashSet<>());
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        Grid<String> map = CollectionUtil.toGrid(input);

        Vector2<Integer> start = Vector2.valueOf(1, 0);
        Vector2<Integer> end = Vector2.valueOf(map.maxX() - 1, map.maxY());

        Map<Vector2<Integer>, Node> nodes = new HashMap<>();
        nodes.put(start, new Node(start));
        nodes.put(end, new Node(end));

        Queue<Tuple4<Vector2<Integer>, Vector2.Direction, Integer, Node>> queue = new LinkedList<>();
        Set<Tuple2<Vector2<Integer>, Vector2.Direction>> seen = new HashSet<>();

        queue.add(Tuple4.valueOf(Vector2.add(start, Vector2.Direction.DOWN), Vector2.Direction.DOWN, 1, nodes.get(start)));
        seen.add(Tuple2.valueOf(start, Vector2.Direction.DOWN));

        while (!queue.isEmpty()) {
            Tuple4<Vector2<Integer>, Vector2.Direction, Integer, Node> current = queue.poll();
            Vector2<Integer> position = current.val1();
            Vector2.Direction direction = current.val2();
            int distance = current.val3();
            Node prevNode = current.val4();

            Tuple2<Vector2<Integer>, Vector2.Direction> posAndDir = Tuple2.valueOf(position, direction);
            if (seen.contains(posAndDir))
                continue;
            seen.add(posAndDir);

            List<Tuple2<Vector2<Integer>, Vector2.Direction>> next = Arrays.stream(Vector2.Direction.values())
                    .filter(d -> d != direction.back())
                    .map(d -> Tuple2.valueOf(Vector2.add(position, d), d))
                    .filter(t -> !map.get(t.val1(), "#").equals("#"))
                    .toList();
            if (next.size() > 1 || position.equals(end)) {
                if (!nodes.containsKey(position)) {
                    Node nextNode = new Node(position);
                    nodes.put(position, nextNode);
                }
                prevNode.addNextNode(nodes.get(position), distance);
                next.forEach(t -> queue.add(Tuple4.valueOf(t.val1(), t.val2(), 1, nodes.get(position))));
            } else {
                for (Tuple2<Vector2<Integer>, Vector2.Direction> nextPos : next) {
                    queue.add(Tuple4.valueOf(nextPos.val1(), nextPos.val2(), distance + 1, prevNode));
                }
            }
        }

        int result = distance(nodes.get(start), nodes.get(end), new HashSet<>());
        logger.info("Result: " + result);
        return result;
    }

    private int distance(Node from, Node to, Set<Node> seen) {
        if (Objects.equals(from.getPosition(), to.getPosition()))
            return 0;

        int d = Integer.MIN_VALUE;
        seen.add(from);
        for (Tuple2<Node, Integer> next : from.getNextNodes()) {
            if (seen.contains(next.val1()))
                continue;
            d = Math.max(d, distance(next.val1(), to, seen) + next.val2());
        }
        seen.remove(from);

        return d;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
