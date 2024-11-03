// Description: Calculates the area of a Rectangle and Triangle
// Author: Tristan Sim
// Version: 1.00

public class Area
{
    // Constructor (Default)
    public Area() {
    }

    // Calculates tje Area of a Rectangle
    public double areaRectangle(double base, double height) {
        return base*height; 
    } 

    // Calculates the Area of a Triangle
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