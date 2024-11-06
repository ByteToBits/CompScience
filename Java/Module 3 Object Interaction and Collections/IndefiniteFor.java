
// Description: Creates an Indefinie For Loop
// Aurthor: Tristan Sim
// Version: 1.00 

import java.util.Scanner; 

public class IndefiniteFor {

/* Task: Write the code for such a for loop that keeps repeating to print out a number until the user tells the loop to exit.  */

    public IndefiniteFor() {
    }

    public static void main(String[] args) 
    {
        // Declare Local Variables
        String userInput; 
        Scanner scanner = new Scanner(System.in);

        for (int iterI = 0; ; iterI++) {

            System.out.println("Current Count: " + iterI);

            System.out.println("Continue Execution? [Y/N]");
            userInput = scanner.nextLine(); 

            if (userInput.equalsIgnoreCase("N")) {
                break; 
            }
        }
        
        System.out.println("Loop Terminated...");
        scanner.close();
    }

}
