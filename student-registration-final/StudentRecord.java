/***
 * SEMESTER LONG PROJECT
 * SAMIRA ASHIF
 * CS 313 SUMMER 2021
 * PROFESSOR SMITH-THOMPSON
 */

import java.util.ArrayList;

public class StudentRecord extends Student {
    public ArrayList<String> courses;

    /***
     * StudentRecord is an ArrayList associated with a student object, containing courses that the student is taking
     * the courses are added manually, one at a time, by calling the addCourse() method with the course as parameter
     * they may also be deleted by calling deleteCourse()
     * we can view the courses a student is taking by calling getCourse(), which prints the array list of courses
     */

    public StudentRecord(){
        courses = new ArrayList<>();
    }

    public StudentRecord(ArrayList<String> courses){
        this.courses = courses;
    }

    public void addCourse(String course) {
        courses.add(course);
    }

    public void deleteCourse(String course) {
        courses.remove(course);
    }

    public void getCourse() {
        courses.toString();
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public String toString(){
        String str = "";
        for(int i=0; i<courses.size(); i++){
            str = str + (courses.get(i) + " ");
        }
        return str;
    }
}
