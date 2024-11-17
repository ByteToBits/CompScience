import java.util.Arrays;
import java.util.Objects;

/**
 * Description: The Enrollment Class for the Rolling Project Assesmnet'
 * @author Tristan Sim
 * @version 1.00
 */

public class Enrollment {
    
    // Fields 
    private String date;
    private Student student; 
    private Unit[] units;
    private static int numberOfEnrolments = 0; 

    /** Default Constructor
     */
    public Enrollment() 
    {
        date = "";
        student = new Student();
        units = new Unit[10]; 
        numberOfEnrolments++; 
    }

    /** Non-Default Constructor
     * @param date The Enrollment Date as a String
     * @param student The Student Information as a nobject of Student Class
     * @param units The Unit Information as an Array of objects of Unit Class
     */
    public Enrollment(String date, Student student, Unit[] units) 
    {
        this.date = date;
        this.student = student;
        this.units = units;
        numberOfEnrolments++; 
    }

    /**
     * Display Method: Display the Enrollment Information
     */
    public String displayEnrollment() {
        
        StringBuffer unitsEnrolled = new StringBuffer(); 

        for (Unit unit:this.units) {
            if (unit != null) {
                if (unitsEnrolled.length() > 0) {
                    unitsEnrolled.append(", "); 
                }
                unitsEnrolled.append(unit.getUnitDescription() + " (" + unit.getUnitCode() + ")");
            }
        }

        return String.format("\nEnrollment Information:\nEnrollment Date: %s\n%sUnits Enrolled: %s\n",
                             this.date, this.student.display(), unitsEnrolled); 
    }


    /** Custom Method: Create a New Student
     * @param name Name of the Student as a String
     * @param address Address of a Student as a String
     * @param phoneNo Phone Numbner of the Student as a String
     * @param email Email of the Student as a String
     */
    public void createStudent(String name, String address, String phoneNo, String email)
    {
        this.student.setName(name);
        this.student.setAddress(address);
        this.student.setPhoneNo(phoneNo);
        this.student.setEmail(email);
    }

    /** Accesor Method: Get Date
     * @return Returns the Enrollment Date as a String
     */
    public String getDate() 
    {
        return this.date; 
    }

    /** Accessor Method: Number of Enrollments
     * @return Number of Enrollment Objects Created 
     */
    public int getNumberOfEnrollments() {
        return numberOfEnrolments; 
    }
    
    /** Get a Specifc Unit
     * @param index The Unit Index in the Units Array as a String
     * @return The Unit from the Units Array as an Object of Unit
     * @throw IndexOutOfBoundsException The index exceeds the bounds of the array 
     */
    public Unit getSpecificUnit(int index) 
    {
        if (index < 0 || index >= this.units.length)
        {
            throw new IndexOutOfBoundsException("The Index Exceeds the Bounds of the Units Array"); 
        }
        return units[index]; 
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
    public Unit[] getUnits() 
    {
        return this.units; 
    }

    /** Accessor Method: Get the Unit Size
     * @return The Length of the Units Array as an Integer
     */
    public int getUnitSize() 
    {
        return this.units.length; 
    }

    /**
     * Mutator Method: Set Date
     * @param date Updates the new Enrollment Date as a String
     */
    public void setDate(String date)
    {
        this.date = date; 
    }
    
    /** Mutator Method: Set Specific Unit
     * @param index The index of the Unit in the Units Array as an Integer
     * @param unitCode The Unit Code for the Subject as a String
     * @param unitDescription The Unit Description as a String
     * @param creditPoints The Credit Points as a Integer
     * @throw IndexOutOfBoundsException The Index input is out of bounds of the Units Array
     */
    public void setSpecificUnit(int index, String unitCode, String unitDescription, int creditPoints)
    {
        if (index < 0 || index >= this.units.length) 
        {
            throw new IndexOutOfBoundsException("The Index is out of bounds for the Units Array"); 
        }
        units[index].setUnitCode(unitCode);
        units[index].setUnitDescription(unitDescription);
        units[index].setCreditPoints(creditPoints);
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
     * @param units[] Updates the New Unit Information as an Array of Objects of Unit Class
     */ 
    public void setUnits(Unit[] units) 
    {  
        if (units == null || Arrays.stream(units).anyMatch(Objects::isNull))
        {
            System.out.println("Alert: Null Values Exist in Units Array!");
        }
        this.units = units;
    }

    /** 
     * Mutator Method: Set Units Size 
     * May remove Units if it old Units Array exceeds the bounds of new Unit Size
     * @param unitSize The Size of the Unit Array as an Integer
     * @throw IndexOutOfBoundsException The Unit Size must be greater than 0 for a new Array
     */
    public void setUnitSize(int unitSize) {
        if (unitSize <= 0) {
            throw new IllegalArgumentException("Unit size must be greater than 0.");
        }
        
        // Initialize the units array with the given size
        this.units = new Unit[unitSize];
        
        // Initialize each Unit object in the array
        for (int i = 0; i < unitSize; i++) {
            this.units[i] = new Unit();  // Initialize each Unit
        }
    }

    public String getLowestCreditPoints() 
    {
        Unit lowestCreditUnit = null;
        for (Unit unit : units) 
        {
                if (unit != null) {
                    if (lowestCreditUnit == null || unit.getCreditPoints() < lowestCreditUnit.getCreditPoints()) {
                    lowestCreditUnit = unit; 
                    }
                }
        }
        if (lowestCreditUnit != null) {
            return lowestCreditUnit.getUnitDescription();
        } else {
            return null;
        }
    }
  
    public void moveFITUnits() 
    {
        int index = 0; // If an element with FIT is detected, Insert at this index
        for (int iterI = 0; iterI < units.length; iterI++) {
            if (units[iterI] != null && units[iterI].getUnitCode().startsWith("FIT")) {
                Unit tempUnit = units[index]; // Pass the Unit to be Swapped Out to Temp
                units[index] = units[iterI];
                units[iterI] = tempUnit;
                index++;  // Increment to the next Index
            }
        }
    }

    // Internal Testing
    public static void main(String[] args)
    {   
        // Have to Create a New Object to Initialize each Object in the Array
        Unit[] studentUnits = new Unit[] {
            new Unit("ITO3888", "Java Programming", 3),
            new Unit("ITO8888", "Databases", 3),
            new Unit("FIT9688", "C++ Programming",5),
            new Unit("FTO8778", "Automata Theory", 2),
            new Unit("FIT9698", "Data Strucutres & Algorithm", 5),
            new Unit("FITO9698", "Distributed Systems", 5),
        };
        
        Student newStudent = new Student();

        Enrollment newEnrollmebnt1 = new Enrollment("20th Oct", newStudent, studentUnits); 
        newEnrollmebnt1.moveFITUnits(); 
        
        newEnrollmebnt1.createStudent("John Hops", "Canberra", "888888", "Hops@gmail.com");

        System.out.println(newEnrollmebnt1.displayEnrollment()); 

        System.out.println("Lowest Credit Point: " + newEnrollmebnt1.getLowestCreditPoints()); 

    }
}   
