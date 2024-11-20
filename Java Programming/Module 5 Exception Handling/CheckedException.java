
/*
 * Checked Exception: Is an Exception that is checked during compile time (Compilation Error); 
 * It won't compile unless it is resolved or is becomes a Declared Exception
 */

import java.io.FileNotFoundException;
import java.io.FileReader; 

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
    private static void readFile(String fileName) throws FileNotFoundException 
    {
        FileReader fileReader = new FileReader(fileName); 
    }

}
