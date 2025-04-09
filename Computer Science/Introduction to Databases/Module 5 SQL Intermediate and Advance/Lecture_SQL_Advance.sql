
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


/* Subquery (Nested): Example for Refresher ------------------------------------------------------------------------------ */
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


/* Subquery to INSERT Data */
INSERT INTO drone_details
   ( SELECT
        drone_id, 
        drone_pur_date,
        dt_model
     FROM
        drone.drone
        NATURAL JOIN drone.drone_type
   );


/* VIEW - Virtual Table Derived From one or More Base Tables -------------------------------------------------------- */
/* Sometimes used as "Access Control" to the Database */

/* VIEW example: For each drone find the customers (cust_id only) who rented drone for the longest duration */
SELECT
   drone_id,
   (rent_in_dt - rent_out_dt) AS maxdaysout,
   cust_id
FROM
   drone.cust_train
   NATURAL JOIN drone.rental
WHERE
   rent_in_dt IS NOT NULL
   AND (drone_id, (rent_in_dt - rent_out_dt)) IN (
      SELECT
         drone_id, maxdays
      FROM
         maxdaysout_view
   )
ORDER BY
   drone_id,
   cust_id;


/* Joins ------------------------------------------------------------------------------------------------------ */
/* Self Join */
SELECT e1.empno, e1.empname, e1.empinit, e1.mgrno, e2.empname AS MANAGER
FROM emp.employee e1 JOIN emp.employee e2 ON ee1.mgrno = e2.empno
ORDER BY e1.empname;

/* INNER JOINs (Equi/Natural Join) will throw away data/rows that don't relate in Table 1 and 2 */
/* FULL OUTER JOINs will fill unrelated data/rows will Nulls instead of throwing away non-matching data */
SELECT * FROM
student s FULL OUTER JOIN mark m ON s.id = m.id;

/* LEFT OUTER JOINs including everything on the Left and NULL filling any no matching data */
/* Left Table 'Student' will be included; 'mark' with non-matching with be NULL Filled */
SELECT * FROM
student s LEFT OUTER JOIN mark m ON s.id = m.id;


/* Exercise Natural Join: List the Number of Times all Drones have been rented */
SELECT
   drone_id,
   COUNT(rent_out_dt) AS times_rented
FROM
   drone.drone
   JOIN drone.rental
   USING (drone_id)
GROUP BY
   drone_id
ORDER BY
   drone_id;

/* Exercise Left Outer Join: List the Number of Times all Drones have been rented */
SELECT
   drone_id,
   COUNT(rent_out_dt) as times_rented
FROM
   drone.drone
   LEFT OUTER JOIN drone.rental USING (drone_id)
GROUP BY
   drone_id
ORDER BY
   drone_id;


/* Relational Set Operators --------------------------------------------------------------------------------------------------
UnionAll: All rows selected by either query, including all duplicates 
Union: All rows selected by either query, removing duplicates (e.g. DISTINCT on Union ALL) 
Intersect: All Distinct Rows selected by both queries
Minus: All distinct rows selected by the first query but not by the second

/* MINUS: Using a Set Operator which drones have not been Rented? */
SELECT
   drone_id,
   TO_CHAR(drone_pur_date, 'dd-Mon-YYYY') AS purchasedate,
   drone_cost_hr
FROM
   drone.drone
WHERE
   drone_id IN (
      SELECT
         drone_id
      FROM 
         drone.drone
      MINUS
      SELECT
         drone_id
      FROM
         drone.rental
   )
ORDER BY
   drone_id;


/* UNION: Using Union Operator, create a single list of all customers 
For those who have completed training show "Completed Training"
For those who have not completed training show "Not Completed Training" */
SELECT DISTINCT
   cust_id,
   cust_fname
   || ' '
   || cust_lname AS custname,
   'Has completed training' AS trainingstatus
FROM
   drone.customer
   NATURAL JOIN drone.cust_train
UNION
SELECT
   cust_id,
   cust_fname
   || ' '
   || cust_lname,
   'Has not completed training'
FROM
   drone.customer
WHERE
   cust_id NOT IN (
      SELECT
         cust_id
      FROM
         drone.cust_train
   )
ORDER BY
   cust_id;
   

/* INTERSECION: Find the Employees who have the same last name as any customer */
SELECT
   emp_no,
   emp_fname,
   emp_lname,
   emp_type
FROM
   drone.employee
WHERE
   emp_lname IN (
      SELECT
         emp_lname
      FROM
         drone.employee
      INTERSECT
      SELECT
         cust_lname
      FROM
         drone.customer
   );



/* EXTRACT and DECODE */
SELECT
   drone_id,
   ds_date_serviced,
   emp_no,
   emp_fname
   || ' '
   || emp_lname AS employee_fullname,
   decode(emp_type, 'F', 'Full time', 'C', 'Contract') AS employee_category
FROM
   drone.employee
   NATURAL JOIN drone.drone_service
WHERE 
   EXTRACT(MONTH FROM ds_date_serviced) BETWEEN 1 AND 3
ORDER BY
   drone_id,
   ds_date_serviced;



/* Padding ----------------------------------------------------------------------------------------------------- */
/* Left PAD (LPAD) */
SELECT lpad ('Page 1', 15, '*') AS "Lpad Example" FROM dual;

SELECT 
   drone_id,
   COUNT(*) AS times_rented,
   LPAD(LTRIM(TO_CHAR(COUNT(*) * 100 / (
      SELECT
         COUNT(rent_in_dt)
      FROM
         drone.rental
   ), '990.99')), 15, '*') AS percent_overall
FROM
   drone.rental
WHERE
   rent_in_dt IS NOT NULL
GROUP BY
   drone_id
ORDER BY
   percent_overall DESC;