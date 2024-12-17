import java.util.Scanner;

/**
 * The main class for the slot machine
 *
 * @Mark Creado
 * @version 1 - 2 September 2020
 */

 /**
  * Not my Code: The is Subject Topic is about Refactoring, the code below has been refactored to remove duplicates 
  */
public class SlotMachine
{
    private int credit;
    
    /**
     * Constructor for objects of class Spin
     */
    public SlotMachine()
    {
        credit = 0;
    }
    
    /**
     * Method to start the game
     */
    public static void main(String[] args)
    {
        SlotMachine slot = new SlotMachine();
        slot.start();
    }

    public int printWinningResults(String message, int multiplier, int bet)
    {
        System.out.println(message + "\n");
        System.out.println("Win is " + multiplier + " x the bet amount of $" + bet + "!");
        int winningAmount = multiplier*bet; 
        System.out.println("You have won $" + (winningAmount)); 
        return winningAmount; 
    }
    
    public void printIntroduction()
    {
        System.out.println("Welcome to the Slot Machine Game");
        System.out.println("Enter 1 to input credit");
        System.out.println("Enter 2 to spin");
        System.out.println("Enter 3 to obtain credit details");
        System.out.println("Enter 4 for help");
        System.out.println("Enter 5 for exit");
    }

    public void printUserHelp()
    {
        System.out.println("");
        System.out.println("Welcome to the Slot Machine Game");
        System.out.println("");
        System.out.println("This is a small help menu to assist the user in playing the game");
        System.out.println("The game consists of five main options as follows:");
        System.out.println("");
        System.out.println("Selecting 1 allows the user to register in the system and to input credit into the machine to start playing.");
        System.out.println("            Selecting option 1 is necessary before starting the game.");
        System.out.println("");
        System.out.println("Selecting 2 allows the user to spin the slot machine 1 time.");
        System.out.println("");
        System.out.println("Selecting 3 allows the user to view their current credit in the machine and their win statistics.");
        System.out.println("");
        System.out.println("Selecting 4 displays this menu.");
        System.out.println("");
        System.out.println("Selecting 5 allows the user to exit the game and quit the system.");
        System.out.println("");
        System.out.println("Winning structure is as follows:");
        System.out.println("");
        System.out.println("The game spins three independent slots and obtains the sum of the three numbers obtained.");
        System.out.println("Winnings are calculated as follows:");
        System.out.println("");
        System.out.println("All THREE numbers are the same, win is 100x the bet amount");
        System.out.println("Sum is  0 or 27, win is 1000x the bet amount");
        System.out.println("Sum is  1 or 26, win is 500x the bet amount");
        System.out.println("Sum is 11 or 17, win is 10x the bet amount");
        System.out.println("Sum is 12 or 16, win is 4x the bet amount");
        System.out.println("Sum is 13 or 15, win is 1x the bet amount");
        System.out.println("");
        System.out.println("Winnings can be cummulative!");
    }

    public void start()
    {
        boolean userRegistered = false;
        String option = "";
        Scanner console = new Scanner(System.in);
        do
        {
            printIntroduction(); 
            option = console.nextLine();
            switch(option)
            {
                case "1":
                    System.out.println("Welcome to the Slot Machine Game");
                    do
                    {
                        System.out.println("How much credit would you like to input enter the system?");
                        credit = Integer.parseInt(console.nextLine());
                        if(credit <= 0)
                            System.out.println("Credit must be a positive number");
                    }while (credit <= 0);
                    System.out.println("Thank you. Your game has been credited with $" + credit + ".");
                    System.out.println("Your balance is $" + credit + ".");
                    userRegistered = true;
                    break;
                case "2":
                    if(userRegistered == true)
                    {
                        System.out.println("Welcome to the Slot Machine Game");
                        int bet = 0;
                        boolean cont = false;
                        do
                        {
                            System.out.println("Please place your bet amount?");
                            bet = Integer.parseInt(console.nextLine());
                            if(bet <= 0)
                            {
                                System.out.println("Bet must be a positive number");
                                break;
                            }
                            if(bet > credit)
                            {
                                System.out.println("Bet must be less than available credit in the machine");
                                break;
                            }
                            cont = true;
                            
                        }while (cont == false);
                
                        System.out.println("Spinning the machine now...");
                        
                        int result1 = 0 + (int)(Math.random() * 10);
                        System.out.println("Outcome of the first slot is: " + result1);
                        int result2 = 0 + (int)(Math.random() * 10);
                        System.out.println("Outcome of the second slot is: " + result2);
                        int result3 = 0 + (int)(Math.random() * 10);
                        System.out.println("Outcome of the third slot is: " + result3);
                       
                        boolean win = false;
                        
                        int total = result1 + result2 + result3;
                        
                        System.out.println("Spin Complete. Your slot total is: " + total);
                        
                        if((result1 == result2) && (result2 == result3))
                        {   
                            int winAmount = printWinningResults("All three numbers are the same. CONGRATULATIONS!", 100, bet); 
                            credit += winAmount;
                            win = true;
                        }
                        
                        if((total == 0) || (total == 27))
                        {   
                            int winAmount = printWinningResults("All three numbers are the same. CONGRATULATIONS!", 1000, bet); 
                            credit += winAmount;
                            win = true;
                        }
                        
                        if((total == 1) || (total == 26))
                        {
                            int winAmount = printWinningResults("All three numbers are the same. CONGRATULATIONS!", 500, bet); 
                            credit += winAmount;
                            win = true;
                        }
                        
                        if((total == 11) || (total == 17))
                        {
                            int winAmount = printWinningResults("All three numbers are the same. CONGRATULATIONS!", 10, bet); 
                            credit += winAmount;
                            win = true;
                        }
                        
                        if((total == 12) || (total == 16))
                        {
                            int winAmount = printWinningResults("All three numbers are the same. CONGRATULATIONS!", 4, bet); 
                            credit += winAmount;
                            win = true;
                        }
                        
                        if((total == 13) || (total == 15))
                        {
                            int winAmount = printWinningResults("All three numbers are the same. CONGRATULATIONS!", 1, bet); 
                            credit += winAmount;
                            win = true;
                        }
                        
                        if(win == false)
                        {
                            System.out.println("Your total does not add up to a winning total. Bad Luck!. Please Try Again!");
                            credit += (-1 * bet);
                        }
                    }   
                    else
                        System.out.println("User Registration must be done first. Please select option 1 first");
                    break;
                case "3":
                    if(userRegistered == true)
                        System.out.println("Your balance is $" + credit + ".");
                    else
                        System.out.println("User Registration must be done first. Please select option 1 first");
                    break;
                case "4":
                    printUserHelp(); 
                    break;
                case "5":
                    System.out.println("Thanks for playing. Goodbye");
                    break;
                default:
                    System.out.println("Please enter a number between 1 and 5");
            }
        }while (!option.equals("5"));

        console.close();
    }
}