package com.vjasal.aoc2023.day23;

import com.vjasal.type.tuple.Tuple2;
import com.vjasal.type.vector.Vector2;

import java.util.LinkedList;
import java.util.List;

public class Node {
    private final Vector2<Integer> position;
    private final List<Tuple2<Node, Integer>> nextNodes = new LinkedList<>();

    public Node(Vector2<Integer> position) {
        this.position = position;
    }

    public Vector2<Integer> getPosition() {
        return position;
    }

    public void addNextNode(Node next, Integer distance) {
        nextNodes.add(Tuple2.valueOf(next, distance));
    }

    public List<Tuple2<Node, Integer>> getNextNodes() {
        return nextNodes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(position);
        sb.append(" -> ");
        for (Tuple2<Node, Integer> next : nextNodes) {
            sb.append(next.val1().position).append(":").append(next.val2()).append(" ");
        }
        return sb.toString();
    }
}
