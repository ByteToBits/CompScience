
// Description: Clock for the Program which Shows the Time
// Aurthor: Tristan Sim
// Version: 1.00 

import java.time.LocalTime;

public class Clock
{   
    // Fields
    private int hour; 
    private int minute; 
    private int second; 
    
    // Default Constructor 
    public Clock() {
        hour = 0; 
        minute = 0;
        second = 0; 
    }

    // Non-Default Constructor 
    public Clock(int newHour, int newMinute, int newSecond) {
        hour = newHour; 
        minute = newMinute; 
        second = newSecond; 
    }

    // Accessor Method (Get)
    public int getHour() {
        return this.hour; 
    }

    public int getMinute() {
        return this.minute; 
    }

    public int getSecond() {
        return this.second; 
    }
    
    // Mutator Method (Set)
    public void setHour(int newHour) {
        this.hour = newHour; 
    }
    
    public void setMinute(int newMinute) {
        this.minute = newMinute; 
    }

    public void setSecond(int newSecond) {
        this.second = newSecond; 
    }

    // Display Method
    public void displayTime() {
        System.out.println("Hour: " + this.hour);
        System.out.println("Minute: " + this.minute);
        System.out.println("Second: " + this.second);
    }

    // Custom Methods: Show Time in hh:mm:ss format
    public void showTime() {
        System.out.println("Time now is " + this.hour + ":" + this.minute + ":" + this.second); 
    }

    public static void main(String[] args)
    {   
        // Get Local Time
        LocalTime currentTime = LocalTime.now(); 
        Clock objClock = new Clock(currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond()); 
        objClock.showTime();
    }

}