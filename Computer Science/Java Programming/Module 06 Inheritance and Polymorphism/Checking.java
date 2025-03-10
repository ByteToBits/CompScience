/**
 * Inheritance (Multi-Level Inheritance)
 * Checking Account is a Child/Sub/Derived Class of the Account Class
 * @author Tristan Sim
 * @version 1.01
 */

public class Checking extends Account
{
    // Fields
    private double minBalance; 

    public Checking()
    {
        this.minBalance = 0;
    }
    
    @Override
    public String display()
    {   
       return String.format("Checking Class: Invoked display()"); 
    }

    public double getMinBalance() 
    {
        return minBalance;
    }

    public void setMinBalance(double minBalance)
    {
        this.minBalance = minBalance;
    }
}
