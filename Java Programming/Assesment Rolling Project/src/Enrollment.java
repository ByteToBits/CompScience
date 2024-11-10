
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
        return String.format("\nEnrollment Information:\n\nEnrollment Date: %s\n\n%s\n%s\n",
                             this.date, this.student.display(), this.unit.display()); 
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

}
