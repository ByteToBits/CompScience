
/**
 * Description: The Student Class contains instructions to handle student information
 * @author Tristan Sim
 * @version 1.00
 */

public class Student 
{
   
    // Fields
    private String name; 
    private String address;
    private String phoneNo;
    private String email; 

    /**
     * Default Constructor
     */
    public Student() 
    {
    name = "";
    address = ""; 
    phoneNo = "";
    email = ""; 
    }

    /** 
     * Non-Default Constructor 
     * @param studentName The Name of the Student as a String
     * @param studentAddress The Address of the Student as a String
     * @param studentPhoneNo The Phone Number of the Student as a String
     * @param studentEmail The Email of the Student as a String
     */
    public Student(String studentName, String studentAddress, String studentPhoneNo, String studentEmail)
    {
        name = studentName; 
        address = studentAddress; 
        phoneNo = studentPhoneNo; 
        email = studentEmail; 
    }

    /**
     * Display Method to Print out the Student Information
     */
    public void display() {
        System.out.printf("Student Name: %s\nStudent Address: %s\nStudent Phone No: %s\nStudent Email: %s\n",
        this.name, this.address, this.phoneNo, this.email); 
    }

    /**
     * Accessor Method for Student Address
     * @return Returns the Address of the Student in a String
     */
    public String getAddress() 
    {
        return this.address;
    }

    /**
     * Accessor Method for Student Email
     * @return Returns the Email of the Student in a String
     */
    public String getEmail() 
    {
        return this.email;
    }

    /**
     * Accessor Method for Student Name
     * @return Returns the Name of the Student in a String
     */
    public String getName() 
    {
        return this.name;
    }

    /**
     * Accessor Method for Student Phone Number
     * @return Returns the Phone Number of the Student in a String
     */
    public String getPhoneNo() 
    {
        return this.phoneNo;
    }
    
    /** 
     * Mutator Method for Student Address
     * @param newAddress A New Address which will update the Student's old Address as a String
     */
    public void setAddress(String newAddress) {
        this.address = newAddress; 
    }

    /** 
     * Mutator Method for Student Name
     * @param newName A New Name which will update the Student's old Name as a String
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /** 
     * Mutator Method for Student Phone Number
     * @param newPhoneNo A New Phone Number which will update the Student's old Phone Number as a String
     */
    public void setPhoneNo(String newPhoneNo) {
        this.phoneNo = newPhoneNo;
    }

    /** 
     * Mutator Method for Student Email
     * @param newEmail A New Email which will update the Student's old Email as a String
     */
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

}
