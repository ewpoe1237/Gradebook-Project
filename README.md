# Gradebook-Project-Part-One
Project for CPSC 2810, Fall 2021 - Jenna Hofseth
Developed using IntelliJ Community 2021

This is a gradebook creator/editor program which prompts the user for the size and details of the initial gradebook, and then gives the user a menu to edit or display items in said gradebook. 

The gradebook is limited to a size of 20, and has 3 different types of grades the user can add--quizzes, discussions, and programs.
> Quizzes have a unique field variable, questionCount,that describes the amount of questions within the individual quiz.
> Discussions have a unique field variable, readingContent, that contains the required reading content for the individual discussion.
> Programs have a unique field variable, concept, that describes the programming concept for the programming assignment.

Grades also have common field variables used in each type:
> Score - the grade's score as an integer number
> Letter - the character (letter grade) that corresponds to the number grade. This is automatically generated when a score is input; the user does not have to manually input it.
> Name - the name of the assignment
> Due Date (MM/DD/YYYY) - the due date of the assignment

Using the menu, users can: 
> Add or delete grades to the program
> Print the list of grades or the average of all grades in the gradebook
> Print the unique field variables of each grade type
