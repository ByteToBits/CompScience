/**
 * Inheritance (Multi-Level Inheritance)
 * Bank Class Runs the Main Method to test this Inheritance Example
 * @author Tristan Sim
 * @version 1.01
 */

public class Bank
{
    public static void main(String[] args)
    {
        System.out.println("Banking System: To Test Inheritance");
        Savings savingAccount1 = new Savings(100, 8000.0, 1.5, 100000, 0); 

        System.out.println("Account Number(Inherited from Super Class 'Accout'): " + savingAccount1.getAccountNumber());
        System.out.println("Account Balance(Inherited from Super Class 'Accout'): " + savingAccount1.getBalance());
        System.out.println("Account Interest(From Class itself): " + savingAccount1.getInterest());

        System.out.println("Protected Message of Account Class Accesed Through Savings: " + savingAccount1.displayProtectedMessage());

        Account account1 = new Account(); 
        System.out.println("Directly Read Protected Message of Account Class: " + account1.displayProtectedMessage());

        System.out.println("Section Below will cover Subtyping of a Super Class... ");
        System.out.println("<Class Name> <Variable Name> (Static Type) = new <Class Constructor>; (Dynamic Type)");
        
        Account savingAccount2 = new Savings(100, 8000.0, 1.5, 100000, 0); 

        System.out.println("Account Number(Inherited from Super Class 'Accout'): " + savingAccount2.getAccountNumber());
        System.out.println("Account Balance(Inherited from Super Class 'Accout'): " + savingAccount2.getBalance());
        // System.out.println("Account Interest(From Class itself): " + savingAccount2.getInterest()); This won't work: Object Type is still Account

    }
}