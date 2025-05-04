/*****PLEASE ENTER YOUR DETAILS BELOW*****/
-- ITO4132
--T3-ma-dml.sql
-- Task 3: Databse Manipulation Language (DML)

--Student ID: 30428831
--Student Name: Tristan Sim

/* Comments for your marker:




*/

-- Please ensure EACH SQL statement you add is formatted
-- and includes a semicolon

/* (a) 
Create a set of sequences that will allow you to enter data into the SERVICE and PART_CHARGE tables—all such 
sequences must start at 1000 and go up in steps of 10 (i.e. the first value is 1000, the next 1010 etc.). 
[1 mark] */

-- Drop Any Existing Sequence to Avoid Conflict
DROP SEQUENCE service_seq; 
DROP SEQUENCE part_charge_seq; 
-- Create a New Sequence Entry in the Databases
CREATE SEQUENCE service_seq START WITH 1000 INCREMENT BY 10; 
CREATE SEQUENCE part_charge_seq START WITH 1000 INCREMENT BY 10; 
COMMIT; 
-- Display all the Sequences from the Oracle Catalogue (Objects)
SELECT sequence_name FROM user_sequences;


/* (b) 
The customer with phone number '6715573197', has called in and informed Monash Automotive that they have changed the 
registration number of the sole vehicle they own to 'GDD132'. You may assume that no other customer has this phone number. 
[2 marks] */
-- Query the Vehicles Old Information based on Customer Information
SELECT * FROM vehicle WHERE cust_no = (
    SELECT cust_no FROM customer WHERE cust_phone = '6715573197'
); 

-- Update the Registration Number
UPDATE vehicle
SET veh_rego = 'GDD132'
WHERE cust_no = (
    SELECT cust_no FROM customer WHERE cust_phone = '6715573197'
); 
COMMIT; 

-- Query the Vehicles New Information based on Customer Information
SELECT * FROM vehicle WHERE cust_no = (
    SELECT cust_no FROM customer WHERE cust_phone = '6715573197'
); 


/* (c) 
Customer number 1030, Farrel Grazier has brought her Mazda CX-5 in for a service (the date should be treated as 21 March 2024). 
She brought it in at 8:30 am and required the vehicle back by 12 noon. The vehicle has completed 12,000 km and she will be paying 
by 'cash' if any charges are involved (this may be a warranty repair). Her reason for bringing the vehicle in is that the 'Rear seat 
belts are not properly retracting'. You may assume that Farrel Grazier only owns one Mazda CX-5.
[2 marks] */

-- Insert Data into the Service Table 
INSERT INTO service (
   serv_no, serv_date, serv_drop_off, serv_req_pickup, 
   serv_kms, serv_instructions, pay_mode_code, veh_vin, cust_no)
SELECT 
   service_seq.NEXTVAL, 
   TO_DATE('21-Mar-2024','DD-MON-YYYY'), 
   TO_DATE('21-Mar-2024 08:30:00', 'DD-MON-YYYY HH24:MI:SS'), 
   TO_DATE('21-Mar-2024 12:00:00', 'DD-MON-YYYY HH24:MI:SS'), 
   12000,
   'Rear seat belts are not properly retracting',
   'S',
   veh_vin,
   1030
FROM VEHICLE
WHERE cust_no = 1030 AND UPPER(veh_make) = UPPER('Mazda') AND UPPER(veh_model) = ('CX-5'); 

-- Query the Service Table to see if the Service Record is Recorded
SELECT * FROM service 
WHERE cust_no = 1030 AND UPPER(TO_CHAR(serv_drop_off, 'DD-MON-YYYY HH24:MI:SS')) = UPPER('21-MAR-2024 08:30:00');


/* (d) 
One of the MA mechanics starts the service job for the service in (c) above, they determine that the retraction issue was due to the 
belt mechanism being jammed. They remove the material causing the jam, so no parts are required. MA decides, in the interests of customer 
goodwill, that there will be no labour cost. The vehicle is ready to be picked up at 9:10 am. Make these changes to the data in the database. 
These changes must be treated as a single transaction.
[3 marks] */

-- Step 1: Update the Service Job
INSERT INTO service_job VALUES (
   service_seq.CURRVAL, 
   1, 
   'Rear seat belt jam removal - no parts needed'
); 

-- Query the Service Job Table to see records
SELECT * FROM service_job WHERE serv_no = (SELECT MAX(serv_no) FROM service) AND sj_job_no = 1; 

-- Step 2: Update the Service Table with New Service Job
UPDATE service 
SET 
   serv_ready_pickup = TO_DATE('21-Mar-2024 09:10:00', 'DD-MON-YYYY HH24:MI:SS'), 
   serv_labour_cost = 0,
   serv_parts_cost = 0
WHERE serv_no = (
   SELECT serv_no 
   FROM service 
   WHERE 
      cust_no = 1030
      AND UPPER(TO_CHAR(serv_drop_off, 'DD-MON-YYYY HH24:MI:SS')) = UPPER('21-MAR-2024 08:30:00')
);

COMMIT; 

-- Query the Service Table to see if the Service Record is Recorded
SELECT * 
FROM service 
WHERE 
   cust_no = 1030 
   AND UPPER(TO_CHAR(serv_drop_off, 'DD-MON-YYYY HH24:MI:SS')) = UPPER('21-MAR-2024 08:30:00');


/* (e) 
(e) Monash Automotive have decided that they no longer wish to source any parts from 'Australian Automotive Parts'. An audit of MA's part usage 
shows that although they have several different Australian Automotive Parts items in stock, they have never been used for any service. They will 
return these items to the vendor and thus remove them from the MA part stock. 
[2 marks]*/

SELECT * FROM PART; 
SELECT * FROM VENDOR; 
-- Verify which Part has be used in Part Charged
SELECT * 
FROM 
   part p1
   JOIN part_charge p2 ON p1.part_code = p2.part_code
WHERE
   p1.vendor_id = (
      SELECT 
        vendor_id
      FROM 
        vendor
      WHERE
        UPPER(vendor_name) = UPPER('Australian Automotive Parts')
   );

DELETE FROM part
WHERE vendor_id = (SELECT vendor_id FROM vendor WHERE UPPER(vendor_name) = UPPER('Australian Automotive Parts'))
AND part_code NOT IN (
    SELECT DISTINCT PART_CODE
    FROM part_charge
);

SELECT * FROM PART; 

COMMIT; 

