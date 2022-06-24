/***
 * SEMESTER LONG PROJECT
 * SAMIRA ASHIF
 * CS 313 SUMMER 2021
 * PROFESSOR SMITH-THOMPSON
 */

public class BST {
    Node root;
    private Exception EmptyTreeException;

    /**
     * CONSTRUCTOR
     * @param emptyTreeException will be thrown if the root is null - thus, the tree is found to be empty
     * to begin, we set the value of root to null
     */
    public BST(Exception emptyTreeException){
        EmptyTreeException = emptyTreeException;
        root = null;
    }

    /***
     * checks if the tree is empty
     * @return true if root is null (no values contained in the BST) and false if root has a value (BST contains values)
     * runtime is O(1)
     */
    public boolean isEmpty(){
        return (root == null);
    }

    /***
     * this method will return the object with max value in the BST
     * this is found by moving to the rightmost node of the BST, which has the largest value
     * @param root begins as the root of the BST, then becomes root.right every time the function is recursively called
     * @return the student whose last name is lexicographically ordered last
     * runtime is O(log n)
     */
    private Student findMax(Node root) {
        if (root.right != null){ return findMax(root.right); }
        return root.s;
    }

    /***
     * recursive method for INSERT
     * @param s student that is being inserted into the BST
     */
    public void insert(Student s){
        root = insert(root, s);
    }

    /***
     * INSERT method
     * @param root root node of the BST
     * @param s new student being added into the BST
     * the last name of Student s is obtained by getLastName() in student class
     * the last name of Student s is compared to the last name of the student in root
     * if Student s last name is lexicographically less than that of the student at the root, it moves to the left
     * if Student s last name is lexicographically greater than that of the student at the root, it moves to the right
     * @return the root of BST, which moves every time the method is called recursively
     * runtime is O(log n)
     */
    private Node insert(Node root, Student s){
        if (root != null){
            if (s.getLastName().compareTo(root.s.getLastName())<0){
                root.left = insert(root.left, s);
            }
            else{
                root.right = insert(root.right, s);
            }
            return root;
        }
        return new Node(s);
    }

    /***
     * recursive method for DELETE
     * @param s student that is being deleted from the BST
     * @throws Exception
     */
    public void delete(Student s) throws Exception {
        if (isEmpty()){ throw EmptyTreeException; }
        root = delete(root, s);
    }

    /***
     * DELETE method
     * @param root root node of the BST
     * @param s existing student being deleted from the BST
     * @return root of BST, or null if the BST is empty (student not found)
     * This method searches through the BST by comparing the last name of the Student s with the last name of the student at root
     * If the last name of s is lexicographically less than that of the root, we move left
     * If it is greater, then we move right
     * the root of the BST moves every time the delete method is called recursively (left or right, depending on value obtained from compareTo)
     * if the right child of the root is null, return the left child
     * if the left child of root is null, return right child
     * replace the deleted value with max value of the BST, obtained by findMax on the left subtree of the root
     * runtime is O(log n)
     */
    private Node delete(Node root, Student s) {
        if (root == null){
            System.out.println("Student not found.");
            return null;
        }
        if(s.getLastName().compareTo(root.s.getLastName()) < 0) {
            root.left = delete(root.left, s);
        }
        else if (s.getLastName().compareTo(root.s.getLastName()) > 0) {
            root.right = delete(root.right, s);
        }
        else {
            if (root.right == null) {
                return root.left;
            }
            if (root.left == null) {
                return root.right;
            }
            root.s = findMax(root.left);
            root.left = delete(root.left, root.s);
        }
        return root;
    }

    /***
     * recursive method for SEARCH
     * @param lastName a string being searched for in BST
     * @return student found by calling search method
     * @throws Exception
     */
    public Student searchLastName(String lastName) throws Exception {
        if (isEmpty()) { throw EmptyTreeException; }
        return searchLastName(lastName, root);
    }

    /***
     * SEARCH method
     * @param lastName the last name of a student being searched for
     * @param root root node of BST
     * @return Student with a last name matching String lastName, if found in BST. If such a student is not found, null is returned.
     * This method searches through the BST by comparing the last name of the Student s with the last name of the student at root
     * If the last name of s is lexicographically less than that of the root, we move left
     * If it is greater, then we move right
     * the root of the BST moves every time the delete method is called recursively (left or right, depending on value obtained from compareTo)
     * If none of the students in BST have a last name matching String lastName, root will eventually reach a null value- thus, student not found
     * If the student was found, however, we print the full name and ID number of that student and return the data at the root of which it was found.
     * runtime is O(log n)
     */
    private Student searchLastName(String lastName, Node root) {
        if (root == null) {
            System.out.println("Student not found.");
            return null;
        }
        if (lastName.compareTo(root.s.getLastName()) < 0) {
            return searchLastName(lastName, root.left);
        }
        if (lastName.compareTo(root.s.getLastName()) > 0) {
            return searchLastName(lastName, root.right);
        }
        System.out.println(root.s.getFullName() + " " + root.s.getIDNo());
        return root.s;
    }

    /***
     * method to obtain length of BST
     * @param root root of BST
     * @return the number of nodes in BST, in other words, how many students are in the tree
     * if the BST is empty, the number of nodes is 0
     * if the root of BST contains a value, then we add 1 and recursively call getNumNodes until we reach null ("end" of BST)
     * runtime is O(n)
     */
    public int getNumNodes(Node root) {
        if(root == null) return 0;
        return 1 + getNumNodes(root.left) + getNumNodes(root.right);
    }

    /***
     * recursive method for inOrder traversal of the BST
     */
    public void inOrder(){
        inOrder(root);
    }

    /***
     * this method will traverse through the BST and print the students
     * by the order in which they were lexicographically sorted into the BST during insertion
     * the output will display a list of students in alphabetical order
     * @param root root of BST
     * runtime is O(n)
     */
    private void inOrder(Node root) {
        if (root != null) {
            inOrder(root.left);
            System.out.print(root.s + "\n");
            inOrder(root.right);
        }
    }

    /***
     * recursive method for toARRAY
     * @param root root of BST
     * @param students the array of student objects
     */
    public void toArray(Node root, Student [] students){
        toArray(0, students, root);
    }

    /***
     * this method will add students of the BST into an array by inorder traversal
     * this way, the array will contain alphabetically sorted students by last name
     * @param i index of the array
     * @param students the array containing student objects
     * @param root root of the BST
     * @return the index of the student last entered into array of students
     * the purpose of this method is to create a array that can be used to copy to a new file, or to copy into a new array
     * runtime is O(n)
     */
    public int toArray(int i, Student [] students, Node root) {
        if(root == null){
            return i;
        }
        i = toArray(i, students, root.left);
        students[i++] = root.s;
        toArray(i, students, root.right);
        return i;
    }

    /***
     * this method creates an array using the inorder traversal of the BST
     * int listLength is equal to the number of nodes in the BST
     * a new array called students is created, with size listLength
     * by calling toArray, we recursively add every student in the BST into students
     * the values in new array (student objects) are printed from index 0 to listLength-1
     * runtime is O(n)
     */
    public void copy() {
        int listLength = getNumNodes(root);
        Student [] students = new Student[listLength];
        int index = 0;
        toArray(index, students, root);

        System.out.println("Saving roster: ");
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
