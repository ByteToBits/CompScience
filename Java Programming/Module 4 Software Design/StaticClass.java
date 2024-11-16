

public class StaticClass {

    // Class Variable (Static): Is Accessible by all Methods throughout the whole Class and Unique to a Class
    private static int numberOfStudents = 0; 
    private static String headMaster = "Laurent";  
    
    // Constant Variales: Class Variable of a Constant
    private static final int maxSudent = 10; 
    
    // Below are Private Fields; Can be said to be variables instances of every object created
    private String studentName; 
    private int studentID; 

    public StaticClass (String studetName, int studentID) 
    {   
        numberOfStudents++; 
        this.studentName = studetName;
        this.studentID = studentID; 
    }

    // Class Method (Static): Static Classes can be invoked through the class and does not need to be instantiaed by an object creation
    public static int getNumberOfStudents () 
    {
        return numberOfStudents; 
    }

    public static void main(String[] args) 
    {   
        // Class Variables are Created during the execution of the class even before any its objects are created
        if (numberOfStudents <= maxSudent) {
            StaticClass student10 = new StaticClass("Timothy", 6988); 
            StaticClass student20 = new StaticClass("Alexander", 1988); 
            StaticClass student30 = new StaticClass("Huang", 9699); 
        }
        
        System.out.println("The Headmaster for this School is " + headMaster); 
        System.out.println("The Number of Studens is " + getNumberOfStudents());

    }
    
}
