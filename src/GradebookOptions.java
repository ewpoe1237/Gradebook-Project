import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/*
Jenna Hofseth
CPSC 2810 - Fall 2021
Gradebook Project
 */

public class GradebookOptions {
    private static boolean alreadyLoggedIn = false;
    private static String user, password;

    static void promptFirstOptions() {
        for (String s : Arrays.asList("Enter 'X' to quit.",
                "(1) ADD A GRADE TO YOUR GRADEBOOK",
                "(2) REMOVE THE FIRST INSTANCE OF A GIVEN ASSIGNMENT FROM GRADEBOOK",
                "(3) PRINT ALL GRADES RECORDED",
                "(4) PRINT AVERAGE OF ALL GRADES IN GRADEBOOK",
                "(5) PRINT THE HIGHEST AND LOWEST SCORES RECORDED",
                "(6) PRINT THE AVERAGE NUMBER OF QUESTIONS AMONG ALL QUIZZES",
                "(7) PRINT ALL ASSOCIATED READINGS FROM DISCUSSIONS",
                "(8) PRINT ALL CONCEPTS COVERED WITHIN PROGRAMMING ASSIGNMENTS",
                "Type \"next\" to see more options; enter a number to perform that action.")) {
            System.out.println(s);
        }
    }

    static void promptSecondOptions() {
        for (String s : Arrays.asList("Enter 'X' to quit.",
                "(9) PRINT GRADEBOOK TO FILE",
                "(10) READ GRADEBOOK FROM FILE",
                "(11) ADD GRADEBOOK CONTENTS TO SQL DATABASE",
                "(12) ADD SQL DATABASE CONTENTS TO CURRENT GRADEBOOK",
                "(13) SEARCH SQL DATABASE",
                "Type \"next\" to see more options; enter a number to perform that action.")) {
            System.out.println(s);
        }
    }
    static String addGradePrompt() {
        System.out.println("\nYou chose to ADD A GRADE to your gradebook.");

        String input = "Z";
        Scanner sc = new Scanner(System.in);

        System.out.println("\nWhich type of grade would you like to add?");
        System.out.println("Type 'Q' for QUIZ");
        System.out.println("Type 'P' for PROGRAM");
        System.out.println("TYPE 'D' for DISCUSSION");
        System.out.println("If you would like to exit this option, type 'X'");

        while(!input.equals("Q") && !input.equals("P") && !input.equals("D") && !input.equals("X")) {
            input = sc.nextLine().toUpperCase().trim();

            if(!input.equals("Q") && !input.equals("P") && !input.equals("D") && !input.equals("X")) System.out.println("Please type 'Q' for quiz, 'P' for program, 'D' for discussion, or 'X' to exit this option.");
        }

        return input;
    }

