
/**
 * Description: Jumper Class is the Main Program for the Jave Jumper Assignment.
 * The Jumper Class is where the Main method runs and where the Player will interact with the Game.
 * @author Tristan Sim
 * @version 1.01
 * @
 */

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner; 
import java.util.ArrayList;

public class Jumper 
{
    // Fields
    private static final String DELIMITER = "\r\n"; 
    private static final String BUILDING_FILE = "Assignment Java Jumper\\resources\\building.txt"; 
    private static final String OUTCOME_FILE = "Assignment Java Jumper\\resources\\outcome.txt";
    private static final int FUEL_CELL_LIFETIME = 3;         // The Lifetime (Turns) until the Fuel Cell expires for a Building 

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
    
    /**
     * Non-Default Constructor
     * @param player Player: Player Object containing the Player Action Methods & Device Objects
     * @param map Map: Map Object containing all the map Attributes like Buildings 
     * @param consoleDisplay ConsoleDisplay: The Console Display Object responsible for Generating the Text Maps & etc
     * @param turn int: The Turn the Player is currently on
     */
    public Jumper(Player player, Map map, ConsoleDisplay consoleDisplay, int turn)
    {
        this.player = player;
        this.map = map; 
        this.consoleDisplay = consoleDisplay; 
        this.turn = turn; 
    }
    
    /**
     * Custom Method: Displays the Welcome Message & Rules. Also Prompts User to Input their Name
     * @param console Scanner: Scanner Object passed from the Caller to Get User input
     */
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
     * Description: Initializes the Game Map from Map Data 
     * Should the Program Fail to Read or Parse the Map Data, it will automatically generate a New Map
     */
    public void initializeGame() 
    {   
        this.player.setLocation(0);

        try
        {
        Building[] buildingData = loadExistingMap(); 
        this.map.setBuildings(buildingData);
        } 
        catch (Exception e)
        {
            System.out.println("Dimension Critically Unstable: Failed to Read Building Data from Source File...");
            System.out.println("Generating New Dimension...");
            this.map.initializeMap(this.player.getLocation());
        }
     
    }

    /**
     * Custom Method: Loads an Existing Map Data from a Text File
     * @return Building[]: Returns a Building Array parsed from the Data File
     * @throws Exception From Reading the Data File or Parsing the Data within the File
     */
    public Building[] loadExistingMap() throws IOException
    {   
        String[] rawData = new String[15]; 
        Building[] buidlingData = new Building[15];
        FileIO buildingFile = new FileIO(BUILDING_FILE); 
        String contents = buildingFile.readFile(); 
        int i = 0; 

        for (String content : contents.split(DELIMITER))
        {
            rawData[i] = content; 
            i++; 
        }

        for (int j = 0; j < buidlingData.length; j++)
        {
            String[] data = rawData[j].split(",");    
            buidlingData[j] = new Building();   // Initialize to prevent Null Values
            buidlingData[j].setHeight(Integer.parseInt(data[0]));        

            if (Boolean.parseBoolean(data[1].toLowerCase())) 
            {
                map.setPortalBuilding(j);
            }
            else if (Boolean.parseBoolean(data[2].toLowerCase()))
            {
                map.setSpecificFuelCellBuilding(j, FUEL_CELL_LIFETIME);
            }
            else if (Boolean.parseBoolean(data[3].toLowerCase()))
            {
                map.setWebTrapBuilding(j);
            }
            
            if (Boolean.parseBoolean(data[4].toLowerCase())) 
            {
                map.setFrozenBuilding(j);
            }
        }
        return buidlingData; 
    }

    /**
     * Main Method: This is the Main Method of the Jumper Class & the Java Jumper Assignment
     */
    public static void main(String[] args) throws IOException
    {   
        // Fields
        Scanner console = new Scanner(System.in); 
        Jumper javaJumper = new Jumper(); 
        boolean terminateGame = false; 

        javaJumper.createPlayer(console);
        
        while (!terminateGame)
        {
            System.out.println("Start Game? [Y/N]");
            String userInput = console.nextLine(); 
            if (userInput.toUpperCase().equals("Y"))
            {
                javaJumper.initializeGame();
                
                String gameStatePacket = javaJumper.player.displayPlayer() + javaJumper.map.displayMap();

                String possibleActions = (javaJumper.player.pathFinder(gameStatePacket));
                javaJumper.consoleDisplay.printMap(gameStatePacket, javaJumper.turn); 
                javaJumper.consoleDisplay.printPlayerActions(possibleActions);  
                System.out.println(gameStatePacket);

            }
            else if (userInput.toUpperCase().equals("N"))
            {
                terminateGame = true;
                System.out.println("Game Terminated...");
            }
        }


        // Players Next Move

        // Input Player Moves

        console.close();
    }

}