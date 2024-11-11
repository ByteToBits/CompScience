
/** 
 * Description: Generic Class gives examples of how to use Generic Class
 * @author Tristan
 * @version 1.00
 */

 public class GenericClass<Type> {
    
    // Fields
    private Type attributeOne; 

    // Constructor
    public GenericClass() {}

    // Display Method
    public String displayGenericClass() {
        return attributeOne != null ? attributeOne.toString() : "null";
    }

    // Set Method 
    public void setGenericClass(Type attributeOne) {
        this.attributeOne = attributeOne;
    }

    public static void main(String[] args) {
        // Create an instance of GenericClass with Integer as the type
        GenericClass<Integer> objOne = new GenericClass<>();
        objOne.setGenericClass(10);  // Set the value of attributeOne
        System.out.println("Attribute 1: " + objOne.displayGenericClass());  // Output: 10
    }
}

