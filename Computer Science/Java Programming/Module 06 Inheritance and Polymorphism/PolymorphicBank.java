
/**
 * Polymorphism 
 * Polymorphic Bank Class Runs the Main Method to test this Polymorphism Example
 * @author Tristan Sim
 * @version 1.01
 */

import java.util.ArrayList;;

public class PolymorphicBank 
{
    public static void main(String[] args)
    {  
        // Polymorphism: Allows a subclass object to be referenced by a superclass variable. 
        // The static type (declared type) determines what methods are accessible, while the dynamic type (actual object type) 
        // determines the implementation used.
        System.out.println("<Class Name> <Variable Name> (Static Type) = new <Class Constructor>; (Dynamic Type)");
        System.out.println("'savingAccount' is a polymorphic variable, since it is of static type 'Account'");
        
        Account savingAccount = new Savings(100, 8000.0, 1.5, 100000, 0); 

        System.out.println("Account Number(Inherited from Super Class 'Account'): " + savingAccount.getAccountNumber());
        System.out.println("Account Balance(Inherited from Super Class 'Account'): " + savingAccount.getBalance());

        // Cast to access subclass-specific method (bad practice)
        // Casting: This explicitly tells the compiler that the object is of type Savings, allowing access to methods unique to Savings.
        if (savingAccount instanceof Savings)
        {
            System.out.println("Account Interest (Explicitly Casted): " + ((Savings)savingAccount).getInterest()); 
        } 
        
        System.out.println("Another Polymorphic Example: Same Static Type, Different Dynamic Type" );
        System.out.println("'@Override' in subclass overrides the Parent Class Method" );

        ArrayList<Account> accounts = new ArrayList<>(); 
        accounts.add(new Checking());
        accounts.add(new Savings()); 
        accounts.add(new Credit()); 
        
        for (Account account : accounts)
        {
            System.out.println(account.display()); 
        }
    }

}
