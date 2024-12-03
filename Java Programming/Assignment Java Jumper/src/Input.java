/**
 * Description: Input Class handles the User Input from the Console. 
 * @author Tristan Sim
 * @version 1.01
 */

import java.util.Scanner; 

public class Input
{   
    Scanner console; 

    /**
     * Default Constructor
    */
    public Input(Scanner console)
    {
        this.console = console; 
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
        String tempString = this.console.nextLine(); 
        return tempString.charAt(index);
    }

    /**
     * Custom Method: Accepts a Display Message as a String to prompt for a Double Input
     * @param message The Message Display to prompt the user Input as a String
     * @return Returns a Double Type
     * @throws NumberFormatException if the input is not a valid double
     */
    public double acceptDoubleInput(String message) throws NumberFormatException 
    {
        System.out.println(message);  
        String tempString = this.console.nextLine(); 
        return Double.parseDouble(tempString); // Convert to double and return immediately
    }

    /**
     * Custom Method: Accepts a Display Message as a String to prompt for an Integer Input
     * @param message The Message Display to prompt the user Input as a String
     * @return Returns an Integer Type
     */
    public int acceptIntegerInput(String message) throws NumberFormatException
    {   
        System.out.println(message); 
        String tempString = this.console.nextLine(); 
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
        return this.console.nextLine(); 
    }

}