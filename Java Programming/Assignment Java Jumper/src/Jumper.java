/**
 * Description: Jumper Class is the Main Program for the Jave Jumper Assignment.
 * The Jumper Class is where the Main Method runs and where the Player will interact with the Game.
 * It initializes the game resources and orchestrates the sub-processes for the game. 
 * @author Tristan Sim
 * @version 1.01
 */

import java.io.IOException;
import java.util.Scanner; 
import java.util.ArrayList;
import java.util.List;
public class Jumper 
{
    // Fields
    private static final String DELIMITER = "\r\n"; 
    private static final String BUILDING_FILE = "Assignment Java Jumper\\resources\\buildings.txt"; 
    private static final String RESULTS_FILE = "Assignment Java Jumper\\resources\\results.txt";
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
    
    /**
     * Custom Method: Execute Actions will carry out the actions the player has selected
     * It applies the Effects in the following Turn after the Map has been updated
     * @param nextHopBuilding int: The Building Object the Player will jump to
     * @param nextHopIndex int: The Index of the Building the Player has selected to Jump to
     * @return boolean: Indicating whether the Player has been Frozen by jumping to a Frozen Building
     */
    public boolean executeActions(Building nextHopBuilding, int nextHopIndex)
    {   
        boolean playerFrozen = false; 

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
                    System.out.println("\nYou Landed on a Web Trap! The NoWhere Police have depleted your Fuel Cells!");
                    winStatus = false; 
                    runProgram = false; 
                } else 
                {
                    System.out.println("\nYou landed on a Web Trap! You Lost 5 Fuel Cells to Break Free...");
                }
            } 
            else if (nextHopBuilding.getFuelCell()) 
            {   
                // Replenish Fuel Cells
                System.out.println("\nFuel Cell Replenished! You gained 5 additional Fuel Cells!");
                int fuelReserves = this.player.getDevice().getFuelReserves();
                System.out.print("Fuel Replenishment Calculation: " + fuelReserves + " + " + REPLENISH_FUEL_AMOUNT + " = ");
                this.player.getDevice().replenishFuelReserves(REPLENISH_FUEL_AMOUNT);
                System.out.print(this.player.getDevice().getFuelReserves() + "\n");
                this.map.removeFuelCell(nextHopIndex);
            } 
            else
            {
                System.out.println("\nSafely Jump to Building " + (nextHopIndex + 1 ) + "!");
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
                int fuelReserves = this.player.getDevice().getFuelReserves();
                System.out.print("Fuel Replenishment Calculation: " + fuelReserves + " + " + REPLENISH_FUEL_AMOUNT + " = ");
                this.player.getDevice().replenishFuelReserves(REPLENISH_FUEL_AMOUNT);
                System.out.print(this.player.getDevice().getFuelReserves() + "\n");
                this.map.removeFuelCell(nextHopIndex);
            } 
            else
            {
                // Game Lost: No Fuel Cells Left
                System.out.println("\nYou have used up all your Fuel Cells!!\n");
                winStatus = false; 
                runProgram = false; 
            }
        }

        return playerFrozen; 
    }

    /** 
     * Description: Initializes the Game Map from Map Data 
     * Should the Program Fail to Read or Parse the Map Data, it will automatically generate a New Map by 
     * Calling the Map Method to generate a Random Map
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
        String[] dataPacket = new String[15]; 
        Building[] buidlingData = new Building[15];

        FileIO buildingFile = new FileIO(BUILDING_FILE); 
        String contents = buildingFile.readFile(); 

        String[] packets = contents.split(DELIMITER);
        for (int i = 0; i < packets.length && i < dataPacket.length; i++) 
        {
            dataPacket[i] = packets[i];
        }

        for (int j = 0; j < buidlingData.length; j++)
        {
            String[] data = dataPacket[j].split(",");    
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
     * The Game Processes Run inside this Method 
     * @param console Scanner: Pass a Scanner Object Resource from the Method Caller
     */
    public void startGame(Scanner console)
    {   
        initializeGame();

        while(runProgram)
        {   
            String gameStatePacket = this.player.displayPlayer() + this.map.displayMap();
            // System.out.println(gameStatePacket);
            consoleDisplay.printMap(gameStatePacket, turn); 
    
            String possibleActions = this.player.pathFinder(gameStatePacket);
            consoleDisplay.printPlayerActions(possibleActions);  

            processTurn(console, possibleActions);
        }
    }
    
    /**
     * Custom Method: Handles the Processing of Each Turns; 
     * Generates the Possible Action Players can Make, Takes the User Inputs 
     * and Updates the Next Turn based on the Player Selected Option
     * @param console Scanner: Pass a Scanner Object Resource from the Method Caller
     * @param possibleActions String: A String of Possible Valid Actions the Player can Make for the Next Turn
     */
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
            runProgram = false;
        }
        else 
        {
            int selectedActionIndex = Integer.parseInt(selectedAction) - 1;
            
            // Process the Selected Action & Update the Game Turn
            boolean playerFrozen = processAction(selectedActionIndex, buildingIndexes, actionsList, fuelCosts);  
            updateGameTurn(console, playerFrozen); 
        }
    }
    
    /**
     * Custom Method: Process Action selected by the User like Jump, Load Rules or Endgame & Runs the Execute Ation Method 
     * @param actionIndex int: The Index of the Action Selected by the User
     * @param buildingIndexes List<Integer>: The Index of the Buidlings that the Player can jump to
     * @param actions List<String>: The Actions the Player can Make
     * @param fuelCosts List<Integer>: The Fuel Cost for each Action
     * @return boolean: Player Frozen | Skip 1 Turn
     */
    public boolean processAction(int actionIndex, List<Integer> buildingIndex, List<String> actions, List<Integer> cost) 
    {
        // Calculate the next building and action details based on selected action index.
        int nextHopIndex = buildingIndex.get(actionIndex) - 1; // 0-based Indexing
        Building nextHopBuilding = this.map.getBuildings()[nextHopIndex];
        String nextAction = actions.get(actionIndex);
        int nextHopCost = cost.get(actionIndex);

        // Retrieve building heights.
        int playerBuildingHeight = this.map.getBuildings()[this.player.getLocation()].getHeight();
        int nextHopHeight = nextHopBuilding.getHeight();

        System.out.printf("< Action Selected: %s > < Next Hop: Building %d > < Fuel Cost Calculations: | %d - %d | + 1 = %d >%n",
                          nextAction, nextHopIndex + 1, playerBuildingHeight, nextHopHeight, nextHopCost);

        // Perform the jump and execute post-jump actions.
        this.player.jump(nextHopIndex, nextHopCost);

        // Execute the Player's Actions
        boolean playerFrozen = executeActions(nextHopBuilding, nextHopIndex); 

        return playerFrozen;
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
    
        while (!validInputFlag) 
        {
            stringInput = "";
            try 
            {
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
                    String message = "Enter 'Yes' to End Game or Press any other Key to Resume Game..."; 
                    String endGameInput = input.acceptStringInput(message);
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
                String message = "Please Select at least 1 of the Numeric Actions to continue..."; 
                System.out.println(validInputFlag ? "" : message);
            } 
            catch (Exception e) 
            {
                System.out.println("Please Select at least 1 of the given Actions...");
            }
        }
        return stringInput;
    }
    
    /** 
     * Custom Method: Parse Winning Data and returns a Single Line String for Text File
     * @param winStatus Boolean: Win Status of the Game
     * @param turn Int: The Number of Turns the Player has made
     * @param remainingFuel Int: The Amount of Fuel the Plaeyer has before the game ends
     * @return String: The parse winning results as a single line String
     */
    public String parseDataToFile(boolean winStatus, int turn, int remainingFuel)
    {
       StringBuilder stringBuilder = new StringBuilder(); 
       
       stringBuilder.append("Player Name: ").append(this.player.getName()).append(DELIMITER);
       stringBuilder.append("Win Status: ").append(winStatus).append(DELIMITER);
       stringBuilder.append("Number of Turns Made: ").append(turn).append(DELIMITER);
       stringBuilder.append("Fuel Cells Remaining: ").append(remainingFuel).append(DELIMITER);

        return stringBuilder.toString(); 
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
            this.map.decrementFuelCellTurnsRemaining();
            this.map.reshuffleMap(this.player.getLocation());
        } 
        else 
        {
            turn++; 
            this.map.reshuffleMap(this.player.getLocation());
        }
    }

    /**
     * Main Method: This is the Main Method of the Jumper Class & the Java Jumper Assignment
     */
    public static void main(String[] args) throws IOException
    {   
        // Instatiates the classes
        Scanner console = new Scanner(System.in); 
        Jumper javaJumper = new Jumper(); 
        FileIO resultsFile = new FileIO(RESULTS_FILE); 
        
        // Initialize Static Variables
        runProgram = true; 
        turn = 1; 
          
        // Run the Main Game Program
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
        
        // Output Game Over Message
        int remainingFuel = javaJumper.player.getDevice().getFuelReserves(); 
        javaJumper.consoleDisplay.printGameOverMessage(winStatus, turn, remainingFuel);

        // Write Game Results to Output File
        String gameResults = javaJumper.parseDataToFile(winStatus, turn, remainingFuel);
        resultsFile.writeFile(gameResults);

        console.close();
    }

}