package com.vjasal.aoc2019.day18;

import java.util.*;
import java.util.logging.Logger;

public class MazeGraph {

    private final static Logger logger = Logger.getLogger(MazeGraph.class.getName());

    private final Map<Character, MazeNode> nodes;
    private final long numberOfKeys;

    public MazeGraph(Maze maze) {
        this.nodes = new HashMap<>();
        for (int y = 0; y < maze.getHeight(); y++) {
            for (int x = 0; x < maze.getWidth(); x++) {
                char element = maze.getMazeElement(x, y);
                if (element != '#' && element != '.') {
                    if (!this.nodes.containsKey(element)) {
                        this.nodes.put(element, new MazeNode(element, x, y));
                    }
                    MazeNode node = this.nodes.get(element);
                    parseReachableNodes(maze, node);
                }
            }
        }

        this.numberOfKeys = this.nodes.keySet().stream().filter(Character::isLowerCase).count();
    }

    public int findShortestRoute() {
        Set<GraphPathState> seen = new HashSet<>();
        Queue<GraphPathState> queue = new PriorityQueue<>();

        queue.add(new GraphPathState(this.nodes.get('@')));

        GraphPathState current;
        while ((current = queue.poll()) != null) {
            if (seen.contains(current)) continue;
            seen.add(current);

            if (current.keys.size() == this.numberOfKeys) {
                return current.getDistance();
            }

            Map<MazeNode, Integer> reachableKeys = getReachableKeys(current.nodes.get(0), current.keys, null);
            // logger.info(current + " -> " + reachableKeys.toString());
            for (Map.Entry<MazeNode, Integer> entry : reachableKeys.entrySet()) {
                queue.add(new GraphPathState(current, entry.getKey(), entry.getValue(), 0));
            }
        }

        return -1;
    }

    public int findShortestRoute2() {
        List<MazeNode> workers = new ArrayList<>(4);
        workers.add(this.nodes.get('0'));
        workers.add(this.nodes.get('1'));
        workers.add(this.nodes.get('2'));
        workers.add(this.nodes.get('3'));

        Set<GraphPathState> seen = new HashSet<>();
        Queue<GraphPathState> queue = new PriorityQueue<>();

        queue.add(new GraphPathState(workers));

        GraphPathState current;
        while ((current = queue.poll()) != null) {
            if (seen.contains(current)) continue;
            seen.add(current);

            if (current.keys.size() == this.numberOfKeys) {
                return current.getDistance();
            }

            for (int i = 0; i < 4; i++) {
                Map<MazeNode, Integer> reachableKeys = getReachableKeys(current.nodes.get(i), current.keys, null);
                // logger.info(index + " " + current + " -> " + reachableKeys.toString());
                for (Map.Entry<MazeNode, Integer> entry : reachableKeys.entrySet()) {
                    queue.add(new GraphPathState(current, entry.getKey(), entry.getValue(), i));
                }
            }
        }

        return -1;
    }

    private Map<MazeNode, Integer> getReachableKeys(MazeNode currentNode, Set<Character> keys, Set<Character> seen) {
        if (seen == null) {
            seen = new HashSet<>();
            seen.add(currentNode.getName());
        }

        Set<Character> nextSeen = new HashSet<>(seen);
        for (MazeNode nextNode : currentNode.getReachableNodes()) {
            nextSeen.add(nextNode.getName());
        }

        Map<MazeNode, Integer> reachableKeys = new HashMap<>();
        for (MazeNode nextNode : currentNode.getReachableNodes()) {
            if (seen.contains(nextNode.getName())) continue;

            int distance = currentNode.getDistanceToNode(nextNode);
            if (nextNode.isUncollectedKey(keys)) {
                reachableKeys.put(nextNode, distance);
            } else if (nextNode.isPassable(keys)) {
                Map<MazeNode, Integer> indirect = getReachableKeys(nextNode, keys, nextSeen);
                for (Map.Entry<MazeNode, Integer> entry : indirect.entrySet()) {
                    reachableKeys.put(entry.getKey(), entry.getValue() + distance);
                }
            }
        }

        return reachableKeys;
    }

    private void parseReachableNodes(Maze maze, MazeNode startNode) {
        Queue<PathState> queue = new LinkedList<>();
        Set<PathState> seen = new HashSet<>();

        queue.add(new PathState(startNode.getX(), startNode.getY(), 0));

        PathState current;
        while ((current = queue.poll()) != null) {
            if (seen.contains(current)) continue;
            seen.add(current);

            int x = current.x;
            int y = current.y;

            char element = maze.getMazeElement(x, y);

            if (element == '#') continue;
            if (element == '.' || element == startNode.getName()) {
                queue.add(new PathState(current, +1, 0));
                queue.add(new PathState(current, -1, 0));
                queue.add(new PathState(current, 0, +1));
                queue.add(new PathState(current, 0, -1));
                continue;
            }

            if (!this.nodes.containsKey(element)) {
                this.nodes.put(element, new MazeNode(element, x, y));
            }
            startNode.addReachableNode(this.nodes.get(element), current.distance);
        }
    }

    private class GraphPathState implements Comparable {

        private final List<MazeNode> nodes;
        private final Set<Character> keys;
        private final List<Integer> distances;

        private GraphPathState(MazeNode node) {
            this.nodes = new ArrayList<>(1);
            this.nodes.add(node);
            this.keys = new HashSet<>();
            this.distances = Collections.singletonList(0);
        }

        private GraphPathState(List<MazeNode> nodes) {
            this.nodes = nodes;
            this.keys = new HashSet<>();
            this.distances = Arrays.asList(0, 0, 0, 0);
        }

        private GraphPathState(GraphPathState other, MazeNode nextNode, int distanceToNext, int index) {
            this.nodes = new ArrayList<>(other.nodes);
            this.nodes.set(index, nextNode);
            this.keys = new HashSet<>(other.keys);
            this.keys.add(nextNode.getName());
            this.distances = new ArrayList<>(other.distances);
            this.distances.set(index, this.distances.get(index) + distanceToNext);
        }

        private int getDistance() {
            return this.distances.stream().reduce(0, Integer::sum);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.keys, this.distances);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof GraphPathState)) return false;
            GraphPathState other = (GraphPathState) obj;
            return this.keys.equals(other.keys) && this.distances.equals(other.distances);
        }


        @Override
        public int compareTo(Object obj) {
            if (this == obj) return 0;
            if (!(obj instanceof GraphPathState))
                throw new ClassCastException();
            GraphPathState other = (GraphPathState) obj;
            return Integer.compare(this.getDistance(), other.getDistance());
        }

        @Override
        public String toString() {
            return "{ w=" + this.nodes.toString() + " d=" + this.distances + " k=" + this.keys + "}";
        }
    }

    private class PathState {
        private final int x;
        private final int y;
        private final int distance;

        private PathState(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        private PathState(PathState other, int dx, int dy) {
            this.x = other.x + dx;
            this.y = other.y + dy;
            this.distance = other.distance + 1;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.x, this.y);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof PathState)) return false;
            PathState other = (PathState) obj;
            return this.x == other.x && this.y == other.y;
        }

        @Override
        public String toString() {
            return x + ":" + y;
        }
    }
}