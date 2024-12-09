
/** 
 * Description: Test the Pass by Reference thought many sources say Java is pass by value
 * @author Tristan
 * @version 1.00
 */

 public class ReferenceAddress {

    public static void main(String[] args)
    {
        MyObject obj1 = new MyObject(10);

        // Print the reference of obj1 before modification
        System.out.println("Before method call:");
        System.out.println("obj1 reference: " + obj1);
        
        // Pass the object to the modifyObject method
        modifyObject(obj1);

        // Print the reference of obj1 after modification
        System.out.println("\nAfter method call:");
        System.out.println("obj1 reference: " + obj1);
        System.out.println("\nValue of obj1 after modification: " + obj1.value);

    }

    // A method that modifies the value of the object
    public static void modifyObject(MyObject object) {
        object.value = 20;
        System.out.println("Inside modifyObject method:");
        System.out.println("o reference: " + object);
    }

 }

 // A simple class with an integer value
class MyObject {
    int value;

    MyObject(int value) {
        this.value = value;
    }
}