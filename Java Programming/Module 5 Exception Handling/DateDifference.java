
/**
 * Description: Date Difference
 * @author Tristan Sim
 * @version 1.00
 */

// Task: Write a program to calculate the difference between two dates.
// Input: 2 Dates in format dd/mm/yyyy
// Output: Program should show the result in number of days as a positive integer number only.

import java.util.Scanner; 
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class DateDifference
{   
    public DateDifference() {}

    private LocalDate inputDate(Scanner console, DateTimeFormatter dateFormat)
    {   
        LocalDate userInputDate = null; 
        while (userInputDate == null)
        {   
            try {
                System.out.println("Please Input the Date (dd/MM/yyyy): ");
                userInputDate = LocalDate.parse(console.nextLine(), dateFormat);
            } catch (DateTimeParseException dtpe) {
                System.out.println("Date Input Invalid. Please try again..."); 
            }
        }
        return userInputDate; 
    }

    public static void main(String[] args)
    {   
        // Initialise Objects 
        Scanner console = new Scanner(System.in); 
        DateDifference dateDifferenceObject = new DateDifference(); 
        DateTimeFormatter requiredDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
        LocalDate userDateInput1 = null; 
        LocalDate userDateInput2 = null; 
        
        // Get User Inputs 
        userDateInput1 = dateDifferenceObject.inputDate(console, requiredDateFormat); 
        userDateInput2 = dateDifferenceObject.inputDate(console, requiredDateFormat); 

        long daysDifference = ChronoUnit.DAYS.between(userDateInput1, userDateInput2);
        System.out.println("The Difference in Days is " + Math.abs(daysDifference));
    }
}
