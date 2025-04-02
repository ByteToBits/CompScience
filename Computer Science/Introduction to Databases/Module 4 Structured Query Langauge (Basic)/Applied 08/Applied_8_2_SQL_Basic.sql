/*
Applied 8-2: SQL Basics
Monash University
Subeject: Introduction to Databases

Student Name: Tristan Sim 
Last Modified Date: 2nd April 2025
*/

/* Part A - Retrieving data from a single table */

-- A1. List all units and their details. Order the output by unit code.
DESC uni.unit;
SELECT * FROM uni.unit ORDER BY unitcode ASC;


-- A2. List the full student details for those students who live in Caulfield. Order the output by student id.
DESC uni.student;
SELECT stuid, stufname, stulname, TO_CHAR(studob, 'dd-Mon-yyyy') AS Date_Of_Birth, stuaddress, stuphone, stuemail 
FROM uni.student 
WHERE UPPER(stuaddress) LIKE UPPER('%Caulfield')
ORDER BY stuid ASC; 


-- A3. List the full student details for those students who have a surname starting with the letter M. In the display,
-- rename the columns stufname and stulname to firstname and lastname. Order the output by student id.
SELECT stuid, stufname AS FIRSTNAME, stulname AS LASTNAME, TO_CHAR(studob, 'dd-Mon-yyyy') AS Date_Of_Birth, stuaddress, stuphone, stuemail 
FROM uni.student
WHERE UPPER(stulname) LIKE 'M%'
ORDER BY stuid ASC; 


-- A4. List the student's id, surname, first name and address for those students who have a surname starting with the 
-- letter S and first name which contains the letter i. Order the output by student id.
SELECT stuid, stufname AS FIRSTNAME, stulname AS SURNAME, stuaddress 
FROM uni.STUDENT
WHERE UPPER(stulname) LIKE 'S%' AND UPPER(stufname) LIKE '%I%'
ORDER BY stuid ASC; 


-- A5. Assuming that a unit code is created based on the following rules:
-- The first three letters represent the faculty abbreviation, eg. FIT for the Faculty of Information Technology.
-- The first digit of the number following the letter represents the year level. For example, FIT2094 is a unit code from 
-- the Faculty of IT (FIT) and the number 2 refers to a second year unit.
-- List the unit details for all first-year units in the Faculty of Information Technology. Order the output by unit code.
DESC uni.unit;
SELECT unitcode, unitname, unitdesc AS Unit_Description
FROM uni.unit
WHERE UPPER(unitcode) LIKE 'FIT1%'
ORDER BY unitcode ASC;


-- A6. List the unit code and semester of all units that are offered in 2021. Order the output by unit code, 
-- and within a given unit code order by semester. To complete this question, you need to use the Oracle function to_char to 
-- convert the data type for the year component of the offering date into text. For example, to_char(ofyear,'yyyy') 
-- here we are only using the year part of the date.
DESC uni.offering;
SELECT unitcode, ofsemester FROM uni.offering
WHERE TO_CHAR(ofyear, 'yyyy') = '2021'
ORDER BY unitcode, ofsemester;


-- A7. List the year and unit code for all units that were offered in either semester 2 of 2019 or semester 2 of 2020. 
-- Order the output by year and then by unit code. To display the offering year correctly in Oracle, you need to use the to_char function. 
-- For example, to_char(ofyear,'yyyy')
SELECT unitcode, TO_CHAR(ofyear,'yyyy')
FROM uni.offering
WHERE 
   (TO_CHAR(ofyear, 'yyyy') = '2019' AND ofsemester = 2)
   OR 
   (TO_CHAR(ofyear, 'yyyy') = '2020' AND ofsemester = 2)
ORDER BY ofyear ASC, unitcode; 


-- A8. List the student id, unit code and mark for those students who have failed any unit in semester 2 of 2021. 
-- Order the output by student id then order by unit code.
DESC uni.enrolment;
SELECT stuid, unitcode, enrolmark FROM uni.ENROLMENT
WHERE TO_CHAR(ofyear, 'yyyy') = '2021' AND ofsemester = 2 AND enrolmark < 50
ORDER BY stuid ASC, unitcode; 


-- A9. List the student id for all students who have no mark and no grade in FIT3176 in semester 1 of 2020. Order the output by student id.
SELECT stuid FROM uni.enrolment
WHERE TO_CHAR(ofyear, 'yyyy') = '2020' AND ofsemester = 1 AND upper(enrolgrade) = 'N'
ORDER BY stuid, unitcode;
