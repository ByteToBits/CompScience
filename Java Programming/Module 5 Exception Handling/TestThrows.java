import java.util.Scanner;
import java.io.*;

public class TestThrows
{   
    public TestThrows()
    {  }

    public void createFile(String fileName)
        throws IOException, FileNotFoundException    // Commenting this out will cause an error 
        // This method might throw these exceptions, it is Declared Exception to let Java know
        // However, the "throw" propogates the exception to the call stack "main" to handle
    {
        FileWriter writer = new FileWriter(fileName);
        writer.write("Hello World!");
        writer.close();
    }

    public static void main(String[] args)
        throws IOException, FileNotFoundException    // Commenting this out will cause an error 
        // This "main" method would also throw exceptions, it is Declared Exception to let Java know
        // However, the "throw" propogates the exception to the Java Virtual Machine to Handle
        // The JVM Handles the Exceptions and prints a Stack Trace of where the exception occured
    {
        System.out.println("Please enter the file name");
        Scanner console = new Scanner(System.in);
        String newFileName = console.next();

        TestThrows testThrows = new TestThrows();
        testThrows.createFile(newFileName);

        System.out.println("File created... attempting to read");

        FileReader reader = new FileReader(newFileName);
        // FileReader reader = new FileReader("abc123xyz.txt");

        console.close();

        console = new Scanner(reader);
        System.out.println(console.nextLine());
        System.out.println("File read!");

        console.close();
    }
}
