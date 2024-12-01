
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

    /**
     * Main Method: This is the Main Method of the Jumper Class & the Java Jumper Assignment
     */
    public static void main(String[] args)
    {
        Jumper javaJumper = new Jumper(); 
        javaJumper.map.initializeMap(javaJumper.player.getLocation());

        System.out.println(javaJumper.consoleDisplay.printWelcomeMessage()); 

        // System.out.println(javaJumper.consoleDisplay.testMap()); 

        String gameStatePacket = javaJumper.player.displayPlayer() + javaJumper.map.displayMap();

        System.out.println(gameStatePacket);

        System.out.println(javaJumper.player.pathFinder(gameStatePacket));
        javaJumper.consoleDisplay.printMap(gameStatePacket, "John", 5); 

        // Players Next Move

        // Input Player Moves


    }

}