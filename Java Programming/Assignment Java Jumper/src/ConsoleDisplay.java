

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
        "\n---------------------------------------------- < NoWhere Land > ---------------------------------------------------------\n"  + //
        "| Player: John  |  Turn: 10  |  Fuel Reserves: 10 (50%)                                     | Legends:                  |\n"  + //
        "|                                                                                           | <P1> Player               |\n"  + //
        "|                                                                                           | -{}- Portal               |\n"  + //
        "|                        **        **                                                       | [FC] Fuel Cell            |\n"  + //
        "|  Level 5:        [FC] [  ]      <P1>           [  ]                                       | [##] Web Trap             |\n"  + //
        "|  Level 4:        [  ] [  ]      [  ]       **  [  ] [FC] [  ]      [  ]      [  ] -{}-    | [FF] Frozen Building      |\n"  + //
        "|  Level 3:   [  ] [  ] [  ]      [  ]      [##] [  ] [  ] [  ]      [  ]      [  ] [FF]    |                           |\n"  + //
        "|  Level 3:   [  ] [  ] [  ] [FC] [  ]      [  ] [  ] [  ] [  ] [  ] [  ]      [  ] [  ]    |  **  Indicator where      |\n"  + //
        "|  Level 1:   [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [  ] [FC] [  ] [  ]    |      the Player will be   |\n"  + //
        "| Building:     1    2    3    4    5    6    7    8    9   10   11   12   13   14   15     |      after selecting one  |\n"  + //
        "|   Height:   ( 3) ( 5) ( 5) ( 2) ( 4) ( 1) ( 3) ( 5) ( 4) ( 4) ( 2) ( 4) ( 2) ( 4) ( 3)    |      of the Actions below |\n"  + //
        "|                                                                                           |                           |\n"  + //
        "|-----------------------------------------------------------------------------------------------------------------------|\n"  + //
        "| Please Select Actions: 1) Jump Right (Cost: 5)  |  2) Jump Left (Cost: 5)  |  3) Stay (Cost: 1)                       |\n"  +//
        "|                        S) Settings  R) Rules   N) New Game  E) End Game                                               |\n"  + //
        "-------------------------------------------------------------------------------------------------------------------------\n";
        return newString; 
    }

    


}