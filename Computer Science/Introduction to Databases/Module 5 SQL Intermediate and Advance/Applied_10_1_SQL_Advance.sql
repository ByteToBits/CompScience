
/*
Applied 10-1: SQL Advance
Institution: Monash University Australia
Subject: ITO 4132 Introduction to Databases
Database Type:: Oracle SQL Database

Student Name: Tristan Sim 
Last Modified Date: 10th April 2025
*/

SPOOL output.txt
SET LINESIZE 300

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
   