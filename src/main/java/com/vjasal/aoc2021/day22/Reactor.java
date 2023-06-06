package com.vjasal.aoc2021.day22;

import com.vjasal.util.CollectionUtil;
import com.vjasal.type.tuple.Tuple3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reactor {

    private static final Pattern pattern = Pattern.compile(
            "^(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)$");

    private final List<Command> commands = new LinkedList<>();

    public Reactor(String input) {
        for (String command : CollectionUtil.toLinkedList(input)) {
            commands.add(new Command(command));
        }
    }

    public long count(int bound) {
        boolean[][][] grid = new boolean[2 * bound + 1][2 * bound + 1][2 * bound + 1];
        for (Command command : commands) {
            int x0 = Math.max(command.min.val1(), -bound);
            int y0 = Math.max(command.min.val2(), -bound);
            int z0 = Math.max(command.min.val3(), -bound);
            int x1 = Math.min(command.max.val1(), bound);
            int y1 = Math.min(command.max.val2(), bound);
            int z1 = Math.min(command.max.val3(), bound);

            for (int x = x0; x <= x1; x++) {
                for (int y = y0; y <= y1; y++) {
                    for (int z = z0; z <= z1; z++) {
                        grid[bound + x][bound + y][bound + z] = command.value;
                    }
                }
            }
        }

        long sum = 0;
        for (int x = -bound; x <= bound; x++) {
            for (int y = -bound; y <= bound; y++) {
                for (int z = -bound; z <= bound; z++) {
                    sum += grid[bound + x][bound + y][bound + z] ? 1 : 0;
                }
            }
        }

        return sum;
    }

    public long count() {
        List<Integer> linesX = new ArrayList<>();
        List<Integer> linesY = new ArrayList<>();
        List<Integer> linesZ = new ArrayList<>();

        for (Command command : commands) {
            linesX.add(command.min.val1());
            linesY.add(command.min.val2());
            linesZ.add(command.min.val3());

            linesX.add(command.max.val1() + 1);
            linesY.add(command.max.val2() + 1);
            linesZ.add(command.max.val3() + 1);
        }
        linesX.sort(Integer::compare);
        linesY.sort(Integer::compare);
        linesZ.sort(Integer::compare);

        boolean[][][] grid = new boolean[linesX.size()][linesX.size()][linesX.size()];
        for (Command command : commands) {
            int x0 = linesX.indexOf(command.min.val1());
            int y0 = linesY.indexOf(command.min.val2());
            int z0 = linesZ.indexOf(command.min.val3());
            int x1 = linesX.indexOf(command.max.val1() + 1);
            int y1 = linesY.indexOf(command.max.val2() + 1);
            int z1 = linesZ.indexOf(command.max.val3() + 1);

            for (int x = x0; x < x1; x++) {
                for (int y = y0; y < y1; y++) {
                    for (int z = z0; z < z1; z++) {
                        grid[x][y][z] = command.value;
                    }
                }
            }
        }

        long sum = 0;
        for (int x = 0; x < linesX.size() - 1; x++) {
            for (int y = 0; y < linesY.size() - 1; y++) {
                for (int z = 0; z < linesZ.size() - 1; z++) {
                    sum += grid[x][y][z] ? (long) (linesX.get(x + 1) - linesX.get(x)) *
                            (linesY.get(y + 1) - linesY.get(y)) *
                            (linesZ.get(z + 1) - linesZ.get(z)) : 0;
                }
            }
        }

        return sum;
    }

    private static class Command {
        private final boolean value;
        private final Tuple3<Integer, Integer, Integer> min;
        private final Tuple3<Integer, Integer, Integer> max;

        public Command(String command) {
            Matcher matcher = pattern.matcher(command);
            if (!matcher.find()) throw new IllegalArgumentException();

            int x0 = Integer.parseInt(matcher.group(2));
            int x1 = Integer.parseInt(matcher.group(3));
            int y0 = Integer.parseInt(matcher.group(4));
            int y1 = Integer.parseInt(matcher.group(5));
            int z0 = Integer.parseInt(matcher.group(6));
            int z1 = Integer.parseInt(matcher.group(7));

            this.value = Objects.equals(matcher.group(1), "on");
            this.min = new Tuple3<>(x0, y0, z0);
            this.max = new Tuple3<>(x1, y1, z1);
        }
    }
}
