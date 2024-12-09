/**
 * Inheritance (Multi-Level Inheritance)
 * Account Class servers as a Super/Parent/Base Class to Checking, Credit and Savings Account
 * @author Tristan Sim
 * @version 1.01
 */

 public class Account
 {
    // Fields
    private int accountNumber;
    private double balance; 

    public Account()
    {
        this.accountNumber = 0;
        this.balance = 0;
    }

    public Account(int accountNumber, double balance)
    {
        this.accountNumber = accountNumber;
        this.balance = balance; 
    }

    public int getAccountNumber() 
    {
        return accountNumber;
    }

    public double getBalance()
    {
        return balance; 
    }

    public void setAccountNumber(int accountNumber)
    {
        this.accountNumber = accountNumber; 
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }
 }