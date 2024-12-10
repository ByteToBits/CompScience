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
    
    // Allow itself and Subclass to Acess | Other classes cannot accest these value
    protected String protectedMessage = "This is a Secret Message from Super (Account)";

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

    protected String displayProtectedMessage()
    {
        return this.protectedMessage; 
    }

    public String displayAccount()
    {   
        
        return String.format("Account Number: %d\nBalance: %.2f", this.accountNumber, this.balance);
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