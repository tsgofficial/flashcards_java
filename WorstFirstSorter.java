
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WorstFirstSorter implements CardOrganizer {

    private String order;

    public WorstFirstSorter(String order) {
        this.order = order;
    }

    @Override
    public List<Flashcard> organize(List<Flashcard> flashcards) {
        if (order.equals("worst-first")) {
            Collections.sort(flashcards, new Comparator<Flashcard>() {
                @Override
                public int compare(Flashcard card1, Flashcard card2) {
                    // Custom sorting logic for worst-first (based on incorrect answers count)
                    return Integer.compare(card2.getIncorrectCount(), card1.getIncorrectCount());
                }
            });
        }
        // Return the list, sorted by worst-first or unchanged if other orders
        return flashcards;
    }
}
