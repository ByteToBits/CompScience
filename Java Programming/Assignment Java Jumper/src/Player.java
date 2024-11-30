
public class Player 
{
    // Fields
    private int location;
    private Device device; 

    /**
    * Default Constructor
    */
    public Player()
    {
        this.location = 0;
        this.device = new Device(); 
    }

    /**
    * Non-Default Constructor
    * @param location int: Current Location of the Player as Building Index
    * @param device Device(Object): The Device Object with its Parameters to be Initialized
    */
    public Player(int location, Device device)
    {
        this.location = location;
        this.device = device; 
    }
 
    /**
    * Display Method: Display String of the Player Details
    * @return String: Containing the Player Details
    */
    public String displayPlayer()
    {   
        String tempString = "Player Location: " + this.location + "\n" + this.device.displayDevice();
        return tempString;
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
     * Accesor Method: Get the Device Object
     * @return Device Object: The Player Device Object
     */
    public Device getDevice()
    {
        return this.device; 
    }
    
    /**
     * Custom Method: The Player Jumps to the Building at the Left
     * @param currentBuildingIndex int: The Current Building Index the Player is currently on
     * @param targetBuildingIndex int: The Target Building Index for the next Hop
     */
    public void jumpLeft(int currentBuildingIndex, int targetBuildingIndex)
    {   

        this.location = targetBuildingIndex; 
        this.device.consumeFuelReserves(1);
    }

     /**
     * Custom Method: The Player Jumps to the Building at the Right
     * @param currentBuildingIndex int: The Current Building Index the Player is currently on
     * @param targetBuildingIndex int: The Target Building Index for the next Hop
     */
    public void jumpRight(int currentBuildingIndex, int targetBuildingIndex)
    {
        this.location = targetBuildingIndex; 
    }
    
    /**
     * Custom Method: Player Stays at Current Position for the Turn
     */
    public void stay()
    {
        // Does return anything, player stays at current location
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
     * Mutator Method: Set Player's Device Object
     * @param device Device Object: The New Device Object to be set to the Player
     */
    public void setDevice(Device device)
    {
        this.device = device; 
    }

    public static void main(String[] args)
    {   
        Player player1 = new Player();
        System.out.println(player1.displayPlayer()); 
    }

}
