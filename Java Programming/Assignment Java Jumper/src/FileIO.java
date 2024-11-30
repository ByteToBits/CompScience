/**
 * Description: The File Input/Ouput (IO) Class class handles te reading & writing functionalities to text files
 * @author Tristan Sim
 * @version V1.01
 */

import java.io.FileReader; 
import java.io.FileWriter;
import java.io.PrintWriter; 
import java.io.IOException;
import java.util.Scanner; 

public class FileIO
{
    private String fileName; 
    private static final String DELIMITER = "\r\n"; 
    private static final String BUILDING_FILE = "Assignment Java Jumper\\resources\\building.txt"; 
    private static final String OUTCOME_FILE = "Assignment Java Jumper\\resources\\outcome.txt";
    
    /**
     * Default Constructor
     */
    public FileIO()
    {
        this.fileName = BUILDING_FILE; 
    }
    
    /**
     * Non-Default Constructor
     */
    public FileIO(String fileName)
    {   
        this.fileName = fileName; 
    }

    /**
     * Custom Method: Read File Contents
     * Remarks: Data Parsing is handle by another class
     * @return A Single String of all the contents of the File
     * @throws IOException: Declare Checked Exceiption, FileNotFoundException, and propogate to the method Caller
     */
    public String readFile() throws IOException
    {
        StringBuffer stringBuffer = new StringBuffer(); 
        // Use a Try-With-Resource to ensure the Scanner is always close
        try (FileReader fileReader = new FileReader(this.fileName); Scanner scanner = new Scanner(fileReader))
        {
            while (scanner.hasNextLine())
            {   
                stringBuffer.append(scanner.nextLine()); 
                // Check if it is the Last Line before appending delimiter
                if (scanner.hasNextLine()) 
                {
                    stringBuffer.append(DELIMITER); 
                }
            }
        }
        return stringBuffer.toString();
    }

    /** Custom Method: Write String Data to File
     * @param contents String: A String Data to be Written to a text file
     * @throws IOException: Declare Checked Exceiption, FileNotFoundException, and propogate to the method Caller
     */
    public void writeFile(String contents) throws IOException
    {
        try (FileWriter fileWriter = new FileWriter(this.fileName); PrintWriter printWriter = new PrintWriter(fileWriter)) 
        {
            for (String contentSegment : contents.split(DELIMITER)) 
            {
                printWriter.println(contentSegment);
            }
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        FileIO fileIO = new FileIO(BUILDING_FILE);
        System.out.println(fileIO.readFile());

        String testString = "Number of Turns: 0,\r\nNumber of Fuel Cells: 10,\r\nWin Status: false"; 
        FileIO writeFileIO = new FileIO(OUTCOME_FILE); 

        writeFileIO.writeFile(testString); 

    }

}