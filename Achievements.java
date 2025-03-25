
import java.util.List;

public class Achievements {

    private int correctCount = 0;
    private int repeatCount = 0;
    private int confidentCount = 0;

    public void checkCorrect(List<Flashcard> flashcards) {
        boolean allCorrect = flashcards.stream().allMatch(card -> card.isCorrect());
        if (allCorrect) {
            correctCount++;
            System.out.println("Achievement: CORRECT");
        }
    }

    public void checkRepeat(List<Flashcard> flashcards) {
        flashcards.forEach(card -> {
            if (card.getRepeatCount() > 5) {
                repeatCount++;
                System.out.println("Achievement: REPEAT");
            }
        });
    }

    public void checkConfident(List<Flashcard> flashcards) {
        flashcards.forEach(card -> {
            if (card.getRepeatCount() >= 3 && card.isCorrect()) {
                confidentCount++;
                System.out.println("Achievement: CONFIDENT");
            }
        });
    }
}
