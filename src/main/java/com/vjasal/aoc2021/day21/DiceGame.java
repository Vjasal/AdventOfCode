package com.vjasal.aoc2021.day21;

import com.vjasal.util.CollectionUtil;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiceGame {

    private static final Logger logger = Logger.getLogger(DiceGame.class.getName());

    private static final Pattern pattern = Pattern.compile("^Player (\\d+) starting position: (\\d+)$");

    private final Dice dice = new Dice();
    private final PlayerState[] players = new PlayerState[2];

    public DiceGame(String input) {
        List<String> lines = CollectionUtil.toArrayList(input);

        for (int i = 0; i < 2; i++) {
            Matcher matcher = pattern.matcher(lines.get(i));
            if (!matcher.find()) throw new IllegalArgumentException();

            int id = Integer.parseInt(matcher.group(1));
            int position = Integer.parseInt(matcher.group(2));
            players[i] = new PlayerState(id, position);
        }
    }

    public int play() {
        int currentPlayer = 0;
        int turn = 1;

        while (true) {
            players[currentPlayer].makeMove(dice.getScore());
            if (players[currentPlayer].score >= 1000) {
                break;
            }
            currentPlayer = (currentPlayer + 1) % 2;
            turn += 1;
        }

        currentPlayer = (currentPlayer + 1) % 2;
        logger.info(turn * 3 + " " + players[currentPlayer].score);


        return players[currentPlayer].score * turn * 3;

    }

    private static class Dice {

        private int state = 0;

        public int getScore() {
            int result = 0;
            for (int i = 0; i < 3; i++)
                result += roll();
            return result;
        }

        private int roll() {
            state = state % 100 + 1;
            return state;
        }
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
