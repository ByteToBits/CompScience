/** Description: Basic Concepts in Arrays
 * @author Tristan Sim
 * @version 1.00
 */

public class ArrayBasics {

    // Method 1: Declare an Object Array
    // Declaring a Product object array
    static class Product {
        String name;
        double price; 

        // Non-Default Constructor
        public Product(String newName, double newPrice) {
            this.name = newName;
            this.price = newPrice; 
        }
    }

    public static void main(String[] args) {   
        
        // Method 2: Declare an Array <Datatype>[] <VariableName> = new <Datatype>[Length of Array]
        int[] values = new int[5];   // The Resulting Values will be "0" (default for int)

        // Method 3: Declare & Initialize by writing values to the array
        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday",
                                 "Thursday", "Friday", "Saturday"};

        // Method 4: Declare and Initialize with a For Loop | Assigning Values to an Element
        System.out.print("Values in the Initialized Number Arrays are: ");
        for (int i = 0; i <= (values.length - 1); i++) {
            values[i] = i + 1;  // Fill the values array with numbers 1 to 5
            System.out.print(values[i] + " ");  // Added space for better output readability
        }
        
        System.out.println(); // Move to a new line after printing values array

        // Accessing Values to an Element 
        System.out.println("\nThe Length of the DaysOfWeek Array: " + daysOfWeek.length); 
        for (int i = 0; i <= (daysOfWeek.length - 1); i++) {
             System.out.println(daysOfWeek[i]);  // Print each day of the week
        }

        // Removing a Specific Value and Handling Null with Validations
        daysOfWeek[4] = null;  // Nullifying Thursday
        for (int i = 0; i <= (daysOfWeek.length - 1); i++) {
            if (daysOfWeek[i] != null) {
                System.out.println(daysOfWeek[i]);  // Print the day if it's not null
            } else {
                System.out.println("Null Value Identified at iteration " + i);  // Indicate null
            }
        }

        // Method 1: Creating an Array of Product Objects
        Product[] products = new Product[5];  // Declare an array of Product objects
        
        // Initialize the products array with Product objects
        products[0] = new Product("Laptop", 999.99);
        products[1] = new Product("Smartphone", 499.99);
        products[2] = new Product("Tablet", 299.99);
        products[3] = new Product("Headphones", 89.99);
        products[4] = new Product("Smartwatch", 199.99);

        // Print Product details
        System.out.println("\nDetails of Products:");
        for (int i = 0; i < products.length; i++) {
            // Display each product's name and price
            System.out.println("Product: " + products[i].name + ", Price: $" + products[i].price);
        }
    }
}