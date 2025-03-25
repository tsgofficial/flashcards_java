
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FlashcardCLI {

    private static final String HELP_MESSAGE = "Usage: flashcard <cards-file> [options]\n"
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

        // Parse options
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--order":
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
                    break;
                case "--repetitions":
                    if (i + 1 < args.length) {
                        repetitions = Integer.parseInt(args[++i]);
                    } else {
                        System.out.println("--repetitions option requires an argument.");
                        return;
                    }
                    break;
                case "--invertCards":
                    invertCards = true;
                    break;
                default:
                    System.out.println("Unknown option: " + args[i]);
                    return;
            }
        }

        try {
            List<Flashcard> flashcards = loadFlashcards(cardsFile);

            if (flashcards == null || flashcards.isEmpty()) {
                System.out.println("No flashcards loaded from file.");
                return;
            }

            // Sort flashcards based on the order option
            CardOrganizer organizer;
            if (order.equals("recent-mistakes-first")) {
                organizer = new RecentMistakesFirstSorter(order);
            } else if (order.equals("worst-first")) {
                organizer = new WorstFirstSorter(order);
            } else {
                organizer = new RandomSorter(order);  // Random sorting is handled here, if you want a separate class for it.
            }

            // Game loop - asking questions one at a time
            Scanner scanner = new Scanner(System.in);

            // Track flashcard repetitions
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

                        // Wait for user input
                        String userAnswer = scanner.nextLine().trim();

                        // Check if the answer is correct
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

                // End game loop if all cards have been answered correctly enough times
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
        BufferedReader reader = new BufferedReader(new FileReader(cardsFile));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (parts.length == 2) {
                flashcards.add(new Flashcard(parts[0].trim(), parts[1].trim()));
            }
        }
        reader.close();

        return flashcards;
    }
}
