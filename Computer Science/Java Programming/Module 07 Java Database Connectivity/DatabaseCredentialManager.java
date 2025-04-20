/**
 * Module 07: Database Credential Manager
 * Institution: Monash Unviersity Australia
 * Subject: Java Programming
 * Description: Class that Reads a JSON File for Database Configuration Details 
 * @author Tristan
 * @version 1.01
*/

import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DatabaseCredentialManager {
    
    // Fields
    private static final String CREDENTIAL_FILE_PATH = ".LocalCache\\Confidential\\Secrets.json";
    private String databaseService;
    private String databaseUsername;
    private String databasePassword;
    private String databaseSoftware;
    private String databaseURL;

    /** Default Constructor: 
     * Initializes a New Database Credential Manager instance with Default Values
     */
    public DatabaseCredentialManager()
    {
        this.databaseServervice = "null";
        this.databaseUsername = "null";
        this.databasePassword = "";
        this.databaseSoftware = "";
        this.databaseURL = "";
    }
    
    /**
     * Get Method: Gets the Username of the Database
     * @return Returns the Username as a String
     */
    public String getDatabaseUsername()
    {
        return databaseUsername; 
    }
    
    /**
     * Get Method: Gets the URL of the Database
     * @return Returns the URL of the Database as a String
     */
    public String getDatabaseURL()
    {
        return databaseURL; 
    }

    /**
     * Get Method: Gets the Password of the Database
     * @return Returns the Password as a String
     */
    public String getDatabasePassword()
    {
        return databasePassword;
    }

    /**
     * Get Method: Gets the Service name of the Database
     * @return Returns the Service name as a String
     */
    public String getDatabaseService()
    {
        return databaseService;
    }

    /**
     * Get Method: Gets the Software name of the Database
     * @return Returns the Software name as a String
     */
    public String getDatabaseSoftware()
    {
        return databaseSoftware;
    }

}