    static void initializeCommonVariables(AssignmentInterface obj) {
        /*
            method that will initialize the score (automatically converted to letter, which is initialized without need for input),
            name of assignment, and duedate by prompting the user for these values.

            the user confirms their input using 'Y' or re-enters it by entering a different character during confirmation.
         */

        Scanner sc = new Scanner(System.in);
        int score = 0;
        String line = "", name = "";
        LocalDate dueDate = LocalDate.of(1900, 01, 01);


        System.out.println("\nPlease enter a name for your assignment.");
        name = sc.nextLine();
        System.out.println("You entered the name as: " + name);
        obj.setName(name);


        while(true) {
            System.out.println("\nPlease enter a score for your assignment.");
            line = sc.nextLine();

            try {
                score = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            break;
        }

        if(score < 0) { //defaulting to the cap in the case of out-of-bounds
            System.out.println("Score input was less than 0. Defaulting to 0.");
            score = 0;
        } else if(score > 250) {
            System.out.println("Score input was greater than 250. Defaulting to 250.");
            score = 250;
        }

        System.out.println("You entered the score as: " + score);
        obj.setScore(score);
        obj.setLetter(obj.scoreToLetter(score));


        System.out.println("\nPlease enter the due date for the assignment.");

        obj.setDueDate(getUserInputDate());
    }

    static int sumValues(ArrayList<AssignmentInterface> gradebook, char inputChoice) {
        int sum = 0;

        switch(inputChoice) {
            case '4': //this is for the avg of scores in gradebook
                for(int i = 0; i < gradebook.size(); i++) {
                    sum += gradebook.get(i).getScore();
                }

                break;
            case '6': //this is for the avg num of questions in quizzes
                int quizCount = 0;
                for(int i = 0; i < gradebook.size(); i++) {
                    if (gradebook.get(i) instanceof Quiz) {
                        quizCount++;
                        sum += ((Quiz) gradebook.get(i)).getQuestionCount();
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
        String readingContent = "";
        Scanner sc = new Scanner(System.in);

        System.out.println("\nPlease enter the reading content for the discussion.");
        readingContent = sc.nextLine();

        System.out.println("You entered the reading content as: " + readingContent);

        myDiscussion.setReadingContent(readingContent);
        return myDiscussion;
    }

    static Quiz addQuiz() {
        System.out.println("\nYou chose to add a QUIZ to your gradebook.");

        Quiz myQuiz = new Quiz();
        int questionCount = 0;
        Scanner sc = new Scanner(System.in);
        initializeCommonVariables(myQuiz);

        while(true) {
            System.out.println("\nPlease enter the total number of questions on the quiz.");
            String line = sc.nextLine();

            try {
                questionCount = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer number.");
                continue;
            }

            if(questionCount <= 0) {
                System.out.println("Question count was less than or equal to 0. Defaulting to 1.");
                questionCount = 1;
            }

            System.out.println("You entered the question count as: " + questionCount);
            break;
        }

        myQuiz.setQuestionCount(questionCount);
        return myQuiz;
    }

    static Program addProgram() {
        System.out.println("\nYou chose to add a PROGRAM to your gradebook.");

        Program myProgram = new Program();
        GradebookOptions.initializeCommonVariables(myProgram);
        String concept = "";
        Scanner sc = new Scanner(System.in);

        System.out.println("\nPlease enter the programming concept for the assignment.");
        concept = sc.nextLine();

        System.out.println("You entered the concept as: " + concept);

        myProgram.setConcept(concept);
        return myProgram;
    }

    static void removeGrades(ArrayList<AssignmentInterface> gradebook) throws GradebookEmptyException, InvalidGradeException {
        System.out.println("\nYou chose to REMOVE A GRADE from your gradebook.");
        if(gradebook.isEmpty()) throw new GradebookEmptyException(); //if the gradebook is empty, we will throw a GradebookEmptyException

        System.out.println("Please enter the name of the assignment you would like to remove.");
        Scanner sc = new Scanner(System.in);
        String assignmentName = sc.nextLine();

        for(int i = 0; i < gradebook.size(); i++) {
            if (gradebook.get(i).getName().equals(assignmentName)) {
                gradebook.remove(gradebook.get(i));
                System.out.println("Grade successfully removed at index: " + i);

                return; //will auto-exit once grade found
            }
        }

        throw new InvalidGradeException(); //no target found -> throw invalid grade exception
    }

    static ArrayList<AssignmentInterface> printGrades(ArrayList<AssignmentInterface> gradebook) throws GradebookEmptyException {
        System.out.println("\nYou chose to PRINT ALL GRADES in gradebook.");

        if(gradebook.isEmpty()) { //check to see whether gradebook is empty
            throw new GradebookEmptyException();
        }

        Scanner sc = new Scanner(System.in);
        String choice = "y";

        while(!choice.equalsIgnoreCase("X")) {
            System.out.println("\nPlease enter what criteria you would like the gradebook to be sorted by:");
            System.out.println("(A) SCORE (NUMERIC)");
            System.out.println("(B) LETTER");
            System.out.println("(C) ALPHABETICAL NAME");
            System.out.println("(D) DUE DATE");
            System.out.println("Enter \"X\" to quit.");

            choice = sc.nextLine();
            //first will create a copy of gradebook so we can sort without changing order in original
            ArrayList<AssignmentInterface> sorted = new ArrayList<>(gradebook);

            switch(choice.toUpperCase()) {
                case "X":
                    System.out.println("\nExiting to main menu.");
                    break;
                case "A":
                    System.out.println("\nYou chose to sort the gradebook by: SCORE (NUMERIC)");
                    printByScoreLetter(sorted);

                    choice = "X";
                    return sorted;
                case "B":
                    System.out.println("\nYou chose to sort the gradebook by: LETTER");
                    printByScoreLetter(sorted);

                    choice = "X";
                    return sorted;
                case "C":
                    System.out.println("\nYou chose to sort the gradebook by: ALPHABETICAL NAME");
                    sorted.sort(Comparator.comparing(g -> g.getName()));

                    for(int i = 0; i < sorted.size(); i++) {
                        System.out.println((i + 1) + ".  " + sorted.get(i).toString());
                    }

                    choice = "X";
                    return sorted;
                case "D":
                    System.out.println("\nYou chose to sort the gradebook by: DUE DATE");
                    sorted.sort(Comparator.comparing(g -> g.getDueDate()));

                    for(int i = 0; i < sorted.size(); i++) {
                        System.out.println((i + 1) + ".  " + sorted.get(i).toString());
                    }

                    choice = "X";
                    return sorted;
                default:
                    System.out.println("\nPlease enter a valid input.");
                    choice = "y";
            }
        }

        return null;
    }

    static void printByScoreLetter(ArrayList<AssignmentInterface> sorted) {
        //will only have to make one sort for score/letter since grade will translate equally to letter, hence different function
        sorted.sort(Comparator.comparing(g -> g.getScore()));

        for(int i = 0; i < sorted.size(); i++) {
            System.out.println((i + 1) + ".  " + sorted.get(i).toString());
        }
    }

    static void printGradeAvg(ArrayList<AssignmentInterface> gradebook) throws GradebookEmptyException {
        System.out.println("\nYou chose to PRINT AVERAGE OF ALL GRADES in gradebook.");
        if(gradebook.isEmpty()) throw new GradebookEmptyException();


        int sum = sumValues(gradebook,'4');
        double avg = sum / gradebook.size();

        System.out.println("The average of all grades is: " + avg);
    }

    static void printQuestionAvg(ArrayList<AssignmentInterface> gradebook) throws GradebookEmptyException {
        System.out.println("\nYou chose to PRINT AVERAGE NUMBER OF QUESTIONS in QUIZZES.");
        if(gradebook.isEmpty()) throw new GradebookEmptyException();

        int sum = sumValues(gradebook,'6');
        double avg = sum / gradebook.size();
        System.out.println("The average number of questions is: " + avg);
    }

    static void printProgramConcepts(ArrayList<AssignmentInterface> gradebook) throws GradebookEmptyException{
        System.out.println("\nYou chose to PRINT ALL CONCEPTS in PROGRAMS.");
        if(gradebook.isEmpty()) throw new GradebookEmptyException();

        int programCount = 0;

        for(int i = 0; i < gradebook.size(); i++) {
            if(gradebook.get(i) instanceof Program) { //instanceof is pretty cool. i didn't know about that
                programCount++;
                System.out.println(gradebook.get(i).getName() + "'s concept: \n" + ((Program)gradebook.get(i)).getConcept());
            }
        }

        if(programCount == 0) System.out.println("You have no programs in your gradebook.");
    }

    static void printAssociatedReadings(ArrayList<AssignmentInterface> gradebook) throws GradebookEmptyException {
        System.out.println("\nYou chose to PRINT ALL ASSOCIATED READINGS in DISCUSSIONS.");
        if(gradebook.isEmpty()) throw new GradebookEmptyException();

        int discussionCount = 0;

        for(int i = 0; i < gradebook.size(); i++) {
            if(gradebook.get(i) instanceof Discussion) {
                discussionCount++;
                System.out.println(gradebook.get(i).getName() + "'s associated reading: \n" + ((Discussion)gradebook.get(i)).getReadingContent());
            }
        }

        if(discussionCount == 0) System.out.println("You have no discussions in your gradebook.");
    }

    static void findMaxMin(ArrayList<AssignmentInterface> gradebook) throws GradebookEmptyException {
        System.out.println("\nYou chose to PRINT HIGHEST AND LOWEST GRADES in gradebook.");
        if(gradebook.isEmpty()) throw new GradebookEmptyException();

        int lowest = gradebook.get(0).getScore(), highest = gradebook.get(0).getScore();

        //finding the highest and lowest scores in the gradebook
        for(int i = 0; i < gradebook.size(); i++) {
            if(gradebook.get(i).getScore() < lowest) {
                lowest = gradebook.get(i).getScore();
            }

            if(gradebook.get(i).getScore() > highest) {
                highest = gradebook.get(i).getScore();
            }
        }

        /* since there could be multiple different assignments with lowest/highest grades, iterate thru gradebook and print each one with
           the same score as the lowest/highest scores we just found */
        System.out.println("\nLowest grades:");
        for(int i = 0; i < gradebook.size(); i++) {
            if(gradebook.get(i).getScore() == lowest) {
                System.out.println(gradebook.get(i).toString());
            }
        }

        System.out.println("\nHighest grades:");
        for(int i = 0; i < gradebook.size(); i++) {
            if(gradebook.get(i).getScore() == highest) {
                System.out.println(gradebook.get(i).toString());
            }
        }
    }

    static void printToFile(ArrayList<AssignmentInterface> gradebook) throws GradebookEmptyException {
        System.out.println("\nYou chose to PRINT GRADEBOOK CONTENT TO FILE.");
        if(gradebook.isEmpty()) throw new GradebookEmptyException();

        Scanner sc = new Scanner(System.in);
        String inputName;
        String directoryString = "GradeTextFiles/";

        //ask for file name, avoid special characters
        //str = str.replaceAll("[^a-zA-Z0-9]", " ");
        System.out.println("\nPlease enter the file name for the file you would like to create in GradeTextFiles. A .txt ending will be auto-appended to the file name.");
        System.out.println("Avoid using spaces or any special characters. If these happen to be included, they will be removed from the file name");

        inputName = sc.nextLine();
        inputName.replaceAll("[^a-zA-Z0-9]", "");
        inputName.trim();

        //check to make sure file name is not blank
        while(inputName.equalsIgnoreCase("")) {
            System.out.println("Please enter a non-empty file name.");

            inputName = sc.nextLine();
            inputName.replaceAll("[^a-zA-Z0-9]", "");
            inputName.trim();
        }

        inputName += ".txt";
        Path filePath = Paths.get(directoryString, inputName);

        //check to make sure file does not already exist
        while(Files.exists(filePath)) {
            System.out.println("Please enter a file name that does not already exist.");

            inputName = sc.nextLine();
            inputName.replaceAll("[^a-zA-Z0-9]", "");
            inputName.trim();

            filePath = Paths.get(directoryString, inputName);
        }

        //attempt creating file within directory
        while(true) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                System.out.println("IOException encountered. Please enter a valid file name and check for a valid directory.");

                inputName = sc.nextLine();
                inputName.replaceAll("[^a-zA-Z0-9]", "");
                inputName.trim();

                filePath = Paths.get(directoryString, inputName);

                continue;
            }

            break;
        }

        File myFile = filePath.toFile();

        try(PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(myFile)))) {
            for(int i = 0; i < gradebook.size(); i++) {
                //iterate thru gradebook and check type then subsequently print to file

                if(gradebook.get(i) instanceof Quiz) { //for quiz output
                    printer.println("Quiz\t" + gradebook.get(i).getName() + "\t" + gradebook.get(i).getScore() + "\t" + gradebook.get(i).getLetter() + "\t" + gradebook.get(i).getDueDate().toString() + "\t" + ((Quiz) gradebook.get(i)).getQuestionCount());
                } else if(gradebook.get(i) instanceof Discussion) { //discussion output
                    printer.println("Discussion\t" + gradebook.get(i).getName() + "\t" + gradebook.get(i).getScore() + "\t" + gradebook.get(i).getLetter() + "\t" + gradebook.get(i).getDueDate().toString() + "\t" + ((Discussion) gradebook.get(i)).getReadingContent());
                } else { //program output
                    printer.println("Program\t" + gradebook.get(i).getName() + "\t" + gradebook.get(i).getScore() + "\t" + gradebook.get(i).getLetter() + "\t" + gradebook.get(i).getDueDate().toString() + "\t" + ((Program) gradebook.get(i)).getConcept());
                }
            }

            System.out.println("\nGradebook has successfully been written into " + inputName + "!");
        } catch(IOException e) {
            System.out.println("\nIOException encountered. Please check your directory path.");
        }
    }

