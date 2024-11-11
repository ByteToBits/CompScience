
/** 
 * Description: Test Array List Methods 
 * @author Tristan Sim
 * @version 1.00
 */

import java.util.ArrayList;
import java.util.Scanner; 

public class TestArrayList {
    
    public static void main(String[] args)
    {
        ArrayList<String> daysOfWeek = new ArrayList<String>(); 
        Scanner console = new Scanner(System.in); 
        
        // Array List Add Method 
        daysOfWeek.add("Monday");   daysOfWeek.add("Tuesday"); 
        daysOfWeek.add("Wednesday");   daysOfWeek.add("Thursday"); 
        daysOfWeek.add("Friday"); daysOfWeek.add("Satuday"); 
  
        System.out.println("Please Enter the Word 'Sunday':"); 
        daysOfWeek.add(console.nextLine()); 
        
        // Array List: Get Method 
        System.out.println("\nObjects inside tje Collections:"); 
        for (int iterI = 0; iterI < daysOfWeek.size(); iterI++) {
            System.out.println(daysOfWeek.get(iterI)); 
        }

        // Remove add Days except Sunday | Use a backwards Iteration
        for (int iterI = daysOfWeek.size() - 2 ; iterI >= 0; iterI--) {
            daysOfWeek.remove(0);
        }
        System.out.println("\nThe Remove Method Excuted and the Collection is left with : " + daysOfWeek.get(0)); 
        // ArrayList Set Method 
        daysOfWeek.set(0, "Sunday"); 
        System.out.println("\nThe Set Method sets the Object to : " + daysOfWeek.get(0)); 

        System.out.println("\nPart 2: Array List for Integers (Autoboxing)"); 

        ArrayList<Integer> evenNumbers = new ArrayList<Integer>(); 

        for (int number = 1; number < 20; number++)
        {
            if (number%2 == 0){
                evenNumbers.add(number); 
            }
        }

        System.out.println("The number of even numbers between 1 to 20 are " + evenNumbers.size()); 
        int firstEvenNumber = evenNumbers.get(0); 
        System.out.println("The First Even Number is " + firstEvenNumber);

    }

}
