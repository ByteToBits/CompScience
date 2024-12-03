/**
 * Description: The Building Class for the Java Jumper Game.
 * The Building Class handles all the Building Attributes like Height, Portal and etc.
 * @aurthor Tristan Sim
 * @version v1.00
 */

public class Building
{
    // Fields
    private static final int MAX_HEIGHT = 5; 
    private static final int MIN_HEIGHT = 1; 
    private int height;
    private boolean portal; 
    private boolean fuelCell; 
    private boolean webTrap; 
    private boolean frozen; 

    /**
     * Default Constructor
     */ 
    public Building()
    {
        this.height = MIN_HEIGHT; 
        this.portal = false;
        this.fuelCell = false;
        this.webTrap = false;
        this.frozen = false; 
    }

    /**
     * Non-Default Constructor
     * @param height Height of the Building as an Integer; Will be clamped between the Max and Min Height
     * @param portal Portal Flag if it exist on the building as a Boolean
     * @param fuelCells Fuel Cell Flag if it exist on the building as a Boolean
     * @param webTraps Web Traps Flag if it exist on the building as a Boolean
     * @param frozen Frozen Flag if the building is frozen as a Boolean
     */
    public Building(int height, boolean portal, boolean fuelCells, boolean webTraps, boolean frozen)
    {
        this.height = Math.max(MIN_HEIGHT, Math.min(MAX_HEIGHT, height));  // Clamp the height to the Max and Min Boundaries
        this.portal = portal;
        this.fuelCell = fuelCells;
        this.webTrap = webTraps;
        this.frozen = frozen; 
    }

    /**
     * Display Method: 
     * @return Returns the Attributes of the Building Class as a String
     */
    public String displayBuilding()
    {
        return String.format("Height: %d; Portal: %b; Fuel Cells: %b; Web Traps: %b; Frozen: %b",
                               this.height, this.portal, this.fuelCell, this.webTrap, this.frozen);
    }

    /**
     * Accesor Method: Get Frozen
     * @return  @return If the Building is Frozen | Boolean: True or False
     */
    public boolean getFrozen()
    {
        return this.frozen; 
    }

    /**
     * Accesor Method: Get Fuel Cell
     * @return If the Building has a Fuel Cell | Boolean: True or False
     */
    public boolean getFuelCell()
    {
        return this.fuelCell; 
    }

    /**
     * Accesor Method: Get Height
     * @return Height of the Building as an Integer
     */
    public int getHeight()
    {
        return this.height; 
    }

    /**
     * Accesor Method: Get Portal
     * @return If the Building has a Portal | Boolean: True or False
     */
    public boolean getPortal()
    {
        return this.portal; 
    }

    /**
    * Accesor Method: Get Web Trap
    * @return If the Building has a Web Trap | Boolean: True or False
    */
    public boolean getWebTrap()
    {
        return this.webTrap; 
    }

    /**
     * Mutator Method: Set Frozen Building
     * @param frozen Assign a Building to be Frozen if true; false removes the Frost
     */
    public void setFrozen(boolean frozen)
    {
        this.frozen = frozen; 
    }

    /**
     * Mutator Method: Set Fuel Cell
     * @param fuelCell Assign a Fuel Cell to a Building if true; false removes the fuel cell
     */
    public void setFuelCell(boolean fuelCell)
    {
        this.fuelCell = fuelCell; 
    }

    /**
     * Mutator Method: Set Height of the Buildings
     * @param height Set the Height of the Building as a an Integer
     */
    public void setHeight(int height)
    {
        this.height = Math.max(MIN_HEIGHT, Math.min(height, MAX_HEIGHT)); 
    }

    /**
    * Mutator Method: Set Portal to a Bulding
    * @param portal Set a Portal to a Building if true; false removes the portal
    */
    public void setPortal(boolean portal)
    {
        this.portal = portal; 
    }
    
    /**
    * Mutator Method: Set Web Trap to a Buidling
    * @param webTrap Set a Web Trap to a Building if true; false removes the Web Trap
    */
    public void setWebTrap(boolean webTrap)
    {
        this.webTrap = webTrap; 
    }

}