    static void readFromFile(ArrayList<AssignmentInterface> gradebook) {
        System.out.println("\nYou chose to READ GRADEBOOK CONTENT FROM FILE.");

        Scanner sc = new Scanner(System.in);
        String inputName;
        String directoryString = "GradeTextFiles/";

        //ask for file name, avoid special characters
        //str = str.replaceAll("[^a-zA-Z0-9]", " ");
        System.out.println("\nPlease enter the file name for the file you would like to load in from the GradeTextFiles folder.");

        inputName = sc.nextLine();
        inputName.replaceAll("[^a-zA-Z0-9]", "");
        inputName.trim();

        //check to make sure file name is not blank
        while(inputName.equalsIgnoreCase("")) {
            System.out.println("Please enter a non-empty file name.");

            inputName = sc.nextLine();
            inputName.replaceAll("[^a-zA-Z0-9]", "");
            inputName.trim();
        }

        inputName += ".txt";
        Path filePath = Paths.get(directoryString, inputName);

        //check to make sure file does not already exist
        while(Files.notExists(filePath)) {
            System.out.println("Please enter a file name that already exists.");

            inputName = sc.nextLine();
            inputName.replaceAll("[^a-zA-Z0-9]", "");
            inputName.trim();

            filePath = Paths.get(directoryString, inputName);
        }

        File myFile = filePath.toFile();
        String line;

        //attempt to read data from given file
        try(BufferedReader in = new BufferedReader(new FileReader(myFile))) {
            while((line = in.readLine()) != null) {

                //parse line based off \t regex
                //we can hard code indexes here since we will always have the same amount of regexes/property identifiers
                String[] propertiesArray = line.split("\t");
                String type = propertiesArray[0];
                String name = propertiesArray[1];
                String gradeInp = propertiesArray[2]; //my code auto-calculates score so we don't need it
                String dueDateInp = propertiesArray[4];
                String uniqueIdentifier = propertiesArray[5];
                int score = 0, year = 1900, month = 01, day = 01;
                LocalDate dueDate;

                //parse date into LocalDate
                String[] splitDate = dueDateInp.split("-");
                try {
                    year = Integer.parseInt(splitDate[0]);
                    month = Integer.parseInt(splitDate[1]);
                    day = Integer.parseInt(splitDate[2]);
                } catch(NumberFormatException e) {
                    System.out.println("Number format exception encountered; defaulting to a due date of 1900-01-01.");
                    year = 1900;
                    month = 01;
                    day = 01;
                }


                //check type first then add new object according to type
                if (type.equalsIgnoreCase("quiz")) {
                    int questionCount;
                    //parse question count into int
                    try {
                        questionCount = Integer.parseInt(uniqueIdentifier);
                    } catch(NumberFormatException e) {
                        System.out.println("Number format exception encountered; defaulting to 10 questions.");
                        questionCount = 10;
                    }

                    //parse score into int
                    try {
                        score = Integer.parseInt(gradeInp);
                    } catch(NumberFormatException e) {
                        System.out.println("Number format exception encountered; defaulting to a grade of 100 (A).");
                        score = 100;
                    }

                    dueDate = LocalDate.of(year, month, day);

                    Quiz newQuiz = new Quiz(questionCount, score, name, dueDate);
                    gradebook.add(newQuiz);
                } else if (type.equalsIgnoreCase("discussion")) {
                    //parse score into int
                    try {
                        score = Integer.parseInt(gradeInp);
                    } catch(NumberFormatException e) {
                        System.out.println("Number format exception encountered; defaulting to a grade of 100 (A).");
                        score = 100;
                    }

                    dueDate = LocalDate.of(year, month, day);

                    Discussion newDiscussion = new Discussion(uniqueIdentifier, score, name, dueDate);
                    gradebook.add(newDiscussion);
                } else {
                    //parse score into int
                    try {
                        score = Integer.parseInt(gradeInp);
                    } catch(NumberFormatException e) {
                        System.out.println("Number format exception encountered; defaulting to a grade of 100 (A).");
                        score = 100;
                    }

                    dueDate = LocalDate.of(year, month, day);

                    Program newProgram = new Program(uniqueIdentifier, score, name, dueDate);
                    gradebook.add(newProgram);
                }
            }

            System.out.println("\nGradebook has successfully loaded into using source file " + inputName + "!");
        } catch(IOException e) {
            System.out.println("\nIOException encountered. Please check your directory path and file name.");
        }
    }

