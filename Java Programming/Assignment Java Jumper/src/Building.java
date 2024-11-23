
/**
 * Description: The Building Class for the Java Jumper Game
 * Final Assesment Project for ITO4131 - Java Programming
 * @aurthor Tristan Sim
 * @version 1.00
 */

public class Building
{
    // Fields
    private static final int MAX_HEIGHT = 5; 
    private static final int MIN_HEIGHT = 1; 
    private int height;
    private boolean portal; 
    private boolean fuelCells; 
    private boolean webTraps; 
    private boolean frozen; 

    /**
     * Default Constructor
     */ 
    public Building()
    {
        this.height = MIN_HEIGHT; 
        this.portal = false;
        this.fuelCells = false;
        this.webTraps = false;
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
        this.height = Math.max(MIN_HEIGHT, Math.min(MAX_HEIGHT, height));  // Clamp the height to the Max & Min Boundaries
        this.portal = portal;
        this.fuelCells = fuelCells;
        this.webTraps = webTraps;
        this.frozen = frozen; 
    }

    /**
     * Custom Method: Create a Building by passing in the Parameters
     * @param height Height of the Building as an Integer; Will be clamped between the Max and Min Height
     * @param portal Portal Flag if it exist on the building as a Boolean
     * @param fuelCells Fuel Cell Flag if it exist on the building as a Boolean
     * @param webTraps Web Traps Flag if it exist on the building as a Boolean
     * @param frozen Frozen Flag if the building is frozen as a Boolean
     */
    public void createBuilding(int height, boolean portal, boolean fuelCells, boolean webTraps, boolean frozen)
    {
        this.height = Math.max(MIN_HEIGHT, Math.min(MAX_HEIGHT, height));  // Clamp the height to the Max & Min Boundaries
        this.portal = portal;
        this.fuelCells = fuelCells;
        this.webTraps = webTraps;
        this.frozen = frozen; 
    }

    /**
     * Display Method: 
     * @return Returns the Attributes of the Building Class as a String
     */
    public String displayBuilding()
    {
        return String.format("Height: %d\nPortal: %b\nFuel Cells: %b\nWeb Traps: %b\nFrozen: %b",
                               this.height, this.portal, this.fuelCells, this.webTraps, this.frozen);
    }

    /**
     * Accesor Method: Get Frozen
     * @return Frozen Status of the Building as a Boolean
     */
    public boolean getFrozen()
    {
        return this.frozen; 
    }

    /**
     * Accesor Method: Get Fuel Cell
     * @return If the Building has a Fuel Cell as a Boolean 
     */
    public boolean getFuelCell()
    {
        return this.fuelCells; 
    }


    /**
     * 
     * @param args
     */

    public static void main(String[] args)
    {
        Building building = new Building(); 
        System.out.println(building.displayBuilding()); 
    }

}