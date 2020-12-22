package com.vjasal.aoc2020.day22;

import com.vjasal.util.AocMainClass;
import com.vjasal.util.CollectionUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class MainClass extends AocMainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    MainClass() {
        super(2020, 22);
    }

    @Override
    public long solvePuzzle1(String input) {
        List<String> sections = CollectionUtil.toListOfSections(input);
        Deck player1 = new Deck(sections.get(0));
        Deck player2 = new Deck(sections.get(1));

        while (!player1.isEmpty() && !player2.isEmpty()) {
            int card1 = player1.remove(0);
            int card2 = player2.remove(0);

            if (card1 > card2) {
                player1.add(card1);
                player1.add(card2);
            } else {
                player2.add(card2);
                player2.add(card1);
            }
        }

        int result = player2.isEmpty() ? player1.getResult() : player2.getResult();
        logger.info("Result: " + result);
        return result;
    }

    @Override
    public long solvePuzzle2(String input) {
        List<String> sections = CollectionUtil.toListOfSections(input);

        Deck player1 = new Deck(sections.get(0));
        Deck player2 = new Deck(sections.get(1));

        Deck winner = recursiveCombat(player1, player2);
        int result = winner.getResult();
        logger.info("Result: " + result);
        return result;
    }

    private Deck recursiveCombat(Deck player1, Deck player2) {
        Set<GameState> seenStates = new HashSet<>();
        while (!player1.isEmpty() && !player2.isEmpty()) {
            GameState state = new GameState(player1, player2);
            if (seenStates.contains(state))
                return player1;
            seenStates.add(state);

            int card1 = player1.remove(0);
            int card2 = player2.remove(0);

            if (card1 <= player1.size() && card2 <= player2.size()) {
                Deck winner = recursiveCombat(player1.subDeck(card1), player2.subDeck(card2));
                if (winner.getPlayerId() == player1.getPlayerId()) {
                    player1.add(card1);
                    player1.add(card2);
                } else {
                    player2.add(card2);
                    player2.add(card1);
                }
            } else {
                if (card1 > card2) {
                    player1.add(card1);
                    player1.add(card2);
                } else {
                    player2.add(card2);
                    player2.add(card1);
                }
            }
        }

        return player2.isEmpty() ? player1 : player2;
    }

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        String input = mainClass.getInput();
        logger.info("Input:\n" + input);

        String test = "" +
                "Player 1:\n" +
                "43\n" +
                "19\n" +
                "\n" +
                "Player 2:\n" +
                "2\n" +
                "29\n" +
                "14";

        mainClass.solvePuzzle1(input);
        mainClass.solvePuzzle2(input);

    }
}
