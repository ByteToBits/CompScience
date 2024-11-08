
/**
 * Description: Units Class contains instructions to handle the Units (Subjects) information
 * @author Tristan Sim
 * @version 1.00

 */

public class Units {
    
    // Fields
    private String unitCode;
    private String unitDescription; 
    private int creditPoints; 

    /**
     * Default Constructor
     */
    public Units() {
        unitCode = ""; 
        unitDescription = "";
        creditPoints = 0; 
    }

    /**
     * Non-Default Constructor
     * @param unitCode The Unit Code for the Unit as a String
     * @param unitDescription The Description for the Unit as a String
     * @param creditPoints The amount of Credit Points for the Unit as an Integer
     * @throw IllegalArgumentException if Unit Code and Unit Desecription are Empty
     */
    public Units(String unitCode, String unitDescription, int creditPoints)
    {   
        if (unitCode == null || unitCode.isEmpty()) {
            throw new IllegalArgumentException("Unit Code cannot be Empty"); 
        }
        if (unitDescription == null || unitDescription.isEmpty()) {
            throw new IllegalArgumentException("Unit Description cannot be Empty"); 
        }

        this.unitCode = unitCode; 
        this.unitDescription = unitDescription;
        this.creditPoints = creditPoints; 
    }

    /**
     * Display Unit Information Method
     */
    public void display() {
        String tempString = String.format(
            "Unit Code: %s\nUnit Description: %s\nCredit Points %d",
            this.unitCode, this.unitDescription, this.creditPoints
        );
        System.out.println(tempString);
    }
}
