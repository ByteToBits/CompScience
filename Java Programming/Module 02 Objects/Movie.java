
import java.util.Scanner;

public class Movie {

    private String name; 
    private double duration; 
    private String company; 
    private Scanner scanner;
    
    // Constructor
    public Movie() {
        scanner = new Scanner(System.in); 
    }
    
    public String getName() {
        return this.name; 
    }

    public double getDuration() {
        return this.duration; 
    }

    public String getCompany() {
        return this.company; 
    }

    public void captureMovieInfo() {
        
        System.out.println("Enter Movie Name:"); 
        name = scanner.nextLine();

        System.out.println("Enter Movie Duration:"); 
        duration = Double.parseDouble(scanner.nextLine()); 

        System.out.println("Enter Company:");
        company = scanner.nextLine(); 

    }

    public void displayMovie() {
        System.out.println("Movie Name is " + this.name); 
        System.out.println("Movie Duration is " + this.duration); 
        System.out.println("Movie Company is " + this.company); 
    }

    public static void main(String[] args) {

        Movie movie = new Movie(); 
        movie.captureMovieInfo(); 

        System.out.println("Movie Information:\n");
        movie.displayMovie();

    }
}
