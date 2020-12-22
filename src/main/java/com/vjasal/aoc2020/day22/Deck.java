package com.vjasal.aoc2020.day22;

import com.vjasal.util.CollectionUtil;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Deck implements Iterable<Integer> {

    private final static Pattern idPattern = Pattern.compile("^Player (\\d+):$");

    private final List<Integer> cards;
    private final int playerId;

    Deck(String input) {
        List<String> lines = CollectionUtil.toArrayListOfLines(input);
        Matcher matcher = idPattern.matcher(lines.get(0));
        if (!matcher.find())
            throw new IllegalArgumentException();

        this.playerId = Integer.parseInt(matcher.group(1));
        this.cards = lines.stream()
                .skip(1)
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
    }

    Deck(Deck other) {
        this.playerId = other.playerId;
        this.cards = new ArrayList<>(other.cards);
    }

    private Deck(Deck other, int toIndex) {
        this.playerId = other.playerId;
        this.cards = new ArrayList<>(other.cards.subList(0, toIndex));
    }

    Deck subDeck(int toIndex) {
        return new Deck(this, toIndex);
    }

    int size() {
        return cards.size();
    }

    boolean isEmpty() {
        return cards.isEmpty();
    }

    boolean add(Integer value) {
        return cards.add(value);
    }

    Integer remove(int index) {
        return cards.remove(index);
    }

    int getPlayerId() {
        return playerId;
    }

    int getResult() {
        int result = 0;
        for (int i = 0; i < cards.size(); i++) {
            result += cards.get(i) * (cards.size() - i);
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Deck))
            return false;
        Deck other = (Deck) obj;
        return playerId == other.playerId && cards.equals(other.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, cards);
    }

    @Override
    public Iterator<Integer> iterator() {
        return cards.iterator();
    }

    @Override
    public Spliterator<Integer> spliterator() {
        return cards.spliterator();
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        cards.forEach(action);
    }

    @Override
    public String toString() {
        return playerId + " " + cards.toString();
    }
}
