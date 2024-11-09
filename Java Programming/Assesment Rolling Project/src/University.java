
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
    
    


}

