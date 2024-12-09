
public class Area
{
    //Replace this line with the code for rectangle area
    private double areaTriangle(int base, int height){
        return (base * height)/2; 
    }
    
    //Replace this line with the code for triangle area
    private double areaRectangle(int length, int width){
        return length * width; 
    }
   
    public static void main(String[] args)
    {
        Area a1 = new Area();
        System.out.println("Rectange area is: " + a1.areaRectangle(6 , 4));
        System.out.println("Triangle area is: " + a1.areaTriangle(6 , 4));
    }
}
