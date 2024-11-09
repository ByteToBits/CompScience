
/**
 * Description: Sum every third number in the range given
 * @author Tristan
 * @version 1.00
 */

/*
You are required to write a method that accepts two input parameters, both of integer types. 
 1) The first represents the lower number of a range.
 2) The second represents the higher number of the range.

Write a class with a main() method. In another method using the two values provided by the user, return an integer value that is the 
total sum of every THIRD number within the range.

For e.g. Lower Number is 1, Upper Number is 10. Range is 1–10. Every third number is 3, 6, 9. The method should return 18 as the total.

Create a JavaDoc -> javadoc -d ./doc "SumThird.java"
*/

import java.util.Scanner; 

public class SumThird {

    /** Fields
    // @param lowerNumber represents the lower number of a range.
    // @param higherNumber represents the higher number of the range.
    **/
    int lowerNumber;
    int higherNumber; 
    int sum; 

    public SumThird() {
        lowerNumber = 0; 
        higherNumber = 0; 
        sum = 0; 
    }
    
    /** Non-default constructor which creates the object for the SumThird class
     * @newLowerNumber Accepts the New Lower Limit Number as a Integer
     * @newHigherNumber Accepts the New Higher Limit Number as a Integer
     */
    public SumThird(int newLowerNumber, int newHigherNumber) {
        lowerNumber = newLowerNumber; 
        higherNumber = newHigherNumber; 
        sum = 0;  
    }
    
     // Accessor Methods
    /**
     * Accesor Method to get the Lower Number 
     * @return Returns the Lower Number as an Integer
     */
    public int getLowerNumber() {
        return this.lowerNumber; 
    }

    public int getHigherNumber() {
        return this.higherNumber; 
    }

    public int getSum() {
        return this.sum; 
    }

    // Mutator Mehtods
    /**
     * Mutator Method to set the lower number value
     * @param newLowerNumber Takes in a New Lower Number value as a Integer
     */
    public void setLowerNumber(int newLowerNumber) {
        this.lowerNumber = newLowerNumber;
    }

    public void setHigherNumber(int newHigherNumber) {
        this.higherNumber = newHigherNumber; 
    }

    // Display Method
    public void displaySumNumbers() {
        System.out.println("Lower Number:" + this.lowerNumber);
        System.out.println("Higher Number:" + this.higherNumber);
        System.out.println("Sum of Third Values:" + this.sum);
    }

    // Custom Methods: Sum the Third Values in the Range 
    public int sumThirdNumbers() {

        int iterI = this.lowerNumber; 
        int counter = 1; 
        int totalizer = 0; 

        while (iterI <= this.higherNumber) {
           if (counter == 3) {
             totalizer += iterI; 
             System.out.println(iterI);
             counter = 0; // Following Counter will set back to 1
           }
            counter++; 
            iterI++; 
        }
        this.sum = totalizer; 

        return this.sum; 
    }

    public static void main(String[] args) {

          Scanner scanner = new Scanner(System.in); 
          SumThird objSumThird = new SumThird(); 

          System.out.println("Input Lower Numeric Value: "); 
          objSumThird.setLowerNumber(Integer.parseInt(scanner.nextLine()));

          System.out.println("Input Higher Numeric Value: "); 
          objSumThird.setHigherNumber(Integer.parseInt(scanner.nextLine()));

          System.out.println("This Sum of Every Third Value is: "); 
          int sumOfThirdNumbers = objSumThird.sumThirdNumbers(); 

          System.out.println("Sum = " + sumOfThirdNumbers); 

          scanner.close();
          
    }

}
