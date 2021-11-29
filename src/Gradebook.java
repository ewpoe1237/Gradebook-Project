import java.time.LocalDate;
import java.util.ArrayList;
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
        System.out.println("--------------------------Gradebook Constructor Program--------------------------");
        System.out.println("----------------------------------Jenna Hofseth----------------------------------");

        System.out.println("Welcome to the Gradebook Constructor Program!");
        System.out.println("The gradebook is a record of assignments with score, due date, and name attributes.");
        System.out.println("You can enter three types of grades: Quizzes, Programs, and Discussions, each with one unique attribute.");
        System.out.println("Quizzes have an attribute for the number of questions, programs have an attribute for the programming concept, and discussions have an attribute for the reading content.\n");
        Scanner sc = new Scanner(System.in);

        String choice = "next";
        boolean wrongInput = false;

        //AssignmentInterface[] gradebook = new AssignmentInterface[bookSize];
        ArrayList<AssignmentInterface> gradebook = new ArrayList<>();

        //once initialized, prompt the user w/ a menu w/ a few different options
        while(!choice.equalsIgnoreCase("X")) {
            System.out.println("\n-----------------------------------Menu------------------------------------");
            wrongInput = false;

            while(choice.equalsIgnoreCase("next")) {
                GradebookOptions.promptFirstOptions();

                choice = sc.nextLine().toUpperCase().trim();

                if(choice.equalsIgnoreCase("next")) {
                    while(true) {
                        System.out.println();
                        GradebookOptions.promptSecondOptions();

                        choice = sc.nextLine().toUpperCase().trim();
                        if (!choice.equals("1")
                                && !choice.equals("2") && !choice.equals("3")
                                && !choice.equals("4") && !choice.equals("5")
                                && !choice.equals("6") && !choice.equals("7")
                                && !choice.equals("8") && !choice.equals("9")
                                && !choice.equals("10") && !choice.equals("11")
                                && !choice.equals("12") && !choice.equals("13")
                                && !choice.equals("X") && !choice.equalsIgnoreCase("next")) {

                            System.out.println("\nPlease enter a valid choice.\n");
                            continue;

                        } else{
                            if(choice.equalsIgnoreCase("next")) System.out.println();
                            break;
                        }
                    }

                } else if (!choice.equals("1")
                        && !choice.equals("2") && !choice.equals("3")
                        && !choice.equals("4") && !choice.equals("5")
                        && !choice.equals("6") && !choice.equals("7")
                        && !choice.equals("8") && !choice.equals("9")
                        && !choice.equals("10") && !choice.equals("11")
                        && !choice.equals("12") && !choice.equals("13")
                        && !choice.equals("X")) {

                    System.out.println("\nPlease enter a valid choice.");
                    continue;
                }
            }

            switch(choice) {
                case "X": //here we literally just need to exit
                    System.out.println("Goodbye!");
                    System.exit(0);

                    break;
                case "1":
                    //add grades: adds a grade to the array
                    //user should be prompted to tell the program which type of grade they
                    //are adding and what the values (name, score, etc) of each grade is
                    //gradebookfullexception occurs if our gradebook doesn't have any more space in it according to its capacity (bookSize)

                    System.out.println("\n◦--------------------------------------------------------------------------------");
                    String assignmentType = "";

                    assignmentType = GradebookOptions.addGradePrompt().toUpperCase().trim();

                    boolean quit = false;
                    //assignment type can be Q for quiz, D for discussion, P for program, and X for quit
                    if(assignmentType.equals("Q") || assignmentType.equals("P") || assignmentType.equals("D") || assignmentType.equals("X")) {
                        switch (assignmentType) { //intellij recommended this interestingly styled switch case... not sure what makes it different but it does look pretty sweet
                            case "X" -> {
                                quit = true;
                                System.out.println("\nExiting to main menu.");
                            }
                            case "Q" -> gradebook.add(GradebookOptions.addQuiz());
                            case "D" -> gradebook.add(GradebookOptions.addDiscussion());
                            case "P" -> gradebook.add(GradebookOptions.addProgram());
                        }
                    }

                    if(quit) break;

                    System.out.println("The assignment has been added to the gradebook!");
                    System.out.println("\n◦--------------------------------------------------------------------------------");
                    break;
                case "2":
                    //remove grades: removes the first instance of a certain grade
                    //based on the name of the assignment
                    //if the gradebook does not contain the grade the user is trying to remove,
                    //program should throw an InvalidGradeException
                    //no grades at all -> GradebookEmptyException
                    System.out.println("\n◦--------------------------------------------------------------------------------");

                    try {
                        GradebookOptions.removeGrades(gradebook);
                    } catch(GradebookEmptyException | InvalidGradeException e) {
                        if(e instanceof GradebookEmptyException) System.out.println("Your gradebook is empty!");
                        else System.out.println("There was no assignment with that name found within the gradebook.");
                    }

                    System.out.println("\n◦--------------------------------------------------------------------------------");
                    break;
                case "3":
                    //print grades: prints all in grade book, no specific formatting required
                    //should gradebook be empty -> GradebookEmptyException
                    System.out.println("\n◦--------------------------------------------------------------------------------");

                    try {
                        GradebookOptions.printGrades(gradebook);
                    } catch(GradebookEmptyException e) {
                        System.out.println("Your gradebook is empty!");
                    }

                    System.out.println("\n◦--------------------------------------------------------------------------------");
                    break;
                case "4":
                    //print average -> print avg of all grades in gradebook
                    //should gradebook be empty -> GradebookEmptyException
                    System.out.println("\n◦--------------------------------------------------------------------------------");

                    try {
                        GradebookOptions.printGradeAvg(gradebook);
                    } catch(GradebookEmptyException e) {
                        System.out.println("Your gradebook is empty!");
                    }

                    System.out.println("\n◦--------------------------------------------------------------------------------");
                    break;
                case "5":
                    //print highest/lowest score(s): print highest/lowest score in gradebook
                    //if only one grade, then that grade is highest and lowest
                    //empty -> GradebookEmptyException
                    System.out.println("\n◦--------------------------------------------------------------------------------");

                    try {
                        GradebookOptions.findMaxMin(gradebook);
                    } catch(GradebookEmptyException e) {
                        System.out.println("Your gradebook is empty!");
                    }

                    System.out.println("\n◦--------------------------------------------------------------------------------");
                    break;
                case "6":
                    //print quiz question avg: print avg # of ?s among all quizzes
                    //empty -> GradebookEmptyException
                    //assignments but no quizzes -> print that there are no quizzes yet
                    System.out.println("\n◦--------------------------------------------------------------------------------");

                    try {
                        GradebookOptions.printQuestionAvg(gradebook);
                    } catch(GradebookEmptyException e) {
                        System.out.println("Your gradebook is empty!");
                    }

                    System.out.println("\n◦--------------------------------------------------------------------------------");
                    break;
                case "7":
                    //print discussion associated readings: print all associated readings from discussions
                    //empty -> GradebookEmptyException
                    //assignments but no discussions -> print that there are no discussions yet
                    System.out.println("\n◦--------------------------------------------------------------------------------");

                    try {
                        GradebookOptions.printAssociatedReadings(gradebook);
                    } catch(GradebookEmptyException e) {
                        System.out.println("Your gradebook is empty!");
                    }

                    System.out.println("\n◦--------------------------------------------------------------------------------");
                    break;
                case "8":
                    //print program concepts: print all concepts covered from programs
                    //empty -> GradebookEmptyException
                    //assignments but no programs -> print that there are no programs yet
                    System.out.println("\n◦--------------------------------------------------------------------------------");

                    try {
                        GradebookOptions.printProgramConcepts(gradebook);
                    } catch(GradebookEmptyException e) {
                        System.out.println("Your gradebook is empty!");
                    }

                    System.out.println("\n◦--------------------------------------------------------------------------------");
                    break;
                case "9":
                    //print current gradebook content to file
                    //prompt user for text file name
                    System.out.println("\n◦--------------------------------------------------------------------------------");

                    try {
                        GradebookOptions.printToFile(gradebook);
                    } catch(GradebookEmptyException e) {
                        System.out.println("Your gradebook is empty!");
                    }

                    System.out.println("\n◦--------------------------------------------------------------------------------");
                    break;
                case "10":
                    //read gradebook content into gradebook from file
                    //prompt user for txt file name
                    System.out.println("\n◦--------------------------------------------------------------------------------");

                    GradebookOptions.readFromFile(gradebook);

                    System.out.println("\n◦--------------------------------------------------------------------------------");
                    break;
                case "11":
                    //add contents to sql db
                    System.out.println("\n◦--------------------------------------------------------------------------------");

                    GradebookOptions.addToSql(gradebook);

                    System.out.println("\n◦--------------------------------------------------------------------------------");
                    break;
                case "12":
                    //add sql database contents to current gradebook
                    System.out.println("\n◦--------------------------------------------------------------------------------");

                    GradebookOptions.loadFromSql(gradebook);

                    System.out.println("\n◦--------------------------------------------------------------------------------");
                    break;
                case "13":
                    //search sql db
                    System.out.println("\n◦--------------------------------------------------------------------------------");

                    GradebookOptions.searchDB();

                    System.out.println("\n◦--------------------------------------------------------------------------------");
                    break;
                default:
                    wrongInput = true;
            }

            if(wrongInput) {
                choice = "next";
                continue;
            }

            System.out.println("\nIf you would like to exit the program now, please enter 'X'. Enter a different character if you would like to continue.");
            choice = sc.nextLine().toUpperCase().trim();

            if(choice.equalsIgnoreCase("X")) System.out.println("Goodbye!");
            else choice = "next";
        }
    }

}
