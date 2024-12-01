
/**
 * Description: Map Class handles all the Map Functionalities like generating of buildings for the Java Jumper Game
 * Final Assesment Project for ITO4131 - Java Programming
 * @author Tristan Sim
 * @version v1.00
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class Map {
    
    // Fields - Constants
    private static final Random RANDOMIZER = new Random();   // Generate Random Numbers
    private static final int NUMBER_OF_BUILDINGS = 15;       // The Number of Building a Map can HAVE
    private static final int MAX_FUEL_CELLS = 4;             // Maximum Number of Fuel Cells a Game can have
    private static final int FUEL_CELL_LIFETIME = 3;         // The Lifetime (Turns) until the Fuel Cell expires for a Building 
    
    // Fields
    private Building[] buildings;
    private int[] fuelCellBuildings;
    private int frozenBuildingIndex; 
    private int portalBuildingIndex; 
    private int webTrapBuildingIndex; 

    /**
     * Default Constructor for Map
     */
    public Map()
    {   
        // Initialize Values
        this.frozenBuildingIndex = 0; 
        this.portalBuildingIndex = 0;
        this.webTrapBuildingIndex = 0; 
        this.buildings = new Building[NUMBER_OF_BUILDINGS];   
        this.fuelCellBuildings = new int[NUMBER_OF_BUILDINGS]; 

        for (int i = 0; i < NUMBER_OF_BUILDINGS; i++) // Prevent Null Objects
        {
            this.buildings[i] = new Building(1, false, false, false, false);  
        }
    }
    
    /**
     * Non-Default Constructor for Map
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
        this.buildings = buildings;
        this.fuelCellBuildings = fuelCellBuildings; 
    }

    /**
     * Custom Method: Decrement Fuel Cell Lifetime
     */
    public void decrementFuelCellTurnsRemaining()
    {   
        for (int i = 0; i < this.fuelCellBuildings.length; i++)
        {
            if (this.fuelCellBuildings[i] > 0) 
            {
                this.fuelCellBuildings[i]--;
            }
        }
    }

    /**
     * Display Method: Display the Fields of the Map Object
     */
    public String displayMap() 
    {
        StringBuilder stringBuilder = new StringBuilder(); 
        stringBuilder.append("Index of Portal Building: ").append(getPortalIndex()).append("\n"); 
        stringBuilder.append("Index of Frozen Building: ").append(getFrozenBuildingIndex()).append("\n"); 
        stringBuilder.append("Index of Web Trap Building: ").append(getWebTrapBuildingIndex()).append("\n"); 
        stringBuilder.append("Index of Fuel Cell Buildings: ");
        
        int counter = 1; 
        for (int i = 0; i < this.fuelCellBuildings.length; i++) 
        {   
            if (this.fuelCellBuildings[i] > 0 && counter != MAX_FUEL_CELLS) 
            {
                stringBuilder.append(i).append(" , ");
                counter++;
            } 
            else if (this.fuelCellBuildings[i] > 0 && counter == MAX_FUEL_CELLS) 
            {
                stringBuilder.append(i).append("\n");
            }
        }
        
        stringBuilder.append("Building Height: "); 
        for (int i = 0; i < NUMBER_OF_BUILDINGS; i++)
        {   
            stringBuilder.append(this.buildings[i].getHeight()); 
            if (i < NUMBER_OF_BUILDINGS - 1) 
            {
                stringBuilder.append(" , ");  
            }
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
     * Custom Method: Generates a unique building index that is not occupied by other buildings
     * @param occupiedBuildings ArrayList<Integer>: List of occupied building indices
     * @return int: Unique index for assigning effects
     */
    private int generateUniqueBuildingIndex(ArrayList<Integer> occupiedBuildings) 
    {
        int buildingIndex;

        do 
        {
            buildingIndex = RANDOMIZER.nextInt(NUMBER_OF_BUILDINGS);
        } 
        while (occupiedBuildings.contains(buildingIndex));

        occupiedBuildings.add(buildingIndex);
        return buildingIndex;
    }

       /** 
     * Custom Method: initialize New Map
     * Will also randomize the Building Height, Effects, Portals & Traps 
     * Method also prevents Duplication of Effects on the same building
     * @param playerLocation Player Location/Index where the player is currently at 
    */
    public void initializeMap(int playerLocation)
    {   
        // This will track the Index to Prevent Duplication of Effects on the same building
        ArrayList<Integer> occupiedBuildings = new ArrayList<Integer>();
        occupiedBuildings.add(playerLocation); 

        for (int i = 0; i < this.fuelCellBuildings.length; i++) 
        {
            if (this.fuelCellBuildings[i] > 0) 
            {
                occupiedBuildings.add(i); 
            }
        }     
        
        randomizeBuildingsHeight();

        // Allows the Effect of Frozen & Portal to be Together
        int newPortalBuildingIndex = generateUniqueBuildingIndex(occupiedBuildings); 
        int newFrozenBuildingIndex = generateUniqueBuildingIndex(occupiedBuildings); 
        occupiedBuildings.add(newPortalBuildingIndex);
        occupiedBuildings.add(newFrozenBuildingIndex);
        setPortalBuilding(newPortalBuildingIndex);
        setFrozenBuilding(newFrozenBuildingIndex);

        // Web Trap Building Generation
        int newWebTrapBuilding = generateUniqueBuildingIndex(occupiedBuildings); 
        occupiedBuildings.add(newWebTrapBuilding);
        setWebTrapBuilding(newWebTrapBuilding);

        resetFuelCells(occupiedBuildings);
    }

    /** 
     * Custom Method: initialize New Map with Existing Building Data, Example Below: -- Needs Updating
     * Height, Portal, Fuel Cells, Trap, Frozen
     * "2,False,False,False,False"
     * @param playerLocation Player Location/Index where the player is currently at 
     * @param buildingData String[]; An Array of String Lines containing Building Information
    */
    public void initializeMap(int playerLocation, String[] buildingData) 
        throws NumberFormatException
    {   
        for (int i = 0; i < buildingData.length; i++)
        {
            String[] stringSegments = buildingData[i].split(",");
            this.buildings[i].setHeight(Integer.parseInt(stringSegments[0])); 

            if (Boolean.parseBoolean(stringSegments[1].toLowerCase())) 
            {
                setPortalBuilding(i);
            } 
            else if (Boolean.parseBoolean(stringSegments[3].toLowerCase())) 
            {
                setWebTrapBuilding(i);
            }
            else if (Boolean.parseBoolean(stringSegments[4].toLowerCase())) 
            {
                setFrozenBuilding(i);
            }

            if  (Boolean.parseBoolean(stringSegments[2].toLowerCase())) 
            {
                setSpecificFuelCellBuilding(i, FUEL_CELL_LIFETIME); 
            }
        }
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

            do 
            {
                buildingIndex = RANDOMIZER.nextInt(NUMBER_OF_BUILDINGS); 
            } 
            while (occupiedBuildings.contains(buildingIndex));  // Ensures no overlapping effects

            occupiedBuildings.add(buildingIndex);  // Similarly, do not spawn another fuel cell on the same building
            setSpecificFuelCellBuilding(buildingIndex, FUEL_CELL_LIFETIME);
        }
    }

        /**
     * Custom Method: Reshuffle the Map for a New Turn
     */
    public void reshuffleMap(int playerLocation)
    {   
        // This will track the Index to Prevent Duplication of Effects on the same building
        ArrayList<Integer> occupiedBuildings = new ArrayList<Integer>();
        occupiedBuildings.add(playerLocation); 

        for (int i = 0; i < this.fuelCellBuildings.length; i++) 
        {
            if (this.fuelCellBuildings[i] > 0) 
            {
                occupiedBuildings.add(i); 
            }
        }     
        
        randomizeBuildingsHeight();
        // Allows the Effect of Frozen & Portal to be Together
        int newPortalBuildingIndex = generateUniqueBuildingIndex(occupiedBuildings); 
        int newFrozenBuildingIndex = generateUniqueBuildingIndex(occupiedBuildings); 
        occupiedBuildings.add(newPortalBuildingIndex);
        occupiedBuildings.add(newFrozenBuildingIndex);

        setPortalBuilding(newPortalBuildingIndex);
        setFrozenBuilding(newFrozenBuildingIndex);
        
        int newWebTrapBuilding = generateUniqueBuildingIndex(occupiedBuildings); 
        occupiedBuildings.add(newWebTrapBuilding);
        setWebTrapBuilding(newWebTrapBuilding);
        
        decrementFuelCellTurnsRemaining();
        int sumTurns = Arrays.stream(this.fuelCellBuildings).sum(); 
        if (sumTurns <= 0) {
            resetFuelCells(occupiedBuildings);
        }
    }
  
    /** 
     * Custom Method: Remove the Fuel Cell from a Building
     * @param index Integer: The Building index where the Fuel Cell was consumed by the Player
    */
    public void removeFuelCell(int index)
    {
        this.fuelCellBuildings[index] = 0; 
    }

    /**
     * Mutator Method: Set Buildings
     * @param buildings Building[]: Accepts an Array of Buildings 
     */
    public void setBuildings(Building[] buildings)
    {
        this.buildings = buildings; 
    }

    /**
     * Mutator Method: Set Frozen Building Index
     * @param index Integer: The index of the building to be frozen
     */
    public void setFrozenBuilding(int index)
    {
        this.frozenBuildingIndex = index; 
        this.buildings[index].setFrozen(true);
    }

    /**
     * Mutator Method: Set Fuel Cell Building
     * @param index Integer[]: An Array of integers containing the Fuel Cell Lifetime for each building
     */
    public void setFuelCellBuildings(int[] fuelCellBuildings)
    {
        this.fuelCellBuildings = fuelCellBuildings;
        for (int i = 0; i < this.fuelCellBuildings.length; i ++)
        {
            if (this.fuelCellBuildings[i] > 0) 
            {
                this.buildings[i].setFuelCell(true);
            } 
            else 
            {
                this.buildings[i].setFuelCell(false);
            }
        }
    }

    /**
     * Mutator Method: Set Specific Fuel Cell Building
     * @param index Integer: The Index of the Building to Set
     * @param fuelCellLifetime Integer: The Lifetime of the Fuel Cell to Set
     */
    public void setSpecificFuelCellBuilding(int index, int fuelCellLifetime)
    {
        this.fuelCellBuildings[index] = Math.max(0, Math.min(fuelCellLifetime, FUEL_CELL_LIFETIME));
        this.buildings[index].setFuelCell(true);
    }

    /**
     * Mutator Method: Set Portal Building Index
     * @param index Integer: The index of the building with the Portal
     */
    public void setPortalBuilding(int index)
    {
        this.portalBuildingIndex = index;
        this.buildings[index].setPortal(true);
    }

    /**
     * Mutator Method: Set Web Trap Building Index
     * @param index Integer: The index of the building with the Web Trap
     */
    public void setWebTrapBuilding(int index)
    {
        this.webTrapBuildingIndex = index;
        this.buildings[index].setWebTrap(true);
    }
    
    /**
     * Main Method for Testing
     * @param args
     */

    public static void main(String[] args) {
        // initialize an instance of the Map
        Map map = new Map();

        // Testing initializeMap()
        System.out.println("Testing initializeMap()...");
        map.initializeMap(0);

        System.out.println("Round 1: " + map.displayMap()); // Assuming there's a displayMap() to show the state of the map

        map.reshuffleMap(0);

        System.out.println("Round 2: " + map.displayMap()); // Assuming there's a displayMap() to show the state of the map
    }
}
