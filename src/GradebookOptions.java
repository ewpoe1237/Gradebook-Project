import java.time.LocalDate;
import java.util.Scanner;

public class GradebookOptions {
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
            while (true) {
                try {
                    inputChar = Character.toUpperCase(sc.nextLine().charAt((0)));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Please enter a valid character.");
                    continue;
                }

                break;
            }

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
                int quizCount = 0;
                for(int i = 0; i < gradeIndex; i++) {
                    try {
                        if (gradebook[i] instanceof Quiz) {
                            quizCount++;
                            sum += ((Quiz) gradebook[i]).getQuestionCount();
                        }
                    } catch(NullPointerException e) {
                        System.out.println("Your gradebook is empty!");
                        break;
                    }
                }

                if(quizCount == 0) System.out.println("Your gradebook has no quizzes.");

                break;
            default:
                System.out.println("Invalid input choice.");
        }

        return sum;
    }

    static Discussion addDiscussion() {
        System.out.println("\nYou chose to add a DISCUSSION to your gradebook.");
        Discussion myDiscussion = new Discussion();
        GradebookOptions.initializeCommonVariables(myDiscussion);
        char selection = 'Z';
        String readingContent = "";
        Scanner sc = new Scanner(System.in);

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
        myDiscussion.setReadingContent(readingContent);

        return myDiscussion;
    }

    static Quiz addQuiz() {
        System.out.println("\nYou chose to add a QUIZ to your gradebook.");
        Quiz myQuiz = new Quiz();
        int questionCount = 0;
        char selection = 'Z';
        Scanner sc = new Scanner(System.in);
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

        return myQuiz;
    }

    static Program addProgram() {
        System.out.println("\nYou chose to add a PROGRAM to your gradebook.");
        Program myProgram = new Program();
        GradebookOptions.initializeCommonVariables(myProgram);
        char selection = 'Z';
        String concept = "";
        Scanner sc = new Scanner(System.in);

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
        myProgram.setConcept(concept);
        return myProgram;
    }

    static int removeGrades(AssignmentInterface[] gradebook, int gradeIndex, int bookSize) {
        System.out.println("\nYou chose to REMOVE A GRADE from your gradebook.");
        System.out.println("Please enter the name of the assignment you would like to remove.");
        Scanner sc = new Scanner(System.in);
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

        if(targetFound && targetIndex == -1) return -1;

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

        return gradeIndex;
    }

    static void printGrades(AssignmentInterface[] gradebook, int gradeIndex) {
        System.out.println("\nYou chose to PRINT ALL GRADES in gradebook.");
        int numCounter = -1;

        for(int i = 0; i < gradeIndex; i++) {
            numCounter = i+1;
            try {
                System.out.println("(" + numCounter + ") " + gradebook[i].toString());
            } catch(NullPointerException e) {
                System.out.println("Your gradebook is empty!");
            }
        }
    }

    static void printGradeAvg(AssignmentInterface[] gradebook, int gradeIndex) {
        System.out.println("\nYou chose to PRINT AVERAGE OF ALL GRADES in gradebook.");

        int sum = sumValues(gradebook,'4', gradeIndex);
        double avg = (double) (sum / gradeIndex);
        System.out.println("The average of all grades is: " + avg);
    }

    static void printQuestionAvg(AssignmentInterface[] gradebook, int gradeIndex) {
        System.out.println("\nYou chose to PRINT AVERAGE NUMBER OF QUESTIONS in QUIZZES.");
        int sum = sumValues(gradebook,'6', gradeIndex);
        double avg = (double) (sum / gradeIndex);
        System.out.println("The average number of questions is: " + avg);
    }

    static void printProgramConcepts(AssignmentInterface[] gradebook, int gradeIndex) {
        System.out.println("\nYou chose to PRINT ALL CONCEPTS in PROGRAMS.");
        int programCount = 0;

        for(int i = 0; i < gradeIndex; i++) {

            try {
                if(gradebook[i] instanceof Program) {
                    programCount++;
                    System.out.println(gradebook[i].getName() + "'s concept: \n" + ((Program)gradebook[i]).getConcept());
                }
            } catch(NullPointerException e) {
                System.out.println("Your gradebook is empty!");
                return;
            }
        }

        if(programCount == 0) System.out.println("You have no programs in your gradebook.");
    }

    static void printAssociatedReadings(AssignmentInterface[] gradebook, int gradeIndex) {
        System.out.println("\nYou chose to PRINT ALL ASSOCIATED READINGS in DISCUSSIONS.");
        int discussionCount = 0;

        for(int i = 0; i < gradeIndex; i++) {

            try {
                if(gradebook[i] instanceof Discussion) {
                    discussionCount++;
                    System.out.println(gradebook[i].getName() + "'s associated reading: \n" + ((Discussion)gradebook[i]).getReadingContent());
                }
            } catch(NullPointerException e) {
                System.out.println("Your gradebook is empty!");
                return;
            }
        }

        if(discussionCount == 0) System.out.println("You have no discussions in your gradebook.");
    }

    static void findMaxMin(AssignmentInterface[] gradebook, int gradeIndex) {
        System.out.println("You chose to PRINT HIGHEST AND LOWEST GRADES in gradebook.");
        int lowest = gradebook[0].getScore(), highest = gradebook[0].getScore();

        for(int i = 0; i < gradeIndex; i++) {
            try {
                if(gradebook[i].getScore() < lowest) {
                    lowest = gradebook[i].getScore();
                }

                if(gradebook[i].getScore() > highest) {
                    highest = gradebook[i].getScore();
                }
            } catch(NullPointerException e) {
                System.out.println("Your gradebook is empty!");
                return;
            }
        }

        System.out.println("Lowest grades:");
        for(int i = 0; i < gradeIndex; i++) {
            if(gradebook[i].getScore() == lowest) {
                System.out.println(gradebook[i].toString());
            }
        }

        System.out.println("Highest grades:");
        for(int i = 0; i < gradeIndex; i++) {
            if(gradebook[i].getScore() == highest) {
                System.out.println(gradebook[i].toString());
            }
        }
    }

}

