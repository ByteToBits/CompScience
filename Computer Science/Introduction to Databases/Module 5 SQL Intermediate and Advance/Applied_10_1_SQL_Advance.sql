
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

DESC uni.unit;
DESC uni.prereq;

SELECT 
   u.unitcode,
   COUNT(PREREQUNITCODE) AS number_of_prerequisites
FROM
   uni.unit u 
   LEFT OUTER JOIN uni.prereq p ON u.unitcode = p.unitcode
GROUP BY
   u.unitcode
ORDER BY 
   number_of_prerequisites DESC, u.unitcode;


/* 4. Display the unit code and unit name for units that do not have a prerequisite. Order the list by unit code. There are many approaches that you
 can take in writing an SQL statement to answer this query. You can use the SET OPERATORS, OUTER JOIN and a SUBQUERY. Write SQL statements 
 based on all three approaches. */

-- Using Outer Join
SELECT
   u.unitcode,
   u.unitname
FROM
   uni.unit u
   LEFT OUTER JOIN uni.prereq p ON u.unitcode = p.unitcode
WHERE
   p.prerequnitcode IS NULL
ORDER BY
   u.unitcode;

-- Using Subquery
SELECT
   unitcode,
   unitname
FROM
   uni.unit
WHERE
   unitcode NOT IN (
      SELECT
         unitcode
      FROM
         uni.prereq
   )
ORDER BY
   unitcode;

-- Using Operator MINUS
SELECT
   u.unitcode,
   unitname
FROM
   uni.unit u 
MINUS
SELECT
   u.unitcode,
   unitname
FROM
   uni.unit u
   JOIN uni.prereq p ON u.unitcode = p.unitcode
ORDER BY
   unitcode;


/* 5. List the unit code, semester, number of enrolments and the average mark for each unit offering in 2019. 
A unit offering is a particular unit in a particular semester for a particular year - for example the offering of FIT3176 in 
semester 2 of 2019 is one offering. Include offerings without any enrolment in the list. Round the average to 2 digits after the decimal point. 
If the average result is 'null', display the average as 0.00. The average must be shown with two decimal digits and right aligned. Order the list 
by the average mark, and when the average mark for several offerings is the same, sort by the semester then by the unit code.
*/

DESC uni.offering;
DESC uni.enrolment;

SELECT
   unitcode,
   ofsemester,
   COUNT(stuid) AS number_of_enrollments,
   LPAD(TO_CHAR(NVL(AVG(enrolmark), 0), '990.99'), 12, ' ') AS average_mark
FROM
   uni.offering
   LEFT OUTER JOIN uni.enrolment USING (ofyear, ofsemester, unitcode)
WHERE
   EXTRACT (YEAR from ofyear) = 2019
GROUP BY
   unitcode,
   ofsemester
ORDER BY
   average_mark,
   ofsemester,
   unitcode;


/* 6. List all units offered in semester 2 2019 which do not have any students enrolled. Include the unit code, unit name, and the chief examiner's 
name in a single column titled ce_name. Order the list based on the unit code.
*/ 

DESC uni.offering;
DESC uni.enrolment;
DESC uni.staff;
DESC uni.unit;

SELECT
  o.unitcode,
  unitname, 
  s.stafffname || ' ' || s.stafflname 
FROM
  (( uni.offering o
  LEFT OUTER JOIN uni.enrolment e 
      ON o.unitcode = e.unitcode
      AND o.ofyear = e.ofyear
      AND o.ofsemester = e.ofsemester)
  JOIN uni.staff s ON s.staffid = o.staffid)
  JOIN uni.unit u ON o.unitcode = u.unitcode
WHERE
  TO_CHAR(o.ofyear, 'yyyy') = '2019'
  AND o.ofsemester = 2
GROUP BY
  o.unitcode,
  unitname,
  stafffname || ' ' || stafflname
HAVING
  COUNT(e.stuid) = 0
ORDER BY
  unitcode;


/* 7. List the id and full name (in a single column titled student_full_name) of students who are enrolled in both Introduction to databases and 
Introduction to computer architecture and networks (note: both unit names are unique) in semester 1 2020. You should note that the case provided for 
these unit names does not necessarily match the case in the database. Order the list by the student id.
*/