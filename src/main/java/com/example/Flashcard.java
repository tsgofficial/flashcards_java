package com.example;

import java.time.Instant;

public class Flashcard {

    final private String question;
    final private String answer;
    private int correctCount;
    private int incorrectCount;
    private long lastIncorrectTime;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.correctCount = 0;
        this.incorrectCount = 0;
        this.lastIncorrectTime = 0;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void incrementCorrectCount() {
        this.correctCount++;
    }

    public int getIncorrectCount() {
        return incorrectCount;
    }

    public void incrementIncorrectCount() {
        this.incorrectCount++;
        this.lastIncorrectTime = Instant.now().toEpochMilli();
    }

    public long getLastIncorrectTime() {
        return lastIncorrectTime;
    }
}
