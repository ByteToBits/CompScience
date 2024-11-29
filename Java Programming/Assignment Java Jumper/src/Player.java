
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
        return String.format("Player Location: " + this.location + "\nDevice Information: \n" + this.device.displayDevice());
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
     * @return Device Object
     */
    public Device getDevice()
    {
        return this.device; 
    }
    
    /**
     * Custom Method: Move the Player Left
     * @param currentBuilding int: The Current Building Index the Player is currently on
     * @param targetBuilding int: The Target Building Index for the next Hop
     */
    public void moveLeft(int currentBuilding, int targetBuilding)
    {
        this.location = currentBuilding + targetBuilding; 
    }

    public static void main(String[] args)
    {   
        Player player1 = new Player();
        System.out.println(player1.displayPlayer()); 
    }

}
