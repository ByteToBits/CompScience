
/**
 * Description: Input Class acts as a Support Class to Take User Inputs
 * @author Tristan Sim
 * @version 1.00
 */

 /*
  Task Instructions: 
  Create a new class called Input as shown in the class diagram above. This class is responsible for accepting input from the user via the keyboard.
  The class accepts different inputs based on the data type. These are mentioned in the class diagram.
  The String parameter passed to each method is the display message that gets printed on the screen informing the user what needs to be entered. 
  For example, "Please enter your name."
  The purpose of this class is to demonstrate the reusability of methods within your program.
  This approach also allows you to suppress some of the errors associated with using the Scanner class when accepting input of different data types.
  */

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
       this.scanner = new Scanner(System.in); 
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
        System.out.println(message); 
        String tempString = scanner.nextLine(); 

        if (index < 0 || index >= message.length()) {
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
        System.out.println(message);  
        String tempString = scanner.nextLine(); 
        return Double.parseDouble(tempString); 
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