
/**
 * Description: The Console Display Class Generates Text Output of the Jave Jumper Game on the Consoel Terminal
 * @author Tristan Sim
 * @version 1.01
 */

import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Collectors; 

public class ConsoleDisplay
{
    /**
     * Default Constructor
     */
    public ConsoleDisplay() {}
    
    /**
     * Custom Method: Generates a Single Line String representing one of the 5 building levels.
     * Example Output: "Level 5   [  ] [  ] [FC] [##] [  ] ..."
     * @param state String: The overall game state (e.g., "Player Location: 0, Index of Portal: 2")
     * @param mapLevel int: The building level to generate
     * @return String: A formatted string representing the specified building level
     * @throws NumberFormatException If parsing from String to Integer fails
     * @throws IndexOutOfBoundsException If the state ArrayList index is out of bounds
     * @throws NullPointerException If the state ArrayList is not correctly initialized
     */
    public String createLevel(ArrayList<String> state, int mapLevel) 
        throws NumberFormatException, IndexOutOfBoundsException, NullPointerException
    {   
        StringBuffer stringBuffer = new StringBuffer(); 
        ArrayList<Integer> height = new ArrayList<>();
        ArrayList<Integer> fuelCellBuildings = new ArrayList<>();
        
        // Check if it's the Additional Level 6  where only Player & Portal will spawn
        if (mapLevel > 5) { 
            stringBuffer.append("|               "); 
        } else {
            stringBuffer.append("|  Level " + mapLevel + ":     "); 
        }

        // Get the Height of the Buildings 
        for (String rawHeight : state.get(7).split(" , "))
        {
            height.add(Integer.parseInt(rawHeight.trim())); 
        }

        // Get Buildings with the Fuel Cells 
        for (String buildings : state.get(6).split(" , "))
        {
            fuelCellBuildings.add(Integer.parseInt(buildings)); 
        }
        
        // Logic for Printing the Buildings on the console Display
        for (int i = 0; i < 15; i++)
        {   
            stringBuffer.append(" ");
            if (mapLevel == height.get(i))
            {
                if (i == Integer.parseInt(state.get(4)))  // Print Frozen Floor
                {  
                    stringBuffer.append("[FF]");   
                } 
                else if (i == Integer.parseInt(state.get(5)))  // Print Web Trap Floor
                {
                    stringBuffer.append("[##]");   
                } 
                else  // Check if it's Building with Fuel Cell, if False print Normal Building
                {   
                    boolean fuelCellFlag = false; 
                    for (int j = 0; j < fuelCellBuildings.size(); j ++) 
                    {
                        if (i == fuelCellBuildings.get(j)) 
                        {
                            fuelCellFlag = true; 
                            stringBuffer.append("[FC]");  
                        }
                    }

                    if (!fuelCellFlag) 
                    {
                        stringBuffer.append("[  ]");   
                    }
                }              
            } 
            else 
            {   
                if (mapLevel == (height.get(i) + 1) || (mapLevel == 6 && height.get(i) == 5) ) // Print Player & Portal
                {
                    if (i == Integer.parseInt(state.get(1)))
                    {
                        stringBuffer.append("<P1>");
                    } 
                    else if ((i == Integer.parseInt(state.get(3)))) 
                    {
                        stringBuffer.append("-{}-");
                    } 
                    else 
                    {
                        stringBuffer.append("    ");
                    }
                } 
                else if (mapLevel < height.get(i)) // Print the Normal Floors
                {
                    stringBuffer.append("[  ]");
                }
                else 
                {
                    stringBuffer.append("    ");
                }
            }
        }

        stringBuffer.append("                  |\n");
        return stringBuffer.toString(); 
    }
    
