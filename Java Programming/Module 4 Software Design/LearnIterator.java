
/**
 * Description: Iterator Class for the use with ArrayList
 * @author Tristan Sim
 * @version 1.00
 */

import java.util.ArrayList;
import java.util.Iterator;

public class LearnIteratorClass {
    
    public static void main(String[] args)
    {
        ArrayList<String> list = new ArrayList<>(); 

        list.add("Hello"); list.add("World!"); 
        list.add("Tomas"); list.add("is"); 
        list.add("Learning"); list.add("Java"); 

        System.out.println("The original size of the Array List is " + list.size()); 

        // Use the Iterator Class
        Iterator<String> iterI = list.iterator(); 
        System.out.println("Output using while loop with iterator:"); 
        // hasNext() Method returns True if the next element in the iterator does not throw an exception
        while (iterI.hasNext()) {
              
            String val = iterI.next(); 
            if (!val.equals("World!")) {
                iterI.remove();
            }
            else 
               System.out.println(val); 

        }

        System.out.println("New Size of Array List " + list.size()); 

    }

}
