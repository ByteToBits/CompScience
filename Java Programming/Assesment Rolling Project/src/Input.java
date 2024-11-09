
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
     * Custom Method: Accept Character Inputs
     * If the User inputs Character a Integer or String, convert it to a Char Type
     * @param inputString 
     * @param inputInteger 
     * @return Returns a Character Type
     */
    public char acceptCharInput(String inputString, int inputInteger) 
    {  
       if (inputString == null || inputString.isEmpty()) 
       {
           // Character Unicode has a Range of 0...65535 (16-bit Unsigned Integer)
           if (inputInteger <= 65535) 
           {   
               // Explicit Casting (Converting a 32-Bit Integer to a 16-Bit Character)
               return (char) inputInteger; 
           } else
           {
               // The high surrogate is in the range of 0xD800 to 0xDBFF
               int highSurrogate = (inputInteger - 0x10000) / 0x400 + 0xD800;
               return (char) highSurrogate; // Return the high surrogate as a char
           }
           
       } else
       {
         return inputString.charAt(0); 
       }
    }
    
   public static void main(String[] args) {
      Input input = new Input();

      // Test with a normal character
      System.out.println(input.acceptCharInput("", 65));    // Outputs: A (U+0041)

      // Test with a larger code point (supplementary character)
      System.out.println(input.acceptCharInput("", 0x1F600)); // Outputs: ���� (surrogate high)

      // Test with a larger code point (supplementary character)
      System.out.println(input.acceptCharInput("Test", 0)); // Outputs: T
  }

}