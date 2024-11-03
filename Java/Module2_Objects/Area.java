// Based on what we have learned thus far, you have been provided with a partially complete 
// code for a program. You are required to write two methods only. Each method needs to be 
// independent and must be defined based on the following requirements.

public class Area
{
    // Constructor (Default)
    public Area() {
    }

    // Replace this line with the code for rectangle area
    public double areaRectangle(double base, double height) {
        return base*height; 
    } 

    // Replace this line with the code for triangle area
    public double areaTriangle(double length, double width) { 
        return (length*width)/2; 
    }

    public static void main(String[] args)
    {
        Area a1 = new Area();
        System.out.println("Rectange area is: " + a1.areaRectangle(6 , 4));
        System.out.println("Triangle area is: " + a1.areaTriangle(6 , 4));
    }
}