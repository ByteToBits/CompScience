
/**
 * Try: Will attempt to execute a set of Instructions
 * Catch: Would Catch the Exception if the instruction encounters an error & execute a set of given instructions
 * Finally: Will always execute
 */

public class ExceptionHandling {
    
    public ExceptionHandling() {}
    
    public static void main(String[] args)
    {   
        try {
            int intValue = Integer.parseInt("Test");
            System.out.println("Integer: " + intValue);
        } catch (NumberFormatException | NullPointerException nfe) {
            System.out.println("The value parse is not a Number!"); 
        } finally {
            System.out.println("This Print will always executed regardless of exceptions"); 
        }
    }
}
