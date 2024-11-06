
// Description: List all the Prime Numbers
// Aurthor: Tristan Sim
// Version: 1.00

// Task: Define a class, and in the main() method of the class ask the user for an integer input. 
//       Based on the input, the method must print out all the prime numbers less than that number.
// Note: a prime number is one which is only divisible by 1 and itself.

import java.util.Scanner;

public class ListPrime {
    
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in); 

        System.out.println("Input a Number: ");
        int userNumberInput = Integer.parseInt(scanner.nextLine()); 
        int numOfDivision; 

        for (int iterI = 1; iterI < userNumberInput; iterI++) {

            numOfDivision = 0; 

            for (int iterJ = 1; iterJ <= iterI; iterJ++) {
                 if (iterI % iterJ == 0) {
                     numOfDivision++; 
                 }
            }

            if (numOfDivision <= 2 || iterI == 1)  {
                System.out.println("Prime Number: " + iterI);
            }

        }


        scanner.close();
    }

}
