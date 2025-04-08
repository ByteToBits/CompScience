
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


/* For Each Drone find the Custoemrs (cust_id only) who rented the drone for the longest duration */
SELECT
   drone_id,
   TO_CHAR(rent_in_dt - rent_out_dt, '9990.9') AS Days_Rented_Out
FROM 
   drone.rental
WHERE
   rent_in_dt IS NOT NULL
ORDER BY
   drone_id;


/* This Variation will Return Each Drone with its Max Rented Days out */ 
SELECT
   drone_id,
   TO_CHAR(MAX(rent_in_dt - rent_out_dt), '9990.9') AS Max_Days_Out
FROM
   drone.rental
WHERE
   rent_in_dt IS NOT NULL
GROUP BY
   drone_id
ORDER BY
   drone_id;


/* Subquery (Nested): Example for Refresher */
SELECT
   drone_id,
   TO_CHAR((rent_in_dt - rent_out_dt),'9990.99') AS Max_Days_Out,
   cust_id
FROM
   drone.rental
   NATURAL JOIN drone.cust_train
WHERE
   rent_in_dt IS NOT NULL
   AND ( drone_id, ( rent_in_dt - rent_out_dt ) ) IN (
       SELECT
          drone_id, MAX(rent_in_dt - rent_out_dt)
       FROM
          drone.rental
       WHERE
          rent_in_dt IS NOT NULL
       GROUP BY
          drone_id
   )
ORDER BY
   drone_id,
   cust_id;

/* 
Subquery (Correlated) (Use a Alias where the Inner Query References the Outer Query)
Correlated Subquery is related to the Outer Query, and is evaluted once for each row of the outer query
Correlated can also be used within Update Statements; Outer Updates occurs based on the Value returned from the subquery
Alias R2 is evaluated first then it references the outer Table R1 to be evaluted (Inefficient in the example below)
*/
SELECT
   drone_id,
   TO_CHAR((rent_in_dt - rent_out_dt),'9990.99') AS Max_Days_Out,
   cust_id
FROM
   drone.cust_train
   NATURAL JOIN drone.rental r1
WHERE
   rent_in_dt IS NOT NULL
   AND ( ( rent_in_dt - rent_out_dt ) ) IN (
       SELECT
          MAX(rent_in_dt - rent_out_dt)
       FROM
          drone.rental r2
       WHERE
          rent_in_dt IS NOT NULL
          AND r1.drone_id = r2.drone_id
   )
ORDER BY
    drone_id,
    cust_id;
          
   

/*
Subquery (Inline)
Creates a Temporary Table called 'maxtable' from a Subquery and Join Temporary Table with Rental Table
*/
SELECT
   r.drone_id,
   TO_CHAR((r.rent_in_dt - r.rent_out_dt), '9990.99') AS Max_Days_Out,
   r.ct_id
FROM
   (
       SELECT
         drone_id, 
         MAX(rent_in_dt - rent_out_dt) AS maxout
       FROM
         drone.rental
       WHERE
         rent_in_dt IS NOT NULL
       GROUP BY
         drone_id
   ) maxtable
JOIN drone.rental r
   ON maxtable.drone_id = r.drone_id
  AND (r.rent_in_dt - r.rent_out_dt) = maxtable.maxout
JOIN drone.cust_train c
   ON r.ct_id = c.ct_id
ORDER BY
   r.drone_id,
   r.ct_id;


/* Subquery (INLINE) Exercise: For each Drone Compute the Percentage of the Company's Rentals Contirbuted by that Drone */

/* Bottom Query: Will Return Each Drone based on Drone ID how many times its been rented */
SELECT
   drone_id,
   COUNT(*) AS times_rented
FROM
   drone.rental
WHERE
   rent_in_dt IS NOT NULL
GROUP BY
   drone_id
ORDER BY
   drone_id;
   
/* To find the Percentages, ( Times_Rented / Total Number of Times All Drones Rented) * 100 - Use Inline Subquery */
SELECT
   drone_id,
   COUNT(*) AS times_rented,
   TO_CHAR(COUNT(*) * 100 / (
       SELECT
          COUNT(rent_in_dt)
       FROM
          drone.rental
   ), '990.99') AS percent_overall
FROM
   drone.rental
WHERE
   rent_in_dt IS NOT NULL
GROUP BY
   drone_id
ORDER BY
   percent_overall DESC;
