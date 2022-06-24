/***
 * SEMESTER LONG PROJECT
 * SAMIRA ASHIF
 * CS 313 SUMMER 2021
 * PROFESSOR SMITH-THOMPSON
 */

import java.io.*;

public class Queue {
    QNode front;
    QNode rear;

    /***
     * This queue will be implemented using a Linked List structure.
     * The nodes of this linked list will be QNodes (see QNode class)
     * To begin, we set front and rear of the queue equal to null.
     */
    public Queue(){
        this.front = null;
        this.rear = null;
    }

    /***
     * ENQUEUE function
     * @param s the student being added to the queue
     * a new node is created containing data of Student s
     * if the queue is empty (checked if front is null), the new node becomes front and rear of the queue
     * if the queue already has a front, it is not empty, so the node is appended as the next node.
     * it is also the last object in the queue, so the new node becomes the rear
     * runtime of O(1)
     */
    public void enQ(Student s){
        QNode t = new QNode(s);
        if (rear == null) {
            front = rear = t;
            return;
        }
        rear.next = t;
        rear = t;
    }

    /***
     * DEQUEUE function
     * if the queue is empty, there are no objects to remove
     * if the queue has a front, the subsequent node is set to be front of queue, removing it from the queue
     * runtime is O(1)
     */
    public void deQ(){
        if (front == null)
            return;
        front = front.next;
        if (front == null)
            rear = null;
    }

    /***
     * this method checks if the queue is empty or not
     * if front is null, then the queue is empty
     * @return true if the queue is empty and false if it contains any value(s)
     * runtime is O(1)
     */
    public boolean isEmpty(){
        return front==null;
    }

    /***
     * int length is a counter (it will start at zero and increment in a while loop)
     * beginning at the front of the queue, we move from node to node, increasing the value of length each time
     * until we reach the end of the queue, which is determined by null
     * @return length of the queue, in other words, how many students are currently in the queue
     * runtime is O(n)
     */
    public int getLength(){
        QNode current = front;
        int length = 0;
        while (current != null)
        {
            length++;
            current = current.next;
        }
        return length;
    }

    /***
     * this method will save the contents of the queue into a new ".txt" file
     * a new FileWriter is created to write to a new file which will be named "Waiting List.txt" in the designated destination
     * starting at the front, we write the student objects from the queue into the new array file called "Waiting List.txt"
     * until reaching null, which means the end of the queue has been reached
     * close the FileWriter
     * runtime is O(n)
     * @throws IOException an error has occurred during an input/output operation of FileWriter
     */
    public void copyToFile (String destination) throws IOException {
        FileWriter writer = new FileWriter(new File(destination, "Waiting List.txt"));
        QNode current = front;

        while (current != null)
        {
            writer.write(current.s + "\n");
            current = current.next;
        }
        writer.close();
    }

    /***
     * PRINT method
     * this method will print the objects (type: Student) in the queue
     * beginning from the front node of the queue, we move from node to node, printing the data (student) contained in each respective node
     * until we reach the end of the queue, determined by null, at which point the while loop will cease
     * runtime is O(n)
     */
    public void print(){
        QNode current = front;
        while (current != null) {
            System.out.println(current.s.toString());
            current = current.next;
        }
    }
}

