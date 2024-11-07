
// Description: Reverse Order of String
// Aurthor: Tristan Sim
// Version: 1.00

/**
You are required to write a class with a main() method which accepts a single input parameter of String type 
and returns nothing. Within the body of the method, you are required to write the code which will print the input string 
on the screen but in reverse order and in the reverse case.

For example, if the input string is "Hello World!!!", the printed output should be "!!!DLROw OLLEh"
**/

import java.util.*; 

public class ReverseString {
    
     // This method takes and String and reverses the orders of the characters 
     /** 
      @param inputString the String which will have the characters reversed in order
      @return A new String in the reversed order
     **/
     public static String reverseStringOrder(String inputString) {
        
        // String Buffer (Multi-Thread Safe): Part of the "java.lang" Package for less memory intensive String Concatenations
        // Alternative is StringBuilder (Not Multi-Thread Safe)
        StringBuffer reverseStringBuffer = new StringBuffer(); 

        for (int iterI = inputString.length() - 1; iterI >= 0; iterI--)  // - 1 for zero based indexing
        {    
            // Fetch the Last Character of the String
             char currentChar = inputString.charAt(iterI); 

             // Invert Capital Letters to Small 
             if (Character.isUpperCase(currentChar)) {
                reverseStringBuffer.append(Character.toLowerCase(currentChar)); 
             } else if (Character.isLowerCase(currentChar)) {
                reverseStringBuffer.append(Character.toUpperCase(currentChar)); 
             } else {
                reverseStringBuffer.append(currentChar); 
             }
        }

        return reverseStringBuffer.toString(); 
     }

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in); 

        System.out.println("Input the Text String: ");
        String inputText = scanner.nextLine(); 
        
        String reverseOrderText = reverseStringOrder(inputText); 

        System.out.println("Reversed Order: " + reverseOrderText); 

        scanner.close();
    }

}