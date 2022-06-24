/***
 * SEMESTER LONG PROJECT
 * SAMIRA ASHIF
 * CS 313 SUMMER 2021
 * PROFESSOR SMITH-THOMPSON
 */

public class QNode {
    QNode next;
    Student s;

    /***
     * CONSTRUCTOR
     * @param s a student object which is the data held by the nodes (QNode) of our queue.
     */
    public QNode(Student s){
        this.s = s;
        next = null;
    }
}
