/**
 * Project: Inheritance & Polymorphism (Vehicle - Mini Practice)
 * Car is a Child Class of Vehicle and a Parent Class to Sports & Electric (Multi-Level Inheritance)
 * @author Tristan
 * @version 1.00
 */

public class Car extends Vehicle
{
   // Fields
   private String transmission; 
   private String driveTrain; 
   private String efficiency; 

   public Car()
   {   
       super(); 
       this.transmission = "6-Speed Auto"; 
       this.driveTrain = "Rear-Wheel Drive"; 
       this.efficiency = "10 km/l";
   }

   public Car(Vehicle vehicle, String transmission, String driveTrain, String efficiency)
   {
       super(vehicle.getNumberOfSeats(), vehicle.getYearOfManufacture(), vehicle.getBrand(), vehicle.getModel(), vehicle.getEngine()); 
       this.transmission = transmission; 
       this.driveTrain = driveTrain; 
       this.efficiency = efficiency;
   }

   public String revEngine()
   {
       return "Reving Engine: Vroom Vroom!";
   }

}