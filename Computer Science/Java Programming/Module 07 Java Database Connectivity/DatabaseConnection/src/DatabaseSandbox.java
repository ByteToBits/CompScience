
/**
 * Module 07: Database Sandbox Class
 * Institution: Monash University Australia
 * Subject: Java Programming
 * Description: A simple testing environment for the DatabaseConnection class using drone example queries
 * @author Tristan
 * @version 1.01
 */

 /**
 * Module 07: Sandbox Class
 * Institution: Monash University Australia
 * Subject: Java Programming
 * Description: A simple testing environment for the DatabaseConnection class using drone example queries
 * @author Tristan
 * @version 1.01
 */

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSandbox {
    
    public static void main(String[] args) {
        // Create a connection to the database
        DatabaseConnection dbConnection = new DatabaseConnection("Monash Oracle SQL Server");
        
        try {
            // Connect to the database
            System.out.println("Connecting to the database...");
            if (dbConnection.connect()) {
                System.out.println("Connection successful!");
                
                // Example 1: Simple SELECT query to get all drones
                System.out.println("\n--- Example 1: List all drones ---");
                String query1 = "SELECT * FROM drones.drone_inventory ORDER BY drone_id";
                ResultSet rs1 = dbConnection.executeQuery(query1);
                
                // Display results
                System.out.println("ID\tModel\t\tManufacturer");
                System.out.println("----------------------------------------");
                while (rs1.next()) {
                    int id = rs1.getInt("drone_id");
                    String model = rs1.getString("drone_model");
                    String manufacturer = rs1.getString("manufacturer");
                    System.out.println(id + "\t" + model + "\t\t" + manufacturer);
                }
                rs1.close();
                
                // Example 2: Query with WHERE clause
                System.out.println("\n--- Example 2: Find drones by manufacturer ---");
                String query2 = "SELECT drone_id, drone_model FROM drones.drone_inventory WHERE manufacturer = 'DJI'";
                ResultSet rs2 = dbConnection.executeQuery(query2);
                
                // Display results
                System.out.println("ID\tModel");
                System.out.println("------------------------");
                while (rs2.next()) {
                    int id = rs2.getInt("drone_id");
                    String model = rs2.getString("drone_model");
                    System.out.println(id + "\t" + model);
                }
                rs2.close();
                
                // Example 3: Aggregation query
                System.out.println("\n--- Example 3: Count drones by manufacturer ---");
                String query3 = "SELECT manufacturer, COUNT(*) as drone_count FROM drones.drone_inventory GROUP BY manufacturer";
                ResultSet rs3 = dbConnection.executeQuery(query3);
                
                // Display results
                System.out.println("Manufacturer\tCount");
                System.out.println("------------------------");
                while (rs3.next()) {
                    String manufacturer = rs3.getString("manufacturer");
                    int count = rs3.getInt("drone_count");
                    System.out.println(manufacturer + "\t\t" + count);
                }
                rs3.close();
                
                // Disconnect from the database
                System.out.println("\nDisconnecting from the database...");
                dbConnection.disconnect();
                System.out.println("Disconnected successfully!");
                
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
    }
}
