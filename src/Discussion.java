import java.time.LocalDate;

/*
Jenna Hofseth
CPSC 2810 - Fall 2021
Gradebook Project
 */

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

        checkValidInput();
    }

    public String getReadingContent() {
        return readingContent;
    }

    private void checkValidInput() {
        int currentYear = LocalDate.now().getYear();

        if(readingContent.trim().equalsIgnoreCase("")) {
            System.out.println("Reading content should not be empty during declaration. Defaulting to \"Default Content\".");
            readingContent = "Default Content";
        }

        if(score < 0) {
            System.out.println("Score should not be less than zero during declaration. Defaulting to zero.");
            score = 0;
            letter = scoreToLetter(score);
        }

        if(name.trim().equalsIgnoreCase("")) {
            System.out.println("Name should not be empty during declaration. Defaulting to \"Default Discussion\".");
            name = "Default Discussion";
        }

        if(dueDate.getYear() < (currentYear - 25)) {
            System.out.println("Year should not be earlier than 25 years before the current year during declaration. Defaulting to the current year (" + currentYear + ").");
            dueDate = LocalDate.of(currentYear, dueDate.getMonth(), dueDate.getDayOfMonth());
        } else if(dueDate.getYear() > (currentYear + 100)) {
            System.out.println("Year should not be more than 100 years after the current year during declaration. Defaulting to the current year (" + currentYear + ").");
            dueDate = LocalDate.of(currentYear, dueDate.getMonth(), dueDate.getDayOfMonth());
        }
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

    public void setReadingContent(String contentInput) {
        readingContent = contentInput;
    }

    @Override
    public void setScore(int inputScore) {
        score = inputScore;
        letter = scoreToLetter(score);
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
        return "Name: " + name + ", Score: " + score + ", Letter: " + letter + ", Due: " + dueDate.getMonth() + " " + dueDate.getDayOfMonth() + ", " + dueDate.getYear() + ", Reading content: " + readingContent;
    }
}
