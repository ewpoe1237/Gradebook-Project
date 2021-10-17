# Gradebook-Project-Part-One
Project for CPSC 2810, Fall 2021 - Jenna Hofseth <br />
Developed using IntelliJ Community 2021 <br />
◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦•◦ <br />
This is a gradebook creator/editor program which prompts the user for the size and details of the initial gradebook, and then gives the user a menu to edit or display items in said gradebook. <br />

The gradebook is limited to a size of 20, and has 3 different types of grades the user can add--quizzes, discussions, and programs. <br />
> Quizzes have a unique field variable, questionCount,that describes the amount of questions within the individual quiz. <br />
> Discussions have a unique field variable, readingContent, that contains the required reading content for the individual discussion. <br />
> Programs have a unique field variable, concept, that describes the programming concept for the programming assignment. <br />

Grades also have common field variables used in each type: <br />
> Score - the grade's score as an integer number <br />
> Letter - the character (letter grade) that corresponds to the number grade. This is automatically generated when a score is input; the user does not have to manually input it. <br />
> Name - the name of the assignment <br />
> Due Date (YYYY/MM/DD) - the due date of the assignment <br />

Using the menu, users can: <br />
> Add or delete grades to the program <br />
> Print the list of grades or the average of all grades in the gradebook <br />
> Print the unique field variables of each grade type <br />
