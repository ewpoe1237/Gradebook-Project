import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

/*
Jenna Hofseth
CPSC 2810 - Fall 2021
Gradebook Project
 */

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

        char choice = 'z';
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

            System.out.println("You have entered your gradebook size as: " + bookSize);
            System.out.println("Is this information correct? Enter 'Y' to continue or a different character to input your size again.");

            /* for the confirmation of choices within each option, i added a try catch to make
                sure the user cannot just hit enter when asked to confirm the value.
             */
            while(true) {
                /*  since this is just for entering a character, and not checking the value of the character yet, i did a while(true)
                    loop. if i were doing more than just checking to make sure input is a valid character i wouldn't do that,
                    but typically despite having a while(true) statement, this little clip of code will have an O(1) (good) time complexity
                    except in the case the user does not enter a valid character.
                 */
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
        //index of the last object
        int gradeIndex = 0;
        choice = 'z';

        //once initialized, prompt the user w/ a menu w/ a few different options
        while(Character.toUpperCase(choice) != 'X') {
            System.out.println("\n•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦Menu•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
            for (String s : Arrays.asList("Enter 'X' to quit.",
                    "(1) ADD A GRADE TO YOUR GRADEBOOK",
                    "(2) REMOVE THE FIRST INSTANCE OF A GIVEN ASSIGNMENT FROM GRADEBOOK",
                    "(3) PRINT ALL GRADES RECORDED",
                    "(4) PRINT AVERAGE OF ALL GRADES IN GRADEBOOK",
                    "(5) PRINT THE HIGHEST AND LOWEST SCORES RECORDED",
                    "(6) PRINT THE AVERAGE NUMBER OF QUESTIONS AMONG ALL QUIZZES",
                    "(7) PRINT ALL ASSOCIATED READINGS FROM DISCUSSIONS",
                    "(8) PRINT ALL CONCEPTS COVERED WITHIN PROGRAMMING ASSIGNMENTS",
                    "Input an option's number to initiate its actions.")) {

                System.out.println(s);
            }

            while(true) {
                try {
                    choice = sc.nextLine().charAt((0));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Please enter a valid character.");
                    continue;
                }

                if((choice != 'X') && (choice != '1') && (choice != '2') &&
                        (choice != '3') && (choice != '4') &&
                        (choice != '5') && (choice != '6') &&
                        (choice != '7') && (choice != '8')) {
                    System.out.println("Please enter a valid choice.");
                    continue;
                }

                break;
            }

            switch(Character.toUpperCase(choice)) {
                case 'X': //here we literally just need to exit
                    System.out.println("Goodbye!");
                    System.exit(0);

                    break;
                case '1':
                    //add grades: adds a grade to the array
                    //user should be prompted to tell the program which type of grade they
                    //are adding and what the values (name, score, etc) of each grade is
                    //gradebookfullexception occurs if our gradebook doesn't have any more space in it according to its capacity (bookSize)

                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    char assignmentType = 'z';

                    try {
                        assignmentType = Character.toUpperCase(GradebookOptions.addGradePrompt(gradeIndex, bookSize));
                    } catch(GradebookFullException e) {
                        System.out.println("Your gradebook is full!");
                        break;
                    }

                    boolean quit = false;
                    //assignment type can be Q for quiz, D for discussion, P for program, and X for quit
                    if(assignmentType == 'X' || assignmentType == 'Q' || assignmentType == 'D' || assignmentType == 'P') {
                        switch (assignmentType) { //intellij recommended this interestingly styled switch case... not sure what makes it different but it does look pretty sweet
                            case 'X' -> {
                                quit = true;
                                System.out.println("\nYou chose to quit this option.");
                            }
                            case 'Q' -> gradebook[gradeIndex] = GradebookOptions.addQuiz();
                            case 'D' -> gradebook[gradeIndex] = GradebookOptions.addDiscussion();
                            case 'P' -> gradebook[gradeIndex] = GradebookOptions.addProgram();
                        }
                    }

                    if(quit) break;

                    gradeIndex++;
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

                    int indexAfterRemoving = -1;

                    try {
                        indexAfterRemoving = GradebookOptions.removeGrades(gradebook, gradeIndex, bookSize);
                    } catch(GradebookEmptyException | InvalidGradeException e) {
                        if(e instanceof GradebookEmptyException) System.out.println("Your gradebook is empty!");
                        else System.out.println("There was no assignment with that name found within the gradebook.");
                    }

                    //since we cannot modify an integer parameter, this method returns indexAfterRemoving, which is the updated last object index
                    if(indexAfterRemoving >= 0) gradeIndex = indexAfterRemoving;

                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    break;
                case '3':
                    //print grades: prints all in grade book, no specific formatting required
                    //should gradebook be empty -> GradebookEmptyException
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");

                    try {
                        GradebookOptions.printGrades(gradebook, gradeIndex);
                    } catch(GradebookEmptyException e) {
                        System.out.println("Your gradebook is empty!");
                    }

                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    break;
                case '4':
                    //print average -> print avg of all grades in gradebook
                    //should gradebook be empty -> GradebookEmptyException
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");

                    try {
                        GradebookOptions.printGradeAvg(gradebook, gradeIndex);
                    } catch(GradebookEmptyException e) {
                        System.out.println("Your gradebook is empty!");
                    }

                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    break;
                case '5':
                    //print highest/lowest score(s): print highest/lowest score in gradebook
                    //if only one grade, then that grade is highest and lowest
                    //empty -> GradebookEmptyException
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");

                    try {
                        GradebookOptions.findMaxMin(gradebook, gradeIndex);
                    } catch(GradebookEmptyException e) {
                        System.out.println("Your gradebook is empty!");
                    }

                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    break;
                case '6':
                    //print quiz question avg: print avg # of ?s among all quizzes
                    //empty -> GradebookEmptyException
                    //assignments but no quizzes -> print that there are no quizzes yet
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");

                    try {
                        GradebookOptions.printQuestionAvg(gradebook, gradeIndex);
                    } catch(GradebookEmptyException e) {
                        System.out.println("Your gradebook is empty!");
                    }

                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    break;
                case '7':
                    //print discussion associated readings: print all associated readings from discussions
                    //empty -> GradebookEmptyException
                    //assignments but no discussions -> print that there are no discussions yet
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");

                    try {
                        GradebookOptions.printAssociatedReadings(gradebook, gradeIndex);
                    } catch(GradebookEmptyException e) {
                        System.out.println("Your gradebook is empty!");
                    }

                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    break;
                case '8':
                    //print program concepts: print all concepts covered from programs
                    //empty -> GradebookEmptyException
                    //assignments but no programs -> print that there are no programs yet
                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");

                    try {
                        GradebookOptions.printProgramConcepts(gradebook, gradeIndex);
                    } catch(GradebookEmptyException e) {
                        System.out.println("Your gradebook is empty!");
                    }

                    System.out.println("\n◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    continue;
            }

            System.out.println("\nIf you would like to exit the program now, please enter 'X'. Enter a different character if you would like to continue.");
            while(true) {
                try {
                    choice = sc.nextLine().charAt((0));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Please enter a valid character.");
                    continue;
                }

                break;
            }

            if(Character.toUpperCase(choice) == 'X') System.out.println("Goodbye!");
        }
    }

}
