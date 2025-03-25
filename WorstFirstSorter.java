
import java.util.Collections;
import java.util.List;

public class WorstFirstSorter implements CardOrganizer {

    final private String order;

    public WorstFirstSorter(String order) {
        this.order = order;
    }

    @Override
    public List<Flashcard> organize(List<Flashcard> flashcards) {
        if (order.equals("worst-first")) {
            Collections.sort(flashcards, (card1, card2)
                    -> Long.compare(card2.getIncorrectCount(), card1.getIncorrectCount())
            );
        }
        return flashcards;
    }
}
