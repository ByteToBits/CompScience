
/** 
 * Description: University Class acts as the Main Program for Rolling Project Part 2
 * @author Tristan Sim
 * @version 1.00
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class University
{
    // Fields
    private ArrayList<Enrollment> enrollments;
    private static final String outputFilePath = "C:\\Repository\\CompScience\\Java Programming\\Assesment Rolling Project\\resource\\output.txt";
    private static final String studentFilePath = "C:\\Repository\\CompScience\\Java Programming\\Assesment Rolling Project\\resource\\student.txt";
    private static final String delimiter = "\r\n"; 

    /**
     * Default Constructor
     */
    public University() {
        enrollments = new ArrayList<Enrollment>(); 
    }

    /**
     * Non-Defualt Constructor
     * @param enrollment The New Enrollment Information as an Array List of Objects of the Enrollment Class
     */
    public University(ArrayList<Enrollment> enrollments) {
        this.enrollments = enrollments; 
    }

    /**
     * Display Method: Display the University Information
     * @return A String Output of the Array List of Enrollment of Ojects
     */
    public String displayUniversity() {
        StringBuffer enrollmentText = new StringBuffer(); 
        for (Enrollment enrollment : this.enrollments) {
            if (enrollment != null) {
                enrollmentText.append(enrollment.displayEnrollment());  
            }
        }
        return enrollmentText.toString(); 
    }
    
    /** 
     * Custom Method: Add a New Enrollment into the Array List
     * @param enrollment The new Enrollment Information to be added as an Object of te Enrollment Class
     */
    public void addEnrollment(Enrollment enrollment)
    {
        this.enrollments.add(enrollment); 
    }

    /** 
     * Accessor Method: Get the Enrollment Details
     * @return The Enrollment Information as a Array List of Objects of the Enrollment Class
     */
    public ArrayList<Enrollment> getEnrollment() 
    {
        return this.enrollments; 
    }

    /** 
     * Accessor Method: Get the Enrollment Size
     * @return The size of the the Enrollment Arraylist as an Integer
     */
    public int getEnrollmentSize()
    {
        return this.enrollments.size(); 
    }

    /** 
     * Accesor Method: Get the Specific Enrollment Details
     * @param index The Index of th Enrollment Object in the Arrray List as an Integer
     * @return The Enrollment as an Object information at the Index of the Array List
     * @throw IndexOutOfBoundsException The Index Input out ouf Bounds of the Array List Size
     */
    public Enrollment getSpecificEnrollment(int index)
    {
        if (index < 0 || index >= this.enrollments.size()) {
            throw new IndexOutOfBoundsException("The Index is out of bound of the Enrollment Array List");
        }
        return this.enrollments.get(index); 
    }

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
            Boolean validInput = false; 
            while (!validInput) {
                userResponse[iterI] = inputFromUser.acceptStringInput("Input " + inputFields[iterI] + ":");       
                if (validateInput.isBlank(userResponse[iterI])) {
                    System.out.println("Validation Failed: " + inputFields[iterI] + " cannot be Blank");
                    userResponse[iterI] = ""; // Write a Default Value in (Assignment Did Not specify Value)
                } else {
                    validInput = true; 
                }
            }

        }

        // Set the Student Object in the 
        student.setName(userResponse[0]);
        student.setAddress(userResponse[1]);
        student.setPhoneNo(userResponse[2]);
        student.setEmail(userResponse[3]);
    }

    /**
     * Custom Method: Get User to Input to Enrol Student
     * @param enrollments The Enrollments Array Lists to be updated for Inputs by the User
     */
    public void inputUnitDetails(ArrayList<Enrollment> enrollments)
    {   
        int currentIndex = enrollments.get(0).getNumberOfEnrollments() - 1; 
        int unitSize = enrollments.get(currentIndex).getUnitSize();
        
        for (int iterI = 0; iterI < unitSize; iterI++) {
            inputUnitDetailsOnce(iterI, this.enrollments.get(currentIndex)); 
        }
    }

    /** 
     * Custom Method: Get User to Input the Unit Details Once
     * @param enrollment The Enrollment Object to be Updated by th User; Passed as an Object of the Enrollment Class
     * @throw IndexOutOfBoundsException The index is out of bounds of the Array List
     */
    public void inputUnitDetailsOnce(int index, Enrollment enrollment)
    {   
        // Get the Unit Object from the Enrollment Class
        Unit[] units = enrollment.getUnits(); 
        Input inputFromUser = new Input(); 
        Validation validateInput = new Validation(); 
        
        String[] inputFields = {"Unit Code", "Unit Desecription", "Credit Score"}; 
        String userResponse; 

        // May need to verify if the Index is within Bounds
        int currentIndex = enrollment.getNumberOfEnrollments() - 1; 
        
        if (index < 0 || index >= enrollments.get(currentIndex).getUnitSize()) {
            throw new IndexOutOfBoundsException("The index is out of bounds of the Array List"); 
        }
        
        // Get User Input and Validate the Input 
        for (int iterI = 0; iterI < inputFields.length; iterI++)
        {   
            Boolean validInput = false; 
            while (!validInput) {
                switch (iterI) 
                {
                    case 0: // Unit Code cannot be blank and must be exactly 7 characters long.  
                    userResponse = inputFromUser.acceptStringInput("Input " + inputFields[iterI] + ":"); 
                    if (validateInput.isBlank(userResponse) || userResponse.length() != 7){     
                        System.out.println("Validation Failed: Unit Code cannot be Blank and must be Exactly 7 Characeters Long");
                    } else {
                        units[index].setUnitCode(userResponse);
                        validInput = true; 
                    }
                    break; 
                    
                    case 1: // Unit Description Must not be Blank and must be less than 25 Characters in Length
                    userResponse = inputFromUser.acceptStringInput("Input " + inputFields[iterI] + ":"); 
                    if (validateInput.isBlank(userResponse) || userResponse.length() > 25) { 
                        System.out.println("Validation Failed: Unit Desecription must not be Blank and be less than 25 Characters in Length"); 
                    } else {
                        units[index].setUnitDescription(userResponse);
                        validInput = true; 
                    }
                    break; 
                    
                    case 2: // Credit Point Must not be smaller than 0 
                    try {
                        int userResponseInteger = inputFromUser.acceptIntegerInput("Input " + inputFields[iterI] + ":");  
                        if (userResponseInteger < 0) { 
                            System.out.println("Validation Failed: The Credit Score must not be lesser than the value 0 "); 
                        } else {
                            units[index].setCreditPoints(userResponseInteger);
                            validInput = true; 
                        }
                    } catch (NumberFormatException nfe) {
                        System.out.println("Invalid Input. Please try again...");
                        validInput = false; 
                    }
                    break; 

                    default: 
                    System.out.println("No Case for Iteration " + iterI);
                    validInput = true; 
                }
            }
        }
    }
    
    /** 
     * Custom Method: Removes an Enrollment Object from Array List
     * @param index The Index of the Enrollment Objec to be Removed from the Array List
     * @throw IndexOutOfBoundsException The Index is out of bounds of the Array List 
     */
    public void removeEnrollment(int index)
    {
        if (index < 0 || index >= this.enrollments.size())
        {
            throw new IndexOutOfBoundsException("The Index is out of bounds of the Array List"); 
        }
        this.enrollments.remove(index); 
    }

    /** Mutator Method: Set Enrolment Details
     * @param enrollments An ArrayList of Enrollment Objects of the Enrollment Class
     */
    public void setEnrollment(ArrayList<Enrollment> enrollments)
    {
        this.enrollments = enrollments; 
    }

    /**
     * Mutator Method: Set Specific Enrollment Details
     * @param index The Index of the Enrollment Object to be Set in the Array List
     * @param enrollment The new Enrollment object to be Updated at the Index
     * @throw The Index is out of bounds for the Enrollment ArrayList
     */
    public void setSpecificEnrollment(int index, Enrollment enrollment)
    {
        if (index < 0 || index >= this.enrollments.size()) {
            throw new IndexOutOfBoundsException("The Index is out of bounds for the Enrollment ArrayList"); 
        }
        this.enrollments.set(index, enrollment);
    }

    /**
     * Custom Method: Parse Student Data from Text File
     * The contents of the text file is passed tp this function to parse into Student and Unit Objects
     * @param contents A Single line of Text from the Text File of the File as a single String
     * @return Enrollmennt object of 1 Student
     */
    public Enrollment parseDataFromFile(String contents) throws Exception
    {   
        Enrollment tempEnrollment = new Enrollment(); 
        ArrayList<String> details = new ArrayList<>(Arrays.asList(contents.split(",")));

        if (details.size() < 6) {
            throw new Exception("Malformed student data: Missing fields in input - " + contents);
        }

        // Populate Student Information
        tempEnrollment.setDate(details.get(0).trim());
        tempEnrollment.createStudent(details.get(1).trim(),details.get(2).trim(),details.get(3).trim(),details.get(4).trim());

        // Populate Units Details 
        String[] enrolledUnits = details.get(5).split(";");
        Unit[] units = new Unit[enrolledUnits.length]; 
        for (int i = 0; i < enrolledUnits.length; i++)
        {
            String[] unitInfo = enrolledUnits[i].split("-");
            if (unitInfo.length == 3) {
                units[i] = new Unit(unitInfo[0], unitInfo[1], Integer.parseInt(unitInfo[2]));
            } else {
                System.out.println("Skipping Malformed Data: " + details.get(1)); 
            }
        }
        tempEnrollment.setUnits(units);
        
        return tempEnrollment; 
    }

    /** Custom Method: Parse the Data from the Program to a Text File
     * @param enrollments ArrayList of Enrollments 
     * @return A Single-Line String of the enrollment contents
     */
    public String parseDataToFile(ArrayList<Enrollment> enrollments)
    {
        StringBuilder tempStringBuilder = new StringBuilder(); 

        for (Enrollment eachEnrollment : enrollments) {
    
            tempStringBuilder.append(eachEnrollment.getDate()).append(","); 
            tempStringBuilder.append(eachEnrollment.getStudent().getName()).append(","); 
            tempStringBuilder.append(eachEnrollment.getStudent().getAddress()).append(","); 
            tempStringBuilder.append(eachEnrollment.getStudent().getPhoneNo()).append(","); 
            tempStringBuilder.append(eachEnrollment.getStudent().getEmail()).append(","); 
    
            Unit[] tempUnitArray = eachEnrollment.getUnits(); 
    
            for (int i = 0; i < tempUnitArray.length; i++) {
                if (i > 0) {
                    tempStringBuilder.append(";");
                }
                    tempStringBuilder.append(tempUnitArray[i].getUnitCode()).append("-").append(tempUnitArray[i].getUnitDescription()).append("-"); 
                    tempStringBuilder.append(tempUnitArray[i].getCreditPoints()); 
                }
            tempStringBuilder.append("\n");
        }

        return tempStringBuilder.toString();
    }

    /**
     * Custom Method: Print Expcetion Message
     * @param exceptionObject The exception object thrown by Java
     */
    public void printExceptionInfo(String message, Exception exceptionObject)
    {
        System.out.println(message);
        System.out.println("Error Message: " + exceptionObject.getMessage()); 
        exceptionObject.printStackTrace();
    }
    
    public void startProgram()
    {
        Input userInput = new Input(); 
        ArrayList<Enrollment> enrollments = new ArrayList<Enrollment>(); 
        University university = new University(enrollments); 
        FileIO studentFileInfo = new FileIO(studentFilePath); 
   
        // Initial prompt message
        System.out.println("\nThis is the University Enrollment Program.");

        System.out.println("\nInformation of Students Currently Enrolled: ");
        try {
            String existingStudents = studentFileInfo.readFile();
            for (String student : existingStudents.split(delimiter))
            {   
                try {
                    university.addEnrollment(university.parseDataFromFile(student));
                } catch (Exception e) {
                    university.printExceptionInfo("Error Reading Data, Skipping Student...", e);
                }
            }
        } catch (Exception e) {
            university.printExceptionInfo("Unable to read from student.txt | Message", e);
        }
        System.out.println(university.displayUniversity());
        
        while (true) {
            // Prompt the user for input
            String userSelection = userInput.acceptStringInput("Please Enter:\n1 - To Enroll a Student\n2 - To Exit the Program");

            // Handle the user's selection
            if (userSelection.equals("1")) {

                Enrollment newEnrollment = new Enrollment();
                int numberOfEnrolments = newEnrollment.getNumberOfEnrollments(); 
                System.out.println("\nEnrollment No. " + (numberOfEnrolments)); 

                // Enter Enrollment Date
                String newEnrollmentDate = userInput.acceptStringInput("\nPlease Enter the Enrollment Date:");
                if (newEnrollmentDate == null || newEnrollmentDate.isEmpty()) {
                    newEnrollmentDate = ""; // Default Value of String in Default Constructor is also Null
                    System.out.println("The Enrollment Date should not be Blank");
                }

                newEnrollment.setDate(newEnrollmentDate);
                
                // Add the new Enrollment to the list
                enrollments.add(newEnrollment); 

                // Get user to Input the Student and Unit Details
                university.inputStudentDetails(newEnrollment);
                
                Boolean numberOfUnitsFlag = false; 
                while (!numberOfUnitsFlag) {
                    try {
                        int numberOfUnits = userInput.acceptIntegerInput("Input the Number of Units to Enroll: ");
                        university.enrollments.get(numberOfEnrolments - 1).setUnitSize(numberOfUnits);
                        numberOfUnitsFlag = true; 
                    } catch (NumberFormatException nfe) {
                        System.out.println("Invalid Input. Please try again...");
                    }
                }

                university.inputUnitDetails(enrollments);

                // Prioritize "FIT" to the Front
                university.enrollments.get(numberOfEnrolments - 1).moveFITUnits();

                // Display the University Object Information
                System.out.println(university.displayUniversity());

                // After enrollment, re-prompt the user whether to enroll again or exit
                String continueSelection = userInput.acceptStringInput("Would you like to enroll another student?\n1 - Yes\n2 - No");
                
                if (continueSelection.equals("2")) {
                    System.out.println("Program Execution has terminated.\n");
                    break; // Exit the loop and terminate the program
                }
                // Otherwise, continue the loop to enroll again if the user selects '1'

            } else if (userSelection.equals("2")) {
                System.out.println("Program Execution has terminated.\n");
                break; // Exit the loop and terminate the program

            } else {
                // If the user enters an invalid option, prompt them again
                System.out.println("Invalid option. Please enter 1 or 2.");
            }
        }

        // Write Data to file before the Program ends
        String outputDataString = university.parseDataToFile(enrollments); 
        FileIO outputFile = new FileIO(outputFilePath); 
        try {
            outputFile.writeFile(outputDataString);
            System.out.println("Data Successfully Saved to File");
        } catch (IOException ioe) {
            university.printExceptionInfo("Error Writing to Output.txt", ioe);
        }
        userInput.closeScanner(); 
    }
    
    /**
     * Main Method of the Unviersity Class
     * Offer the user the option to select between 2 choices: 1) Enrols a student 2) Exits the program
     */
    public static void main(String[] args)
    {   
        University university = new University(); 
        university.startProgram();
    }
}

