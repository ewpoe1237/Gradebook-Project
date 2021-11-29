import org.junit.jupiter.api.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/*
Jenna Hofseth
CPSC 2810 - Fall 2021
Gradebook Project
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GradebookTest {
    //tests for both remove grade & print sorted grades
    private final InputStream sysIn = System.in;
    private final PrintStream sysOut = System.out;
    private ByteArrayInputStream testInput;
    private ByteArrayOutputStream testOutput;
    private static ArrayList<AssignmentInterface> gradebookTest = new ArrayList<>();
    private static Quiz testQuiz, brokenQuiz;
    private static Discussion testDiscussion, brokenDiscussion;
    private static Program testProgram, brokenProgram;
    private String testString;
    private boolean encounteredException = false, emptyGBFlag = false, invalidGradeFlag = false;
    private static int originalSize;

    /*
        Setup for reading scanner i/o code referenced from commenter Antônio Medeiros at:
        https://stackoverflow.com/questions/1647907/junit-how-to-simulate-system-in-testing
     */

    private void getInput(String data) {
        testInput = new ByteArrayInputStream(data.getBytes());
        System.setIn(testInput);
    }

    @BeforeAll
    private static void mainSetup() {
        //create normal and "broken" versions of every type to later ensure they are defaulted to/assigned the right values
        testQuiz = new Quiz(3, 100, "Quiz One", LocalDate.of(2002, 06, 12));
        gradebookTest.add(testQuiz); //Quiz One

        brokenQuiz = new Quiz(0, -10, "", LocalDate.of(1900, 12,  01));
        gradebookTest.add(brokenQuiz); //Default Quiz

        testDiscussion = new Discussion("Discussion Reading Content", 85, "Discussion One", LocalDate.of(2010, 1, 06));
        gradebookTest.add(testDiscussion); //Discussion One

        brokenDiscussion = new Discussion("", 0, "", LocalDate.of(2155, 8, 26));
        gradebookTest.add(brokenDiscussion); //Default Discussion

        testProgram = new Program("Programming Concept", 30, "Program One", LocalDate.of(2021, 11, 29));
        gradebookTest.add(testProgram); //Program One

        brokenProgram = new Program("", -1, "   ", LocalDate.of(1955, 11, 29));
        gradebookTest.add(brokenProgram); //Default Program

        originalSize = gradebookTest.size();
    }

    @Test
    public void testGoodRemove() {
        //test function that tests for both 1) no exceptions and 2) that the gradebookTest array actually does not have the grade anymore
        testString = "Quiz One";
        getInput(testString);

        try {
            GradebookOptions.removeGrades(gradebookTest);
        } catch(GradebookEmptyException | InvalidGradeException e) {
            encounteredException = true;
        }

        assertEquals(encounteredException, false);
        assertEquals(gradebookTest.contains(testQuiz), false);
        assertEquals(originalSize, (gradebookTest.size() + 1));
    }

    @Test
    public void testEmptyException() {
        ArrayList<AssignmentInterface> empty = new ArrayList<>();

        //test function that tests for gradebookEmptyException
        testString = "Quiz One";
        getInput(testString);

        try {
            GradebookOptions.removeGrades(empty);
        } catch(GradebookEmptyException | InvalidGradeException e) {
            //for some reason the expected tag just is not recognized by intellij... had to use flags for that reason
            if(e instanceof GradebookEmptyException) emptyGBFlag = true;
            if(e instanceof InvalidGradeException) invalidGradeFlag = true;
        }

        assertEquals(emptyGBFlag, true);
        assertEquals(invalidGradeFlag, false);
    }

    @Test
    public void testInvalidGrade() {
        //test function that tests for gradebookEmptyException
        testString = "This should not be found";
        getInput(testString);

        try {
            GradebookOptions.removeGrades(gradebookTest);
        } catch(GradebookEmptyException | InvalidGradeException e) {
            if(e instanceof GradebookEmptyException) emptyGBFlag = true;
            if(e instanceof InvalidGradeException) invalidGradeFlag = true;
        }

        assertEquals(emptyGBFlag, false);
        assertEquals(invalidGradeFlag, true);
    }

    @Test
    public void testPrintEmpty() {
        //uses a flag to test whether printing an empty arraylist auto-throws gb empty exception
        ArrayList<AssignmentInterface> empty = new ArrayList<>();

        testString = "A";
        getInput(testString);

        try {
            GradebookOptions.printGrades(empty);
        } catch(GradebookEmptyException e) {
            emptyGBFlag = true;
        }

        assertEquals(emptyGBFlag, true);
    }

    @Test
    public void testScoreSort() {
        //manually create arraylist of test values sorted by grade and compare to output after sorting gradebookTest
        //since we default edge cases to certain values on creation these sorts will also test for whether the "broken" grades stay broken
        ArrayList<AssignmentInterface> sortedByScore = new ArrayList<>();
        sortedByScore.add(brokenQuiz);
        sortedByScore.add(brokenDiscussion);
        sortedByScore.add(brokenProgram);
        sortedByScore.add(testProgram);
        sortedByScore.add(testDiscussion);
        sortedByScore.add(testQuiz);

        testString = "A";
        getInput(testString);

        try {
            assertEquals(GradebookOptions.printGrades(gradebookTest), sortedByScore);
        } catch(GradebookEmptyException e) {
            encounteredException = true;
        }

        assertEquals(encounteredException, false);
    }

    @Test
    public void testLetter() {
        //manually create arraylist of test values sorted by letter (same as grade) and compare to output after sorting gradebookTest
        //since we default edge cases to certain values on creation these sorts will also test for whether the "broken" grades stay broken
        ArrayList<AssignmentInterface> sortedByScore = new ArrayList<>();
        sortedByScore.add(brokenQuiz);
        sortedByScore.add(brokenDiscussion);
        sortedByScore.add(brokenProgram);
        sortedByScore.add(testProgram);
        sortedByScore.add(testDiscussion);
        sortedByScore.add(testQuiz);

        testString = "B";
        getInput(testString);

        try {
            assertEquals(GradebookOptions.printGrades(gradebookTest), sortedByScore);
        } catch(GradebookEmptyException e) {
            encounteredException = true;
        }

        assertEquals(encounteredException, false);
    }

    @Test
    public void testAlphabeticalSort() {
        //manually create arraylist of test values sorted by name and compare to output after sorting gradebookTest
        //since we default edge cases to certain values on creation these sorts will also test for whether the "broken" grades stay broken
        ArrayList<AssignmentInterface> sortedByAlpha = new ArrayList<>();
        sortedByAlpha.add(brokenDiscussion);
        sortedByAlpha.add(brokenProgram);
        sortedByAlpha.add(brokenQuiz);
        sortedByAlpha.add(testDiscussion);
        sortedByAlpha.add(testProgram);
        sortedByAlpha.add(testQuiz);

        testString = "C";
        getInput(testString);

        try {
            assertEquals(GradebookOptions.printGrades(gradebookTest), sortedByAlpha);
        } catch(GradebookEmptyException e) {
            encounteredException = true;
        }

        assertEquals(encounteredException, false);
    }

    @Test
    public void testDueDateSort() {
        //manually create arraylist of test values sorted by date and compare to output after sorting gradebookTest
        //since we default edge cases to certain values on creation these sorts will also test for whether the "broken" grades stay broken
        ArrayList<AssignmentInterface> sortedByDue = new ArrayList<>();
        sortedByDue.add(testQuiz);
        sortedByDue.add(testDiscussion);
        sortedByDue.add(brokenDiscussion);
        sortedByDue.add(testProgram);
        sortedByDue.add(brokenProgram);
        sortedByDue.add(brokenQuiz);

        testString = "D";
        getInput(testString);

        try {
            assertEquals(GradebookOptions.printGrades(gradebookTest), sortedByDue);
        } catch(GradebookEmptyException e) {
            encounteredException = true;
        }

        assertEquals(encounteredException, false);
    }

    @AfterEach
    public void restoreIO() {
        System.setIn(sysIn);
        System.setOut(sysOut);
    }

    @AfterEach
    public void restoreDefaultVariables() {
        //we need to ensure we are testing with the same values each time
        gradebookTest.clear();
        gradebookTest.add(testQuiz);
        gradebookTest.add(brokenQuiz);
        gradebookTest.add(testDiscussion);
        gradebookTest.add(brokenDiscussion);
        gradebookTest.add(testProgram);
        gradebookTest.add(brokenProgram);

        encounteredException = false;
        emptyGBFlag = false;
        invalidGradeFlag = false;
    }

}
