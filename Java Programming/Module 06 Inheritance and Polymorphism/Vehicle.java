
/**
 * Inheritance & Polymorphism Example
 * Vehicle is a Parent Class of Cars and Trucks (Multi-Level Inheritance)
 * @author Tristan Sim
 * @version 1.00
 */

public abstract class Vehicle
{
    // Fields
    protected short numberOfSeats; 
    protected short yearOfManufacture; 
    protected String brand; 
    protected String model; 
    protected Engine engine; 

    public Vehicle()
    {
        this.numberOfSeats = 2; 
        this.yearOfManufacture = 2000; 
        this.brand = "Mercedes"; 
        this.model = "C300"; 
        this.engine = new Engine(); 
    }

    public Vehicle(short numberOfSeats, short yearOfManufacture, String brand, String model, String engineType, double enginePower)
    {

    }
}