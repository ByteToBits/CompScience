
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


-- Task 4: Question C Validation
-- Parts that have been used in at least one service
SELECT 
   DISTINCT p.part_code, 
   p.part_description
FROM 
   part p
JOIN 
   part_charge pc ON p.part_code = pc.part_code
ORDER BY 
   p.part_code;

-- Parts that have not been used in any service
SELECT 
   p.part_code, 
   p.part_description
FROM 
   part p
WHERE 
   NOT EXISTS (
      SELECT 1 
      FROM part_charge pc 
      WHERE pc.part_code = p.part_code
   )
ORDER BY 
   p.part_code;

-- Count of used parts:
SELECT COUNT(DISTINCT pc.part_code) AS used_parts_count
FROM part_charge pc;

-- Count all Parts
SELECT COUNT(*) 
FROM part;

-- Count of unused parts:
SELECT COUNT(*) AS unused_parts_count
FROM part p
WHERE NOT EXISTS (
   SELECT 1 
   FROM part_charge pc 
   WHERE pc.part_code = p.part_code
);

-- Task 4: Question D Validation
SELECT * FROM part_charge WHERE PART_CODE = 'WR2419';

-- Task 4: Question F Validation
SELECT AVG(customer_total) AS average_parts_per_customer
FROM (
    SELECT
        c.cust_no,
        SUM(pc.pc_linecost) AS customer_total
    FROM
        customer c
        JOIN service s ON c.cust_no = s.cust_no
        JOIN service_job sj ON s.serv_no = sj.serv_no
        JOIN part_charge pc ON sj.serv_no = pc.serv_no AND sj.sj_job_no = pc.sj_job_no
    WHERE
        s.serv_ready_pickup IS NOT NULL  
    GROUP BY
        c.cust_no
) customer_totals;


SELECT * FROM part;