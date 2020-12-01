package com.vjasal.aoc2019.day18;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MazeNode {

    private final Map<MazeNode, Integer> reachableNodes;
    private final Character name;
    private final int x;
    private final int y;

    public MazeNode(Character name, int x, int y) {
        this.reachableNodes = new HashMap<>();
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public char getName() {
        return this.name;
    }

    public boolean isUncollectedKey(Set<Character> keys) {
        return Character.isLowerCase(this.name) && !keys.contains(this.name);
    }

    public boolean isPassable(Set<Character> keys) {
        if (this.name == '@') return true;
        if (Character.isDigit(this.name)) return true;
        if (Character.isLowerCase(this.name) && keys.contains(this.name)) return true;
        return Character.isUpperCase(this.name) && keys.contains(Character.toLowerCase(this.name));
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void addReachableNode(MazeNode node, int distance) {
        if (!this.reachableNodes.containsKey(node) || this.reachableNodes.get(node) > distance) {
            this.reachableNodes.put(node, distance);
        }
    }

    public Set<MazeNode> getReachableNodes() {
        return this.reachableNodes.keySet();
    }

    public int getDistanceToNode(MazeNode other) {
        if (!this.reachableNodes.containsKey(other))
            throw new IllegalArgumentException("No path from [" + this.name + "] to [" + other.name + "]");
        return this.reachableNodes.get(other);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MazeNode)) return false;
        MazeNode other = (MazeNode) obj;
        return this.name.equals(other.name);
    }

    @Override
    public String toString() {
        return "[" + name + "]";
    }
}
