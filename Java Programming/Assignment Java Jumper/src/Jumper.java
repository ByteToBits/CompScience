
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
            System.out.println(this.map.displayMap());
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

    
    public void startGame(Scanner console)
    {   
        boolean terminateGame = false; 
        createPlayer(console);

        while (!terminateGame)
        {
            System.out.println("Start Game? [Y/N]");
            String userInput = console.nextLine(); 
            if (userInput.toUpperCase().equals("Y"))
            {
                initializeGame();
                
                String gameStatePacket = player.displayPlayer() + map.displayMap();
                consoleDisplay.printMap(gameStatePacket, turn); 

                String possibleActions = player.pathFinder(gameStatePacket);
                consoleDisplay.printPlayerActions(possibleActions);  

                processTurn(console, possibleActions);
            }
            else if (userInput.toUpperCase().equals("N"))
            {
                terminateGame = true;
                System.out.println("Game Terminated...");
            }
        }
    }

    public void processTurn(Scanner console, String possibleActions)
    {
        ArrayList<Character> actionsNumber = new ArrayList<>(); 
        ArrayList<String> actionsList = new ArrayList<>();
        ArrayList<Integer> buildingIndexes = new ArrayList<>();
        ArrayList<Integer> fuelCosts = new ArrayList<>();
        String[] segment = possibleActions.split(";");

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
        int convertedActionIndex = Integer.parseInt(selectedAction) - 1; 
        
        // Get the Building the player will next jump to and get the Effects & Need a mechanic to apply effect next turn!
        // System.out.println("Seleced Actions: " + actionsList.get(convertedActionIndex));
        // System.out.println("Building Index: " + buildingIndices.get(convertedActionIndex));
        // System.out.println("Fuel Costs: " + fuelCosts.get(convertedActionIndex));
        
        // Get Next Turn Effect; (Portal, Trap, Web, Fuel Cell)
        Building nextBuilding = this.map.getBuildings()[buildingIndexes.get(convertedActionIndex)]; 

        System.out.println("Building: " + buildingIndexes.get(convertedActionIndex));
        System.out.println(this.map.getBuildings()[buildingIndexes.get(convertedActionIndex) - 1 ].displayBuilding());
        System.out.println("Building 2: " + this.map.getBuildings()[1].displayBuilding());
        System.out.println("Building 3: " + this.map.getBuildings()[2].displayBuilding());
        System.out.println("Building 4: " + this.map.getBuildings()[3].displayBuilding());
        System.out.println("Building 5: " + this.map.getBuildings()[4].displayBuilding());
        System.out.println("Building 6: " + this.map.getBuildings()[5].displayBuilding());
        System.out.println("Building 7: " + this.map.getBuildings()[6].displayBuilding());
        System.out.println("Building 8: " + this.map.getBuildings()[7].displayBuilding());
        System.out.println("Building 9: " + this.map.getBuildings()[8].displayBuilding());
        System.out.println("Building 10: " + this.map.getBuildings()[9].displayBuilding());
        System.out.println("Building 11: " + this.map.getBuildings()[10].displayBuilding());
        System.out.println("Building 12: " + this.map.getBuildings()[11].displayBuilding());
        System.out.println("Building 13: " + this.map.getBuildings()[12].displayBuilding());
        System.out.println("Building 14: " + this.map.getBuildings()[13].displayBuilding());
        System.out.println("Building 15: " + this.map.getBuildings()[14].displayBuilding());
        System.out.println(this.map.displayMap());
        

        // Update Player Fuel Cost & Location | If Player has not enough Fuel, Game Lost if Portal == True && Frozen != True; Win
        // Increment Turn 
        // Update Turn & Map
        // Apply Next Turn Effect;

    }
    
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
                for (int i = 0; i < actionsNumber.size(); i++) {
                    String validAction = String.valueOf(actionsNumber.get(i));
                    if (stringInput.equals(validAction)) {
                        validInputFlag = true;
                        return stringInput; // Return the valid action
                    }
                }
    
                // Handle special cases like 'S' and 'R'
                if (stringInput.equalsIgnoreCase("S")) {
                    System.out.println("Setting Page Selected");
                } else if (stringInput.equalsIgnoreCase("R")) {
                    System.out.println("Rules Page Selected");
                }
    
                // Message to prompt for valid numeric input
                System.out.println(validInputFlag ? "" : "Please Select at least 1 of the Numeric Actions to continue...");
            } catch (Exception e) {
                System.out.println("Please Select at least 1 of the given Actions...");
            }
        }
        return stringInput;
    }
    


    /**
     * Main Method: This is the Main Method of the Jumper Class & the Java Jumper Assignment
     */
    public static void main(String[] args) throws IOException
    {   
        // Fields
        Scanner console = new Scanner(System.in); 
        Jumper javaJumper = new Jumper(); 

        javaJumper.startGame(console);

        // Players Next Move

        // Input Player Moves

        console.close();
    }

}