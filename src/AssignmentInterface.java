import java.time.LocalDate;

public interface AssignmentInterface {
    int getScore();
    char getLetter();
    LocalDate getDueDate();
    String getName();
    char scoreToLetter(int inputScore);

    void setScore(int inputScore);
    void setLetter(char inputLetter);
    void setName(String inputName);
    void setDueDate(LocalDate input);

    String toString();
}
