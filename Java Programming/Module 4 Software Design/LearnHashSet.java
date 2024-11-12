
/** Description: This class is to practice the use of Hash Sets 
 * @author Tristan Sim
 * @version 1.00
 */

// Learning Material: "Coding with John" From Youtube
// When is HashSet Useful? Store Data with no Duplicates with no particular order
// Why Hash? It uses the HashTable as a storage mechanism

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set; 

public class LearnHashSet {
    
    public static void main(String[] args) 
    {   
        Set<String> names = new HashSet<>(); 

        names.add("Walter"); 
        names.add("Skyler"); 
        names.add("Saul"); 
        names.add("Mike");  
        names.add("Walter"); // Duplicate || Sets Do not Allow Duplciates
        
        // ArrayList has a Order as you add them
        // Hash Set has not particular Order
        System.out.println(names);

        // For-Each Loop (No Order)
        // Remarks: Java Doesn't allow the ".remove()" Method for the For-Each Loop
        System.out.println("Printed using the Basic For Each Loop");
        for (String name : names)
        {
            System.out.println(name);
        }
        
        // Lambda Function for For-Eacg Loop
        System.out.println("\nPrinted using a Lambda Function");
        names.forEach(System.out::println); 

        // Application: We want to remove duplicates from a List
        List <Integer> numberList = new ArrayList<>(); 
        numberList.add(1); numberList.add(2); 
        numberList.add(2); numberList.add(3); 
        numberList.add(1); numberList.add(3); 
        System.out.println("\nNumber List: " + numberList);

        Set<Integer> numberSet = new HashSet<>(numberList); 

        System.out.println("Using HashSet to Remove Duplicates: " + numberSet + "\n");

    }
}
