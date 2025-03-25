
import java.time.Instant;

public class Flashcard {

    private String question;
    private String answer;
    private int correctCount;  // Number of times answered correctly
    private int incorrectCount; // Number of times answered incorrectly
    private long lastIncorrectTime; // Timestamp of the last incorrect answer

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.correctCount = 0;
        this.incorrectCount = 0;
        this.lastIncorrectTime = 0; // Default to 0 when never answered incorrectly
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
        this.lastIncorrectTime = Instant.now().toEpochMilli();  // Set the current timestamp when the answer is incorrect
    }

    public long getLastIncorrectTime() {
        return lastIncorrectTime;
    }
}
