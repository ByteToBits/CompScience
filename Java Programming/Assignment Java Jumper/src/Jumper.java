
/**
 * Description: Jumper Class is the Main Program for the Jave Jumper Assignment.
 * The Jumper Class is where the Main method runs and where the Player will interact with the Game.
 * @author Tristan Sim
 * @version 1.01
 * @
 */

import java.util.Scanner; 

public class Jumper 
{
    // Fields
    private Player player;
    private Map map; 
    private ConsoleDisplay consoleDisplay; 
    private int turn;

    /**
     * Default Constructor
     */
    public Jumper()
    {
        this.player = new Player();
        this.map = new Map(); 
        this.consoleDisplay = new ConsoleDisplay(); 
        this.turn = 0; 
    }

    public Jumper(Player player, Map map, ConsoleDisplay consoleDisplay, int turn)
    {
        this.player = player;
        this.map = map; 
        this.consoleDisplay = consoleDisplay; 
        this.turn = turn; 
    }

    public void createPlayer(Scanner console)
    {   
        // Initialize the Class Dependencies
        Input input = new Input(console); 
        Validation validation = new Validation(); 
        boolean invalidInputFlag = true; 

        this.consoleDisplay.printWelcomeMessage(); 
        String message = "Our Agents need to know who they will be rescuing at the Portal, Please Enter Your Name (3-13 characters): ";
        
        while (invalidInputFlag)
        {
            try {
                String playerNameInput = input.acceptStringInput(message); 
                if (!validation.isBlank(playerNameInput) && validation.lengthWithinRange(playerNameInput, 3, 13))
                {
                    this.player.setName(playerNameInput);
                    System.out.println("Recevied " + this.player.getName() + ", Over! We will let the Extraction Team know... "); 
                    System.out.println("One last thing, here is a run-through the NoWhere Land's Dimensional Rules. Best of Luck " + this.player.getName() + "!"); 
                    this.consoleDisplay.printRules(); 
                    invalidInputFlag = false; 
                } 
                else 
                {
                    message = "Player Name must be within 3-13 Characters Long! Please Enter Name Again!";
                }
            } 
            catch (Exception e)
            {
                System.out.println(e.getMessage()); 
            }
        }        
       
    }

    /**
     * Main Method: This is the Main Method of the Jumper Class & the Java Jumper Assignment
     */
    public static void main(String[] args)
    {   
        // Fields
        Scanner console = new Scanner(System.in); 
        Jumper javaJumper = new Jumper(); 

        javaJumper.player.setLocation(0);
        javaJumper.map.initializeMap(javaJumper.player.getLocation());

        javaJumper.createPlayer(console);

        String gameStatePacket = javaJumper.player.displayPlayer() + javaJumper.map.displayMap();

        String possibleActions = (javaJumper.player.pathFinder(gameStatePacket));
        javaJumper.consoleDisplay.printMap(gameStatePacket, "John", 5); 
        javaJumper.consoleDisplay.printPlayerActions(possibleActions);  
        System.out.println(gameStatePacket);

        // Players Next Move

        // Input Player Moves

        console.close();
    }

}