
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
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DatabaseCredentialManager {
    
    // Fields
    private static final String CREDENTIAL_FILE_PATH = "resources\\Secrets.json";
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
        this.databaseService = "null";
        this.databaseUsername = "null";
        this.databasePassword = "";
        this.databaseSoftware = "";
        this.databaseURL = "";
    }

    /** Non-Default Constructor: 
     * @param serviceName The Name of the Service, For Example "Microsoft SQL University Database"
     * Initializes a New Database Credential Manager instance with Default Values From a JSON File
     */
    public DatabaseCredentialManager(String serviceName)
    {
        loadCredentials(serviceName); 
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

    /**
     * Custom Method: Reads Database Configuration Settings from JSON File
     * @param serviceName The Service Type, For Example "Microsoft SQL University Database"
     */

    private void loadCredentials(String serviceName)
    {
        // Instantiate a JSON Parser Class
        JSONParser parser = new JSONParser(); 
        
        try (FileReader reader = new FileReader(CREDENTIAL_FILE_PATH)) {
            
            // Get all the JSON Root Object & Read the Crendential Arrays
            JSONObject rootObject = (JSONObject) parser.parse(reader); 
            JSONArray credentialArray = (JSONArray) rootObject.get("loginCredentials"); 

            // Look for the Credentials for the Specified Service
            for (Object credentialObject : credentialArray) {
                JSONObject credential = (JSONObject) credentialObject;
                String service = (String) credential.get("service"); 

                // Check if Service Name Matches Search Criteria
                if (service != null && service.equals(serviceName)) {
                    this.databaseUsername = (String) credential.get("username");
                    this.databasePassword = (String) credential.get("password"); 
                    this.databaseURL = (String) credential.get("url"); 
                    return;
                }
            }

            System.err.println("No Credentials Found for Service " + serviceName); 

        } catch (IOException | ParseException e) {
            System.err.println("Error Loading Database Credentials: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Hellow World!");    
        DatabaseCredentialManager dbCredentials = new DatabaseCredentialManager("Monash Oracle SQL Server");    
        System.out.println("Database Username: " + dbCredentials.getDatabaseUsername());
    }

}
