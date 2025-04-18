
/*
Lecture 5: SQL Intermediate
Institution: Monash University Australia
Subject: ITO 4132 Introduction to Databases
Database Type:: Oracle SQL Database

Student Name: Tristan Sim 
Last Modified Date: 2nd April 2025
*/

-- COUNT Function: Counts all Data in Column which are not Null
DESC drone.rental; 
SELECT 
   COUNT(*), 
   COUNT(rent_out_dt), 
   COUNT(rent_in_dt)
FROM
   drone.rental; 

-- AVERAGE Function: Averages all the Values in a Column
SELECT 
   AVG(drone_flight_time)
FROM 
   drone.drone; 

-- GROUP BY Function: Groups Data in Groups 
-- This section will Group then Averages the Groups
SELECT
   dt_code, 
   AVG(drone_flight_time)
FROM
   drone.drone
GROUP BY
   dt_code
ORDER BY
   dt_code; 

-- Extra Exercise 1 for GROUP BY & COUNT Function
SELECT count(*) FROM drone.cust_train; 

SELECT cust_id, COUNT(*) AS NO_COURSES_TAKEN
FROM drone.cust_train
GROUP BY cust_id
ORDER BY cust_id; 

SELECT AVG(COUNT(*)) AS AVERAGE_NO_COURSES_TAKEN
FROM drone.cust_train
GROUP BY cust_id;

-- Extra Exercise 2 for GROUP BY & COUNT Function
SELECT cust_id, train_code, count(train_code) AS NO_OF_COURSES_TAKEN
FROM drone.CUST_TRAIN
GROUP BY cust_id, train_code
ORDER BY cust_id, train_code;

-- Extra Exercise 2: Alias cannot be used to Produce and Output
SELECT cust_id,
   TO_CHAR(ct_date_start, 'yyyy') as LICENSE_START_YEAR,
   count(train_code) AS NO_OF_COURSES_TAKEN
FROM drone.CUST_TRAIN
GROUP BY cust_id, TO_CHAR(ct_date_start, 'yyyy')
ORDER BY cust_id, LICENSE_START_YEAR; 

-- Exercise 3: GROUP BY & HAVING Clause - Customers who have done More that 1 Training
SELECT cust_id, train_code, COUNT(train_code) AS NO_OF_COURSES_TAKEN
FROM drone.CUST_TRAIN
GROUP BY cust_id, train_code
HAVING COUNT(train_code) > 1
ORDER BY cust_id, train_code; 

-- Exercise 3: GROUP BY & HAVING Clause - Drone Flight Time > 50
SELECT dt_code, AVG(drone_flight_time) AS AVERAGE_DRONE_FLIGHT
FROM drone.drone
GROUP BY dt_code
HAVING AVG(drone_flight_time) > 50
ORDER BY dt_code; 

-- Exercise 4: Example of HAVING and WHERE Clause
-- WHERE - Clause is Applied to ALL Rows in Table
-- HAVING - Clause is Applied to the Groups Defined by the GROUP BY Clause
SELECT dt_code, AVG(drone_flight_time) as AVERAGE_DRONE_FLIGHT
FROM drone.DRONE
WHERE TO_CHAR(drone_pur_date, 'yyyy') = '2021'
GROUP BY dt_code 
HAVING AVG(drone_flight_time) > 50
ORDER BY AVERAGE_DRONE_FLIGHT DESC; 

-- Exercise 5: Sub-Query
-- A Sub-query (A Single Value can't compare to a Multi-Row Value Return of a Sub-query)
-- For Multiple Row: Need a Comparison Operators like IN / ALL / ANY
SELECT *
FROM drone.drone
WHERE drone_pur_price > (SELECT AVG(drone_pur_price) FROM drone.drone GROUP BY drone_pur_date);


-- Create a VIEW for the Next Exercise (VIEW will be covered in the Advance Lesson)
CREATE VIEW dronetypeprice AS 
SELECT drone_id, dt_code, dt_model, drone_pur_price
FROM drone.drone_type NATURAL JOIN drone.drone;

SELECT * FROM dronetypeprice;

-- 1st Query Returns each Drone Type's Max Price (IN: Returns Multiple Rows of Values)
-- 2nd Query Returns all the Rows where the Drone Prices Matches the Max Values Return; Causing Duplicates.
SELECT * 
FROM dronetypeprice
WHERE drone_pur_price IN (SELECT MAX(drone_pur_price) FROM dronetypeprice GROUP BY dt_code);

-- 1st Query Returns to Find Minimum Drone Purchase Price
-- 2nd Query Returns Finds all the Drones where the Prices are greater than the Minimum Values Return; Causing Duplicates.
SELECT *
FROM dronetypeprice
WHERE drone_pur_price > ANY (SELECT MIN(drone_pur_price) FROM dronetypeprice GROUP BY dt_code)
ORDER BY drone_id;

-- 1st Query Returns to Find Minimum Drone Purchase Price
-- 2nd Query Returns Finds all Drones Prices which are Highes than the Max value of the 1st Query; Causing Duplicates.
SELECT *
FROM dronetypeprice
WHERE drone_pur_price > ALL (SELECT MIN(drone_pur_price) FROM dronetypeprice GROUP BY dt_code)
ORDER BY drone_id;

-- SQL Task: Write the SQL Query to Find all of the drones which have a purchase price less than the average purchase price
-- for all drones Manufactured by DJI Da-Jiang Innovations
-- Output must show the Drone ID, the Type Code, the Purchase Price, the Year Purchased and the Manufacturers Name. 
-- Order by output by Drone ID.CUST_TRAIN

SELECT drone_id, dt_code, drone_pur_price, TO_CHAR(drone_pur_date, 'yyyy') as yearpurchased, manuf_name
FROM drone.drone NATURAL JOIN drone.drone_type NATURAL JOIN drone.manufacturer
WHERE
   drone_pur_price < (
      SELECT 
         AVG(drone_pur_price)
      FROM
         drone.drone
         NATURAL JOIN drone.DRONE_TYPE
         NATURAL JOIN drone.MANUFACTURER
      WHERE
         upper(manuf_name) = 'DJI DA-JIANG INNOVATIONS'
   )
ORDER BY
   drone_id;
