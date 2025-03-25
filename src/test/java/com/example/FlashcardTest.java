package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class FlashcardTest {

    @Test
    public void testFlashcardCreation() {
        Flashcard flashcard = new Flashcard("A large body of water that is surrounded by land", "Lake");
        assertEquals("A large body of water that is surrounded by land", flashcard.getQuestion());
        assertEquals("Lake", flashcard.getAnswer());
    }

    @Test
    public void testIncrementCorrectCount() {
        Flashcard flashcard = new Flashcard("A domesticated carnivorous mammal that is often kept as a pet", "Dog");
        flashcard.incrementCorrectCount();
        assertEquals(1, flashcard.getCorrectCount());
    }

    @Test
    public void testIncrementIncorrectCount() {
        Flashcard flashcard = new Flashcard("A large, heavy machine used for carrying materials", "Crane");
        flashcard.incrementIncorrectCount();
        assertEquals(1, flashcard.getIncorrectCount());
    }

    @Test
    public void testAnotherFlashcard() {
        Flashcard flashcard = new Flashcard("A device that measures temperature", "Thermometer");
        assertEquals("Thermometer", flashcard.getAnswer());
    }

    @Test
    public void testAuthorCard() {
        Flashcard flashcard = new Flashcard("A person who writes books, articles, or other written works", "Author");
        assertEquals("Author", flashcard.getAnswer());
    }
}
