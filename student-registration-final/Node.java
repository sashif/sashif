/***
 * SEMESTER LONG PROJECT
 * SAMIRA ASHIF
 * CS 313 SUMMER 2021
 * PROFESSOR SMITH-THOMPSON
 */

/***
 * BST Node class
 * These are the nodes of the Binary Search Tree structure
 * each node contains: a Student object, a left node, and a right node
 */
public class Node {
    Student s;
    Node left;
    Node right;

    /***
     * CONSTRUCTOR
     * @param s a student object, which is the data held by the nodes of our BST.
     * we set the values of the left and right nodes to null
     */
    public Node(Student s){
        this.s = s;
        left = null;
        right = null;
    }
}