    static void addToSql(ArrayList<AssignmentInterface> gradebook) {
        System.out.println("\nYou chose to ADD GRADEBOOK CONTENTS TO SQL DATABASE.");
        Scanner sc = new Scanner(System.in);

        if(!alreadyLoggedIn) {
            System.out.println("\nPlease enter your database access username. ");
            user = sc.nextLine();
            System.out.println("Now, please enter your password.");
            password = sc.nextLine();
            alreadyLoggedIn = true;
        }

        Connection connection;

        try {
            connection = DBUtil.getConnection(user, password, true);
        } catch(SQLException e) {
            System.out.println("\nThere was an error attempting to get the connection. Look over your username and password.");
            System.out.println("Exiting to menu.");
            alreadyLoggedIn = false;
            return;
        }

        String sql;

        //if the table does not yet exist, create it
        sql = "CREATE TABLE IF NOT EXISTS Gradebook ( "
                + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "
                + "Type VARCHAR(12) NOT NULL, "
                + "Name VARCHAR(100) NOT NULL, "
                + "Score INT NOT NULL, "
                + "Letter CHAR(1) NOT NULL, "
                + "DueDate VARCHAR(12) NOT NULL, "
                + "UniqueProperty VARCHAR(600) NOT NULL )";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println("\nThere was a SQLException encountered while trying to create a table in the case it doesn't exist.");
            System.out.println("ERROR CODE: " + e.getErrorCode());
            System.out.println("Exiting to menu.");
            return;
        }

