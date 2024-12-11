/**
 * Inheritance (Multi-Level Inheritance)
 * Credit Account is a Child/Sub/Derived Class of the Account Class
 * @author Tristan Sim
 * @version 1.01
 */

public class Credit extends Account
{
    // Fields 
    private double interestRate; 
    private int interestFreeDays;
    private double cashRate;

    public Credit()
    {
        super();
        this.interestRate = 0;
        this.interestFreeDays = 0;
        this.cashRate = 0; 
    }

    public Credit(int accountNumber, double balance, double interestRate, int interestFreeDays, double cashRate)
    {
        super(accountNumber, balance);
        this.interestRate = interestRate;
        this.interestFreeDays = interestFreeDays;
        this.cashRate = cashRate; 
    }
    
    // Example of Method Overriding | Overriding the Parent Class display()
    @Override
    public String display()
    {
        StringBuilder stringBuilder = new StringBuilder(); 
        stringBuilder.append(super.display()); 
        stringBuilder.append("\nInterest Rate: ").append(this.interestRate); 
        stringBuilder.append("\nInterest Free Days: ").append(this.interestFreeDays); 
        stringBuilder.append("\nCash Rate: ").append(this.cashRate); 

        return String.format("Credit Class: Invoked display()");
    }

    public double getCashRate() 
    {
        return this.cashRate;
    }

    public int getInterestFreeDays() 
    {
        return this.interestFreeDays;
    }

    public double getInterestRatew()
    {
        return this.interestRate; 
    }
    
    public double interestCharged()
    {
        return this.getBalance() * this.interestRate * (30 - this.interestFreeDays);
    }

    public void setCashRate(double cashRate) 
    {
        this.cashRate = cashRate;
    }

    public void setInterestFreeDays(int interestFreeDays) 
    {
        this.interestFreeDays = interestFreeDays;
    }

    public void setInterestRate(double interestRate)
    {
        this.interestRate = interestRate; 
    }
}
