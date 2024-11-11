
/** Description: This class is to practice the use of Hash Table 
 * @author Tristan Sim
 * @version 1.00
 */

// Learning Material: "Bro Code" From Youtube

import java.util.*; 

public class LearnHashTable {

    public static void main(String[] args)
    {   
        // The Key will be the String and Value will be the String
        Hashtable<String, String> table = new Hashtable<>(20); 

        table.put("100", "Tommy Cliffhanger"); 
        table.put("123", "Tomas Pierre"); 
        table.put("321", "Shaun Longjohns"); 
        table.put("666", "Katana Joe"); 
        table.put("888", "Wrangler Bob"); 

        // Print all the Keys of
        for (String key : table.keySet()) 
        {
            System.out.println("Hashcode: " + key.hashCode() + "\tBucket Index: " + key.hashCode() % 10 + "\tKey:" + key + "\tValue: " + table.get(key));
        }

    }

}