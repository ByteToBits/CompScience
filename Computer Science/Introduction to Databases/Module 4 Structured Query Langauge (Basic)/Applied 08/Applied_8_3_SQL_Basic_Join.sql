/*
Applied 8-3: SQL Basics (Joins)
Monash University
Subeject: Introduction to Databases

Student Name: Tristan Sim 
Last Modified Date: 3rd April 2025
*/

/* Part B - Retrieving data from multiple tables */

/* In this Unit, It is REQUIRED to use ANSI Joins (Join..On.. , Join..Using.. , Natural Join)
   Placing Joins in WHERE clauses are prohibited */ 

-- B1. List all the unit codes, semesters and name of chief examiners (ie. the staff who is responsible 
-- for the unit) for all the units that are offered in 2021. Order the output by semester then by unit code.
DESC uni.offering; 
DESC uni.staff; 

SELECT 
   unitcode, ofsemester, stafffname, stafflname
FROM 
   uni.offering o 
   JOIN uni.staff s 
   ON o.staffid = s.staffid
WHERE 
   TO_CHAR(ofyear, 'yyyy') = '2021'
ORDER BY 
   ofsemester ASC, unitcode; 


-- B2. List all unit codes, unit names and their year and semester of offering. 
-- Order the output by unit code then by offering year and then semester.
DESC uni.offering; 
DESC uni.unit; 

SELECT 
   u.unitcode, unitname, TO_CHAR(ofyear,'yyyy') AS OFFERING_YEAR, ofsemester 
FROM
   uni.unit u
   JOIN uni.offering o
   ON u.unitcode = o.unitcode
ORDER BY
   unitcode ASC, ofyear ASC, ofsemester ASC;


-- B3. List the student id, student name (firstname and surname) as one attribute 
-- and the unit name of all enrolments for semester 1 of 2021. Order the output by unit name, 
-- within a given unit name, order by student id.
-- Operator: The || symbol in SQL is the string concatenation operator
SELECT 
   s.stuid, stufname || ' ' || stulname AS STUDENT_NAME, unitname
FROM 
   uni.student s
   JOIN uni.enrolment e ON s.stuid = e.stuid
   JOIN uni.unit u ON u.unitcode = e.unitcode
WHERE 
   ofsemester = 1 AND TO_CHAR(ofyear, 'yyyy') = '2021'
ORDER BY
   unitname, stuid;


-- B4. List the id and full name of all students who have marks in the range of 80 to 100 in FIT9132 
-- semester 2 of 2019. Order the output by full name. If there are more than one student with the same 
-- name, order them based on their id.
DESC uni.student; 
DESC uni.enrolment;

SELECT 
   s.stuid, stufname || ' ' || stulname AS FULLNAME, enrolmark
FROM
   uni.student s
   JOIN uni.enrolment e 
   ON s.stuid = e.stuid
WHERE 
   enrolmark BETWEEN 80 AND 100 
   AND UPPER(unitcode) = 'FIT9132'
   AND ofsemester = 2
   AND TO_CHAR(ofyear, 'yyyy') = '2019'
ORDER BY
   FULLNAME, stuid; 


-- B5. List the unit code, semester, class type (lecture or tutorial), day, time and duration (in minutes) for 
-- all units taught by Windham Ellard in 2021. Sort the list according to the unit code, within a given unit 
-- code, order by offering semester.
DESC uni.schedclass;
DESC uni.staff;

SELECT
   c.unitcode, ofsemester, cltype AS CLASS_TYPE, clday AS CLASS_Day, cltime AS CLASS_TIME, clduration AS CLASS_DURATION, 
   c.staffid, stafffname || ' ' || stafflname AS LECTURER_NAME
FROM 
   uni.schedclass c
   JOIN uni.staff s
   ON c.staffid = s.staffid
WHERE
   TO_CHAR(ofyear,'yyyy') = '2021'
ORDER BY 
   unitcode,
   ofsemester;


-- B6. Create a study statement for Brier Kilgour. A study statement contains unit code, unit name, semester and year the study 
-- was attempted, the mark and grade. If the mark and/or grade is unknown, show the mark and/or grade as ‘N/A’. Sort the list by year, 
-- then by semester and unit code.
DESC uni.student;
DESC uni.enrolment;
DESC uni.unit;

SELECT
   u.unitcode, unitname, ofsemester AS SEMESTER, TO_CHAR(ofyear, 'yyyy') AS YEAR, 
   NVL(TO_CHAR(enrolmark, '999'), 'N/A') as MARK, NVL(enrolgrade, 'N/A') AS GRADE
FROM 
   uni.unit u
   JOIN uni.enrolment e ON e.unitcode = u.unitcode
   JOIN uni.student s ON e.stuid = s.stuid
WHERE 
   UPPER(stufname) = UPPER('Brier') AND 
   UPPER(stulname) = UPPER('Kilgour')
ORDER BY
   ofyear, ofsemester, unitcode; 


-- B7. List the unit code, unit name and the unit code and unit name of the prerequisite 
-- units for all units in the database which have prerequisites. Order the output by unit 
-- code and prerequisite unit code.
DESC uni.unit;
DESC uni.prereq;
-- Query Retrieves the Unit Code & Name 
-- Query is Joining the Prereq Table to get Prerequisite Unit Code 
-- This Self-Join retrieves and does the necessary lookup to get the Name of the Prerequisite Units 
SELECT 
   u1.unitcode, u1.unitname, prerequnitcode AS prereq_unit_code, u2.unitname AS prereq_unit_name
FROM 
   uni.unit u1 
   JOIN uni.prereq p ON u1.unitcode = p.unitcode
   JOIN uni.unit u2 ON u2.unitcode = p.prerequnitcode
ORDER BY 
   unitcode, prerequnitcode;


-- B8. List the unit code and unit name of the prerequisite units of the Introduction to data 
-- science unit. Order the output by prerequisite unit code.
SELECT
   u1.unitcode, u1.unitname, prerequnitcode AS prereq_unit_code, u2.unitname AS prereq_unit_name
FROM 
   uni.unit u1
   JOIN uni.prereq p ON u1.unitcode = p.unitcode
   JOIN uni.unit u2 ON u2.unitcode = p.prerequnitcode
WHERE
   UPPER(u1.unitname) = UPPER('Introduction to Data Science')
ORDER BY
   prerequnitcode; 


-- B9. Find all students (list their id, firstname and surname) who have received an HD for 
-- FIT2094 in semester 2 of 2021. Sort the list by student id.
DESC uni.student;
DESC uni.enrolment;
DESC uni.unit;

SELECT
   s1.stuid, stufname, stulname, enrolmark, enrolgrade
FROM 
   uni.student s1
   JOIN uni.enrolment e1 ON s1.stuid = e1.stuid
   JOIN uni.unit u1 ON e1.unitcode = u1.unitcode
WHERE
   UPPER(u1.unitcode) = 'FIT2094' AND ofsemester = 2 AND TO_CHAR(ofyear,'yyyy') = '2021'
   AND enrolgrade = 'HD'
ORDER BY
   stuid; 


-- B10. List the student's full name, unit code for those students who have no mark in any
-- unit in semester 1 of 2021. Sort the list by student full name.
SELECT 
   stufname || ' ' || stulname AS STUDENT_FULL_NAME, unitcode, enrolmark
FROM 
   uni.student s1
   JOIN uni.enrolment e1 ON s1.stuid = e1.stuid
WHERE
   enrolmark IS NULL 
   AND ofsemester = 1 AND TO_CHAR(ofyear,'yyyy') = '2021'
ORDER BY
   STUDENT_FULL_NAME;