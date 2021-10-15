import java.time.LocalDate;

public class Discussion implements AssignmentInterface{
    private String readingContent;
    private int score;
    private char letter;
    private String name;
    private LocalDate dueDate;

    public Discussion() {
        readingContent = "";
        score = -1;
        name = "";
        letter = 'Z';
        dueDate = LocalDate.of(1900, 01, 01);
    }

    public Discussion(String readingContent, int score, String name, LocalDate dueDate) {
        this.readingContent = readingContent;
        this.score = score;
        this.name = name;
        this.dueDate = dueDate;
        letter = scoreToLetter(this.score);
    }

    public String getReadingContent() {
        return readingContent;
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

    public void setReadingContent(String contentInput) {
        readingContent = contentInput;
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

    @Override
    public String toString() {
        return "Name: " + name + ", Score: " + score + ", Letter: " + letter + ", Due: " + dueDate.getMonth() + "/" + dueDate.getDayOfMonth() + "/" + dueDate.getYear() + ", Reading content: " + readingContent;
    }
}
