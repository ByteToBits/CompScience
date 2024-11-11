
/** Description: This class is to practice the use of Hash Maps 
 * @author Tristan Sim
 * @version 1.00
 */

// Learning Material: "Coding with John" From Youtube

import java.util.HashMap;

public class LearnHashMap {
    
    public static void main(String[] args) 
    {
        // Create a Generic Hash Map Class 
        //  HashMap<String, Integer> => The Key is a String while the Value is an Integer (Java Class)
        HashMap<String,Integer> employeeID = new HashMap<>();
        
        // Put Method of Hash Map
        employeeID.put("Pierre", 5555); 
        employeeID.put("John", 1236); 
        employeeID.put("Tomas", 8888); 
        
        System.out.println("Print the Hash Map:"); 
        System.out.println(employeeID); 
        
        // Get Method of Hash Map
        System.out.print("Hash Map Get Method, Get Tomas' ID: "); 
        System.out.println(employeeID.get("Tomas")); 

        // Check if Key or Value exist in HashMap
        System.out.print("Check if the Name 'George' Exist in Hash Map: "); 
        System.out.println(employeeID.containsKey("George")); 
        System.out.print("Check if the Value '1236' Exist in Hash Map: "); 
        System.out.println(employeeID.containsValue(1236)); 

        // Replace Value for a Specific Key
        employeeID.replace("Tomas", 1988);
        System.out.print("Replace Value for Tomas, New Value is: " + employeeID.get("Tomas"));

    }

}
