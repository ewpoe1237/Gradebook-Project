import java.time.LocalDate;
/*
Jenna Hofseth
CPSC 2810 - Fall 2021
Gradebook Project
 */

public class Program implements AssignmentInterface{
    private String concept;
    private int score;
    private char letter;
    private String name;
    private LocalDate dueDate;

    public Program() {
        concept = "";
        score = -1;
        name = "";
        letter = 'Z';
        dueDate = LocalDate.of(1900, 01, 01);
    }

    public Program(String concept, int score, String name, LocalDate dueDate) {
        this.concept = concept;
        this.score = score;
        this.name = name;
        this.dueDate = dueDate;
        letter = scoreToLetter(this.score);
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

    public String getConcept() {
        return concept;
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

    public void setConcept(String input) {
        concept = input;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Score: " + score + ", Letter: " + letter + ", Due: " + dueDate.getMonth() + " " + dueDate.getDayOfMonth() + ", " + dueDate.getYear() + ", Programming concept: " + concept;
    }
}
