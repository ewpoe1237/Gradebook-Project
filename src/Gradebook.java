import java.util.Scanner;

public class Gradebook { //our main class

    public static void main(String[] args) {
        //when first run, should prompt the user to initialize your gradebook
        //gradebook is an array of your interface
        //ask the user to initialize the size of the gradebook -- max 20 grades
        System.out.println("•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦Gradebook Constructor Program•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
        System.out.println("•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦Jenna Hofseth•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");

        System.out.println("Welcome to the Gradebook Constructor Program!");
        System.out.println("Please enter the size of your gradebook as a positive integer, with a maximum of 20 grades.");
        int bookSize = 0;
        Scanner sc = new Scanner(System.in);
        char choice = 'n';
        while (choice != 'y'){
            try {
                String line = sc.nextLine();
                bookSize = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer (whole number) value.");
                continue;
            }

            if(bookSize > 20 || bookSize < 0) {
                System.out.println("Please enter a size less than or equal to 20, and greater or equal to 0.");
                continue;
            }

            System.out.println("You have confirmed your gradebook size as: " + bookSize);
            System.out.println("Is this information correct? Enter 'y' to continue or a different character to input your size again.");
            choice = sc.next().charAt(0);
        }

        //once initialized, prompt the user w/ a menu w/ a few different options
        System.out.println("•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦Menu•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦");
        System.out.println("Input an option's number to initiate its actions.");
        System.out.println("(1) ADD A GRADE TO YOUR GRADEBOOK");
        System.out.println("(2) REMOVE THE FIRST INSTANCE OF A GIVEN ASSIGNMENT FROM GRADEBOOK");
        System.out.println("(3) DISPLAY ALL GRADES RECORDED");
        System.out.println("(4) DISPLAY AVERAGE OF ALL GRADES IN GRADEBOOK");
        System.out.println("(5) DISPLAY THE HIGHEST AND LOWEST SCORES RECORDED");
        System.out.println("(6) DISPLAY THE AVERAGE NUMBER OF QUESTIONS AMONG ALL QUIZZES");
        System.out.println("(7) DISPLAY ALL ASSOCIATED READINGS FROM DISCUSSIONS");
        System.out.println("(8) DISPLAY ALL CONCEPTS COVERED WITHIN PROGRAMMING ASSIGNMENTS");

        //add grades: adds a grade to the array
        //user should be prompted to tell the program which type of grade they
        //are adding and what the values (name, score, etc) of each grade is

        //remove grades: removes the first instance of a certain grade
        //based on the name of the assignment
        //if the gradebook does not contain the grade the user is trying to remove,
        //program should throw an InvalidGradeException
        //no grades at all -> GradebookEmptyException

        //print grades: prints all in grade book, no specific formatting required
        //should gradebook be empty -> GradebookEmptyException

        //print average -> print avg of all grades in gradebook
        //should gradebook be empty -> GradebookEmptyException

        //print highest/lowest score(s): print highest/lowest score in gradebook
        //if only one grade, then that grade is highest and lowest
        //empty -> GradebookEmptyException

        //print quiz question avg: print avg # of ?s among all quizzes
        //empty -> GradebookEmptyException
        //assignments but no quizzes -> print that there are no quizzes yet

        //print discussion associated readings: print all associated readings from discussions
        //empty -> GradebookEmptyException
        //assignments but no discussions -> print that there are no discussions yet

        //print program concepts: print all concepts covered from programs
        //empty -> GradebookEmptyException
        //assignments but no programs -> print that there are no programs yet
    }
}
