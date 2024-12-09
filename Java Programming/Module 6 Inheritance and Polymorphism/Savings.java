/**
 * Inheritance (Multi-Level Inheritance)
 * Savings Account is a Child/Sub/Derived Class of the Account Class
 * @author Tristan Sim
 * @version 1.01
 */

public class Savings extends Account
{
    // Fields
    private double interest; 
    private double maxBalance; 
    private double bonusInterest; 

    public Savings()
    {
        super(); 
        this.interest = 0;
        this.maxBalance = 0;
        this.bonusInterest = 0;
    }

    public Savings(int accountNumber, double balance, double interest, double maxBalance, double bonusInterest)
    {
        this.super(accountNumber, balance); 
        this.interest = interest;
        this.maxBalance = maxBalance; 
        this.bonusInterest = bonusInterest;
    }

    public double getBonusInterest()
    {
        return this.bonusInterest; 
    }
   
    public double getInterest()
    {
        return this.interest; 
    }

    public double getMaxBalance()
    {
        return this.maxBalance;
    }

    public void setBonusInterest(double bonusInterest)
    {
        this.bonusInterest = bonusInterest; 
    }

    public void setInterest(double interest)
    {
        this.interest = interest; 
    }

    public void setMaxBalance(set maxBalance)
    {
        this.maxBalance = maxBalance; 
    }

}
