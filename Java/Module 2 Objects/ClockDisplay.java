
public class ClockDisplay {

    // Fields 
    private int hour; 
    private int minute;
    private int second; 

    // Constructor (Default)
    public ClockDisplay() {
        hour = 0; 
        minute = 0; 
        second = 0; 
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
    }
    

}