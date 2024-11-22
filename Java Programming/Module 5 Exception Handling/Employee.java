
import java.io.FileReader; 
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*; 

public class Employee
{   
    private String name;
    private int salaryPerHour;
    private String skill; 

    // 1 - Must include constructors, accessors, mutators, and a display method 
    public Employee() {}

    public Employee(String name, int salaryPerHour, String skill)
    {
        this.name = name; 
        this.salaryPerHour = salaryPerHour; 
        this.skill = skill; 
    }

    public String getName()
    {
        return this.name;
    }

    public int getSalaryPerHour()
    {
        return this.salaryPerHour;
    }

    public String getSkill() 
    {
        return this.skill; 
    }

    public void setName(String name)
    {
        this.name = name; 
    }

    public void setSalaryPerHour(int salaryPerHour)
    {
        this.salaryPerHour = salaryPerHour; 
    }

    public void setSkill(String skill)
    {
        this.skill = skill; 
    }

    public String displayEmployee()
    {
        return String.format("Name: %s\nSalary Per Hour: %d\nSkill: %s", this.name, this.salaryPerHour, this.skill);
    }

    /** 
     * Custom Method: Read File Contents
     * Read Required Attribute from Text File: Name, Salary per hour, Skill 
     * Store in Appropriate Collection
     * @param filePath The location of the Text Document as a String
     * @return The contents of the Text Document as a Set of Strings
     */
    public static Set<String> readFile(String filePath) 
    {   
        Set<String> contentHashSet = new HashSet<String>(); 
        try (FileReader reader = new FileReader(filePath); Scanner scanner = new Scanner(reader))
        {   
            while (scanner.hasNextLine()) {
               contentHashSet.add(scanner.nextLine());
            }  
        } catch (IOException ioe) {
            System.out.println("Unabled to Read from " + filePath + " | Error Message: " + ioe.getMessage());
        }
        return contentHashSet; 
    }

    public static void appendText(String inputText, String filePath)
    {   
        // FileWriter has an Append Mode Enabled through a Boolean
        // Print Writer allows for flexibility and FileWriter is wrapped with it
        try (FileWriter fileWriter = new FileWriter(filePath, true); PrintWriter writer = new PrintWriter(fileWriter))
        {   
            writer.print("\n" + inputText);
        } catch (IOException ioe) {
            System.out.println("Unabled to Read from " + filePath + " | Error Message: " + ioe.getMessage());
        }
    }

    public static void main(String[] args) throws IOException
    {   
        String fileName = "Java Programming\\Module 5 Exception Handling\\employee.txt";

        appendText("Tomas, 80, Java", fileName); 
        List<String> rawEmployeeDetails = new ArrayList<>(readFile(fileName));
        
        for (String employee : rawEmployeeDetails)
        {   
            try {
                String[] employeeInfo = employee.split(","); 
                System.out.println("\nName: " + employeeInfo[0] + "\nPay per Hour: " + employeeInfo[1] + "\nSkill: " + employeeInfo[2]); 
            } catch (IndexOutOfBoundsException iobe) {
                System.out.print("Error Message: " + iobe.getMessage());
            }
        }
    }
}
