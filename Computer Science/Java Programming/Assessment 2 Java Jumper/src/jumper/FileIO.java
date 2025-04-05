package jumper;
/**
 * Description: The File Input/Ouput (IO) Class handles the reading and writing functionalities to text files.
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
    private static final String BUILDING_FILE = "Assignment Java Jumper\\resources\\buildings.txt"; 
    
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
     * Custom Method: Displays the File Name/Path
     * @return String: Returns a String with the File Name
     */
    public String displayFileIO()
    {
        return "File Path: " + this.fileName; 
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
    
    /**
     * Mutator Method: Sets the File Name
     * @param fileName String: File Name as a String Input
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName; 
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

}