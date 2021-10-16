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

        System.out.println("◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");

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

            char selection = 'Z';
            int sum = 0;
            double avg = 0;
            boolean emptySpace = false;

            switch(Character.toUpperCase(choice)) {
                case 'X': //here we literally just need to break so we can exit
                    break;
                case '1':
                    //add grades: adds a grade to the array
                    //user should be prompted to tell the program which type of grade they
                    //are adding and what the values (name, score, etc) of each grade is

                    char assignmentType = Character.toUpperCase(addGradePrompt());
                    //assignment type can be Q for quiz, D for discussion, P for program, and X for quit

                    if(assignmentType == 'X' || assignmentType == 'Q' || assignmentType == 'D' || assignmentType == 'P') {
                        switch (assignmentType) {
                            case 'X' -> System.out.println("\nYou chose to quit this option.");
                            case 'Q' -> {
                                System.out.println("\nYou chose to add a QUIZ to your gradebook.");
                                Quiz myQuiz = new Quiz();
                                initializeCommonVariables(myQuiz);
                                while (Character.toUpperCase(selection) != 'Y') {
                                    System.out.println("\nPlease enter the total number of questions on the quiz.");
                                    String line = sc.nextLine();

                                    try {
                                        questionCount = Integer.parseInt(line);
                                    } catch (NumberFormatException e) {
                                        System.out.println("Please enter a valid integer number.");
                                        continue;
                                    }

                                    System.out.println("You entered the question count as: " + questionCount);

                                    System.out.println("Is this correct? Enter 'Y' for yes. Enter a different character if you would like to edit it.");

                                    while (true) {
                                        try {
                                            selection = sc.nextLine().charAt((0));
                                        } catch (IndexOutOfBoundsException e) {
                                            System.out.println("Please enter a valid character.");
                                            continue;
                                        }

                                        break;
                                    }
                                }
                                myQuiz.setQuestionCount(questionCount);
                                gradebook[gradeIndex] = myQuiz;
                                gradeIndex++;
                            }
                            case 'D' -> {
                                System.out.println("\nYou chose to add a DISCUSSION to your gradebook.");
                                AssignmentInterface myDiscussion = new Discussion();
                                initializeCommonVariables(myDiscussion);
                                selection = 'Z';
                                while (Character.toUpperCase(selection) != 'Y') {
                                    System.out.println("\nPlease enter the reading content for the discussion.");
                                    readingContent = sc.nextLine();

                                    System.out.println("You entered the reading content as: " + readingContent);
                                    System.out.println("Is this correct? Enter 'Y' for yes. Enter a different character if you would like to edit it.");
                                    while (true) {
                                        try {
                                            selection = sc.nextLine().charAt((0));
                                        } catch (IndexOutOfBoundsException e) {
                                            System.out.println("Please enter a valid character.");
                                            continue;
                                        }

                                        break;
                                    }
                                }
                                ((Discussion) myDiscussion).setReadingContent(readingContent);
                                gradebook[gradeIndex] = myDiscussion;
                                gradeIndex++;
                            }
                            case 'P' -> {
                                System.out.println("\nYou chose to add a PROGRAM to your gradebook.");
                                AssignmentInterface myProgram = new Program();
                                initializeCommonVariables(myProgram);
                                selection = 'Z';
                                while (Character.toUpperCase(selection) != 'Y') {
                                    System.out.println("\nPlease enter the programming concept for the assignment.");
                                    concept = sc.nextLine();

                                    System.out.println("You entered the concept as: " + concept);
                                    System.out.println("Is this correct? Enter 'Y' for yes. Enter a different character if you would like to edit it.");
                                    while (true) {
                                        try {
                                            selection = sc.nextLine().charAt((0));
                                        } catch (IndexOutOfBoundsException e) {
                                            System.out.println("Please enter a valid character.");
                                            continue;
                                        }

                                        break;
                                    }
                                }
                                ((Program) myProgram).setConcept(concept);
                                gradebook[gradeIndex] = myProgram;
                                gradeIndex++;
                            }
                        }
                    }

                    System.out.println("The assignment has been added to the gradebook!");

                    break;
                case '2':
                    //remove grades: removes the first instance of a certain grade
                    //based on the name of the assignment
                    //if the gradebook does not contain the grade the user is trying to remove,
                    //program should throw an InvalidGradeException
                    //no grades at all -> GradebookEmptyException
                    System.out.println("\nYou chose to REMOVE A GRADE from your gradebook.");
                    System.out.println("Please enter the name of the assignment you would like to remove.");
                    String assignmentName = sc.nextLine();
                    int targetIndex = -1;
                    boolean targetFound = false;

                    for(int i = 0; i < gradeIndex && !targetFound; i++) {
                        try {
                            if (gradebook[i].getName().equals(assignmentName)) {
                                targetIndex = i;
                                targetFound = true;
                            }
                        } catch(NullPointerException e) {
                            targetIndex = -1;
                            targetFound = true;
                            System.out.println("The grade was not found within the gradebook.");
                        }
                    }

                    if(targetFound && targetIndex == -1) break;

                    //scooting everything down by an index to eliminate null points in array
                    if(targetFound && targetIndex == bookSize - 1) {
                        AssignmentInterface[] temp = new AssignmentInterface[bookSize];
                        for(int i = 0; i < targetIndex; i++) {
                            temp[i] = gradebook[i];
                        }

                        gradebook = temp;
                        gradeIndex--;
                        System.out.println("Grade successfully removed.");
                    } else if(targetIndex >= 0 && targetIndex < bookSize - 1 && targetFound) {
                        for(int i = targetIndex; i < bookSize - 1; i++) {
                            gradebook[i] = gradebook[i+1];
                        }

                        gradeIndex--;
                        System.out.println("Grade successfully removed.");
                    }


                    break;
                case '3':
                    //print grades: prints all in grade book, no specific formatting required
                    //should gradebook be empty -> GradebookEmptyException
                    System.out.println("\nYou chose to PRINT ALL GRADES IN GRADEBOOK.");
                    int numCounter = -1;

                    for(int i = 0; i < gradeIndex; i++) {
                        numCounter = i+1;
                        try {
                            System.out.println("(" + numCounter + ") " + gradebook[i].toString());
                        } catch(NullPointerException e) {
                            System.out.println("Your gradebook is empty!");
                        }
                    }

                    break;
                case '4':
                    //print average -> print avg of all grades in gradebook
                    //should gradebook be empty -> GradebookEmptyException
                    System.out.println("\nYou chose to PRINT AVERAGE OF ALL GRADES IN GRADEBOOK.");

                    sum = sumValues(gradebook,'4', gradeIndex);
                    avg = (double) (sum / (gradeIndex + 1));
                    System.out.println("The average of all grades is: " + avg);

                    break;
                case '5':
                    //print highest/lowest score(s): print highest/lowest score in gradebook
                    //if only one grade, then that grade is highest and lowest
                    //empty -> GradebookEmptyException

                    break;
                case '6':
                    //print quiz question avg: print avg # of ?s among all quizzes
                    //empty -> GradebookEmptyException
                    //assignments but no quizzes -> print that there are no quizzes yet

                    sum = sumValues(gradebook,'6', gradeIndex);
                    avg = (double) (sum / (gradeIndex + 1));
                    System.out.println("The average of all grades is: " + avg);

                    break;
                case '7':
                    break;
                case '8':
                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    continue;
            }

            if(Character.toUpperCase(choice) == 'X') {
                System.out.println("\nGoodbye!");
            }

        } while(Character.toUpperCase(choice) != 'X');

        //print discussion associated readings: print all associated readings from discussions
        //empty -> GradebookEmptyException
        //assignments but no discussions -> print that there are no discussions yet

        //print program concepts: print all concepts covered from programs
        //empty -> GradebookEmptyException
        //assignments but no programs -> print that there are no programs yet
    }

    static int sumValues(AssignmentInterface[] gradebook, char inputChoice, int gradeIndex) {
        int sum = 0;
        boolean emptySpace = false;

        switch(inputChoice) {
            case '4': //this is for the avg of scores in gradebook
                emptySpace = false;

                for(int i = 0; i < gradeIndex && !emptySpace; i++) {
                    try {
                        sum += gradebook[i].getScore();
                    } catch(NullPointerException e) {
                        emptySpace = true;
                    }
                }

                break;
            case '6': //this is for the avg num of questions in quizzes
                for(int i = 0; i < gradeIndex && !emptySpace; i++) {
                    try {
                        if (gradebook[i] instanceof Quiz) {
                            sum += ((Quiz) gradebook[i]).getQuestionCount();
                        }
                    } catch(NullPointerException e) {
                        emptySpace = true;
                    }
                }

                break;
            default:
                System.out.println("Invalid input choice.");
        }

        return sum;
    }

    static char addGradePrompt() {
        System.out.println("\nYou chose to ADD A GRADE to your gradebook.");
        char inputChar = 'z';
        Scanner sc = new Scanner(System.in);

        System.out.println("\nWhich type of grade would you like to add?");
        System.out.println("Type 'Q' for QUIZ");
        System.out.println("Type 'P' for PROGRAM");
        System.out.println("TYPE 'D' for DISCUSSION");
        System.out.println("If you would like to exit this option, type 'X'");

        while(inputChar != 'Q' && inputChar != 'P' && inputChar != 'D' && inputChar != 'X') {
            inputChar = Character.toUpperCase(sc.nextLine().charAt(0));

            if(inputChar != 'Q' && inputChar != 'P' && inputChar != 'D' && inputChar != 'X') System.out.println("Please type 'Q' for quiz, 'P' for program, 'D' for discussion, or 'X' to exit this option.");
        }

        return inputChar;
    }

    static void initializeCommonVariables(AssignmentInterface obj) {
        Scanner sc = new Scanner(System.in);
        String line = "";

        int score = 0;
        char letter = 'Z', choice = 'Z';
        String name = "";
        LocalDate dueDate = LocalDate.of(1900, 01, 01);

        while(Character.toUpperCase(choice) != 'Y') {
            System.out.println("\nPlease enter a name for your assignment.");

            name = sc.nextLine();

            System.out.println("You entered the assignment name as: " + name);
            System.out.println("Is this correct? Enter 'Y' for yes. Enter a different character if you would like to edit it.");
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
        obj.setName(name);
        choice = 'Z';

        while(Character.toUpperCase(choice) != 'Y') {
            System.out.println("\nPlease enter a score for your assignment.");
            line = sc.nextLine();

            try {
                score = Integer.parseInt(line);
            } catch(NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            if(score < 0) {
                System.out.println("Score input was less than 0. Defaulting to 0.");
                score = 0;
            }

            System.out.println("You entered the score as: " + score);
            System.out.println("Is this correct? Enter 'Y' for yes. Enter a different character if you would like to edit it.");
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
        obj.setScore(score);
        obj.setLetter(obj.scoreToLetter(score));
        choice = 'Z';


        System.out.println("\nPlease enter the due date for the assignment.");
        int inputYear = 0, inputMonth = 0, inputDay = 0;
        choice = 'Z';

        while(Character.toUpperCase(choice) != 'Y') {
            System.out.println("\nEnter the year as an integer number (ex: '2021') greater than 0.");
            line = sc.nextLine();

            try {
                inputYear = Integer.parseInt(line);
            } catch(NumberFormatException e) {
                System.out.println("Please enter a valid integer number.");
                continue;
            }

            if(inputYear <= 0) {
                System.out.println("Input year was less than or equal to 0. Defaulting to 1.");
                inputYear = 1;
            }

            System.out.println("You entered the year as: " + inputYear);
            System.out.println("Is this correct? Enter 'Y' for yes. Enter a different character if you would like to edit it.");
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

        choice = 'Z';

        while(Character.toUpperCase(choice) != 'Y') {
            System.out.println("\nEnter the month as an integer number (ex: '12' for December) greater than 0 and less than or equal to 12");
            line = sc.nextLine();

            try {
                inputMonth = Integer.parseInt(line);
            } catch(NumberFormatException e) {
                System.out.println("Please enter a valid integer number.");
                continue;
            }

            if(inputMonth <= 0) {
                System.out.println("Input month was less than or equal to 0. Defaulting to 1.");
                inputMonth = 1;
            } else if(inputMonth > 12) {
                System.out.println("Input month was greater than 12. Defaulting to 12.");
                inputMonth = 12;
            }

            System.out.println("You entered the month as: " + inputMonth);
            System.out.println("Is this correct? Enter 'Y' for yes. Enter a different character if you would like to edit it.");
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

        choice = 'Z';

        while(Character.toUpperCase(choice) != 'Y') {
            System.out.println("\nEnter the day as an integer number greater than 0 and less than or equal to 31.");
            line = sc.nextLine();

            try {
                inputDay = Integer.parseInt(line);
            } catch(NumberFormatException e) {
                System.out.println("Please enter a valid integer number.");
                continue;
            }

            if(inputDay <= 0) {
                System.out.println("Input day was less than or equal to 0. Defaulting to 1.");
                inputDay = 1;
            } else if(inputDay > 31) {
                System.out.println("Input day was greater than 31. Defaulting to 31.");
                inputDay = 31;
            }

            System.out.println("You entered the day as: " + inputDay);
            System.out.println("Is this correct? Enter 'Y' for yes. Enter a different character if you would like to edit it.");
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

        dueDate = LocalDate.of(inputYear, inputMonth, inputDay);
        obj.setDueDate(dueDate);
    }
}
