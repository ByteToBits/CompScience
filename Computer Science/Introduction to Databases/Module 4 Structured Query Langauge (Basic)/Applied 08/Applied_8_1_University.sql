
-- Applied 8-1: The University Database, Handling Dates and Strings
-- Subject: ITO 4132 Introduction to Databases
-- Student: Tristan Sim
-- Date: 1st April 2025
-- Database: Oracle SQL Database

-- In the Monash Oracle database, this UNIVERSITY set of tables has been created under the user "UNI".

-- Retrieve the UNIT Table
SELECT unitcode, unitname FROM uni.unit;

-- SQL Date Formats 
-- to_date: converts from a string to a date according to a format string
SELECT stuid, stufname, stulname, studob AS student_date_of_birth
FROM uni.student
WHERE studob < TO_DATE('30/Apr/1992', 'dd/Mon/yyyy')
ORDER BY stuid; 

-- SQL Convert Date into Characters
-- to_char: converts from a date to a string according to a format string
SELECT TO_CHAR(sysdate, 'dd/Mon/yyyy hh24:mi:ss') AS server_date FROM dual; 

SELECT TO_CHAR(sysdate+10/24, 'hh24:mi:ss') AS server_time_plus_10_hours FROM dual;

-- Comparing Strings in SQL Statements 
DESC drone.manufacturer; 
SELECT * FROM drone.manufacturer WHERE UPPER(manuf_name) = UPPER('DJI Da-Jiang Innovations');

-- Using an Operator to Combine two Attributes to form 1 search condition
DESC drone.employee; 
SELECT * FROM drone.employee 
WHERE UPPER(emp_fname) = UPPER('Malika') AND UPPER(emp_lname) = UPPER('Casey');

