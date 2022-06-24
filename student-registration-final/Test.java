/***
 * SEMESTER LONG PROJECT
 * SAMIRA ASHIF
 * CS 313 SUMMER 2021
 * PROFESSOR SMITH-THOMPSON
 */

import java.io.*;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Test {

    public static void main(String[] args){

        System.out.println("Welcome to the Student Registration System." + "\n" + "__________________________________________________________________");

/***
 * create a new Scanner, called scan, that will be used to obtain user input throughout this Test class
 * create a new Queue called waitingList, which will serve as a waiting list for students if a current roster is full
 * create a new BST called roster, which will contain students in the roster. They are added by last name
 * This BST will be the primary structure to make changes to the roster, including insert, delete, and search by name
 * create a new HashTable called rosterTable, which will contain all students in the roster. They are added by ID number
 * This HashTable will be the structure to make changes to the roster, such as delete and search by ID number
 */

        Scanner scan = new Scanner(System.in);
        Queue waitingList = new Queue();
        BST roster = new BST(null);
        HashTable rosterTable = new HashTable(20);

        /***
         The while loop will continue to run indefinitely unless the loop is broken.
         We want the program to keep running, and to only cease when the user attempts to save changes or exit.
         */

        while (true) {

            int userInput;
            System.out.println("""

                    Main menu:\s
                    (1) Load roster from a file\s
                    (2) Add student\s
                    (3) Drop student\s
                    (4) View waiting list\s
                    (5) Search student\s
                    (6) Save\s
                    (7) Save changes\s
                    (8) Exit\s
                    _____________________________________________________""");

            /***
             Before evaluating the user input, we use a try/catch block to catch an exception and correct the user
             if an input that is not an integer is entered.
             The main menu is displayed again if an InputMismatchException is caught, and the user
             is prompted to enter another input
             */

            try{
                userInput = scan.nextInt();

                /***
                 * Create a switch statement. User input is compared with the values of each case.
                 */

                switch (userInput) {
                    case 1:

                        /***
                         * Before executing the code in case 1, we add a try/catch block to catch FileNotFoundException
                         * in the case that the file entered by user was not located.
                         * Instead of ceasing the program, we catch the exception and tell the user the file was not found.
                         * The main menu is displayed for the user to try again.
                         * The text file should be located in the same folder of the "project" or "workspace" folder of the IDE,
                         * where the .java files are also saved.
                         * for example, the text file I use is labeled "roster.txt"
                         */

                        try {

                            /***
                             * If userInput is equal to 1, we attempt to load a roster from an existing text file
                             * User is prompted to enter file name, and the BufferedReader opens that file
                             * reader will go through every line of the file, and value of lines is incremented time
                             * int lines is equal to the number of lines in the file
                             */

                            scan.nextLine();
                            System.out.println("Enter a file.");
                            String filename = scan.nextLine();
                            BufferedReader reader = new BufferedReader(new FileReader(filename));
                            int lines = 0;
                            while (reader.readLine() != null) {
                                lines++;
                            }
                            reader.close();

                            File file = new File(filename);

                            /***
                             * Scanner reads file and splits the text by spaces, entering each subsequent string into
                             * string values by .next()
                             * a new student object is created for every line in the file, entering the String values obtained from .next()
                             * as the arguments
                             * the new student is added into rosterTable (type: HashTable) by the insert method in the HashTable class
                             * the new student is also added into roster (type: BST) by the insert method in the BST class
                             * The current roster is printed from the BST by inorder traversal, displaying students
                             * in alphabetical order.
                             */

                            Scanner readFile = new Scanner(file);
                            readFile.useDelimiter("[ \n]");
                            for (int i = 0; i < lines; i++) {
                                String firstName = readFile.next();
                                String lastName = readFile.next();
                                String IDNo = readFile.next().trim();
                                Student newStudent = new Student(firstName, lastName, IDNo);
                                rosterTable.insert(newStudent, rosterTable.table);
                                roster.insert(newStudent);
                            }

                            System.out.println("Current roster:");
                            roster.inOrder();
                            break;

                        } catch (FileNotFoundException e) {
                            System.out.println("File could not be located.");
                        }

                    case 2:

                        /***
                         *If userInput is equal to 2, a new student must be added into the roster
                         * Adding to both the BST and the HashTable
                         * the user is prompted to enter first and last name
                         * adding a while loop before entering student ID number attempts to correct a wrong ID number entry
                         * If the ID number entered by user is invalid, they are prompted to try again.
                         * if the ID number entered by user is a valid entry (first letter of last name, followed by 6 digits)
                         * then a new student object is created.
                         * maximum capacity of roster is currently set to 20.
                         * if the roster is not yet full, the new student is added to roster and rosterTable
                         * we print the current, updated roster by an inorder traversal of the BST roster.
                         * If the roster is at maximum capacity, the new student is added to the queue waitingList
                         */

                        scan.nextLine();
                        System.out.println("Enter student first name:");
                        String inputFirstName = scan.nextLine();
                        String addFirstName = inputFirstName.substring(0, 1).toUpperCase() + inputFirstName.substring(1).toLowerCase();
                        System.out.println("Enter student last name:");
                        String inputLastName = scan.nextLine();
                        String addLastName = inputLastName.substring(0, 1).toUpperCase() + inputLastName.substring(1).toLowerCase();

                        while(true) {
                            System.out.println("Enter student ID number:");
                            String inputIDNo = scan.nextLine();
                            String addIDNo = inputIDNo.substring(0, 1).toUpperCase() + inputIDNo.substring(1);
                            Student addStudent = new Student(addFirstName, addLastName, addIDNo);
                            if((rosterTable.search(addStudent.getIDNo(), rosterTable.table)==null)) {
                                if (addStudent.checkIDNo(addStudent)) {
                                    if (roster.getNumNodes(roster.root) < 20) {
                                        roster.insert(addStudent);
                                        rosterTable.insert(addStudent, rosterTable.table);
                                        System.out.println("Student added to record: " + addStudent);

                                        System.out.println("\n" + "The updated roster is: ");
                                        roster.inOrder();

                                    } else {
                                        waitingList.enQ(addStudent);
                                        System.out.println("The roster is full. Student added to waiting list.");
                                    }
                                    break;
                                } else {
                                    System.out.println("Invalid ID number. Enter again:");
                                }
                            }
                            else{
                                System.out.println("ID number already exists in roster! Enter again.");
                            }
                        }

                        break;

                    case 3:

                        /***
                         *If userInput is equal to 3, an existing student will be deleted from the roster
                         * the student is removed from both the BST and HashTable
                         * inorder traversal of BST is displayed, so that the user can view which students are currently in the roster
                         * the user is asked to enter a last name or an ID number.
                         */

                        System.out.println("The current roster is: ");
                        roster.inOrder();
                        scan.nextLine();
                        System.out.println("Enter student ID number or student last name:");
                        String inputSearchStudent = scan.nextLine();
                        String searchStudent = inputSearchStudent.substring(0, 1).toUpperCase() + inputSearchStudent.substring(1).toLowerCase();

                        /***
                         * if the input is an ID number (this is checked by grabbing the last character of the input; if it is
                         * a number, then the user has entered an ID number) the HashTable is searched for the student
                         * corresponding to the ID number entered
                         * If found, the user will be asked for confirmation. Upon entering Y, the student will be removed
                         * from both the BST and HashTable
                         * if there are students in the waiting list, then the first student on queue will be added to both
                         * the BST and the HashTable, and the waiting list is dequeued.
                         * the user is given confirmation that the student has been deleted.
                         */

                        if(Character.isDigit(searchStudent.charAt(searchStudent.length()-1))){
                            System.out.println(rosterTable.search(searchStudent, rosterTable.table));
                            if (rosterTable.search(searchStudent, rosterTable.table) != null){
                                System.out.println("Confirm student?" + "\n" + "(Y) Yes (N) No");
                                String confirm = scan.nextLine();
                                if (confirm.equals("Y") || confirm.equals("Yes") || confirm.equals("yes")) {
                                    Student deleteStudent = rosterTable.search(searchStudent, rosterTable.table);
                                    roster.delete(deleteStudent);
                                    rosterTable.delete(deleteStudent.getIDNo());
                                    System.out.println(deleteStudent + " has been deleted from the roster.");
                                    if (waitingList.front != null) {
                                        roster.insert(waitingList.front.s);
                                        rosterTable.insert(waitingList.front.s, rosterTable.table);
                                        waitingList.deQ();
                                    }
                                    System.out.println("\n" + "The updated roster is: ");
                                    roster.inOrder();
                                }
                            }
                        }

                        /***
                         * if the input is a last name (this is the only other option if the user input does not end in a number)
                         * the BST is searched by student last name
                         * If found, the user will be asked for confirmation. Upon entering Y, the student will be removed
                         * from both the BST and HashTable
                         * if there are students in the waiting list, then the first student on queue will be added to both
                         * the BST and the HashTable, and the waiting list is dequeued.
                         * the user is given confirmation that the student has been deleted.
                         */

                        else {
                            roster.searchLastName(searchStudent);
                            if (roster.searchLastName(searchStudent) != null) {
                                System.out.println("Confirm student?" + "\n" + "(Y) Yes (N) No");
                                String confirm = scan.nextLine();
                                if (confirm.equals("Y") || confirm.equals("Yes") || confirm.equals("yes")) {
                                    rosterTable.delete(roster.searchLastName(searchStudent).getIDNo());
                                    roster.delete(roster.searchLastName(searchStudent));
                                    System.out.println("has been deleted from the roster.");
                                    if (waitingList.front != null) {
                                        roster.insert(waitingList.front.s);
                                        rosterTable.insert(waitingList.front.s, rosterTable.table);
                                        waitingList.deQ();
                                    }
                                    System.out.println("\n" + "The updated roster is: ");
                                    roster.inOrder();
                                }
                            }
                        }
                        break;

                    case 4:

                        /***
                         * If user input is equal to 4, the waiting list will be displayed to the user.
                         * If the waiting list is empty (checked by isEmpty() method in Queue class), the user will be notified.
                         * If isEmpty() is false, there are students in the waiting list- so the length of the waiting list is printed,
                         * and the waiting list is displayed by the print() method in Queue class
                         */

                        if(waitingList.isEmpty()){
                            System.out.println("Waiting list is currently empty.");
                        }
                        else{
                            System.out.println("Waiting list (" + waitingList.getLength() + "): ");
                            waitingList.print();
                        }
                        break;

                    case 5:

                        /***
                         * If user input is equal to 5, the BST or HashTable will be searched through.
                         * The user is asked if they would like to search by last name or ID number
                         * A while loop contains the code for searching, to correct the user if an input
                         * that is neither 1 or 2 is entered
                         */

                        scan.nextLine();
                        while(true) {
                            System.out.println("""
                                    Would you like to search by:\s
                                    (1) Last name
                                    (2) ID number""");
                            String option = scan.nextLine();

                            /***
                             * If the user enters 1, they are prompted to enter a last name
                             * the BST is searched through for the last name entered
                             * If a student is found with a matching last name, the searchLastName() method in BST will return the student
                             * If not, the user will be notified that the student was not found.
                             */

                            if (option.equals("1")) {
                                System.out.println("Enter the last name of the student:");
                                String inputSearchName = scan.nextLine();
                                String searchName = inputSearchName.substring(0, 1).toUpperCase() + inputSearchName.substring(1).toLowerCase();
                                roster.searchLastName(searchName);
                                break;
                            }

                            /***
                             * If the user enters 2, they are prompted to enter an ID number
                             * the HashTable is searched through for the ID number entered
                             * If a student is found with a matching ID number, the search() method in HashTable will return the student
                             * If not, the user will be notified that the student was not found.
                             */

                            else if(option.equals("2")){
                                System.out.println("Enter the ID number of the student:");
                                String inputSearchIDNo = scan.nextLine();
                                String searchIDNo = inputSearchIDNo.substring(0, 1).toUpperCase() + inputSearchIDNo.substring(1);
                                if(rosterTable.search(searchIDNo, rosterTable.table)!=null){
                                    System.out.println(rosterTable.search(searchIDNo, rosterTable.table));
                                }
                                break;
                            }
                            else{
                                System.out.println("Enter 1 or 2 and try again.");
                            }
                        }
                        break;

                    case 6:

                        /***
                         * If the user input is equal to 6, the BST will be copied into an array and printed for the user.
                         * User is notified that the roster has been "saved" (into an array).
                         */

                        roster.copy();
                        System.out.println("Saved.");
                        break;

                    case 7:

                        /***
                         * If the user input is equal to 7, the BST is copied into an array of Student objects, called students
                         * The values (Student objects) of students are written to a new file called "newRoster.txt" in the designated
                         * destination
                         * The Queue waitingList is also written to a new file by calling copyToFile() method of the Queue method
                         */
                        scan.nextLine();
                        System.out.println("Enter the destination to which you would like to save the roster: " + "\n" +
                                "example: C:\\Users\\ashif\\IdeaProjects\\CS313_Project4 ");
                        String destination = scan.nextLine();
                        FileWriter writer = new FileWriter(new File(destination, "newRoster.txt"));
                        int listLength = roster.getNumNodes(roster.root);
                        Student [] students = new Student[listLength];
                        roster.toArray(roster.root, students);

                        System.out.println("Saving roster: ");
                        for (Student student : students) {
                            System.out.println(student);
                            writer.write(student + "\n");
                        }
                        writer.close();
                        waitingList.copyToFile(destination);
                        System.out.println("Changes saved.");
                        break;

                    case 8:

                        /***
                         * If the user input is equal to 8, a message is displayed to the user that
                         * the program is closing.
                         */

                        System.out.println("Closing program...");
                        break;
                }

                /***
                 * If user input is outside of the cases (less than 1, greater than 8), prompt user to enter a new input.
                 * If user input is 7 or 8, execute case 7 or 8 and end the while loop. If userInput is neither 7 or 8,
                 * the while loop will continue.
                 */

                if ((userInput < 1) || (userInput > 8)) {
                    System.out.println("Please select an option from 1-8 and try again.");
                }

                if ((userInput != 7) && (userInput != 8)) {
                    continue;
                }
                break;
            }
            catch(InputMismatchException e){
                scan.nextLine();
                System.out.println("Please select an option from 1-8 and try again.");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}