
/** 
 * Description: University Class acts as the Main Program for Rolling Project Part 2
 * @author Tristan Sim
 * @version 1.00
 */

public class University
{
    // Fields
    private Enrollment enrollment; 

    /**
     * Default Constructor
     */
    public University() {
        enrollment = new Enrollment(); 
    }

    /**
     * Non-Defualt Constructor
     * @param enrollment The New Enrollment Information as an Object of the Enrollment Class
     */
    public University(Enrollment enrollment) {
        this.enrollment = enrollment; 
    }

    /**
     * Display Method: Display the University Information
     */
    public void displayUniversity() {
        this.enrollment.displayEnrollment(); 
    }

    /** 
     * Accessor Method: Get the Enrollment Details
     * @return the Enrollment Information as a object of the Enrollment Class
     */
    public Enrollment getEnrollment() {
        return this.enrollment; 
    }
    
    /* Now write the inputStudentDetails() method.
    The purpose of this method is to accept student information by asking the user to enter all the 
    student information one at a time while performing the following validations on the inputs:

    For the Student object:
    name, address, phoneNo, and email cannot be blank.
    If any of the validations fail, print a message on the screen and assign the default value specified in the default constructor.

    The method then assigns these values to the student fields in the enrolment class.
    Hint: You can achieve this by obtaining the object first from the Enrolment class via a method 
    and then using that object to call another method in the Student class to set the value. Which method would that be?
   */

   /** 
    * Custom Method: Input the Student Details
    * @param enrollment A Enrollment Details to be Set by the Method as an object of the Enrollment Class
    */
    public void inputStudentDetails(Enrollment enrollment) 
    {   
        // Check if the Student Object Has already been Previously Instatiated
        Student student = enrollment.getStudent(); 
        if (student == null) {
            student = new Student(); // Create a New Student if it has not and initialize
            enrollment.setStudent(student);
        }

        Input inputFromUser = new Input(); 
        Validation validateInput = new Validation(); 

        String[] inputFields = {"Name", "Address", "Phone No", "E-mail"}; 
        String[] tempString = new String[inputFields.length]; 
        
        // 1) Get User Input 2) Validate Input if it is Not Blank 
        for (int iterI = 0; iterI < inputFields.length; iterI++)
        {
            tempString[iterI] = inputFromUser.acceptStringInput("Input " + inputFields[iterI] + ":");       
            if (validateInput.isBlank(tempString[iterI])) {
                System.out.println("Validation Failed: " + inputFields[iterI] + "cannot be Blank");
                tempString[iterI] = ""; // Write a Default Value in (Assignment Did Not specify Value)
            }; 
        }

        // 3) Set the Student Object in the 
        student.setName(tempString[0]);
        student.setAddress(tempString[1]);
        student.setPhoneNo(tempString[2]);
        student.setEmail(tempString[3]);
    }

    public static void main(String[] args)
    {
        University university = new University();
        Enrollment enrollment = new Enrollment(); 

        university.inputStudentDetails(enrollment); 
        System.out.println(enrollment.displayEnrollment()); 
    }


}

