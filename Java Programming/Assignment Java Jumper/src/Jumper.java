
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
import java.util.Arrays;

public class Jumper 
{
    // Fields
    private static final String DELIMITER = "\r\n"; 
    private static final String BUILDING_FILE = "Assignment Java Jumper\\resources\\building.txt"; 
    private static final String OUTCOME_FILE = "Assignment Java Jumper\\resources\\outcome.txt";
    private static final int FUEL_CELL_LIFETIME = 3;       // The Lifetime (Turns) until the Fuel Cell expires for a Building 
    private static final int REPLENISH_FUEL_AMOUNT = 5; 
    private static final int TRAP_FUEL_PENALTY = 5;
    
    private static boolean runProgram = false; 
    private static boolean winStatus = false; 
    private static int turn = 0;

    private Player player;
    private Map map; 
    private ConsoleDisplay consoleDisplay; 

    /**
     * Default Constructor
     */
    public Jumper()
    {
        this.player = new Player();
        this.map = new Map(); 
        this.consoleDisplay = new ConsoleDisplay(); 
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
    
    public boolean executeActions(Building nextHopBuilding, int nextHopIndex)
    {   
        boolean playerFrozen = false; 
        System.out.println(nextHopBuilding.displayBuilding()); 

        if (this.player.getDevice().getFuelReserves() > 0) 
        {   
            
            if (nextHopBuilding.getPortal() && !nextHopBuilding.getFrozen()) 
            {
                winStatus = true; 
                runProgram = false; 
            } 
            else if (nextHopBuilding.getFrozen()) 
            {
                playerFrozen = true;   // Effects will be handle when Updating Map
            } 
            else if (nextHopBuilding.getWebTrap()) 
            {   
                this.player.getDevice().consumeFuelReserves(TRAP_FUEL_PENALTY);

                if (this.player.getDevice().getFuelReserves() <= 0) 
                {
                    winStatus = false; 
                    runProgram = false; 
                } else 
                {
                    System.out.println("You landed on a Web Trap and Lost 5 Fuel Cells.");
                }
            } 
            else if (nextHopBuilding.getFuelCell()) 
            {   
                // Replenish Fuel Cells
                System.out.println("Fuel Cell Replenished! You gained 5 additional Fuel Cells!");
                this.player.getDevice().replenishFuelReserves(REPLENISH_FUEL_AMOUNT);
            } 
            else
            {
                System.out.println("Jump to Building " + (nextHopIndex + 1 ) + "!");
            }
        } 
        else 
        {   
            
            if (nextHopBuilding.getPortal() && !nextHopBuilding.getFrozen()) 
            {   
                // Edge Case: If Player Lands on the Portal Building When he Exactly Runs out of Fuel
                winStatus = true; 
                runProgram = false; 
            } 
            else if (nextHopBuilding.getFuelCell())  
            {   
                // Edge Case: If Player Lands on the Fuel Cell Building After Running out of Fuel
                System.out.println("Fuel Cell Replenished! You gained 5 additional Fuel Cells!");
                this.player.getDevice().replenishFuelReserves(REPLENISH_FUEL_AMOUNT);
            } 
            else
            {
                // Game Lost: No Fuel Cells Left
                winStatus = false; 
                runProgram = false; 
            }
        }
        
        return playerFrozen; 
    }

    /** 
     * Description: Initializes the Game Map from Map Data 
     * Should the Program Fail to Read or Parse the Map Data, it will automatically generate a New Map by 
     * Calling the Map Method 
     */
    public void initializeGame() 
    {   
        this.player.setLocation(0);
        winStatus = false; 
        
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
                buidlingData[j].setPortal(true); 
            }
            else if (Boolean.parseBoolean(data[2].toLowerCase()))
            {
                map.setSpecificFuelCellBuilding(j, FUEL_CELL_LIFETIME);
                buidlingData[j].setFuelCell(true);
            }
            else if (Boolean.parseBoolean(data[3].toLowerCase()))
            {
                map.setWebTrapBuilding(j);
                buidlingData[j].setWebTrap(true);
            }
            
            if (Boolean.parseBoolean(data[4].toLowerCase())) 
            {
                map.setFrozenBuilding(j);
                buidlingData[j].setFrozen(true);
            }
        }
        return buidlingData; 
    }

    /**
     * Custom Method: The Game Runs until the Player Wins or Ends the Game
     * The Main Processes Run inside this Method 
     * @param console Scanner: Pass a Scanner Object Resource from the Method Caller
     */
    public void startGame(Scanner console)
    {   
        initializeGame();

        while(runProgram)
        {   
            String gameStatePacket = this.player.displayPlayer() + this.map.displayMap();
            consoleDisplay.printMap(gameStatePacket, turn); 
    
            String possibleActions = this.player.pathFinder(gameStatePacket);
            consoleDisplay.printPlayerActions(possibleActions);  

            processTurn(console, possibleActions);
        }
    }

    public void processTurn(Scanner console, String possibleActions)
    {
        ArrayList<Character> actionsNumber = new ArrayList<>(); 
        ArrayList<String> actionsList = new ArrayList<>();
        ArrayList<Integer> buildingIndexes = new ArrayList<>();
        ArrayList<Integer> fuelCosts = new ArrayList<>();
        String[] segment = possibleActions.split(";");
        
        // Section is responsible for Parsing the Game State Data 
        for (int i = 0; i < segment.length; i++) 
        {
            String[] parts = segment[i].trim().split("\\)");
            if (parts.length > 0) 
            {
                actionsNumber.add(parts[0].charAt(0));  // Extact the Action Number 
                String actionDetails = parts[1].trim();
                String[] details = actionDetails.split("\\|");
                if (details.length == 3) // Extract the Action Details 
                {
                    String action = details[0].trim();
                    int buildingIndex = Integer.parseInt(details[1].split(":")[1].trim());
                    int fuelCost = Integer.parseInt(details[2].split(":")[1].trim());
                    
                    actionsList.add(action);
                    buildingIndexes.add(buildingIndex);
                    fuelCosts.add(fuelCost);
                }
            }
        }
        
        String selectedAction = promptUserInput(console, actionsNumber); 

        if (selectedAction == "End Game")
        {   
            this.consoleDisplay.printGameOverMessage(winStatus, turn, this.player.getDevice().getFuelReserves());
            runProgram = false;
        }
        else 
        {
            int selectedActionIndex = Integer.parseInt(selectedAction) - 1; 

            Building nextHopBuilding = this.map.getBuildings()[buildingIndexes.get(selectedActionIndex) - 1];

            String nextAction = actionsList.get(selectedActionIndex); 
            int nextHopBuildingIndex = buildingIndexes.get(selectedActionIndex) - 1;  // 0-based Indexing
            int nextHopFuelCost = fuelCosts.get(selectedActionIndex); 
            
            int nextHopBuildingHeight = nextHopBuilding.getHeight(); 
            int playerBuildingHeight = this.map.getBuildings()[this.player.getLocation()].getHeight(); 
    
            System.out.print("< Action Selected: " + nextAction + " > < Next Hop: Building " + nextHopBuildingIndex  +  " > < ");
            System.out.println("Fuel Cost Calculations: | " + playerBuildingHeight + " - " + nextHopBuildingHeight + " | + 1 = "+ nextHopFuelCost + " >");
            
            this.player.jump(nextHopBuildingIndex, nextHopFuelCost);
            boolean playerFrozen = executeActions(nextHopBuilding, nextHopBuildingIndex); 
    
            updateGameTurn(console, playerFrozen); 
        }
    }

    /**
     * Custom Method: Prompts the User for Input for the Java Jumper Game Controls
     * @param console Scanner: Pass a Scanner Object Resource from the Method Caller
     * @param actionsNumber ArrayList<Character>: An Array List of Possible Actions "Index" the Player can Make Next Turn 
     * @return String: A String Containing the Action the Player has selected
     */
    public String promptUserInput(Scanner console, ArrayList<Character> actionsNumber) {
        Input input = new Input(console);
        boolean validInputFlag = false;
        String stringInput = "";
    
        while (!validInputFlag) {
            stringInput = "";
            try {
                // Assuming acceptCharInput now accepts a String input instead of a char
                stringInput = input.acceptStringInput("Please Input Options:");
    
                // Validate user input against the list of actions
                for (int i = 0; i < actionsNumber.size(); i++) 
                {
                    String validAction = String.valueOf(actionsNumber.get(i));
                    if (stringInput.equals(validAction)) 
                    {
                        validInputFlag = true;
                        return stringInput; // Return the valid action
                    }
                }
    
                // Handle special cases like 'E' and 'R'
                if (stringInput.equals("E"))  // End Game
                {
                    String endGameInput = input.acceptStringInput("Enter 'Yes' to End Game or Press any other Key to Resume Game...");
                    if (endGameInput.toLowerCase().equals("yes"))
                    {   
                        stringInput = "End Game"; 
                        validInputFlag = true; 
                    }
                } 
                else if (stringInput.equals("R"))  // Print Rules
                {
                    consoleDisplay.printRules();
                }
    
                // Message to prompt for valid numeric input
                System.out.println(validInputFlag ? "" : "Please Select at least 1 of the Numeric Actions to continue...");
            } 
            catch (Exception e) 
            {
                System.out.println("Please Select at least 1 of the given Actions...");
            }
        }
        return stringInput;
    }
    
    /**
     * Custom Method: Update the Game Turn and Map | Also Applies the Frozen Affect
     * @param console Scanner: Pass a Scanner Object Resource from the Method Caller
     * @param frozen boolean: if the player has been frozen after jumping
     */
    public void updateGameTurn(Scanner console, boolean frozen)
    {   
        if (frozen) 
        {
            boolean playerAcknowledge = false;
            while (!playerAcknowledge) 
            {
                System.out.println("You are Frozen! To thaw yourself out, you will have to skip 2 turns...");
                System.out.println("For brevity, the Dimension has shifted twice... Press Enter to continue...");
                console.nextLine(); 
                playerAcknowledge = true; // Exit the loop after player acknowledges
            }
            turn += 2; 
            map.reshuffleMap(this.player.getLocation());
        } 
        else 
        {
            turn++; 
            map.reshuffleMap(this.player.getLocation());
        }
    }

    /**
     * Main Method: This is the Main Method of the Jumper Class & the Java Jumper Assignment
     */
    public static void main(String[] args) throws IOException
    {   
        // Fields
        Scanner console = new Scanner(System.in); 
        Jumper javaJumper = new Jumper(); 

        runProgram = true; 
        turn = 1; 

        javaJumper.createPlayer(console);

        while (runProgram)
        {   
            System.out.println("Start Game? [Y/N]");
            String userInput = console.nextLine(); 
            if (userInput.toUpperCase().equals("Y"))
            {
                javaJumper.startGame(console);
            }
            else if (userInput.toUpperCase().equals("N"))
            {
                runProgram = false;
                System.out.println("Game Terminated...");
            }
        }
          
        javaJumper.consoleDisplay.printGameOverMessage(winStatus, turn, javaJumper.player.getDevice().getFuelReserves());
        // Need to Write Score to Outcome File

        console.close();
    }

}