        for(int i = 0; i < gradebook.size(); i++) {
            try {
                insertGrade(gradebook.get(i), connection); //insert grades using for loop iterating thru gradebook
            } catch(SQLException e) {
                System.out.println("Exiting to menu.");
                return;
            }
        }

        System.out.println("\nSuccessfully added contents to SQL database!");
    }

    static void insertGrade(AssignmentInterface grade, Connection connection) throws SQLException {
        String sql = "INSERT INTO Gradebook "
                + "(Type, Name, Score, Letter, DueDate, UniqueProperty) "
                + "VALUES "
                + "(?, ?, ?, ?, ?, ?) ";

        //helper; use PS to create a sql statement that will insert an individual grade into db
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            if(grade instanceof Quiz) {
                ps.setString(1, "Quiz");
                ps.setString(2, grade.getName());
                ps.setInt(3, grade.getScore());
                ps.setString(4, Character.toString(grade.getLetter()));
                ps.setString(5, grade.getDueDate().toString());
                ps.setString(6, Integer.toString(((Quiz) grade).getQuestionCount()));
            } else if(grade instanceof Discussion) {
                ps.setString(1, "Discussion");
                ps.setString(2, grade.getName());
                ps.setInt(3, grade.getScore());
                ps.setString(4, Character.toString(grade.getLetter()));
                ps.setString(5, grade.getDueDate().toString());
                ps.setString(6, ((Discussion) grade).getReadingContent());
            } else { //program
                ps.setString(1, "Program");
                ps.setString(2, grade.getName());
                ps.setInt(3, grade.getScore());
                ps.setString(4, Character.toString(grade.getLetter()));
                ps.setString(5, grade.getDueDate().toString());
                ps.setString(6, ((Program) grade).getConcept());
            }

            ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println("\nThere was an error trying to add grade \"" + grade.getName() + "\" to the DB.");
            System.out.println("ERROR CODE: " + e.getErrorCode());
            throw new SQLException(e);
        }
    }

    static void loadFromSql(ArrayList<AssignmentInterface> gradebook) {
        System.out.println("\nYou chose to ADD SQL DATABASE CONTENTS TO CURRENT GRADEBOOK.");
        /*
        check if there’s any unadded grades from the SQL table that are
        not in the gradebook.
        unadded grades -> call a SQL query (or queries) to return the grades and
        parse the result in a way that can be added to gradebook.
         */

        Scanner sc = new Scanner(System.in);

        if(!alreadyLoggedIn) {
            System.out.println("\nPlease enter your database access username. ");
            user = sc.nextLine();
            System.out.println("Now, please enter your password.");
            password = sc.nextLine();
            alreadyLoggedIn = true;
        }

        Connection connection;

        try {
            connection = DBUtil.getConnection(user, password, true);
        } catch(SQLException e) {
            System.out.println("\nThere was an error attempting to get the connection. Look over your username and password.");
            System.out.println("Exiting to menu.");
            alreadyLoggedIn = false;
            return;
        }

        String sql = "SELECT * FROM Gradebook " +
                "ORDER BY ID";

        try(PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                //Type, Name, Score, Letter, DueDate, UniqueProperty
                String inputType = rs.getString("Type");
                String name = rs.getString("Name");
                int score = rs.getInt("Score");
                //don't need letter because code auto-calculates
                String originalDate = rs.getString("DueDate");
                String uniqueProperty = rs.getString("UniqueProperty");

                //parsing the individual parts of each row's date
                String[] parsedDate = originalDate.split("-");
                int year;
                try {
                    year = Integer.parseInt(parsedDate[0]);
                } catch(NumberFormatException e) {
                    System.out.println("\nNumber format encountered while trying to parse due year. Will be defaulted as 1990");
                    year = 1990;
                }

                int month;
                try {
                    month = Integer.parseInt(parsedDate[1]);
                } catch(NumberFormatException e) {
                    System.out.println("\nNumber format encountered while trying to parse due month. Will be defaulted as 01");
                    month = 1;
                }
                int day;
                try {
                    day = Integer.parseInt(parsedDate[2]);
                } catch(NumberFormatException e) {
                    System.out.println("\nNumber format encountered while trying to parse due day. Will be defaulted as 01");
                    day = 1;
                }

                //check type, create object, compare
                if(inputType.equalsIgnoreCase("Quiz")) {
                    int parsedQuestions;
                    try {
                        parsedQuestions = Integer.parseInt(uniqueProperty);
                    } catch(NumberFormatException e) {
                        System.out.println("\nNumber format encountered while trying to parse questions in quiz. Will be defaulted as -1");
                        parsedQuestions = -1;
                    }

                    Quiz myQuiz = new Quiz(parsedQuestions, score, name, LocalDate.of(year, month, day));
                    if(!gradebook.contains(myQuiz)) gradebook.add(myQuiz);

                } else if(inputType.equalsIgnoreCase("Discussion")) {
                    Discussion myDiscussion = new Discussion(uniqueProperty, score, name, LocalDate.of(year, month, day));
                    if(!gradebook.contains(myDiscussion)) gradebook.add(myDiscussion);
                } else {
                    Program myProgram = new Program(uniqueProperty, score, name, LocalDate.of(year, month, day));
                }
            }
        } catch(SQLException e) {
            System.out.println("\nThere was an error trying to load grades from the DB into the gradebook.");
            System.out.println("ERROR CODE: " + e.getErrorCode());
            return;
        }

        System.out.println("\nDatabase contents successfully loaded into the gradebook!");
    }

    static void searchDB() {
        System.out.println("\nYou chose to SEARCH SQL DATABASE.");

        Scanner sc = new Scanner(System.in);

        if(!alreadyLoggedIn) {
            System.out.println("\nPlease enter your database access username. ");
            user = sc.nextLine();
            System.out.println("Now, please enter your password.");
            password = sc.nextLine();
            alreadyLoggedIn = true;
        }

        Connection connection;

        try {
            connection = DBUtil.getConnection(user, password, true);
        } catch(SQLException e) {
            System.out.println("\nThere was an error attempting to get the connection. Look over your username and password.");
            System.out.println("Exiting to menu.");
            alreadyLoggedIn = false;
            return;
        }

        String choice = "y", inputR = "";
        int minVal = 0, maxVal = 0;

        while(!choice.equalsIgnoreCase("X")) {
            System.out.println("\nPlease enter what criteria you would like the database to be searched by:");
            System.out.println("(A) QUIZZES");
            System.out.println("(B) PROGRAMS");
            System.out.println("(C) DISCUSSIONS");
            System.out.println("(D) GRADES WITHIN A CERTAIN RANGE [X,Y]");
            System.out.println("(E) GRADES WITHIN A CERTAIN DUE DATE RANGE");
            System.out.println("(F) GRADES WITH AN EVEN SCORE");
            System.out.println("Enter \"X\" to quit.");

            choice = sc.nextLine();

            //as long as choice is an invalid input we will continue printing menu, otherwise perform function and exit
            //uses search helper function(s)
            switch(choice.toUpperCase()) {
                case "X":
                    System.out.println("\nExiting to main menu.");
                    break;
                case "A":
                    System.out.println("\nYou chose to search the database information according to: QUIZZES");
                    search(connection, "A", 0, 0);

                    choice = "X";
                    break;
                case "B":
                    System.out.println("\nYou chose to search the database information according to: PROGRAMS");
                    search(connection, "B", 0, 0);

                    choice = "X";
                    break;
                case "C":
                    System.out.println("\nYou chose to search the database information according to: DISCUSSIONS");
                    search(connection, "C", 0, 0);

                    choice = "X";
                    break;
                case "D":
                    System.out.println("\nYou chose to search the database information according to: GRADES WITHIN A CERTAIN RANGE [X,Y]");

                    while(true) {
                        System.out.println("\nPlease enter the minimum grade in the search range.");
                        inputR = sc.nextLine().trim();
                        try {
                            minVal = Integer.parseInt(inputR);
                        } catch (NumberFormatException e) {
                            System.out.println("\nPlease enter a correctly-formatted integer (whole number) value.");
                            continue;
                        }

                        break;
                    }

                    if(minVal < 0) {
                        System.out.println("Input under the minimum value of 0. Defaulting to 0.");
                        minVal = 0;
                    }

                    while(true) {
                        System.out.println("\nPlease enter the maximum grade in the search range.");
                        inputR = sc.nextLine().trim();
                        try {
                            maxVal = Integer.parseInt(inputR);
                        } catch (NumberFormatException e) {
                            System.out.println("\nPlease enter a correctly-formatted integer (whole number) value.");
                            continue;
                        }

                        break;
                    }

                    if(maxVal > 100) {
                        System.out.println("Input over the maximum value of 100. Defaulting to 100.");
                        maxVal = 100;
                    }

                    search(connection, "D", minVal, maxVal);

                    choice = "X";
                    break;
                case "E":
                    System.out.println("\nYou chose to search the database information according to: GRADES WITHIN A CERTAIN DUE DATE RANGE [X,Y]");

                    //grab input min/max
                    System.out.println("\nPlease enter the minimum due date (inclusive).");
                    LocalDate minDate = getUserInputDate();

                    System.out.println("\nPlease enter the maximum due date (inclusive).");
                    LocalDate maxDate = getUserInputDate();

                    search(connection, "E", minDate, maxDate);

                    choice = "X";
                    break;
                case "F":
                    System.out.println("\nYou chose to search the database information according to: GRADES WITH AN EVEN SCORE");

                    search(connection, "F", 0, 0);

                    choice = "X";
                    break;
                default:
                    System.out.println("\nPlease enter a valid input.");
                    choice = "y";
            }
        }
    }

    static void search(Connection connection, String inputCode, int minVal, int maxVal) {
        String sql = "SELECT * FROM Gradebook "
                + "ORDER BY ID";
        int count = 0;
        //selects all, and based off inputCode parameter looks at properties that match the searched one. prints those to terminal.

        System.out.println("");

        try(PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                //Type, Name, Score, Letter, DueDate, UniqueProperty
                String name = rs.getString("Name");
                String sqlType = rs.getString("Type");
                String stringScore = Integer.toString(rs.getInt("Score"));
                String letter = rs.getString("Letter");
                String dueDate = rs.getString("DueDate");
                String uniqueProperty = rs.getString("UniqueProperty");

                int score = 0;

                try {
                    score = Integer.parseInt(stringScore);
                } catch(NumberFormatException e) {
                    System.out.println("There was an error parsing the score column value into an integer. Defaulting to 0.");
                }

                switch(inputCode) {
                    case "A":
                        count = printSameType(count, name, score, letter, dueDate, uniqueProperty, sqlType, "Quiz");
                        break;
                    case "B":
                        count = printSameType(count, name, score, letter, dueDate, uniqueProperty, sqlType, "Program");
                        break;
                    case "C":
                        count = printSameType(count, name, score, letter, dueDate, uniqueProperty, sqlType, "Discussion");
                        break;
                    case "D":
                        if(inGradeRange(score, minVal, maxVal)) {
                            count++;
                            printDBInfo(count, name, score, letter, dueDate, uniqueProperty, sqlType);
                        }
                        break;
                    case "F":
                        if(evenScore(score)) {
                            count++;
                            printDBInfo(count, name, score, letter, dueDate, uniqueProperty, sqlType);
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch(SQLException e) {
            System.out.println("\nThere was an error trying to search for the content.");
            System.out.println("ERROR CODE: " + e.getErrorCode());
        }

    }

    static void search(Connection connection, String inputCode, LocalDate minVal, LocalDate maxVal) {
        //overloaded function for date range search
        String sql = "SELECT * FROM Gradebook "
                + "ORDER BY ID";
        int count = 0;

        System.out.println("");

        try(PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                //Type, Name, Score, Letter, DueDate, UniqueProperty
                String name = rs.getString("Name");
                String sqlType = rs.getString("Type");
                String stringScore = Integer.toString(rs.getInt("Score"));
                String letter = rs.getString("Letter");
                String dueDate = rs.getString("DueDate");
                String uniqueProperty = rs.getString("UniqueProperty");

                int score = 0;

                try {
                    score = Integer.parseInt(stringScore);
                } catch(NumberFormatException e) {
                    System.out.println("There was an error parsing the score column value into an integer. Defaulting to 0.");
                }

                switch(inputCode) {
                    case "E":

                        int day = 01, month = 01, year = 1990;

                        //parsing the individual parts of each row's date
                        String[] parsedDate = dueDate.split("-");
                        try {
                            year = Integer.parseInt(parsedDate[0]);
                        } catch(NumberFormatException e) {
                            System.out.println("\nNumber format encountered while trying to parse due year. Will be defaulted as 1990");
                            year = 1990;
                        }

                        try {
                            month = Integer.parseInt(parsedDate[1]);
                        } catch(NumberFormatException e) {
                            System.out.println("\nNumber format encountered while trying to parse due month. Will be defaulted as 01");
                            month = 1;
                        }

                        try {
                            day = Integer.parseInt(parsedDate[2]);
                        } catch(NumberFormatException e) {
                            System.out.println("\nNumber format encountered while trying to parse due day. Will be defaulted as 01");
                            day = 1;
                        }

                        LocalDate observed = LocalDate.of(year, month, day);

                        //subsequently compare using helper
                        if(inDateRange(observed, minVal, maxVal)) {
                            count++;
                            printDBInfo(count, name, score, letter, dueDate, uniqueProperty, sqlType);
                        }

                        break;
                    default:
                        break;
                }
            }
        } catch(SQLException e) {
            System.out.println("\nThere was an error trying to search for the content.");
            System.out.println("ERROR CODE: " + e.getErrorCode());
        }

    }

    //helper functions to find certain properties (grade range, even score)
    static boolean inGradeRange(int score, int min, int max) {
        if(score >= min && score <= max) return true;
        else return false;
    }

    static boolean evenScore(int score) {
        if(score % 2 == 0) return true;
        else return false;
    }

    //prints an individual db row according to its type in the Type column
    static void printDBInfo(int count, String name, int score, String letter, String dueDate, String uniqueProperty, String sqlType) {
        if(sqlType.equalsIgnoreCase("Quiz")) {
            System.out.println(count + "." + " Name: "
                    + name + ", Score: "
                    + score + ", Letter: "
                    + letter + ", Due: " +
                    dueDate + ", Number of questions: "
                    + uniqueProperty);
        } else if(sqlType.equalsIgnoreCase("Discussion")) {
            System.out.println(count + "." + " Name: "
                    + name + ", Score: "
                    + score + ", Letter: "
                    + letter + ", Due: " +
                    dueDate + ", Reading content: "
                    + uniqueProperty);
        } else if(sqlType.equalsIgnoreCase("Program")) {
            System.out.println(count + "." + " Name: "
                    + name + ", Score: "
                    + score + ", Letter: "
                    + letter + ", Due: " +
                    dueDate + ", Programming concept: "
                    + uniqueProperty);
        }
    }

    //prints to terminal if we observe a row that has the same type as we are looking for
    static int printSameType(int count, String name, int score, String letter, String dueDate, String uniqueProperty, String sqlType, String type) {
        if(sqlType.equalsIgnoreCase("Quiz") && type.equalsIgnoreCase("Quiz")) {
            System.out.println(count + "." + " Name: "
                    + name + ", Score: "
                    + score + ", Letter: "
                    + letter + ", Due: " +
                    dueDate + ", Number of questions: "
                    + uniqueProperty);
            return count + 1;
        } else if(sqlType.equalsIgnoreCase("Discussion") && type.equalsIgnoreCase("Discussion")) {
            System.out.println(count + "." + " Name: "
                    + name + ", Score: "
                    + score + ", Letter: "
                    + letter + ", Due: " +
                    dueDate + ", Reading content: "
                    + uniqueProperty);
            return count + 1;
        } else if(sqlType.equalsIgnoreCase("Program") && type.equalsIgnoreCase("Program")) {
            System.out.println(count + "." + " Name: "
                    + name + ", Score: "
                    + score + ", Letter: "
                    + letter + ", Due: " +
                    dueDate + ", Programming concept: "
                    + uniqueProperty);
            return count + 1;
        }

        return count;
    }

    //date range helper
    static boolean inDateRange(LocalDate input, LocalDate min, LocalDate max) {
        if((input.compareTo(min) > 0 || input.compareTo(min) == 0) && (input.compareTo(max) < 0 || input.compareTo(max) == 0)) return true;
        else return false;
    }

    //an annoyingly long io function to create a LocalDate whenever we want to ask the user for that info (ie in the case of adding or searching date range)
    static LocalDate getUserInputDate() {
        int inputYear = 0, inputMonth = 0, inputDay = 0;
        int currentYear = LocalDate.now().getYear();
        Scanner sc = new Scanner(System.in);
        String line = "";

        while(true) {
            System.out.println("\nEnter the year as an integer number (ex: '2021'), no earlier than " + (currentYear - 25) + " and no later than " + (currentYear + 100) + ".");
            line = sc.nextLine();

            try {
                inputYear = Integer.parseInt(line);
            } catch(NumberFormatException e) {
                System.out.println("Please enter a valid integer number.");
                continue;
            }

            if(inputYear < currentYear - 25) {
                System.out.println("Your input is over 25 years earlier than the current year. Defaulting to " + currentYear + ".");
                inputYear = currentYear;
            } else if(inputYear > currentYear + 100) {
                System.out.println("Your input is over 100 years later than the current year. Defaulting to  " + (currentYear + 100) + ".");
                inputYear = currentYear + 100;
            }

            System.out.println("You entered the year as: " + inputYear);
            break;
        }

        while(true) {
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
            break;
        }

        while(true) {
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
            break;
        }

        return LocalDate.of(inputYear, inputMonth, inputDay);
    }

}

