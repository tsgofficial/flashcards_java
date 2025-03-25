package com.example;

import java.util.Collections;
import java.util.List;

public class RecentMistakesFirstSorter implements CardOrganizer {

    final private String order;

    public RecentMistakesFirstSorter(String order) {
        this.order = order;
    }

    @Override
    public List<Flashcard> organize(List<Flashcard> flashcards) {
        if (order.equals("recent-mistakes-first")) {
            Collections.sort(flashcards, (card1, card2)
                    -> Long.compare(card2.getLastIncorrectTime(), card1.getLastIncorrectTime())
            );
        }
        return flashcards;
    }
}
