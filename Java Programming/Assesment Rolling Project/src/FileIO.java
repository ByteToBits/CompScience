import java.io.FileReader; 
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter; 
import java.util.Scanner; 

public class FileIO
{   
    private String fileName; 
    private static final String delimiter = "\r\n"; 
    private static final String studentFile = "Java Programming\\Assesment Rolling Project\\resource\\student.txt"; 
    private static final String outputFile = "Java Programming\\Assesment Rolling Project\\resource\\output.txt"; 

    public FileIO() 
    {
        this.fileName = "student.txt"; 
    }

    public FileIO(String fileName)
    {
        this.fileName = fileName; 
    }

    public String getFilePath()
    {
        return this.fileName; 
    }

    /**
     * Reads The File Content
     * Project Requirement: Read each line and add a delimiter that is not "," or "-"
     * The data parsing will be handle by another method in a difference class
     * @return A Single String of all the Contents in the File
     */
    public String readFile() throws IOException
    {   
        StringBuffer tempStringBuffer = new StringBuffer(); 
        try (FileReader reader = new FileReader(this.fileName); Scanner scanner = new Scanner(reader))
        {
            while (scanner.hasNextLine()) {
                tempStringBuffer.append(scanner.nextLine()).append(delimiter);
            }
            return tempStringBuffer.toString(); 
        }
    }

    public void setFilePath(String fileName)
    {
        this.fileName = fileName; 
    }

    /**
     * Write Data to File 
     * @param contents The contents to be written to the File as String
     */
    public void writeFile(String contents) throws IOException
    {
        try (FileWriter writer = new FileWriter(this.fileName); PrintWriter printWriter = new PrintWriter(writer))
        {
            for (String segment : contents.split(delimiter))
            {
                printWriter.println(segment);
            }
        }
    }

    public static void main(String[] args) 
    {
        FileIO objStudentFile = new FileIO(studentFile);
        FileIO objOutputFile = new FileIO(outputFile); 
        
        try {
            System.out.println(objStudentFile.readFile()); 
        } catch (IOException ioe) {
            System.out.println("Invalid File Path"); 
        }
        
        try {
            objOutputFile.writeFile(objStudentFile.readFile()); 
        } catch (IOException ioe) {
            System.out.println("Invalid File Path"); 
        }
    }
}