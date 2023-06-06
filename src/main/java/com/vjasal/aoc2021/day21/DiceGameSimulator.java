package com.vjasal.aoc2021.day21;

import com.vjasal.util.CollectionUtil;
import com.vjasal.type.tuple.Tuple4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiceGameSimulator {

    private static final Logger logger = Logger.getLogger(DiceGameSimulator.class.getName());


    private static final Pattern pattern = Pattern.compile("^Player (\\d+) starting position: (\\d+)$");

    private static final Map<Integer, Integer> diceScores = new HashMap<Integer, Integer>() {{
        put(3, 1);
        put(4, 3);
        put(6, 6);
        put(5, 7);
        put(7, 6);
        put(8, 3);
        put(9, 1);
    }};

    private final Map<Tuple4<Integer, Integer, Integer, Integer>, Long> seenStates = new HashMap<>();

    private final PlayerState[] players = new PlayerState[2];

    public DiceGameSimulator(String input) {
        List<String> lines = CollectionUtil.toArrayList(input);

        for (int i = 0; i < 2; i++) {
            Matcher matcher = pattern.matcher(lines.get(i));
            if (!matcher.find()) throw new IllegalArgumentException();

            int id = Integer.parseInt(matcher.group(1));
            int position = Integer.parseInt(matcher.group(2));
            players[i] = new PlayerState(id, position);
        }
    }

    public static long day21pt2(int player) {
        int p1 = 10, p2 = 7;
        int p1Score = 0, p2Score = 0;
        long wins = 0L;
        boolean p1Turn = true;

        wins += diceRoll(p1, p2, p1Score, p2Score, 9, p1Turn, player);
        wins += diceRoll(p1, p2, p1Score, p2Score, 8, p1Turn, player) * 3;
        wins += diceRoll(p1, p2, p1Score, p2Score, 7, p1Turn, player) * 6;
        wins += diceRoll(p1, p2, p1Score, p2Score, 6, p1Turn, player) * 7;
        wins += diceRoll(p1, p2, p1Score, p2Score, 5, p1Turn, player) * 6;
        wins += diceRoll(p1, p2, p1Score, p2Score, 4, p1Turn, player) * 3;
        wins += diceRoll(p1, p2, p1Score, p2Score, 3, p1Turn, player);

        return wins;
    }

    public static long diceRoll(int p1, int p2, int p1Score, int p2Score, int roll, boolean p1Turn, int player) {
        long wins = 0L;

        if (p1Turn) {
            p1 += roll;
            p1Score += (p1 - 1) % 10 + 1;
        } else {
            p2 += roll;
            p2Score += (p2 - 1) % 10 + 1;
        }
        p1Turn = !p1Turn;

        if (p1Score >= 21 || p2Score >= 21) {
            if (player == 1) {
                if (p1Score >= 21) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                if (p2Score >= 21) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } else {
            wins += diceRoll(p1, p2, p1Score, p2Score, 9, p1Turn, player);
            wins += diceRoll(p1, p2, p1Score, p2Score, 8, p1Turn, player) * 3;
            wins += diceRoll(p1, p2, p1Score, p2Score, 7, p1Turn, player) * 6;
            wins += diceRoll(p1, p2, p1Score, p2Score, 6, p1Turn, player) * 7;
            wins += diceRoll(p1, p2, p1Score, p2Score, 5, p1Turn, player) * 6;
            wins += diceRoll(p1, p2, p1Score, p2Score, 4, p1Turn, player) * 3;
            wins += diceRoll(p1, p2, p1Score, p2Score, 3, p1Turn, player);
        }
        return wins;
    }

    // 444356092776315
    // 487862530706573
    public void simulate() {
        int firstPlayerStart = 4;
        int secondPlayerStart = 8;

        long firstPlayerWins = 0;

        for (int turn = 1; turn <= 100; turn++) {
            for (int firstScore = 21; firstScore <= 21 + 9; firstScore++) {
                for (int firstPosition = 1; firstPosition <= 10; firstPosition++) {
                    for (int secondScore = 0; secondScore < 21; secondScore++) {
                        for (int secondPosition = 1; secondPosition <= 10; secondPosition++) {
                            firstPlayerWins += num(turn, firstScore, firstPosition, firstPlayerStart) *
                                    num(turn - 1, secondScore, secondPosition, secondPlayerStart);
                        }
                    }
                }
            }
        }

        logger.info("1 -> " + firstPlayerWins);
    }

    // number of ways a given position can be achieved
    public long num(int turn, int score, int position, int start) {
        if (turn == 1)
            return position == start && score == 0 ? 1 : 0;

        if (turn < 1 || score <= 0 || score - position >= 21)
            return 0;

        Tuple4<Integer, Integer, Integer, Integer> v = new Tuple4<>(turn, score, position, start);
        if (seenStates.containsKey(v))
            return seenStates.get(v);

        long result = 0;
        for (int roll : diceScores.keySet()) {
            int prevPosition = (((position - roll - 1 % 10) + 10) % 10) + 1;
            result += num(turn - 1, score - position, prevPosition, start) * diceScores.get(roll);
        }

        seenStates.put(v, result);

        return result;
    }

    private static class PlayerState {

        private final int id;
        private int position;
        private int score = 0;

        public PlayerState(int id, int position) {
            this.id = id;
            this.position = position;
        }

        public void makeMove(int dice) {
            position = (position + dice - 1) % 10 + 1;
            score += position;
        }
    }

}
