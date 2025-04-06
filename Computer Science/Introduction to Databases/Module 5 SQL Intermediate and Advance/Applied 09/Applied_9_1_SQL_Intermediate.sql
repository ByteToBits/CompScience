
/*
Lecture 5 Lecture: SQL Intermediate
Institution: Monash University Australia
Subject: ITO 4132 Introduction to Databases
Database Type:: Oracle SQL Database

Student Name: Tristan Sim 
Last Modified Date: 2nd April 2025

*/

-- 1 Find the maximum mark for FIT9136 in semester 2, 2019.
/* MAX is an Agggregate Function of the GROUP Clause and can't be used in a WHERE Clause */
DESC uni.enrolment; 
SELECT MAX(ENROLMARK) AS max_mark
FROM uni.enrolment
WHERE 
   UPPER(unitcode) = UPPER('FIT9136') 
   AND ofsemester = 2 
   AND TO_CHAR(ofyear,'yyyy') = '2019';



-- 2 Find the average mark for FIT2094 in semester 2, 2020. Show the average mark with two decimal places. 
-- Name the output column as average_mark.

SELECT AVG(enrolmark) AS average_mark
FROM uni.enrolment
WHERE
   UPPER(unitcode) = UPPER('FIT2094')
   AND ofsemester = 2
   AND TO_CHAR(ofyear, 'yyyy') = '2020';



-- 3 List the average mark for each offering of FIT9136. A unit offering is an instance of a particular unit in a particular semester - 
-- for example FIT1045 offered in semester 1 of 2019 - is a unit offering. In the listing, include the year and semester number. 
-- Sort the result according to the year then the semester.
/* GROUP BY Clause Allows the Comparison of Multiple Rows of Data 
   Data is First Grouped, then Averaged 
*/
SELECT 
   TO_CHAR(ofyear, 'yyyy') AS year, 
   ofsemester, 
   TO_CHAR(AVG(enrolmark), '999.0') AS average_mark
FROM
   uni.enrolment
WHERE
   UPPER(unitcode) = UPPER('FIT9136')
GROUP BY
   TO_CHAR(ofyear, 'yyyy'),
   ofsemester
ORDER BY
   year, ofsemester ASC; 



-- 4 Find the number of students enrolled in FIT1045 in the year 2019, under the following conditions (note two separate selects are required):
-- a. Repeat students are counted multiple times in each semester of 2019
DESC uni.enrolment; 
SELECT COUNT(stuid) AS student_count
FROM uni.enrolment 
WHERE 
   TO_CHAR(ofyear, 'yyyy') = '2019'
   AND UPPER(unitcode) = UPPER('FIT1045'); 

-- b. Repeat students are only counted once across 2019 (Use the DISTINCT Clause)
SELECT COUNT(DISTINCT stuid) AS student_count
FROM uni.enrolment 
WHERE 
   TO_CHAR(ofyear, 'yyyy') = '2019'
   AND UPPER(unitcode) = UPPER('FIT1045'); 



-- 5 Find the total number of prerequisite units for FIT5145.
DESC uni.prereq; 
SELECT COUNT(prerequnitcode) AS prereq_count
FROM uni.prereq
WHERE 
   UPPER(unitcode) = UPPER('FIT5145');



-- 6 Find the total number of enrolments per semester for each unit in the year 2019. The list should include the unit code, semester and the total 
-- number of enrolments. Order the list in increasing order of enrolment numbers. For units with the same number of enrolments, display them by the 
-- unit code order then by the semester order.
SELECT 
   unitcode, ofsemester, COUNT(stuid) AS Total_Enrolment
FROM 
   uni.enrolment
WHERE
   TO_CHAR(ofyear, 'yyyy') = '2019'
GROUP BY 
   unitcode, ofsemester
ORDER BY 
   unitcode, ofsemester;



-- 7 Find the total number of prerequisite units for each unit which has prerequisites. In the list, include the unit code for which the count is 
-- applicable. Order the list by unit code.
SELECT
   unitcode, COUNT(prerequnitcode) AS Number_of_Prerequisites
FROM
   uni.prereq
GROUP BY 
   unitcode
ORDER BY
  unitcode;



-- 8 Find the total number of students whose marks are being withheld (grade is recorded as WH) for each unit offered in semester 2 2020. In the 
-- listing include the unit code for which the count is applicable. Sort the list by descending order of the total number of students whose marks are 
-- being withheld, then by the unit code.
DESC uni.enrolment;
SELECT 
   unitcode, COUNT(DISTINCT stuid) AS Number_of_Students
FROM 
   uni.enrolment
WHERE
   TO_CHAR(ofyear,'yyyy') = '2020'
   AND ofsemester = 2
   AND UPPER(enrolgrade) = UPPER('WH')
GROUP BY 
   unitcode
ORDER by
   Number_of_Students DESC, unitcode DESC; 



-- 9 For each prerequisite unit, calculate how many times it has been used as a prerequisite (number of times used). In the listing include the 
-- prerequisite unit code, the prerequisite unit name and the number of times used. Sort the output by prerequisite unit code.
DESC uni.prereq;
DESC uni.unit;
   
SELECT
   prerequnitcode AS unit_code,
   u1.unitname AS unit_name,
   COUNT(u1.unitcode) AS No_Of_Time_Used
FROM
   uni.prereq p1
   JOIN uni.unit u1 ON u1.unitcode = p1.prerequnitcode 
GROUP BY 
   prerequnitcode, 
   u1.unitname
ORDER BY 
   unit_code ASC;



-- 10 Display the unit code and unit name of units which had at least 2 students who were granted a deferred exam (grade is recorded as DEF) in 
-- semester 2 2021. Order the list by unit code.
DESC uni.unit;
DESC uni.enrolment;

SELECT
   unitcode, 
   unitname,
   COUNT(*)
FROM
   uni.unit NATURAL JOIN uni.enrolment
WHERE 
   ofsemester = 2
   AND TO_CHAR(ofyear, 'yyyy') = '2021'
   AND UPPER(enrolgrade) = UPPER('DEF')
GROUP BY
   unitcode,
   unitname
HAVING 
   COUNT(*) >= 2
ORDER BY
   unitcode DESC;



-- 11 Find the oldest student/s in FIT9132. Display the student’s id, full name and the date of birth. Sort the list by student id.



-- 12 A unit offering is an instance of a particular unit in a particular semester - for example FIT1045 offered in semester 1 of 2019 - is a unit offering. 
-- All unit offerings are listed in the OFFERING table. Find the unit offering/s with the highest number of enrolments for any unit offering which occurred in 
-- the year 2019. Display the unit code, offering semester and number of students enrolled in the offering. Sort the list by semester then by unit code. 



-- 13 Find all students enrolled in FIT3157 in semester 1, 2020 who have scored more than the average mark for FIT3157 in the same offering. Display the students' 
-- name and the mark. Sort the list in the order of the mark from the highest to the lowest then in increasing order of student name.







