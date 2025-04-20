/**
 * Module 07: Database Connection (Oracle SQL)
 * Institution: Monash University Australia
 * Subject: Java Programming
 * Description: Class that Connects to an Oracle SQL Database using the Java Database Connectivity Driver(JDBC)
 * @author Tristan
 * @version 1.01
 */

 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
 
 public class DatabaseConnection {
     // Fields
     private Connection connection;
     private DatabaseCredentialManager credentialManager;
     private String databaseURL;
     private String username;
     private String password;
     private boolean isConnected;
     
     /**
      * Default Constructor: 
      * Initializes a new DatabaseConnection instance with default values
      */
     public DatabaseConnection() {
         this.connection = null;
         this.credentialManager = new DatabaseCredentialManager();
         this.databaseURL = "";
         this.username = "";
         this.password = "";
         this.isConnected = false;
     }
     
     /**
      * Non-Default Constructor: 
      * @param serviceName The name of the service to connect to
      * Initializes a new DatabaseConnection instance with credentials from the specified service
      */
     public DatabaseConnection(String serviceName) {
         this.connection = null;
         this.credentialManager = new DatabaseCredentialManager(serviceName);
         this.databaseURL = credentialManager.getDatabaseURL();
         this.username = credentialManager.getDatabaseUsername();
         this.password = credentialManager.getDatabasePassword();
         this.isConnected = false;
     }
     
     /**
      * Connect Method: Establishes a connection to the database
      * @return true if connection is successful, false otherwise
      */
     public boolean connect() {
         try {
             // Load the Oracle JDBC driver
             Class.forName("oracle.jdbc.driver.OracleDriver");
             
             // Establish connection
             connection = DriverManager.getConnection(databaseURL, username, password);
             isConnected = true;
             System.out.println("Database connection established successfully.");
             return true;
         } catch (ClassNotFoundException e) {
             System.err.println("Oracle JDBC Driver not found: " + e.getMessage());
             return false;
         } catch (SQLException e) {
             System.err.println("Database connection failed: " + e.getMessage());
             return false;
         }
     }
     
     /**
      * Disconnect Method: Closes the database connection
      * @return true if disconnection is successful, false otherwise
      */
     public boolean disconnect() {
         if (connection != null) {
             try {
                 connection.close();
                 isConnected = false;
                 System.out.println("Database connection closed successfully.");
                 return true;
             } catch (SQLException e) {
                 System.err.println("Error closing database connection: " + e.getMessage());
                 return false;
             }
         }
         return false;
     }
     
     /**
      * Execute Query Method: Executes a SELECT query
      * @param query The SQL query to execute
      * @return ResultSet containing the query results
      * @throws SQLException if there is an error executing the query
      */
     public ResultSet executeQuery(String query) throws SQLException {
         if (!isConnected) {
             throw new SQLException("Not connected to database. Call connect() method first.");
         }
         
         Statement statement = connection.createStatement();
         return statement.executeQuery(query);
     }
     
     /**
      * Execute Update Method: Executes an INSERT, UPDATE, or DELETE query
      * @param query The SQL query to execute
      * @return int indicating the number of rows affected
      * @throws SQLException if there is an error executing the query
      */
     public int executeUpdate(String query) throws SQLException {
         if (!isConnected) {
             throw new SQLException("Not connected to database. Call connect() method first.");
         }
         
         Statement statement = connection.createStatement();
         return statement.executeUpdate(query);
     }
     
     /**
      * Prepare Statement Method: Creates a PreparedStatement for parameterized queries
      * @param query The SQL query with placeholders
      * @return PreparedStatement object
      * @throws SQLException if there is an error preparing the statement
      */
     public PreparedStatement prepareStatement(String query) throws SQLException {
         if (!isConnected) {
             throw new SQLException("Not connected to database. Call connect() method first.");
         }
         
         return connection.prepareStatement(query);
     }
     
     /**
      * Begin Transaction Method: Disables auto-commit to start a transaction
      * @throws SQLException if there is an error beginning the transaction
      */
     public void beginTransaction() throws SQLException {
         if (!isConnected) {
             throw new SQLException("Not connected to database. Call connect() method first.");
         }
         
         connection.setAutoCommit(false);
     }
     
     /**
      * Commit Transaction Method: Commits the current transaction
      * @throws SQLException if there is an error committing the transaction
      */
     public void commitTransaction() throws SQLException {
         if (!isConnected) {
             throw new SQLException("Not connected to database. Call connect() method first.");
         }
         
         connection.commit();
         connection.setAutoCommit(true);
     }
     
     /**
      * Rollback Transaction Method: Rolls back the current transaction
      * @throws SQLException if there is an error rolling back the transaction
      */
     public void rollbackTransaction() throws SQLException {
         if (!isConnected) {
             throw new SQLException("Not connected to database. Call connect() method first.");
         }
         
         connection.rollback();
         connection.setAutoCommit(true);
     }
     
     /**
      * Get Connection Method: Returns the current database connection
      * @return Connection object
      */
     public Connection getConnection() {
         return connection;
     }
     
     /**
      * Is Connected Method: Checks if there is an active database connection
      * @return true if connected, false otherwise
      */
     public boolean isConnected() {
         return isConnected;
     }
     
     /**
      * Main Method: For testing the DatabaseConnection class
      * @param args Command line arguments
      */
     public static void main(String[] args) {
         // Create a connection to the database
         DatabaseConnection dbConnection = new DatabaseConnection("Monash Oracle SQL Server");
         
         // Connect to the database
         if (dbConnection.connect()) {
             try {
                 // Execute a sample query
                 ResultSet resultSet = dbConnection.executeQuery("SELECT * FROM uni.unit");
                 
                 // Process the results
                 System.out.println("Unit Code\tUnit Name");
                 System.out.println("---------------------------");
                 while (resultSet.next()) {
                     String unitCode = resultSet.getString("unitcode");
                     String unitName = resultSet.getString("unitname");
                     System.out.println(unitCode + "\t" + unitName);
                 }
                 
                 // Close the result set
                 resultSet.close();
                 
                 // Disconnect from the database
                 dbConnection.disconnect();
             } catch (SQLException e) {
                 System.err.println("Error executing query: " + e.getMessage());
             }
         }
     }
 }