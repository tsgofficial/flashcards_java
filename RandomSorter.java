
import java.util.Collections;
import java.util.List;

public class RandomSorter implements CardOrganizer {

    final private String order;

    public RandomSorter(String order) {
        this.order = order;
    }

    @Override
    public List<Flashcard> organize(List<Flashcard> flashcards) {
        if (order.equals("random")) {
            Collections.shuffle(flashcards);
        }
        return flashcards;
    }
}
