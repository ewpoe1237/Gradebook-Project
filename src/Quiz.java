import java.time.LocalDate;

public class Quiz implements AssignmentInterface {
    private int questionCount;
    private int score;
    private char letter;
    private String name;
    private LocalDate dueDate;

    public Quiz() {
        questionCount = -1;
        score = -1;
        name = "";
        letter = 'Z';
        dueDate = LocalDate.of(1900, 01, 01);
    }
    public Quiz(int questionCount, int score, String name, LocalDate dueDate) {
        this.questionCount = questionCount;
        this.score = score;
        this.name = name;
        this.dueDate = dueDate;
        letter = scoreToLetter(this.score);
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int countInput) {
        questionCount = countInput;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public char getLetter() {
        return letter;
    }

    @Override
    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setScore(int inputScore) {
        score = inputScore;
    }

    @Override
    public void setLetter(char inputLetter) {
        letter = inputLetter;
    }

    @Override
    public void setName(String inputName) {
        name = inputName;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Score: " + score + ", Letter: " + letter + ", Due: " + dueDate.getMonth() + " " + dueDate.getDayOfMonth() + ", " + dueDate.getYear() + ", Number of questions: " + questionCount;
    }

    @Override
    public void setDueDate(LocalDate input) {
        dueDate = input;
    }

    @Override
    public char scoreToLetter(int inputScore) {
        if(inputScore >= 90) return 'A';
        else if(inputScore >= 80) return 'B';
        else if(inputScore >= 70) return 'C';
        else if(inputScore >= 60) return 'D';
        else return 'F';
    }
}
