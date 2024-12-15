package university;

/** 
 * Description: The Validation Class validates all the user Input
 * @author Tristan Sim
 * @version 1.00 
 */

/** Task: 
 * Now create a new class called Validation as shown in the class diagram above. 
 * This class is responsible for performing validation on the various inputs made by the user. 
 * The required methods are defined in the class diagram.
 */

 public class Validation 
 {
    
    /** Default Constructor */
    public Validation() {}
    
    /**
     * Custom Method: Checks if the String is Null
     * @param inputString The input String to be checked if it is null
     * @return returns a boolean (True - Null Value | False - String has a Value)
     */
    public boolean isBlank(String inputString)
    {
        if (inputString == null || inputString.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
  
    /** 
     * Custom Method: Checks if the String Input is within the size range
     * @param inputString The input String to be checked for size limit
     * @param minLength The Minimum Length the String can be as an Integer Type
     * @param maxLength The Maximum Length the String can be as an Integer Type
     * @return returns a boolean (True - Within Range | False - Out of Range)
     */
    public boolean lengthWithinRange(String inputString, int minLength, int maxLength)
    {
         if (inputString == null || inputString.isEmpty()) {
            return false;
         } 
         return ((inputString.length() >= minLength) && (inputString.length() <= maxLength)); 
    }
    
 }