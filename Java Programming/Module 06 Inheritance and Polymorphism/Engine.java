/**
 * Project: Inheritance & Polymorphism (Vehicle - Mini Practice)
 * Represents an engine with various attributes such as model, type, and specifications.
 * Fields are Encapsulated in privates attributes, accesibles by the accesors and Mutator Methods.
 * @author Tristan Sim
 * @version 1.00
 */

public class Engine {

    // Fields
    private String model;
    private String countryOfOrigin;
    private String type;
    private double displacement;
    private double hoursepower;

    /**
     * Default constructor that initializes the engine with default values
     */
    public Engine() {
        this.model = "FooBar";
        this.countryOfOrigin = "United States";
        this.type = "Petrol";
        this.displacement = 3.0;
        this.hoursepower = 150;
    }

    /**
     * Parameterized constructor to initialize the engine with specific values
     * @param model           The model of the engine
     * @param countryOfOrigin The country where the engine was manufactured
     * @param type            The type of engine (e.g., Petrol, Diesel)
     * @param displacement    The engine's displacement in liters
     * @param hoursepower     The engine's horsepower
     */
    public Engine(String model, String countryOfOrigin, String type, double displacement, double hoursepower) {
        this.model = model;
        this.countryOfOrigin = countryOfOrigin;
        this.type = type;
        this.displacement = displacement;
        this.hoursepower = hoursepower;
    }

    /**
     * Display Method: Displays the State of the Engine Class
     * @return String: The State of the Engine Class, delimited by ':' & '\n'
     */
    public String display() 
    {
        StringBuffer stringBuffer = new StringBuffer(); 
        stringBuffer.append("Model: ").append(this.model).append("\n"); 
        stringBuffer.append("Country Of Origin: ").append(this.countryOfOrigin).append("\n"); 
        stringBuffer.append("Type: ").append(this.type).append("\n"); 
        stringBuffer.append("Displacement: ").append(this.displacement).append("\n"); 
        stringBuffer.append("Horsepower: ").append(this.hoursepower).append("\n"); 
    
        return stringBuffer.toString(); 
    }

    /**
     * Gets the model of the engine.
     * @return The model of the engine.
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the engine.
     * @param model The model to set.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the country where the engine was manufactured.
     * @return The country of origin.
     */
    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    /**
     * Sets the country where the engine was manufactured.
     * @param countryOfOrigin The country of origin to set.
     */
    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    /**
     * Gets the type of the engine (e.g., Petrol, Diesel).
     * @return The type of the engine.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the engine.
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the displacement of the engine in liters.
     * @return The engine's displacement.
     */
    public double getDisplacement() {
        return displacement;
    }

    /**
     * Sets the displacement of the engine in liters.
     * @param displacement The displacement to set.
     */
    public void setDisplacement(double displacement) {
        this.displacement = displacement;
    }

    /**
     * Gets the horsepower of the engine.
     * @return The engine's horsepower.
     */
    public double getHorsepower() {
        return hoursepower;
    }

    /**
     * Sets the horsepower of the engine.
     * @param hoursepower The horsepower to set.
     */
    public void setHorsepower(double hoursepower) {
        this.hoursepower = hoursepower;
    }
}
