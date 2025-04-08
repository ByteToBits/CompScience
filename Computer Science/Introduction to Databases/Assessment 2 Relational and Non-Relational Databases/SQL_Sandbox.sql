
/*
Script: SQL Sandbox (To Test Queries for Database Values)
Institution: Monash University Australia
Subject: ITO 4132 Introduction to Databases
Database Type:: Oracle SQL Database

Student Name: Tristan Sim 
Last Modified Date: 2nd April 2025
*/

-- Delete: To Delete Data Fast and and Logs in Database for Audit
DELETE FROM SERVICE_JOB;
DELETE FROM SERVICE;
DELETE FROM PART_CHARGE;
COMMIT;


-- Truncate: To Delete Data Fast and Does Not Log (Ussually for Test Data)
-- TRUNCATE TABLE table_name;

-- Search for Data inserted in Task 2
SELECT * FROM SERVICE;
SELECT * FROM SERVICE_JOB;
SELECT * FROM PART_CHARGE;

-- Find Information of the Customer Table
DESC CUSTOMER;
SELECT CUST_NO, CUST_NAME FROM CUSTOMER;


