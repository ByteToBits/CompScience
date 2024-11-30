
import java.text.ParseException;
import java.util.ArrayList; 

public class ConsoleDisplay
{
    
    public ConsoleDisplay() {}

    public String printWelcomeMessage()
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
        "-----------------------------------------------------------------------------------------------------------------------------------------------\n"; 
        return message; 
    }
    
    public String testMap(){

        String newString =  
        "\n-------------------------------------------- < NoWhere Land > ------------------------------------------------\n"  + //
        "| Player: John                                                                                               |\n"  + //
        "| Turn: 10  |  Fuel Reserves: 10 (50%)                                                                       |\n"  + //
        "|                                                                                                            |\n"  + //
        "|                                                                                                            |\n"  + //
        "|  Level 5:          [FC] [  ]      <P1>           [  ]                                                      |\n"  + //
        "|  Level 4:          [  ] [  ]      [  ]           [  ] [FC] [  ]      [  ]      [  ] -{}-                   |\n"  + //
        "|  Level 3:     [  ] [  ] [  ]      [  ]      [##] [  ] [  ] [  ]      [  ]      [  ] [FF]                   |\n"  + //
        "|  Level 3:     [  ] [  ] [  ] [FC] [  ]      [  ] [  ] [  ] [  ] [  ] [  ]      [  ] [  ]                   |\n"  + //
        "|  Level 1:     [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [FC] [  ] [  ]                   |\n"  + //
        "| Building:       1    2    3    4    5    6    7    8    9   10   11   12   13   14   15                    |\n"  + //
        "|   Height:     ( 3) ( 5) ( 5) ( 2) ( 4) ( 1) ( 3) ( 5) ( 4) ( 4) ( 2) ( 4) ( 2) ( 4) ( 3)                   |\n"  + //
        "|                                                                                                            |\n"  + //
        "|------------------------------------------------------------------------------------------------------------|\n"  + //
        "| Legends:  <P1> Player  |  -{}- Portal  |  [FC] Fuel Cell  |  [##] Web Trap |  [FF] Frozen Building         |\n"  + //
        "|------------------------------------------------------------------------------------------------------------|\n"  + //
        "| Please Select Actions: 1) Jump Right (Cost: 5)  |  2) Jump Left (Cost: 5)  |  3) Stay (Cost: 1)            |\n"  + //
        "|                        S) Settings  |  R) Rules                                                            |\n"  + //
        "--------------------------------------------------------------------------------------------------------------\n";
        return newString; 
    }
    
    /**
     * Description: Print the Jave Jumper Map onto the Console
     * This Method Generates the Map using a String Data containing the Player & Building States
     * @param gameStates String: The States of the Building and Player Object parsed as a String 
     * @param turn int: The current Turn of the Game
     */
    public void printMap(String gameStates, String playerName, int turn)
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
        map.add(String.format("%-109s|\n",String.format("| Player: %-13s", playerName))); 
        map.add(String.format("%-109s|\n", String.format("| Turn %2d  |  Fuel Reserves: %-10s", turn, gameState.get(1)))); 
        map.add( "|                                                                                                            |\n"); 
        
        for (String mapSegment : map){
            System.out.print(mapSegment);
        }

    }
    
    String testBuilding = "Height: 4; Portal: false; Fuel Cells: false; Web Traps: false; Frozen: false; Fuel Cell Lifetime (Turns): 0";

    public String createBuildingLevel(ArrayList<String> state, int mapLevel) throws ParseException
    {   
        StringBuffer stringBuffer = new StringBuffer("|  Level " + mapLevel + ":     "); 
        String[] effects = {"[FF]", "[##]", "[FC]"}; 

        for (int i = 0; i < 15; i++)
        {
            if (mapLevel == Integer.parseInt(state.get(0))) {
                
                

            } else {
                stringBuffer.append("    ");
            }
        }

        return floor; 
    }




}