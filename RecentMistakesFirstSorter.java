
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecentMistakesFirstSorter implements CardOrganizer {

    private String order;

    public RecentMistakesFirstSorter(String order) {
        this.order = order;
    }

    @Override
    public List<Flashcard> organize(List<Flashcard> flashcards) {
        if (order.equals("recent-mistakes-first")) {
            Collections.sort(flashcards, new Comparator<Flashcard>() {
                @Override
                public int compare(Flashcard card1, Flashcard card2) {
                    // Custom sorting logic for recent mistakes first
                    return Integer.compare(card2.getMistakeCount(), card1.getMistakeCount());
                }
            });
        }
        // Other orders (random, worst-first) can be implemented similarly
        return flashcards;
    }
}
