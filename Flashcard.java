
public class Flashcard {

    private String question;
    private String answer;
    private boolean correct;
    private int repeatCount;
    private int mistakeCount;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void markCorrect() {
        this.correct = true;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public int getMistakeCount() {
        return mistakeCount;
    }
}
