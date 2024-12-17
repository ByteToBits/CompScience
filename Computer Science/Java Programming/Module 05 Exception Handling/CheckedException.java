
/*
 * Checked Exception: Is an Exception that is checked during compile time (Compilation Error); 
 * It won't compile unless it is resolved or is becomes a Declared Exception
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException; 

public class CheckedException {
    
    public static void main(String[] args) throws FileNotFoundException // This removes the checked exception
    {   
        try {
            readFile("myFile.txt"); 
        } catch (Exception e) {
            System.out.println("No File was Found..."); 
        }
    }
    
    // File Not File Error, is a Checked Error
    // "throws" makes it a Declared Exception and propogates the exception handling to the caller of this method
    private static void readFile(String fileName) throws FileNotFoundException, IOException {
        try (@SuppressWarnings("unused")
        FileReader fileReader = new FileReader(fileName)) {
            // FileReader is used here, and no further action is needed for a minimal approach
        } catch (FileNotFoundException e) {
            throw e;  // Re-throwing the exception to be caught in main
        }
    }

}