    /**
     * Custom Method: Generates the Game Over Message
     * @param win boolean: if the Player Won or Loss the Game
     * @param turns int: The Number of turns the Player made in the game
     * @param fuelCells int: The remaining Fuel Cells Left in the Player's Device
     */
    public void printGameOverMessage(boolean win, int turns, int fuelCells)
    {   
        String message; 
        if (win) 
        {
            message = 
            "------------------------------------------------------------------------------------------------------------------------------------------------\n" + //
            "                                                  < Congratulations! You've Won! >                                                              \n" + //
            "                                    < You've Escaped in " + turns + " Turns with " + fuelCells + " Fuel Cells Remaining! >                                                \n" + //
            "------------------------------------------------------------------------------------------------------------------------------------------------\n";        
        }
        else 
        {   
            message = 
            "------------------------------------------------------------------------------------------------------------------------------------------------\n" + //
            "                                                     < Game Over! You've Lost! >                                                                \n" + //
            "                           < You're trapped in Nowhere Land! You made  " + turns + " Turns with " + fuelCells + " Fuel Cells Remaining! >                                  \n" + //
            "------------------------------------------------------------------------------------------------------------------------------------------------\n";  
        }
        System.out.println(message);
    }
    /**
     * Custom Method: Print the Jave Jumper Map onto the Console
     * This Method Generates the Map using a String Data containing the Player & Building States
     * It will also use System.out.println to print the Map onto the Console
     * @param gameStates String: The States of the Building and Player Object parsed as a String 
     * @param turn int: The current Turn of the Game
     */
    public void printMap(String gameStates, int turn) 
      throws NumberFormatException, IndexOutOfBoundsException, NullPointerException
    {   
        ArrayList<String> map = new ArrayList<String>();
        ArrayList<String> state = new ArrayList<String>(); 

        for (String  segment : gameStates.split("\n"))
        {   
            String[] tempString = segment.split(": ");
            state.add(tempString[1].trim());   
        }

        // Each Line is 110 Characters Long & Use "paddings" in string format to keep the second line consistently 110 Characters Long
        map.add("\n-------------------------------------------- < NoWhere Land > ------------------------------------------------\n"); 
        map.add(String.format("%-109s|\n",String.format("| Player: %-13s", state.get(0)))); 
        map.add(String.format("%-109s|\n", String.format("| Turn %2d  |  Fuel Reserves: %-10s", turn, state.get(2)))); 
        map.add( "|                                                                                                            |\n"); 
        
        for (int i = 5; i > 0; i--) 
        {
            if (i == 5) 
            {
                map.add(createLevel(state, i + 1));   // Account for 1 Row Additional Row above Level 5 (Where Player & Portal can exist)
                map.add(createLevel(state, i)); 
            } 
            else 
            {
                map.add(createLevel(state, i)); 
            }
        }

        map.add( "| Building:        1    2    3    4    5    6    7    8    9   10   11   12   13   14   15                   |\n"); 
        //map.add( "|   Height:      ( 3) ( 5) ( 5) ( 2) ( 4) ( 1) ( 3) ( 5) ( 4) ( 4) ( 2) ( 4) ( 2) ( 4) ( 3)                  |\n");
        map.add( "|                                                                                                            |\n"); 
        map.add( "|------------------------------------------------------------------------------------------------------------|\n"); 
        map.add( "| Legends:  <P1> Player  |  -{}- Portal  |  [FC] Fuel Cell  |  [##] Web Trap |  [FF] Frozen Building         |\n");
        map.add( "|------------------------------------------------------------------------------------------------------------|\n"); 

        for (String mapSegment : map) 
        {
            System.out.print(mapSegment);
        }
    }
    
    /** 
     * Custom Method: Print the Valid Actions the Player can make next turn
     * @param playerActions String: A Single String containing the valid Actions the Player can make next turn (Left, Right or Stay)
     */
    public void printPlayerActions(String playerActions)
    {   
        ArrayList<String> actions = new ArrayList<>(); 

        actions.addAll(Arrays.stream(playerActions.split(";")).collect(Collectors.toList())); 

        String stringOutput = String.format("| Please Select Action: %-85s|\n", actions.get(0)); 

        for (int i = 1; i < actions.size(); i ++)
        {
            stringOutput = stringOutput + String.format("|                       %-85s|\n", actions.get(i));
        }

        String optionString = ( "|                       E) End Game  |  R) Rules                                                             |\n" ); 
        String borderString = ( "|------------------------------------------------------------------------------------------------------------|\n"); 

        System.out.println(stringOutput + optionString + borderString); 
    }
    
    /**
     * Custom Method: Prints the Rules for the Game
     */
    public void printRules()
    {    
        String message = 
        "\n---------------------------------------------------------- < Dimensional Rules > --------------------------------------------------------------\n" + //
        "                Win/Lose: Win by reaching the portal. Lose if fuel runs out.\n" + //
        "                Movement: Jump left or right by the building's height. Fuel is consumed for each jump.\n" + //
        "                    Fuel: Starts at 50% (10 Fuel Cells). Jump cost = | height difference of Buildings| + 1. Max fuel is 20 Fuel Cells.\n" + //
        "         Frozen Building: Landing on it means skipping a turn. Can't use the portal if it's frozen.\n" + //
        "               Web Traps: Costs 5 extra fuel if landed on.\n" + //
        "              Fuel Cells: Recharge 5 points. Expire after 3 turns.\n" + //
        " Dimensional Instability: Building Height changes every turn.\n"  + //
        "------------------------------------------------------------------------------------------------------------------------------------------------\n";
        System.out.println(message);    
    }
    
    /**
     * Custom Method: Prints the Welcome Message for the Game
     */
    public void printWelcomeMessage()
    {    
        String message = 
        "\n                                                         < The Java Jumper Game >                                                              \n" + //
        "------------------------------------------------------------------------------------------------------------------------------------------------\n" + //
        "| Welcome to Nowhere Land, a mysterious dimension where you're trapped. You must make your way to the Escape Portal to get Home.               |\n" + //
        "| To reach the escape portal, you've been equipped with a special Jumper Device, which will help you jump and traverse across buildings.       |\n" + //
        "| The Jumper Device is not perfect; it consumes fuel every time you use it, and if you run out, you may be trapped here forever!               |\n" + //
        "| Fortunately, there are Fuel Cells scattered across the different buildings, allowing you to replenish your Jumper Device fuel reserves.      |\n" + //
        "| Nowhere Land is a Dangerous Place, the reality is highly unstable so things shift and warp unpredictably. Moreover, the Nowhere Police       |\n" + //
        "| have setup Web Traps to catch extra-dimensional beings like you! Carefully Navigate through all the Dangers and reach the Portal to Esecape! |\n" + //
        "------------------------------------------------------------------------------------------------------------------------------------------------\n";
        System.out.println(message); 
    }

}