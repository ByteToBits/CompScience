
import java.util.*; 

public class Player {
    
    // Declare the Fields | Characteristics of the Player
    private String name; 
    private int age; 
    private double score; 
    private Scanner scanner; 

    // Constructor (Using a Scanner Class to handle console input) 
    public Player () {
        scanner = new Scanner(System.in); 
    }

    // Method to get Player Information 
    public void capturePlayerInfo() {

        System.out.println("Enter Player Name: ");
        name = scanner.nextLine(); 

        System.out.println("Enter Player Age: ");
        age = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter Player Score");
        score = Double.parseDouble(scanner.nextLine()); 
    
    }
    
    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age; 
    }

    public double getScore() {
        return this.score;
    }
    
    // Print Information
    public void displayInformation() {
        System.out.println("Player Name: " + this.name); 
        System.out.println("Player Age: " + this.age);
        System.out.println("Player Score: " + this.score);
    }

}
