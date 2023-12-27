package com.vjasal.aoc2023.day25;

import com.vjasal.type.tuple.Tuple2;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.security.SecureRandom;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());
    private static final Pattern pattern = Pattern.compile("(\\w+): (.+)$");

    public MainClass() {
        super(2023, 25);
    }

    @Override
    public long solvePuzzle1(String input) {
        Set<String> nodes = new HashSet<>();
        Map<String, Set<String>> con = new HashMap<>();
        for (String line : CollectionUtil.toLinkedList(input)) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find())
                throw new IllegalArgumentException(line);
            nodes.add(matcher.group(1));
            Set<String> c = new HashSet<>();
            for (String next : matcher.group(2).split(" ")) {
                c.add(next);
                nodes.add(next);
            }
            con.put(matcher.group(1), c);
        }

        for (String node : nodes) {
            con.putIfAbsent(node, new HashSet<>());
            for (String next : con.get(node)) {
                con.putIfAbsent(next, new HashSet<>());
                con.get(next).add(node);
            }
        }

        List<String> nodeList = new LinkedList<>(nodes);
        Random random = new SecureRandom();

        for (int k = 0; k < 3; k++) {
            Map<Connection, Integer> count = new HashMap<>();
            for (int i = 0; i < 100; i++) {
                int a = random.nextInt(nodes.size());
                int b = random.nextInt(nodes.size());
                while (b == a) b = random.nextInt(nodes.size());

                String start = nodeList.get(a);
                String end = nodeList.get(b);

                Queue<Tuple2<String, Set<Connection>>> queue = new LinkedList<>();
                Set<String> seen = new HashSet<>();
                queue.add(Tuple2.valueOf(start, new HashSet<>()));

                while (!queue.isEmpty()) {
                    Tuple2<String, Set<Connection>> current = queue.poll();
                    String position = current.val1();
                    seen.add(current.val1());

                    if (current.val1().equals(end)) {
                        for (Connection c : current.val2())
                            count.merge(c, 1, Integer::sum);
                        break;
                    }

                    for (String next : con.get(position)) {
                        if (seen.contains(next))
                            continue;
                        Set<Connection> visited = new HashSet<>(current.val2());
                        visited.add(new Connection(position, next));
                        queue.add(Tuple2.valueOf(next, visited));
                    }
                }
            }
            Connection max = count.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElseThrow();
            con.get(max.a()).remove(max.b());
            con.get(max.b()).remove(max.a());
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> seen = new HashSet<>();
        queue.add(nodeList.get(random.nextInt(nodes.size())));

        while (!queue.isEmpty()) {
            String current = queue.poll();
            seen.add(current);

            for (String next : con.get(current)) {
                if (seen.contains(next))
                    continue;
                queue.add(next);
            }
        }

        // This may sometimes fail (because we use random), but this is good enough
        int result = seen.size() * (nodes.size() - seen.size());
        logger.info("Result: " + result);
        return 0;
    }

    @Override
    public long solvePuzzle2(String input) {

        return 0;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        String test = """
                jqt: rhn xhk nvd
                rsh: frs pzl lsr
                xhk: hfx
                cmg: qnr nvd lhk bvb
                rhn: xhk bvb hfx
                bvb: xhk hfx
                pzl: lsr hfx nvd
                qnr: nvd
                ntq: jqt hfx bvb xhk
                nvd: lhk
                lsr: lhk
                rzs: qnr cmg lsr rsh
                frs: qnr lhk lsr""";

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
