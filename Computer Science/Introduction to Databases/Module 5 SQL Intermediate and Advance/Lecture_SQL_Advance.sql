
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

   
/*
Applied 10-1: SQL Advance
Institution: Monash University Australia
Subject: ITO 4132 Introduction to Databases
Database Type:: Oracle SQL Database

Student Name: Tristan Sim 
Last Modified Date: 10th April 2025
*/

-- SPOOL output.txt
-- SET LINESIZE 300


/* 1. Assuming that the student name is unique, display Claudette Serman’s academic record. 
Include the unit code, unit name, year, semester, mark and explained_grade in the listing. The Explained Grade 
column must show Fail for N, Pass for P, Credit for C, Distinction for D and High Distinction for HD. Order by year, 
within the same year order the list by semester, and within the same semester order the list by the unit code. */

SELECT
   unitcode,
   unitname,
   TO_CHAR(ofyear, 'yyyy') AS year,
   ofsemester,
   enrolmark,
   CASE upper(enrolgrade)
       WHEN 'N' THEN
          'Fail'
       WHEN 'P' THEN
          'Pass'
       WHEN 'C' THEN
          'Credit'
       WHEN 'D' THEN
          'Distinction'
       WHEN 'HD' THEN
          'High Distinction'
   END AS explained_grade
FROM
   uni.enrolment NATURAL JOIN uni.unit
WHERE
   stuid = (
       SELECT 
          stuid
       FROM
          uni.student
       WHERE
          upper(stufname) = upper('Claudette')
          AND upper(stulname) = upper('Serman')
   )
ORDER BY
   year,
   ofsemester,
   unitcode; 
/* Alternate Approach using Oracle's Decode */
SELECT
    unitcode,
    unitname,
    EXTRACT(YEAR FROM ofyear)  AS year,
    ofsemester,
    enrolmark,
    DECODE(UPPER(enrolgrade), 'N', 'Fail', 'P', 'Pass',
           'C', 'Credit', 'D', 'Distinction', 'HD',
           'High Distinction') AS explained_grade
FROM
    uni.enrolment
    NATURAL JOIN uni.unit
    NATURAL JOIN uni.student
WHERE
    upper(stufname) = upper('Claudette')
    AND UPPER(stulname) = upper('Serman')
ORDER BY
    year,
    ofsemester,
    unitcode;



/* 2. Find the number of scheduled classes assigned to each staff member for each semester in 2019. 
If the number of classes is 2 then this should be labelled as a correct load, more than 2 as an overload and less than 2 as an underload.
Include the staff id, staff first name, staff last name, semester, number of scheduled classes and load in the listing. Sort the 
list by decreasing order of the number of scheduled classes and when the number of classes is the same, sort by the staff id then by the semester.
*/

DESC uni.unit;
DESC uni.schedclass;
DESC uni.staff;

SELECT 
   s.staffid,
   s.stafffname,
   s.stafflname,
   sh.ofsemester,
   COUNT(*) AS numberclasses, 
   CASE
      WHEN COUNT(*) > 2 THEN
        'Overload'
      WHEN COUNT(*) = 2 THEN
        'Correct Load'
      ELSE
        'Underload'
   END AS load
FROM
   uni.schedclass sh
   JOIN uni.staff s ON s.staffid = sh.staffid
WHERE
   TO_CHAR(ofyear, 'yyyy') = '2019'
GROUP BY
   s.staffid,
   s.stafffname,
   s.stafflname,
   sh.ofsemester
ORDER BY
   numberclasses DESC, s.staffid ASC, sh.ofsemester ASC;


/* 3. Find the total number of prerequisite units for all units. Include in the list the unit code of units that do not have a prerequisite. 
Order the list in descending order of the number of prerequisite units and where several units have the same number of prerequisites order then by unit code.
*/
SELECT
    u.unitcode,
    COUNT(prerequnitcode) AS no_of_prereq
FROM
    uni.unit      u
    LEFT OUTER JOIN uni.prereq    p ON u.unitcode = p.unitcode
GROUP BY
    u.unitcode
ORDER BY
    no_of_prereq DESC, unitcode;


/* 4. Display the unit code and unit name for units that do not have a prerequisite. Order the list by unit code. There are many approaches that you 
can take in writing an SQL statement to answer this query. You can use the SET OPERATORS, OUTER JOIN and a SUBQUERY. Write SQL statements based on all three approaches. 
*/ 



/* 5. List the unit code, semester, number of enrolments and the average mark for each unit offering in 2019. A unit offering is a particular unit in a particular 
semester for a particular year - for example the offering of FIT3176 in semester 2 of 2019 is one offering. Include offerings without any enrolment in the list. 
Round the average to 2 digits after the decimal point. If the average result is 'null', display the average as 0.00. The average must be shown with two decimal digits and right aligned. 
Order the list by the average mark, and when the average mark for several offerings is the same, sort by the semester then by the unit code.
*/



/* 6. List all units offered in semester 2 2019 which do not have any students enrolled. Include the unit code, unit name, and the chief examiner's name in a single column titled ce_name. 
Order the list based on the unit code.
*/



/* 7. List the id and full name (in a single column titled student_full_name) of students who are enrolled in both Introduction to databases and Introduction to computer architecture 
and networks (note: both unit names are unique) in semester 1 2020. You should note that the case provided for these unit names does not necessarily match the case in the database. 
Order the list by the student id.
*/


/* 8. Given that the payment rate for a tutorial is $42.85 per hour and the payment rate for a lecture is $75.60 per hour, calculate the weekly payment per type of class for each 
staff member in semester 1 2020. In the display, include staff id, staff name, type of class (lecture - L or tutorial - T), number of classes, number of hours (total duration), 
and weekly payment (number of hours * payment rate). The weekly payment must be displayed to two decimal points and right aligned. Order the list by the staff id and for a given 
staff id by the type of class.
*/


/* 9. Given that the payment rate for a tutorial is $42.85 per hour and the payment rate for a lecture is $75.60 per hour, calculate the total weekly payment (the sum of both 
tutorial and lecture payments) for each staff member in semester 1 2020. In the display, include staff id, staff name, total weekly payment for tutorials, total weekly payment
for lectures and the total weekly payment as a single line of output. If the payment is null, show it as $0.00. The tutorial payment, lecture payment and total weekly payment 
must be displayed to two decimal points and right aligned. Order the list by the staff id.
*/


/* 10. Assume that all units are worth 6 credit points each, calculate each student’s Weighted Average Mark (WAM) and GPA. Please refer to these Monash websites:
https://www.monash.edu/exams/results/wam and https://www.monash.edu/exams/results/gpa for more information about WAM and GPA respectively. Do not include NULL, 
WH or DEF grade in the calculation.

Calculation example for student 14374036 (Claudette Serman):
WAM = (56x6 + 16x6 + 81x6 + 77x6 + 64x6)/(6+6+6+6+6) = 58.80
GPA = (1x6+ 0.3x6 + 4x6 + 3x6 + 2x6)/(6+6+6+6+6) = 2.06

Calculation example for student 23545528 (Benny Plunket):
WAM = (53x3 + 97x3 + 78x6 + 94x6 + 85 x 6)/(3+3+6+6+6) = 83.00
GPA = (1x6 + 4x6+ 3x6 + 4x6 + 4x6)/(6+6+6+6+6) = 3.20

Include student id, student full name (in a 40 characters wide column headed student_fullname), WAM and GPA in the display. Order the list by descending order of 
WAM then descending order of GPA. If two students have the same WAM and GPA, order them by their respective id.
*/



