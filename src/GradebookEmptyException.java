/*
Jenna Hofseth
CPSC 2810 - Fall 2021
Gradebook Project
 */

public class GradebookEmptyException extends Exception{
    public GradebookEmptyException() { }

    public GradebookEmptyException(String message) {
        super(message);
    }
}
