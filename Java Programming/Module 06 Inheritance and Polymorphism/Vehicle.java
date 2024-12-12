/**
 * Inheritance & Polymorphism Example
 * Vehicle is a Parent Class of Cars and Trucks (Multi-Level Inheritance)
 * @author Tristan Sim
 * @version 1.00
 */

public abstract class Vehicle
{
    // Fields
    protected String brand; 
    protected String model; 
    protected Engine engine; 
    protected short numberOfSeats; 
    protected short yearOfManufacture; 
    
    /**
     * Non-Parameterized/Default constructor to initialize the Vehicle Class
     */
    public Vehicle()
    {   
        this.brand = "Mercedes"; 
        this.model = "C300"; 
        this.engine = new Engine(); 
        this.numberOfSeats = 2; 
        this.yearOfManufacture = 2000; 
    }
    
    /**
     * Parameterized/Non-Default constructor to initialize the Vehicle Class with specific values
     * @param numberOfSeats      The Number of Seats the Vehicle has
     * @param yearOfManufacture  The Year of the Manufacture
     * @param brand              The Brand of the Vehicle
     * @param model              The Model of the Vehicle
     * @param engine             The Engine Object of the Vehicle Class
     */
    public Vehicle(short numberOfSeats, short yearOfManufacture, String brand, String model, Engine engine)
    {   
        this.brand = brand; 
        this.model = model; 
        this.engine = engine; 
        this.numberOfSeats = numberOfSeats; 
        this.yearOfManufacture = yearOfManufacture; 
    }
    
    /**
     * Display Method: Displays the State of the Engine Class
     * @return String: The State of the Engine Class, delimited by ':' & '\n'
     */
    public String display() 
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Brand: ").append(this.brand).append("\n");
        stringBuffer.append("Model: ").append(this.model).append("\n");
        stringBuffer.append("Year of Manufacture: ").append(this.yearOfManufacture).append("\n");
        stringBuffer.append("Number of Seats: ").append(this.numberOfSeats).append("\n");
        stringBuffer.append("Engine Details:\n").append(this.engine.display()); 
        
        return stringBuffer.toString();
    }
    

}
