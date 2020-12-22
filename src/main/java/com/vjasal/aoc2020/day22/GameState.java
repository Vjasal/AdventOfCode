package com.vjasal.aoc2020.day22;

import java.util.Objects;

public class GameState {

    private final Deck player1;
    private final Deck player2;

    GameState(Deck player1, Deck player2) {
        this.player1 = new Deck(player1);
        this.player2 = new Deck(player2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player1, player2);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof GameState))
            return false;
        GameState other = (GameState) obj;
        return player1.equals(other.player1) && player2.equals(other.player2);
    }
}
