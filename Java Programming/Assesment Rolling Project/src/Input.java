
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

import java.text.ParseException;
import java.util.Scanner; 

public class Input 
{
    // Fields 
    private Scanner scanner; 

    /** 
     * Default Constructor: to Initialize the Scanner Class
     */
    public Input() 
    {
      // this.scanner = new Scanner(System.in); 
    }

    /**
     * Custom Method:  Accepts a Display Message as a String to prompt for a Character
     * @param message The Message Display to prompt the user Input as a String
     * @param index The Index of the Character as an Integer, Default as 0
     * @return Returns a Character Type
     * @throws IndexOutOfBoundsException if the index is out of range for the input string.
     */
    public char acceptCharInput(String message, int index) 
    {   
        try (Scanner console = new Scanner(System.in)) 
        {
            System.out.println(message); 
            String tempString= console.nextLine(); 

            if (index < 0 || index >= message.length())
            {
                throw new IndexOutOfBoundsException("Index is out of range");
            }
            return tempString.charAt(index);
        }
    }
    
    /**
     * Custom Method: Accepts a Display Message as a String to prompt for a Double Input
     * @param message The Message Display to prompt the user Input as a String
     * @return Returns a Double Type
     */
    public double acceptDoubleInput(String message) 
    {   
        Double convertedString = 0.0; 
        try (Scanner console = new Scanner(System.in))
        {   
            while (true) {
                try {
                    System.out.println(message);  
                    String tempString = console.nextLine(); 
                    convertedString = Double.parseDouble(tempString);
                    return convertedString;
                } catch (NumberFormatException nfe) {
                    System.out.println("Invalid Input. Please try again...");  
                }
            }
        }
    }

    /**
     * Custom Method: Accepts a Display Message as a String to prompt for a Integer Input
     * @param message The Message Display to prompt the user Input as a String
     * @return Returns a Integer Type
     */
    public int acceptIntegerInput(String message)
    {   
        System.out.println(message);
        String temString = scanner.nextLine(); 
        return Integer.parseInt(temString); 
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
     * Custom Methods: Closes the Scanner Class for the Input Class
     */
    public void closeScanner() {
      if (scanner != null) {
            scanner.close();
        }
    }

}