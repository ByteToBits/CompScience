import java.util.Scanner;

public class VolumeCylinder {

    // Internal Fields
    private double volCylinder; 
    private double radius; 
    private double height;

    // Constructor (Parameterized)
    public VolumeCylinder(double newRadius, double newHeight) {
        radius = newRadius; 
        height = newHeight;  
        volCylinder = 0; 
    }

    // Mutator Method or Set Method 
    public void setVolume(double newRadius, double newHeight) {
        radius = newRadius; 
        height = newHeight; 
    }

    // Calculate Volume of Cylinder
    public double calculateVolume() {
        this.volCylinder = Math.PI * Math.pow(this.radius, 2) * this.height; 
        return this.volCylinder; 
    }

    public static void main(String[] args) {
        
        // Instantiate the Object
        Scanner scanner = new Scanner(System.in); 
        
        System.out.println("To Calculate the Volume of a Cylinder, please input... ");
        System.out.print("The Radius of the Cylinder: ");
        double userRadius = scanner.nextDouble(); 
        System.out.print("The Height of the Cylinder: "); 
        double userHeight = scanner.nextDouble(); 

        VolumeCylinder objCylinder = new VolumeCylinder(userRadius, userHeight);
        double results = objCylinder.calculateVolume();

        System.out.println("The Volume of the Cylinder is " + results + " cubic units."); 

        scanner.close();
    }
}
