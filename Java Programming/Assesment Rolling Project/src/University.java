
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
    public String displayUniversity() {
        return this.enrollment.displayEnrollment(); 
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
    * Custom Method: Method to get User Input for the Student Details
    * @param enrollment A Enrollment Details to be Set by the Method as an object of the Enrollment Class
    */
    public void inputStudentDetails(Enrollment enrollment) 
    {   
        // Get the Student information from the Enrollment Class
        Student student = enrollment.getStudent(); 
        Input inputFromUser = new Input(); 
        Validation validateInput = new Validation(); 

        String[] inputFields = {"Name", "Address", "Phone No", "E-mail"}; 
        String[] userResponse = new String[inputFields.length]; 
        
        // Get User Input & Validate Input if it is Not Blank 
        for (int iterI = 0; iterI < inputFields.length; iterI++)
        {
            userResponse[iterI] = inputFromUser.acceptStringInput("Input " + inputFields[iterI] + ":");       
            if (validateInput.isBlank(userResponse[iterI])) {
                System.out.println("Validation Failed: " + inputFields[iterI] + " cannot be Blank");
                userResponse[iterI] = ""; // Write a Default Value in (Assignment Did Not specify Value)
            }; 
        }

        // Set the Student Object in the 
        student.setName(userResponse[0]);
        student.setAddress(userResponse[1]);
        student.setPhoneNo(userResponse[2]);
        student.setEmail(userResponse[3]);
    }

    /* 
     * Now write the inputUnitDetails() method.
     * The purpose of this method is to accept unit information by asking the user to enter all the unit information one at a time 
     * while performing the following validations on the inputs via the Input class:

     * For the Unit object:
     * unitCode cannot be blank and must be exactly 7 characters long.
     * unitDescription cannot be blank and must be less than 25 characters long 
     * creditPoints cannot be < 0.

     * If any of the validations fail, print a message on the screen and assign the default value specified in the default constructor.
     * The method then assigns these values to the unit fields in the enrolment class.
     * Hint: You can achieve this by obtaining the object first from the Enrolment class via a method and then 
     * using that object to call another method in the Unit class to set the value. Which method would that be? 
     * */

    /** 
     * Custom Method: Get User to Input the Unit Details
     * @param enrollment The Enrollment Object to be Updated by th User; Passed as an Object of the Enrollment Class
     */
    public void inputUnitDetails(Enrollment enrollment)
    {   
        // Get the Unit Object from the Enrollment Class
        Unit unit = enrollment.getUnit(); 
        Input inputFromUser = new Input(); 
        Validation validateInput = new Validation(); 
        
        String[] inputFields = {"Unit Code", "Unit Desecription", "Credit Score"}; 
        String userResponse; 
        
        // Get User Input and Validate the Input 
        for (int iterI = 0; iterI < inputFields.length; iterI++)
        {
            switch (iterI) 
            {
                case 0: // Unit Code cannot be blank and must be exactly 7 characters long.  
                   userResponse = inputFromUser.acceptStringInput("Input " + inputFields[iterI] + ":"); 
                   if (validateInput.isBlank(userResponse) || userResponse.length() != 7){     
                       userResponse = ""; // Default Constructor also initializes null 
                       System.out.println("Validation Failed: Unit Code cannot be Blank and must be Exactly 7 Characeters Long");
                   }
                   unit.setUnitCode(userResponse);
                   break; 
                
                case 1: // Unit Description Must not be Blank and must be less than 25 Characters in Length
                   userResponse = inputFromUser.acceptStringInput("Input " + inputFields[iterI] + ":"); 
                   if (validateInput.isBlank(userResponse) || userResponse.length() > 25) {
                       userResponse = ""; // Default Constructor also initializes null 
                       System.out.println("Validation Failed: Unit Desecription must not be Blank and be less than 25 Characters in Length"); 
                   }
                   unit.setUnitDescription(userResponse);
                   break; 
                
                case 2: // Credit Point Must not be smaller than 0 
                   int userResponseInteger = inputFromUser.acceptIntegerInput("Input " + inputFields[iterI] + ":"); 
                   if (userResponseInteger < 0) {
                       userResponseInteger = 0; 
                       System.out.println("Validation Failed: The Credit Score must not be lesser than the value 0 "); 
                   }
                   unit.setCreditPoints(userResponseInteger);
                   break; 

                default: 
                   System.out.println("No Case for Iteration " + iterI);
            }
        }
    }

    /*
     * Lastly, write the main() method in the University class.
     * If the user selects Option 1, ask the user for the enrolment date, which should not be blank. 
     * If blank, print an error message and assign the default date value specified in the default constructor.
     * Next call the inputStudentDetails() and inputUnitDetails() methods.
     * Then display the university object state on the screen.For now, you should have one student enrolled in one unit only.
     */
    
    /**
     * Main Method of the Unviersity Class
     * Offer the user the option to select between 2 choices: 1) Enrols a student 2) Exits the program
     */
    public static void main(String[] args)
    {   
        Input userInput = new Input(); 
        Enrollment enrollment = new Enrollment(); 
        University university = new University(enrollment); 

        System.out.println("\nThis is the University Enrollment Program."); 
        String userSelection = userInput.acceptStringInput("Please Enter:\n1 - To Enroll a Student\n2 - To Exit the Program"); 
        
        if (userSelection.equals("1")) {

            // Enter Enrollment Date 
            String newEnrollmentDate = userInput.acceptStringInput("\nPlease Enter the Enrollment Date:");
            if (newEnrollmentDate == null || newEnrollmentDate.isEmpty()) {
                newEnrollmentDate = ""; // Default Value of String in Default Constructor is also Null
                System.out.println("The Enrollment Date should not be Blank"); 
            }
            enrollment.setDate(newEnrollmentDate);
            
            // Get user to Input the Student and Unit Details
            university.inputStudentDetails(enrollment);
            university.inputUnitDetails(enrollment);

            // Display the University Object Information
            System.out.println(university.displayUniversity());
      
        } else {
            System.out.println("Program Execution has terminated.\n"); 
        }
    }
}