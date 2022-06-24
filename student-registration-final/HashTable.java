/***
 * SEMESTER LONG PROJECT
 * SAMIRA ASHIF
 * CS 313 SUMMER 2021
 * PROFESSOR SMITH-THOMPSON
 */

import java.util.ArrayList;
import java.util.Arrays;
public class HashTable {
    ArrayList<Student>[] table;
    int len;

    /***
     * CONSTRUCTOR
     * @param len an integer value that determines the size of the hashtable (type: int)
     * a hash table will be created as an "array of ArrayLists" of whatever size is taken as an argument
     * the hashtable is filled with null values
     */
    public HashTable(int len) {
        this.len = len;
        table = new ArrayList[len];
        Arrays.fill(table, null);
    }

    /***
     * INSERT method
     * @param s the student being added into the hashtable (type: Student)
     * @param table the hash table (type: array)
     * this method will insert a student into the hashtable based on their ID number
     * String ID is the ID number of the student s, obtained from the .getIDNo() method in Student class
     * String lastTwo is a substring containing the last two characters in String ID
     * int slot is the String lastTwo converted into an Integer using parseInt()
     * if statement first checks if the index of the hashtable equal to int slot is null, if so, create a new arraylist in that index
     * if the slot already has an arraylist, simply add the Student s into the hashtable at the index equal to the value of int slot
     * runtime of insert is O(1)
     */
    public void insert(Student s, ArrayList<Student>[] table) {
        String ID = s.getIDNo().trim();
        String lastTwo = ID.substring(ID.length()-2);
        int slot = Integer.parseInt(lastTwo)%(len-1);
        if(table[slot]==null){
            table[slot]= new ArrayList<>();
        }
        table[slot].add(s);
    }

    /***
     * SEARCH method
     * @param x the student ID number being searched (type:String)
     * @param table the hash table (type: array)
     * this method will search for ID in the hashtable
     * int key is the last two digits of the student ID number (string x)
     * int arrayIndex is the index of the hashtable in which the ID number is stored, found by %(array length-1)
     * if the slot contains an arraylist, we search through the arraylist to see if any student objects have an IDNo equal to x (user input)
     * however, if the slot in which the ID is assumed to be in is empty (in other words, if the index contains null value), the ID is not in the hashtable
     * @return the student found to have an ID number equal to x.
     * null value will be returned if a student with an IDNo equal to x is not found in the arraylist,
     * or if the index of the hashtable associated with x is null
     * runtime of search is O(1)
     */
    public Student search(String x, ArrayList<Student>[] table){
        int key = Integer.parseInt(x.substring(x.length()-2));
        int arrayIndex = key%(len-1);
        if(table[arrayIndex]!=null) {
            for (int i = 0; i < table[arrayIndex].size(); i++) {
                if (x.equals((table[arrayIndex].get(i).getIDNo()).trim())) {
                    return table[arrayIndex].get(i);
                }
            }
        }
        System.out.println("Student not found.");
        return null;
    }

    /***
     * DELETE method
     * @param x the student ID number being deleted (type: String)
     * int key is the last two digits of the ID number (String x)
     * int arrayIndex is the index of the hashtable in which the ID number is stored, found by %(array length-1)
     * the arraylist at that index is iterated through to find a student object which has an ID number matching String x
     * if a student is found with an ID number matching string x, we use the remove() method of ArrayList to delete the Student from the ArrayList
     * runtime of delete is O(1)
     */
    public void delete(String x){
        int key = Integer.parseInt(x.substring(x.length()-2));
        int arrayIndex = key%(len-1);
        if(table[arrayIndex]!=null) {
            for (int i = 0; i < table[arrayIndex].size(); i++) {
                if (x.equals(table[arrayIndex].get(i).getIDNo().trim())) {
                    table[arrayIndex].remove(table[arrayIndex].get(i));
                }
            }
        }
    }

}
