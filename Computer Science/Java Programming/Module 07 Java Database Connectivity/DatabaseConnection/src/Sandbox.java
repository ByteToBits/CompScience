/**
 * Module 07: Sandbox Class
 * Institution: Monash University Australia
 * Subject: Java Programming
 * Description: A simple testing environment for the DatabaseConnection class using university database tables
 * @author Tristan
 * @version 1.01
 */

 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.PreparedStatement;
 
 public class Sandbox {
     
     public static void main(String[] args) {
         // Create a connection to the database
         DatabaseConnection dbConnection = new DatabaseConnection("Monash Oracle SQL Server");
         
         try {
             // Connect to the database
             System.out.println("Connecting to the database...");
             if (dbConnection.connect()) {
                 System.out.println("Connection successful!");
                 
                 // Example 1: Simple SELECT query to get all units
                 System.out.println("\n--- Example 1: List all units ---");
                 String query1 = "SELECT * FROM uni.unit ORDER BY unitcode";
                 ResultSet rs1 = dbConnection.executeQuery(query1);
                 
                 // Display results
                 System.out.println("Unit Code\tUnit Name");
                 System.out.println("----------------------------------------");
                 while (rs1.next()) {
                     String unitCode = rs1.getString("unitcode");
                     String unitName = rs1.getString("unitname");
                     System.out.println(unitCode + "\t" + unitName);
                 }
                 rs1.close();
                 
                 // Example 2: Query with WHERE clause - Find students in Caulfield
                 System.out.println("\n--- Example 2: Find students in Caulfield ---");
                 String query2 = "SELECT stuid, stufname, stulname FROM uni.student WHERE UPPER(stuaddress) LIKE UPPER('%Caulfield%')";
                 ResultSet rs2 = dbConnection.executeQuery(query2);
                 
                 // Display results
                 System.out.println("ID\tFirst Name\tLast Name");
                 System.out.println("----------------------------------------");
                 while (rs2.next()) {
                     int id = rs2.getInt("stuid");
                     String firstName = rs2.getString("stufname");
                     String lastName = rs2.getString("stulname");
                     System.out.println(id + "\t" + firstName + "\t\t" + lastName);
                 }
                 rs2.close();
                 
                 // Example 3: Join query - Students and their enrollments
                 System.out.println("\n--- Example 3: Students and their enrollments ---");
                 String query3 = "SELECT s.stuid, s.stufname, s.stulname, e.unitcode, e.enrolmark, e.enrolgrade " +
                                 "FROM uni.student s " +
                                 "JOIN uni.enrolment e ON s.stuid = e.stuid " +
                                 "WHERE e.enrolmark IS NOT NULL " +
                                 "ORDER BY s.stuid, e.unitcode";
                 ResultSet rs3 = dbConnection.executeQuery(query3);
                 
                 // Display results
                 System.out.println("ID\tName\t\tUnit\tMark\tGrade");
                 System.out.println("------------------------------------------------");
                 while (rs3.next()) {
                     int id = rs3.getInt("stuid");
                     String firstName = rs3.getString("stufname");
                     String lastName = rs3.getString("stulname");
                     String unitCode = rs3.getString("unitcode"); 
                     int mark = rs3.getInt("enrolmark");
                     String grade = rs3.getString("enrolgrade");
                     System.out.println(id + "\t" + firstName + " " + lastName + "\t" + unitCode + "\t" + mark + "\t" + grade);
                 }
                 rs3.close();
                 
                 // Example 4: Aggregation query - Average marks by unit
                 System.out.println("\n--- Example 4: Average marks by unit ---");
                 String query4 = "SELECT unitcode, AVG(enrolmark) as avg_mark, COUNT(*) as num_students " +
                                "FROM uni.enrolment " +
                                "WHERE enrolmark IS NOT NULL " +
                                "GROUP BY unitcode " +
                                "ORDER BY avg_mark DESC";
                 ResultSet rs4 = dbConnection.executeQuery(query4);
                 
                 // Display results
                 System.out.println("Unit Code\tAverage Mark\tNumber of Students");
                 System.out.println("------------------------------------------------");
                 while (rs4.next()) {
                     String unitCode = rs4.getString("unitcode");
                     double avgMark = rs4.getDouble("avg_mark");
                     int numStudents = rs4.getInt("num_students");
                     System.out.printf("%s\t\t%.2f\t\t%d\n", unitCode, avgMark, numStudents);
                 }
                 rs4.close();
                 
                 // Example 5: Using a prepared statement for parameterized query
                 System.out.println("\n--- Example 5: Find units by code pattern (Prepared Statement) ---");
                 String query5 = "SELECT unitcode, unitname FROM uni.unit WHERE unitcode LIKE ?";
                 PreparedStatement pstmt = dbConnection.prepareStatement(query5);
                 
                 // Set parameter for FIT units
                 pstmt.setString(1, "FIT%");
                 ResultSet rs5 = pstmt.executeQuery();
                 
                 // Display results
                 System.out.println("Unit Code\tUnit Name");
                 System.out.println("----------------------------------------");
                 while (rs5.next()) {
                     String unitCode = rs5.getString("unitcode");
                     String unitName = rs5.getString("unitname");
                     System.out.println(unitCode + "\t" + unitName);
                 }
                 rs5.close();
                 pstmt.close();
                 
                 // Disconnect from the database
                 System.out.println("\nDisconnecting from the database...");
                 dbConnection.disconnect();
                 System.out.println("Disconnected successfully!");
                 
             } else {
                 System.out.println("Failed to connect to the database.");
             }
         } catch (SQLException e) {
             System.err.println("Error executing query: " + e.getMessage());
             e.printStackTrace();
         }
     }
 }