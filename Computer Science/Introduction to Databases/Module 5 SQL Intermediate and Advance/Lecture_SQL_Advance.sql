
/*
Lecture 5: SQL Advance
Institution: Monash University Australia
Subject: ITO 4132 Introduction to Databases
Database Type:: Oracle SQL Database

Student Name: Tristan Sim 
Last Modified Date: 8th April 2025
*/

/* 
The CASE statement used in a select list enables a query to evaluate an attribute and output
a particular value based on that evaluation.
Drones which can carry objects have been classified by HyFlying as:
▪ 'No Load' if the carrying capacity is 0 Kg,
▪ 'Light Loads' for carrying greater than 0 but less than 4 Kg, and
▪ 'Heavy Loads' for 4 Kg and greater.
For all drones display the drone id, the carrying capacity classification eg 'No load' and the
drone cost per hour
*/

SELECT
   drone_id, 
   CASE
      WHEN dt_carry_kg = 0 THEN
        'No Load'
      WHEN dt_carry_kg < 4 THEN
        'Light Loads'
      ELSE
        'Heavy Loads'
   END AS carryingcapacity,
   drone_cost_hr
FROM
   drone.drone_type
   NATURAL JOIN drone.drone
ORDER BY
   drone_id;