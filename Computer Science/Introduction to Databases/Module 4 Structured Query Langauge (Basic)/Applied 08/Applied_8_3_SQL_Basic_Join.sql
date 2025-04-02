/*
Applied 8-2: SQL Basics
Monash University
Subeject: Introduction to Databases

Student Name: Tristan Sim 
Last Modified Date: 2nd April 2025
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


-- B4


-- B5


-- B6


-- B7


-- B8


-- B9


-- B10
