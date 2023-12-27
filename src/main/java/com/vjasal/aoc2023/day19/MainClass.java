package com.vjasal.aoc2023.day19;

import com.vjasal.type.tuple.Tuple2;
import com.vjasal.type.vector.Vector2;
import com.vjasal.type.vector.Vector4;
import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());
    private static final Pattern workflowPattern = Pattern.compile("^(\\w+)\\{(.+)}$");
    private static final Pattern partPattern = Pattern.compile("^\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)}$");

    public MainClass() {
        super(2023, 19);
    }

    @Override
    public long solvePuzzle1(String input) {
        String workflowLines = CollectionUtil.toListOfSections(input).get(0);
        String partLines = CollectionUtil.toListOfSections(input).get(1);

        Map<String, List<Workflow>> workflows = new HashMap<>();
        for (String line : CollectionUtil.toLinkedList(workflowLines)) {
            Matcher matcher = workflowPattern.matcher(line);
            if (!matcher.find())
                throw new IllegalArgumentException();
            workflows.put(matcher.group(1),
                    Arrays.stream(matcher.group(2).split(",")).map(Workflow::new).toList());
        }

        Queue<Tuple2<Vector4<Integer>, String>> queue = new LinkedList<>();
        for (String line : CollectionUtil.toLinkedList(partLines)) {
            Matcher matcher = partPattern.matcher(line);
            if (!matcher.find())
                throw new IllegalArgumentException();
            queue.add(Tuple2.valueOf(
                    Vector4.valueOf(
                            Integer.parseInt(matcher.group(1)),
                            Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(3)),
                            Integer.parseInt(matcher.group(4))),
                    "in"));
        }

        long result = 0;
        while (!queue.isEmpty()) {
            Tuple2<Vector4<Integer>, String> current = queue.poll();

            Vector4<Integer> part = current.val1();
            String workflowId = current.val2();

            int k = 0;
            String nextWorkflow = null;
            while (nextWorkflow == null)
                nextWorkflow = workflows.get(workflowId).get(k++).getNext(part);

            if (nextWorkflow.equals("A")) {
                result += part.val1() + part.val2() + part.val3() + part.val4();
                continue;
            }
            if (nextWorkflow.equals("R")) {
                continue;
            }
            queue.add(Tuple2.valueOf(part, nextWorkflow));
        }
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        String workflowLines = CollectionUtil.toListOfSections(input).get(0);

        Map<String, List<Workflow>> workflows = new HashMap<>();
        for (String line : CollectionUtil.toLinkedList(workflowLines)) {
            Matcher matcher = workflowPattern.matcher(line);
            if (!matcher.find())
                throw new IllegalArgumentException();
            workflows.put(matcher.group(1),
                    Arrays.stream(matcher.group(2).split(",")).map(Workflow::new).toList());
        }

        Queue<Tuple2<Vector4<Vector2<Integer>>, String>> queue = new LinkedList<>();
        queue.add(Tuple2.valueOf(Vector4.valueOf(
                Vector2.valueOf(1, 4000),
                Vector2.valueOf(1, 4000),
                Vector2.valueOf(1, 4000),
                Vector2.valueOf(1, 4000)),
                "in"));

        long result = 0;
        while (!queue.isEmpty()) {
            Tuple2<Vector4<Vector2<Integer>>, String> current = queue.poll();
            Vector4<Vector2<Integer>> value = current.val1();
            String workflowId = current.val2();

            if (Objects.equals(workflowId, "A")) {
                result += (long) (value.val1().val2() - value.val1().val1() + 1) *
                        (value.val2().val2() - value.val2().val1() + 1) *
                        (value.val3().val2() - value.val3().val1() + 1) *
                        (value.val4().val2() - value.val4().val1() + 1);
                continue;
            }
            if (Objects.equals(workflowId, "R")) {
                continue;
            }

            List<Workflow> workflowList = workflows.get(workflowId);

            LinkedList<Tuple2<Vector4<Vector2<Integer>>, String>> next = workflowList.get(0).split(value);
            int k = 1;
            while (next.getLast().val2() == null) {
                Tuple2<Vector4<Vector2<Integer>>, String> x = next.removeLast();
                next.addAll(workflowList.get(k++).split(x.val1()));
            }
            queue.addAll(next);
        }

        logger.info("Result: " + result);
        return result;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);
    }
}
