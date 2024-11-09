
/**
 * Description: The Enrollment Class for the Rolling Project Assesmnet'
 * @author Tristan Sim
 * @version 1.00
 */

public class Enrollment {
    
    // Fields 
    private String date;
    private Student student; 
    private Unit unit; 

    /** Default Constructor
     */
    public Enrollment() 
    {
        date = "";
        student = new Student();
        unit = new Unit(); 
    }

    /** Non-Default Constructor
     * @param date The Enrollment Date as a String
     * @param student The Student Information as a nobject of Student Class
     * @param unit The Unit Information as an object of Unit Class
     */
    public Enrollment(String date, Student student, Unit unit) 
    {
        this.date = date;
        this.student = student;
        this.unit = unit; 
    }

    /**
     * Display Method: Display the Enrollment Information
     */
    public String displayEnrollment() {
        return String.format("Enrollment Information\n\nStudent:\n%s\nUnit:\n%s\n\nEnrollment Date: %s\n",
                                               this.student.display(), this.unit.display(), this.date); 
    }

    /** Accesor Method: Get Date
     * @return Returns the Enrollment Date as a String
     */
    public String getDate() 
    {
        return this.date; 
    }

    /** Accessor Method: Get Student 
     * @return Returns the Student Information as an Object of the Student Class
     */
    public Student getStudent() 
    {
        return this.student; 
    }

    /** Accessor Method: Get Unit
     * @return Returns the Unit Information as an Object of the Unit Class
     */
    public Unit getUnit() 
    {
        return this.unit; 
    }

    /**
     * Mutator Method: Set Date
     * @param date Updates the new Enrollment Date as a String
     */
    public void setDate(String date)
    {
        this.date = date; 
    }

    /**
     * Mutator Method: Set Student
     * @param student Updates the New Student Information as an Object of Student Class
     * @throw IllegalArgumentException The New Student Information cannot be Null
     */
    public void setStudent(Student student) 
    {
        if (student == null) {
            throw new IllegalArgumentException("Student Object cannot be Null");
        }
        this.student = student; 
    }

    /** 
     * Mutator Method: Set Unit
     * @param unit Updates the New Unit Information as an Object of Unit Class
     * @throw IllegalArgumentException The New Unit Information cannot be Null
     */ 
    public void setUnit(Unit unit) 
    {
        if (unit == null) {
            throw new IllegalArgumentException("Unit Object cannot be Null"); 
        }
        this.unit = unit; 
    }

    /** Main Method
     * Given the above class diagram, you main() method must be included in your Enrolment class for now if you would like to test your code. 
     */
    public static void main(String[] args) 
    {    
        // 1) Create and object of the Student class using the non-default constructor
        Student objStudent = new Student("John Doe", "No Where Land, Wasteland", "12345678", "john@gmail.com"); 

        // 2) Create and object of the Unit class using the non-default constructor
        Unit objUnit = new Unit("CS 8888", "Java Programming", 6); 
        
        //  3) Create and object of the Enrolment class using the non-default constructor
        Enrollment objEnrollment = new Enrollment("9-Nov-2024", objStudent, objUnit); 
        
        // 4) Now call the display() method in the enrolment class to display the state of the enrolment object.
        System.out.println(objEnrollment.displayEnrollment());
        
    }

}
