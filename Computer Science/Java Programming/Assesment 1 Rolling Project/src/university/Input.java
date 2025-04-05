package university;
/**
 * Description: Input Class acts as a Support Class to Take User Inputs
 * @author Tristan Sim
 * @version 1.00
 */

/*
  Changelog: 
  1) Removed the Scanner Class from the Constructor 
  Using Scanner as a class attribute isn’t ideal since it locks the console/terminal until explicitly closed or garbage collected. 
  Instead, define it as a temporary object within methods to avoid locking issues and ensure proper resource management.
*/

import java.util.Scanner; 

public class Input 
{
    // Fields 
    private static final Scanner scanner = new Scanner(System.in); // Shared Scanner instance for Input Class

    /** 
     * Default Constructor: Removed Scanner Initialization 
     * Scanner is now shared across methods as a static field.
     */
    public Input() 
    {
       // No initialization required for Scanner
    }

    /**
     * Custom Method: Accepts a Display Message as a String to prompt for a Character
     * @param message The Message Display to prompt the user Input as a String
     * @param index The Index of the Character as an Integer, Default as 0
     * @return Returns a Character Type
     * @throws IndexOutOfBoundsException if the index is out of range for the input string.
     */
    public char acceptCharInput(String message, int index) 
    {   
        System.out.println(message); 
        String tempString = scanner.nextLine(); 
        if (index < 0 || index >= tempString.length()) // Fixed to validate against tempString length
        {
            throw new IndexOutOfBoundsException("Index is out of range");
        }
        return tempString.charAt(index);
    }
    
    /**
     * Custom Method: Accepts a Display Message as a String to prompt for a Double Input
     * @param message The Message Display to prompt the user Input as a String
     * @return Returns a Double Type
     */
    public double acceptDoubleInput(String message) 
    {   
        while (true) {
            try {
                System.out.println(message);  
                String tempString = scanner.nextLine(); 
                return Double.parseDouble(tempString); // Convert to double and return immediately
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid Input. Please try again...");  
            }
        }
    }

    /**
     * Custom Method: Accepts a Display Message as a String to prompt for an Integer Input
     * @param message The Message Display to prompt the user Input as a String
     * @return Returns an Integer Type
     */
    public int acceptIntegerInput(String message) throws NumberFormatException
    {   
        System.out.println(message); 
        String tempString = scanner.nextLine(); 
        return Integer.parseInt(tempString); // Convert to integer and return immediately
    }

    /**
     * Custom Method: Accepts a Display Message as a String to prompt for a String Input
     * @param message The Message Display to prompt the user Input as a String
     * @return Returns a String Type
     */
    public String acceptStringInput(String message)
    {  
        System.out.println(message); 
        return scanner.nextLine(); 
    }

    /**
     * Custom Method: Closes the shared Scanner instance for Input Class
     * To be called explicitly when the program ends.
     */
    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
