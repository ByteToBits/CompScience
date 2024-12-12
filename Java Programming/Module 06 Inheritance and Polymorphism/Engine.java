
/**
 * Inheritance & Polymorphism Example
 * Engine Class is a Server Class to Vehicle
 * @author Tristan Sim
 * @version 1.00
 */

public class Engine 
{
    // Fields 
    private String model;
    private String countryOfOrigin; 
    private String type; 
    private double displacement;
    private double hoursepower; 

    public Engine()
    {
        this.model = "FooBar";
        this.countryOfOrigin = "United States";
        this.type = "Petrol";
        this.displacement = 3.0; 
        this.hoursepower = 150; 
    }

    public Engine(String model, String countryOfOrigin, String type, double displacement, double hoursepower)
    {
        this.model = model;
        this.countryOfOrigin = countryOfOrigin; 
        this.type = type; 
        this.displacement = displacement; 
        this.hoursepower = hoursepower; 
    }

    
}
