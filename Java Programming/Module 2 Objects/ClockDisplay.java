
public class ClockDisplay {

    // Fields 
    private int hour; 
    private int minute;
    private int second; 
    private String displayString; 

    // Constructor (Default)
    public ClockDisplay() {
        hour = 0; 
        minute = 0; 
        second = 0; 
        displayString = "";
    }

    /**
    Constructor (Non-Default)
    @param newHour The New Hour Value passed to the Object
    @param newMinute the New Minute Value passed to the Object
    @param newSecond The New Second Value passed to the Object
    **/
    public ClockDisplay(int newHour, int newMinute, int newSecond) {
        hour = newHour; 
        minute = newMinute; 
        second = newSecond; 
        displayString = hour + "h:" + minute + "m:" + second + "s";
    }

    // Accessor Methods (Get)
    public int getHour() {
        return hour; 
    }
    
    public int getMinute() {
        return minute;  
    }

    public int getSecond() {
        return second; 
    }

    public String getDisplayString() {
        return displayString;
    }

    // Mutator Methods (Set)
    public void setHour(int newHour) {
        this.hour = newHour;
    }
    
    public void setMinute(int newMinute) {
        this.minute = newMinute;
    }

    public void setSecond(int newSecond) {
        this.second = newSecond; 
    }

    public void setDisplayString(String newDisplayString) {
        this.displayString = newDisplayString; 
    }

    // Display Method
    public void displayClockDisplay() {
        System.out.println("Hour: " + this.hour); 
        System.out.println("Minute: " + this.minute);
        System.out.println("Second: " + this.second);
        System.out.println("Display String: " + this.displayString);
    }

    // Custom Methods: setTime (sets the hour, minutes, and seconds and then displays the time)
    public void setTime(int newHour, int newMinute, int newSecond) {
        this.hour = newHour; 
        this.minute = newMinute; 
        this.second = newSecond; 
        System.out.println("Current Hour: " + this.hour); 
        System.out.println("Current Minute: " + this.minute); 
        System.out.println("Current Second: " + this.second); 
    }

    // Custom Methods: timeTick (increases the seconds by 1 and then updates displayString)
    public void timeTick() {
        this.second += 1; 
        this.displayString = this.hour + "h:" + this.minute + "m:" + this.second + "s";
        System.out.println("After Time Tick: " + this.displayString);
    }

    public void updateDisplayString() {
        displayString = this.hour + "h:" + this.minute + "m:" + this.second + "s";
        System.out.println("Display String: " + displayString); 
    }
 
    public static void main(String[] args) {
        
        // Initialize the Class
        ClockDisplay objClockDisplay = new ClockDisplay(); 

        objClockDisplay.setTime(10, 27, 30);
        objClockDisplay.updateDisplayString();

        objClockDisplay.timeTick();

        objClockDisplay.updateDisplayString();
    }

}