
/* 
Databases Applied 1
Task  1: Retrieve Data for Oracle SQL Database 

student id: 30428831
student name: Tristan Sim
last modified date: 1/3/2025

*/

/* Step 1: Retrieves All Student Details */
SELECT * FROM student; 

/* Step 2: Display all Students who live in Moorabin */
SELECT * FROM student WHERE studaddress like '%Moorabbin%';

/* Step 3: Display the First Name and Last Name of All the Students who live in Moorabin */
select studfname, studlname from student where studaddress like '%Moorabbin%';
