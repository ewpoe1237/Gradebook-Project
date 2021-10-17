import java.time.LocalDate;
import java.util.Scanner;

public class Gradebook { //our main class

    public static void main(String[] args) {
        //when first run, should prompt the user to initialize your gradebook
        //gradebook is an array of your interface
        //ask the user to initialize the size of the gradebook -- max 20 grades
        System.out.println("•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦Gradebook Constructor Program•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
        System.out.println("•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦Jenna Hofseth•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");

        System.out.println("Welcome to the Gradebook Constructor Program!");
        System.out.println("The gradebook is a record of assignments with score, due date, and name attributes.");
        System.out.println("You can enter three types of grades: Quizzes, Programs, and Discussions, each with one unique attribute.");
        System.out.println("Quizzes have an attribute for the number of questions, programs have an attribute for the programming concept, and discussions have an attribute for the reading content.");

        System.out.println("◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");

        int bookSize = 0;
        Scanner sc = new Scanner(System.in);

        char choice = 'n';
        while (Character.toUpperCase(choice) != 'Y'){
            System.out.println("\nPlease enter the maximum size of your gradebook as a positive integer, with a maximum of 20 grades.");

            try {
                String line = sc.nextLine();
                bookSize = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer (whole number) value.");
                continue;
            }

            if(bookSize > 20 || bookSize <= 0) {
                System.out.println("Please enter a size less than or equal to 20, and greater than 0.");
                continue;
            }

            System.out.println("You have confirmed your gradebook size as: " + bookSize);
            System.out.println("Is this information correct? Enter 'Y' to continue or a different character to input your size again.");

            while(true) {
                try {
                    choice = sc.nextLine().charAt((0));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Please enter a valid character.");
                    continue;
                }

                break;
            }
        }

        AssignmentInterface[] gradebook = new AssignmentInterface[bookSize];
        int gradeIndex = 0;

        int questionCount = 0;
        String concept = "", readingContent = "";

        //once initialized, prompt the user w/ a menu w/ a few different options
        do {
            System.out.println("\n•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦Menu•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
            System.out.println("Input an option's number to initiate its actions.");
            System.out.println("(1) ADD A GRADE TO YOUR GRADEBOOK");
            System.out.println("(2) REMOVE THE FIRST INSTANCE OF A GIVEN ASSIGNMENT FROM GRADEBOOK");
            System.out.println("(3) DISPLAY ALL GRADES RECORDED");
            System.out.println("(4) DISPLAY AVERAGE OF ALL GRADES IN GRADEBOOK");
            System.out.println("(5) DISPLAY THE HIGHEST AND LOWEST SCORES RECORDED");
            System.out.println("(6) DISPLAY THE AVERAGE NUMBER OF QUESTIONS AMONG ALL QUIZZES");
            System.out.println("(7) DISPLAY ALL ASSOCIATED READINGS FROM DISCUSSIONS");
            System.out.println("(8) DISPLAY ALL CONCEPTS COVERED WITHIN PROGRAMMING ASSIGNMENTS");

            System.out.println("\nEnter 'X' to quit.");
            while(true) {
                try {
                    choice = sc.nextLine().charAt((0));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Please enter a valid character.");
                    continue;
                }

                break;
            }

            boolean emptySpace = false;
            int numCounter = -1;

            switch(Character.toUpperCase(choice)) {
                case 'X': //here we literally just need to break so we can exit
                    break;
                case '1':
                    //add grades: adds a grade to the array
                    //user should be prompted to tell the program which type of grade they
                    //are adding and what the values (name, score, etc) of each grade is
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    char assignmentType = Character.toUpperCase(GradebookOptions.addGradePrompt());
                    //assignment type can be Q for quiz, D for discussion, P for program, and X for quit

                    if(assignmentType == 'X' || assignmentType == 'Q' || assignmentType == 'D' || assignmentType == 'P') {
                        switch (assignmentType) {
                            case 'X' -> System.out.println("\nYou chose to quit this option.");
                            case 'Q' -> {
                                gradebook[gradeIndex] = GradebookOptions.addQuiz();
                                gradeIndex++;
                            }
                            case 'D' -> {
                                gradebook[gradeIndex] = GradebookOptions.addDiscussion();
                                gradeIndex++;
                            }
                            case 'P' -> {
                                gradebook[gradeIndex] = GradebookOptions.addProgram();
                                gradeIndex++;
                            }
                        }
                    }

                    System.out.println("The assignment has been added to the gradebook!");
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    break;
                case '2':
                    //remove grades: removes the first instance of a certain grade
                    //based on the name of the assignment
                    //if the gradebook does not contain the grade the user is trying to remove,
                    //program should throw an InvalidGradeException
                    //no grades at all -> GradebookEmptyException
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    int indexAfterRemoving = GradebookOptions.removeGrades(gradebook, gradeIndex, bookSize);
                    if(indexAfterRemoving >= 0) gradeIndex = indexAfterRemoving;
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    break;
                case '3':
                    //print grades: prints all in grade book, no specific formatting required
                    //should gradebook be empty -> GradebookEmptyException
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    GradebookOptions.printGrades(gradebook, gradeIndex);
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    break;
                case '4':
                    //print average -> print avg of all grades in gradebook
                    //should gradebook be empty -> GradebookEmptyException
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    GradebookOptions.printGradeAvg(gradebook, gradeIndex);
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    break;
                case '5':
                    //print highest/lowest score(s): print highest/lowest score in gradebook
                    //if only one grade, then that grade is highest and lowest
                    //empty -> GradebookEmptyException
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    GradebookOptions.findMaxMin(gradebook, gradeIndex);
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    break;
                case '6':
                    //print quiz question avg: print avg # of ?s among all quizzes
                    //empty -> GradebookEmptyException
                    //assignments but no quizzes -> print that there are no quizzes yet
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    GradebookOptions.printQuestionAvg(gradebook, gradeIndex);
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    break;
                case '7':
                    //print discussion associated readings: print all associated readings from discussions
                    //empty -> GradebookEmptyException
                    //assignments but no discussions -> print that there are no discussions yet
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    GradebookOptions.printAssociatedReadings(gradebook, gradeIndex);
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    break;
                case '8':
                    //print program concepts: print all concepts covered from programs
                    //empty -> GradebookEmptyException
                    //assignments but no programs -> print that there are no programs yet
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    GradebookOptions.printProgramConcepts(gradebook, gradeIndex);
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    continue;
            }

            if(Character.toUpperCase(choice) == 'X') {
                System.out.println("\nGoodbye!");
            }

        } while(Character.toUpperCase(choice) != 'X');
    }

}
