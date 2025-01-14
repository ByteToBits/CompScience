package jumper;
/** 
 * Description: The Player Class handles the Player States and Actions 
 * It consist of the player's information, Device Object and handles the player actions. 
 * @author Tristan Sim
 * @version 1.01
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Player 
{
    // Fields
    private static final int NUMBER_OF_BUILDINGS = 15;             // The Number of Building a Map can HAVE
    private static final String ACTION_JUMP_RIGHT = "Jump Right"; 
    private static final String ACTION_JUMP_LEFT = "Jump Left"; 
    private static final String ACTION_STAY = "Stay"; 

    private String name; 
    private int location;
    private Device device; 

    /**
    * Default Constructor
    */
    public Player()
    {   
        this.name = "Tristan"; 
        this.location = 0;
        this.device = new Device(); 
    }

    /**
    * Non-Default Constructor
    * @param location int: Current Location of the Player as Building Index
    * @param device Device(Object): The Device Object with its Parameters to be Initialized
    */
    public Player(String name, int location, Device device)
    {   
        this.name = name;
        this.location = location;
        this.device = device; 
    }
 
    /**
    * Display Method: Display String of the Player Details
    * @return String: Conta                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ining the Player Details
    */
    public String displayPlayer()
    {   
        String tempString = "Player Name: " + this.name + "\nPlayer Building Location: " + (this.location) + "\n" + this.device.displayDevice();
        return tempString;
    }

    /**
     * Accesor Method: Get the Device Object
     * @return Device Object: The Player Device Object
     */
    public Device getDevice()
    {
        return this.device; 
    }
    
    
    /**
     * Accesor Method: Get Location of the Player (Building Index)
     * @return int: Player Location as a Building Index
     */
    public int getLocation()
    {
        return this.location; 
    }
    
    /**
     * Accesor Method: Get Name of the Player
     * @return String: The Name of the Player
     */
    public String getName()
    {
        return this.name; 
    }
    
     /**
     * Custom Method: The Player Jumps to the Building at the Right
     * @param targetBuildingIndex int: The Target Building Index for the next Hop
     * * @param fuelConsumed int: The amount of Fuel Consumed for the Next Hop
     */
    public void jump(int targetBuildingIndex, int fuelConsumed)
    {
        this.location = targetBuildingIndex; 
        this.device.consumeFuelReserves(fuelConsumed);
    }
    
    /**
     * Custom Method: PathFinder is a Method to determine Next Move what the player allowed to make
     * This method will check for the Bounds of the Map (must be between Building 0 to 14)
     * Remarks: Testing out and learning how to use some Lambda Functions 
     * @param gameState String: The State of the Game as a Single String 
     * @return String: A string output of the Possible Actions and corresponding Fuel Cost the player can take
     * @throws NumberFormatException If parsing from String to Integer fails
     * @throws IndexOutOfBoundsException If the state ArrayList index is out of bounds
     * @throws NullPointerException If the state ArrayList is not correctly initialized
     */
    public String pathFinder(String gameState) 
       throws NumberFormatException, IndexOutOfBoundsException, NullPointerException
    {   
        ArrayList<String> states = new ArrayList<>();
        ArrayList<Integer> height = new ArrayList<>(); 
        
        // Remarks: Testing Out Lambda Functions using Streams API | Get Game States and Building Height
        // collect(): Returns the New Collection, it collects elements into a new list, without modiftying external states like a forEach
        states.addAll(Arrays.stream(gameState.split("\n")).map(segment -> segment.split(": ")[1].trim()).collect(Collectors.toList())); 

        height.addAll(Arrays.stream(states.get(7).split(" , ")).map(rawheight -> Integer.parseInt(rawheight.trim())).collect(Collectors.toList())); 

        // Step 2: Get Player Building Height and Validate if the endpoints index (Next Jump) are within bounds | Using Tenary Operators
        int currentBuildingHeight = height.get(this.location); 
        int rightEndpointsIndex = (currentBuildingHeight + this.location < NUMBER_OF_BUILDINGS) ? (currentBuildingHeight + this.location) : NUMBER_OF_BUILDINGS;
        int leftEndpointsIndex = (this.location - currentBuildingHeight >= 0) ? (this.location - currentBuildingHeight) : -1 ; 

        // Step 3: Check for all the Valid Endpoint Actions (Left, Right, Stay) and Append to a StringBuilder
        StringBuilder stringBuilder = new StringBuilder(); 
        int actionNumber = 1; 
        int fuelCost = 0; 

        if (rightEndpointsIndex < 15 && rightEndpointsIndex >= 0)
        {   
            fuelCost = device.calculateFuelConsumption(currentBuildingHeight, height.get(rightEndpointsIndex)); 
            stringBuilder.append(actionNumber + ") " + ACTION_JUMP_RIGHT + "  | Building: " + (rightEndpointsIndex + 1) + " | Fuel Cost: " + fuelCost + " |;"); 
            actionNumber++; 
        } 
        
        if (leftEndpointsIndex < 15 && leftEndpointsIndex >= 0)
        {
            fuelCost = device.calculateFuelConsumption(currentBuildingHeight, height.get(leftEndpointsIndex)); 
            stringBuilder.append(actionNumber + ") " + ACTION_JUMP_LEFT + "   | Building: " + (leftEndpointsIndex + 1) + " | Fuel Cost: " + fuelCost + " |;"); 
            actionNumber++; 
        }
 
        stringBuilder.append(actionNumber + ") " + ACTION_STAY + "        | Building: " + (this.location + 1) + " | Fuel Cost: " + 1 + " |"); 

        return stringBuilder.toString(); 
    }

    /**
     * Custom Method: Player Stays at Current Position for the Turn
     */
    public void stay()
    {
        // Does return anything, player stays at current location
    }

    /**
     * Mutator Method: Set Player's Device Object
     * @param device Device Object: The New Device Object to be set to the Player
     */
    public void setDevice(Device device)
    {
        this.device = device; 
    }

    /**
     * Mutator Method: Set Player Location
     * @param buildingIndex int: The Building Index where to set the Player Location
     */
    public void setLocation(int buildingIndex)
    {
        this.location = buildingIndex;
    }
    
    /**
     * Mutator Method: Set Player Name
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

}
