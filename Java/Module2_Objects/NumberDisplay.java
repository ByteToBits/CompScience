
// Dsecription: Displays the Numbers
// Aurthor: Tristan Sim
// Version: 1.00

// Class 1 - NumberDisplay
// Params:
// - limit (stores the max value that can be stored)
// - value (stores the actual value less than the limit)
// Methods:
// - Accessors
// - Mutators
// - Display
// - Increment (increases the value by 1 and makes sure value is less than the limit)

public class NumberDisplay 
{
     
    // Fields 
    private double limit;
    private double value; 

    // Constructor
    public NumberDisplay() 
    {
        limit = 80000; 
        value = 0; 
    }

    public NumberDisplay(double newLimit, double newValue) 
    {
        limit = newLimit; 
        value = newValue; 
    }

    // Accesors 
    public void getLimit() 
    {
        System.out.println("Limit: " + this.limit); 
    }
 
    public void getValue() 
    {
        System.out.println("Value: " + this.value);
    }

    // Mutators 
    public void setLimit(double newLimit) 
    {
        this.limit = newLimit; 
    }

    public void setValue(double newValue) 
    {
        this.value = newValue; 
    }

    // Custom Methods: Displays
    public void displayNumbers() {
        System.out.println("The Limit is " + this.limit); 
        System.out.println("The Value is " + this.value); 
    }

    // Custom Methods: Increment Value
    public void incrementNumber() {
        if (!(this.value >= this.limit)) {
            this.value += 1; 
        } else {
            this.value = this.limit; 
        }
    }
    
    public static void main(String[] args) {

        NumberDisplay objNumber = new NumberDisplay(); 
        
        System.out.println("\nNumber Display");
        objNumber.setLimit(10);
        objNumber.setValue(8); 
        objNumber.displayNumbers();
        
        System.out.println("\nNumber After Incrementing by 1");
        objNumber.incrementNumber();
        objNumber.displayNumbers();

    }


}