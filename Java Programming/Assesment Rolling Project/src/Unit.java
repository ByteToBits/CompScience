
/**
 * Description: Unit Class contains instructions to handle the Unit (Subjects) information
 * @author Tristan Sim
 * @version 1.00
 */

public class Unit {
    
    // Fields
    private String unitCode;
    private String unitDescription; 
    private int creditPoints; 

    /**
     * Default Constructor
     */
    public Unit() 
    {
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
    public Unit(String unitCode, String unitDescription, int creditPoints)
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
     * Display Method:L Display Unit Information
     */
    public String display()
    {
        String tempString = String.format(
            "Unit Code: %s\nUnit Description: %s\nCredit Points %d",
            this.unitCode, this.unitDescription, this.creditPoints
        );
        return tempString; 
    }

    /**
     * Accessor Method: Get the Credit Points
     * @return Returns the Credit Points in as a Integer
     */
    public int getCreditPoints() 
    {
        return this.creditPoints;
    }

    /**
     * Accessor Method: Get the Unit Code
     * @return Returns the Unit Code as a String
     */
    public String getUnitCode() 
    {
        return this.unitCode;
    }

    /**
     * Accesor Method: Get the Unit Description
     * @return Returns the Unit Description as a String
     */
    public String getUnitDescription() 
    {
        return this.unitDescription;
    }
    
    /** Mutator Method: Set the Credit Points
     * @param creditPoints The New Credit Point to be Updated for the Unit as a Integer
     */
    public void setCreditPoints(int creditPoints) 
    {
        this.creditPoints = creditPoints;
    }

    /** Mutator Method: Set the Unit Code
     * @param unitCode The New Unit Code to be Updated for the Unit as a String
     */
    public void setUnitCode(String unitCode)
    {
        this.unitCode = unitCode;
    }

    /** Mutator Method: Set the Unit Description
     * @param unitDescription The New Unit Description to be Updated for the Unit as a String
     */
    public void setUnitDescription(String unitDescription)
    {
        this.unitDescription = unitDescription; 
    }

}
