
/**
 * Description: Map Class handles all the Map Functionalities like generating of buildings for the Java Jumper Game
 * Final Assesment Project for ITO4131 - Java Programming
 * @author Tristan Sim
 * @version v1.00
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level; 
import java.util.logging.Logger;

public class Map {
    
    // Fields - Constants
    private static final Logger LOGGER = Logger.getLogger(Map.class.getName());  // For Internal Debugging Purposes
    private static final Random RANDOMIZER = new Random();   // Generate Random Numbers
    private static final boolean DEBUG_FLAG = true;          // Internal Flag for Debugging Purposes (System.out.print)
    private static final int NUMBER_OF_BUILDINGS = 15;       // The Number of Building a Map can HAVE
    private static final int MAX_FUEL_CELLS = 4;             // Maximum Number of Fuel Cells a Game can have
    private static final int FUEL_CELL_LIFETIME = 3;         // The Lifetime (Turns) until the Fuel Cell expires for a Building 
    
    // Fields
    private Building[] buildings = new Building[NUMBER_OF_BUILDINGS]; 
    private int[] fuelCellBuildings = new int[NUMBER_OF_BUILDINGS]; 
    private int frozenBuildingIndex; 
    private int portalBuildingIndex; 
    private int webTrapBuildingIndex; 

    /**
     * Default Constructor for Map
     */
    public Map()
    {   
        // Initialize Values
        this.frozenBuildingIndex = 1; 
        this.portalBuildingIndex = 1; 
        this.webTrapBuildingIndex = 1; 
        this.buildings = new Building[NUMBER_OF_BUILDINGS];   
        this.fuelCellBuildings = new int[NUMBER_OF_BUILDINGS]; 

        for (int i = 0; i < NUMBER_OF_BUILDINGS; i++) // Prevent Null Objects
        {
            this.buildings[i] = new Building(1, false, false, false, false);  
        }
    }
    
    /**
     * Non-Default Constructor for Map
     * This Constructor will Automatically Handle Invalid Bounds or Null Objects inside the Building Array
     * @param buildings Array: Building Objects to be Constructed
     * @param fuelCellBuildings HashMap: Key represents the building index (Integer). Value represents the remaining turns for the fuel cell (Integer).
     * @param frozenBuildingIndex Integer: The Index of which Building will be Frozen
     * @param webTrapBuildingIndex Integer: The Index of which Building will be Web Trapped
     */
    public Map(Building[] buildings, int[] fuelCellBuildings, int frozenBuildingIndex, int portalBuildingIndex, int webTrapBuildingIndex)
    {   
        // Initialize Values
        this.frozenBuildingIndex = frozenBuildingIndex;
        this.portalBuildingIndex = portalBuildingIndex; 
        this.webTrapBuildingIndex = webTrapBuildingIndex; 
        this.buildings = new Building[NUMBER_OF_BUILDINGS];   
        this.fuelCellBuildings = new int[NUMBER_OF_BUILDINGS]; 
        
        if (buildings.length != NUMBER_OF_BUILDINGS)  // Software Debugging Purposes
        {
            debugException(new IndexOutOfBoundsException("Index Out of Bounds")); 
        }
        for (int i = 0; i < NUMBER_OF_BUILDINGS; i++) // Prevent Null Objects
        {   
            if (buildings.length > i && buildings[i] != null) {
                this.buildings[i] = new Building(buildings[i].getHeight(), buildings[i].getPortal(), buildings[i].getFuelCell(), buildings[i].getWebTrap(), buildings[i].getFrozen());
            } else {
                this.buildings[i] = new Building(1, false, false, false, false); 
            }
            if (fuelCellBuildings.length > i) {
                this.fuelCellBuildings[i] = fuelCellBuildings[i]; 
            } 
        }
    }

    /** 
     * Custom Method: Create New Map
     * Will also randomize the Building Height, Effects, Portals & Traps 
     * @param playerLocation Player Location/Index where the player is currently at 
    */
    public void createMap(int playerLocation)
    {   
        // This will track the Index of Which Building has Effects & Players
        ArrayList<Integer> occupiedBuildings = new ArrayList<Integer>();  
        occupiedBuildings.add(playerLocation); 

        int[] randomIndex = new int[3]; 
        for (int i = 0; i < randomIndex.length; i++)
        {
            do {
                randomIndex[i] = RANDOMIZER.nextInt(NUMBER_OF_BUILDINGS); 
            } while (occupiedBuildings.contains(randomIndex[i]));
        }
        
        randomizeBuildingsHeight();
    }

    /**
     * Private Custom Method: Print Runtime Errors using Logger 
     * @param exception Exception: The Exception Thrown by Java to be display in the console 
     */
    private static void debugException(Exception exception)
    {   
        if (DEBUG_FLAG) {
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
            throw new RuntimeException(exception); 
        }
    }
    
    /**
     * Display Method: Display the Fields of the Map Object
     */
    public String displayMap() 
    {
        StringBuilder stringBuilder = new StringBuilder(); 
        stringBuilder.append("\nIndex of Frozen Building: ").append(this.frozenBuildingIndex).append("\n"); 
        stringBuilder.append("Index of Web Trap Building: ").append(this.webTrapBuildingIndex).append("\n"); 

        for (int i = 0; i < NUMBER_OF_BUILDINGS; i++)
        {   
            stringBuilder.append("\nBuilding ").append(i+1).append(" Info:\n");
            stringBuilder.append(this.buildings[i].displayBuilding()); 
            stringBuilder.append("\nFuel Cell Lifetime (Turns): ").append(this.fuelCellBuildings[i]).append("\n"); 
        }
        return stringBuilder.toString(); 
    }

    /** 
     * Accessor Method: Get Buildings 
     * @return Array<Building>: Returns an Array Containing the Building Objects 
    */
    public Building[] getBuildings()
    {
        return this.buildings;
    }
    
    /** 
     * Accessor Method: Get Fuel Cells of the Buildings
     * @return Array<integer>: Returns an Array of Integer Containing the Fuel Cell Lifetime (Turns)
    */
    public int[] getFuelCellBuildings()
    {
        return this.fuelCellBuildings; 
    }

    /** 
     * Accessor Method: Get the Index of the Frozen Building
     * @return integer: Returns the Index of the Frozen Building
    */
    public int getFrozenBuildingIndex()
    {
        return this.frozenBuildingIndex; 
    }

    /** 
     * Accessor Method: Get the Index of the Portal Building
     * @return integer: Returns the Index of the Portal Building
    */
    public int getPortalIndex()
    {
        return this.portalBuildingIndex;
    }

    /** 
     * Accessor Method: Get the Index of the Web Trap Building
     * @return integer: Returns the Index of the Web Trap Building
    */
    public int getWebTrapBuildingIndex()
    {
        return this.webTrapBuildingIndex;
    }

    /** 
     * Custom Method: Randomize the Building Height
    */
    public void randomizeBuildingsHeight() 
    {   
        for (Building building : this.buildings)
        {
            building.setHeight(RANDOMIZER.nextInt(5) + 1);
        }
    }

    /** 
     * Custom Method: Randomize the Frozen Building Index
     * @return Integer: The new Randomize Index of the Frozen Building
    */
    public void randomizeFrozenBuilding()
    {
        this.frozenBuildingIndex = RANDOMIZER.nextInt(NUMBER_OF_BUILDINGS-1);
    }

    /** 
     * Custom Method: Randomize the Portal Building Index
     * @return Integer: The new Randomize Index of the Portal Building
    */
    public void randomizePortalBuilding()
    {
        this.portalBuildingIndex = RANDOMIZER.nextInt(NUMBER_OF_BUILDINGS-1);
    }

    /** 
     * Custom Method: Randomize the Web Trap Building Index
     * @return Array<Building>: Returns an Array Containing the Building Objects 
    */
    public void randomizeWebTrapBuilding()
    {
        this.webTrapBuildingIndex = RANDOMIZER.nextInt(NUMBER_OF_BUILDINGS - 1); 
    }

    /** 
     * Custom Method: Reset the Fuel Cells Lifetime & Building Index
     * Does not spawn on another building with effect like Player, Frozen, Traps & Portals are located
     * @param occupiedBuildings ArrayList<Integer>: An Array List of Integers where Player, Frozen, Traps & Portals are located
     * @return Array<Building>: Returns an Array Containing the Building Objects 
    */
    public void resetFuelCells(ArrayList<Integer> occupiedBuildings)
    {   
        for (int i = 0; i < NUMBER_OF_BUILDINGS; i++)
        {
            this.fuelCellBuildings[i] = 0;
        }

        for (int i = 0; i < MAX_FUEL_CELLS; i++)
        {
            int buildingIndex = 0;
            do {
                buildingIndex = RANDOMIZER.nextInt(NUMBER_OF_BUILDINGS); 
            } while (occupiedBuildings.contains(buildingIndex));  // Ensures no overlapping effects
            occupiedBuildings.add(buildingIndex);  // Similarly, do not spawn another fuel cell on the same building
            this.fuelCellBuildings[buildingIndex] = FUEL_CELL_LIFETIME; 
        }
    }

    /** 
     * Mutator Method: Set the Web Trap Building Index
     * @param index Integer: Index of the Building where the Web Trap will be set
    */

    
    /** 
     * Custom Method: Remove the Fuel Cell from a Building
     * @param index Integer: The Building index where the Fuel Cell was consumed by the Player
    */
    public void removeFuelCell(int index)
    {
        this.fuelCellBuildings[index] = 0; 
    }

    /**
     * Main Method 
     * @param args
     */
    
    public static void main(String[] args)
    {
        Map map1 = new Map(); 
        System.out.println(map1.displayMap());
    }
}
