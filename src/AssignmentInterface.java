import java.time.LocalDate;

public interface AssignmentInterface {
    int score = -1;
    char letter = 'z';
    String name = "";
    LocalDate dueDate = LocalDate.of(1900, 01, 01);

    int getScore();
    char getLetter();
    LocalDate getDueDate();
    char scoreToLetter(int inputScore);

    void setScore(int inputScore);
    void setLetter(char inputLetter);
    void setName(String inputName);
    void setDueDate(LocalDate input);

    String toString();
}
