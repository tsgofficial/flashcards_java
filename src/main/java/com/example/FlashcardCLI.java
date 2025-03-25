package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FlashcardCLI {

    private static final String HELP_MESSAGE
            = "Usage: flashcard <cards-file> [options]\n"
            + "Options:\n"
            + "--help          Show help information\n"
            + "--order <order> Organize the order, default 'random'\n"
            + "--repetitions <num> How many times to answer a card correctly\n"
            + "--invertCards   Show question and answer swapped\n";

    private static final String[] VALID_ORDERS = {"random", "worst-first", "recent-mistakes-first"};
    private static final String ORDER_DEFAULT = "random";

    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("--help")) {
            System.out.println(HELP_MESSAGE);
            return;
        }

        String cardsFile = args[0];
        String order = ORDER_DEFAULT;
        int repetitions = 1;
        boolean invertCards = false;

        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--order": {
                    if (i + 1 < args.length) {
                        order = args[++i];
                        if (!Arrays.asList(VALID_ORDERS).contains(order)) {
                            System.out.println("Invalid order. Available options: random, worst-first, recent-mistakes-first");
                            return;
                        }
                    } else {
                        System.out.println("--order option requires an argument.");
                        return;
                    }
                }
                case "--repetitions": {
                    if (i + 1 < args.length) {
                        repetitions = Integer.parseInt(args[++i]);
                    } else {
                        System.out.println("--repetitions option requires an argument.");
                        return;
                    }
                    System.out.println("You must answer correctly " + repetitions + " times.");
                }
                case "--invertCards":
                    invertCards = true;
                    System.out.println("Invert value: " + invertCards);
                default: {
                    System.out.println("Unknown option: " + args[i]);
                    return;
                }
            }

        }

        try {
            List<Flashcard> flashcards = loadFlashcards(cardsFile);

            if (flashcards == null || flashcards.isEmpty()) {
                System.out.println("No flashcards loaded from file.");
                return;
            }

            CardOrganizer organizer;

            switch (order) {
                case "recent-mistakes-first":
                    organizer = new RecentMistakesFirstSorter(order);
                    break;
                case "worst-first":
                    organizer = new WorstFirstSorter(order);
                    break;
                default:
                    organizer = new RandomSorter(order);
                    break;
            }

            Scanner scanner = new Scanner(System.in);

            while (true) {
                boolean allAnsweredCorrectly = true;
                flashcards = organizer.organize(flashcards);
                for (Flashcard flashcard : flashcards) {
                    if (flashcard.getCorrectCount() < repetitions) {
                        allAnsweredCorrectly = false;

                        if (invertCards) {
                            System.out.println("Answer: " + flashcard.getQuestion());
                            System.out.println("Question: " + flashcard.getAnswer());
                        } else {
                            System.out.println("Question: " + flashcard.getQuestion());
                        }

                        String userAnswer = scanner.nextLine().trim();

                        if (userAnswer.equalsIgnoreCase(flashcard.getAnswer())) {
                            flashcard.incrementCorrectCount();
                            System.out.println("Correct!");
                            System.out.println("Correct count: " + flashcard.getCorrectCount());

                        } else {
                            flashcard.incrementIncorrectCount();
                            System.out.println("Incorrect. The correct answer was: " + flashcard.getAnswer());
                            System.out.println("Incorrect count: " + flashcard.getIncorrectCount());
                        }
                    }
                }

                /// action test commit 
                if (allAnsweredCorrectly) {
                    System.out.println("All cards have been answered correctly " + repetitions + " times.");
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading flashcards: " + e.getMessage());
        }
    }

    private static List<Flashcard> loadFlashcards(String cardsFile) throws IOException {
        List<Flashcard> flashcards = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(cardsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    flashcards.add(new Flashcard(parts[0].trim(), parts[1].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading flashcards: " + e.getMessage());
        }

        return flashcards;
    }
}
