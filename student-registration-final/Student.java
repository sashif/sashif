/***
 * SEMESTER LONG PROJECT
 * SAMIRA ASHIF
 * CS 313 SUMMER 2021
 * PROFESSOR SMITH-THOMPSON
 */

public class Student {
    String firstName;
    String lastName;
    String IDNo;

    public Student(){
        firstName = "";
        lastName = "";
        IDNo = "";
    }

    /***
     * CONSTRUCTOR
     * a student object will take three arguments, the first name, last name and ID number of the student
     * @param firstName first name of student
     * @param lastName last name of student
     * @param IDNo ID number of student
     */

    public Student(String firstName, String lastName, String IDNo){
        this.firstName = firstName;
        this.lastName = lastName;
        this.IDNo = IDNo;
    }

    /***
     * prints values contained in student as a string
     */

    public String toString(){
        return firstName + " " + lastName + " " + IDNo;
    }

    /***
     * this method compares if two student objects are equal to one another
     * if the last name, first name, and ID number of the two students are the same
     * true is returned
     */
    public boolean compareTo (Student other){
        if (getLastName().compareTo(other.getLastName())==0){
            if (getFirstName().compareTo(other.getFirstName())==0){
                if (getIDNo().compareTo(other.getIDNo())==0){
                    return true;
                }
            }
        }
        return false;
    }

    /***
     * this method confirms whether or not a string is a number (needed to check ID number of student)
     * @param num the string we are checking
     * @return true if all the characters in the string return true for isDigit, false if not (then the string
     * is not a number)
     */
    public boolean isNum(String num) {
        return (num.chars().allMatch(Character::isDigit));

    }

    public boolean equals(Student other){
        return getIDNo().trim().equals(other.getIDNo().trim());
    }

    /***
     * this method will check if the ID number of the student is valid
     * a student ID number is valid if the first letter matches that of the student's last name,
     * followed by 6 digits
     * and is a total length of 7 digits
     * @param s is a student we are checking the ID number of
     * @return true if the student ID is valid
     */
    public boolean checkIDNo(Student s){
        String ID;
        ID = s.getIDNo().trim();
        return ((ID.length()) == 7) && (s.getLastName().charAt(0) == ID.charAt(0)) && (isNum(ID.substring(ID.length()-6)));
    }

    /***
     * Below are the appropriate accessors and mutators for the Student class.
     */

    public String getFirstName(){
        return firstName;
    }

    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIDNo(){
        return IDNo;
    }

    public void setIDNo(String IDNo) {
        this.IDNo = IDNo;
    }

}
