
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
