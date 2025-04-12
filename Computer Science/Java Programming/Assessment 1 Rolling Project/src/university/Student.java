package university;

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
     * Constructs a Student instance with the specified name, address, phone number, and email.
     * 
     * @param studentName the name of the student, as a non-null, non-empty String
     * @param studentAddress the address of the student, as a non-null, non-empty String
     * @param studentPhoneNo the phone number of the student, as a non-null String
     * @param studentEmail the email of the student, as a non-null String
     * @throws IllegalArgumentException if studentName, studentAddress, studentPhoneNo, or studentEmail is null or empty
     */
    public Student(final String studentName, final String studentAddress, final String studentPhoneNo, final String studentEmail) {
        if (studentName == null || studentName.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be Empty");
        }
        if (studentAddress == null || studentAddress.isEmpty()) {
            throw new IllegalArgumentException("Address cannot be Empty");
        }
        if (studentPhoneNo == null || studentPhoneNo.isEmpty()) {
            throw new IllegalArgumentException("Phone Number cannot be Empty");
        }
        if (studentEmail == null || studentEmail.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        this.name = studentName;
        this.address = studentAddress;
        this.phoneNo = studentPhoneNo;
        this.email = studentEmail;
    }
    
    /**
     * Display Method to Print out the Student Information
     */
    public String display() {
        return String.format("Student Name: %s\nStudent Address: %s\nStudent Phone No: %s\nStudent Email: %s\n",
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
