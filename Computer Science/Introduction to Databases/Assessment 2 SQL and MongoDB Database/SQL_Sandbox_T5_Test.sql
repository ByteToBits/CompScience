
/*
Script: SQL Sandbox To Test Question 5
Institution: Monash University Australia
Subject: ITO 4132 Introduction to Databases
Database Type:: Oracle SQL Database

Student Name: Tristan Sim 
Last Modified Date: 2nd April 2025
*/

-- Delete this Customer Data First
DELETE FROM part_purchase WHERE part_sale_no IN (SELECT part_sale_no FROM part_sale WHERE cust_no = 1456);
DELETE FROM part_sale WHERE cust_no = 1456;
DELETE FROM customer WHERE cust_no = 1456;
COMMIT;

-- Populate Some Data and Query to Test Results and Test Part A
INSERT INTO customer VALUES (
   1456,
   'Wile E Coyote',
   '123 Desert Chase',
   'NeverEnding, Vic',
   '3234',
   '9903262'
);

INSERT INTO part_sale(part_sale_no, cust_no, part_sale_date, part_sale_total_paid) VALUES(
   part_sale_no_seq.NEXTVAL,
   1456,
   TO_DATE('01-JUL-2023','DD-MON-YYYY'),
   1540.00
); 

INSERT INTO part_purchase VALUES (
   part_purchase_no_seq.NEXTVAL, 
   part_sale_no_seq.CURRVAL,
   'ONE2-5',
   1450.00,
   1
);

INSERT INTO part_purchase VALUES (
   part_purchase_no_seq.NEXTVAL, 
   part_sale_no_seq.CURRVAL,
   'TPS146',
   450.00,
   2
);

COMMIT;

SELECT * FROM part_sale ps JOIN customer c ON ps.cust_no = c.cust_no WHERE c.cust_no = 1456;

SELECT 
   ps.part_sale_no,
   pp.part_pur_no,
   p.part_code,
   p.part_description, 
   pp.part_pur_unit_price,
   pp.part_pur_quantity,
   TO_CHAR(pp.part_pur_unit_price * pp.part_pur_quantity, '9990.99') AS part_pur_amount
FROM 
   part_purchase pp 
   JOIN part_sale ps ON ps.part_sale_no = pp.part_sale_no
   JOIN part p ON pp.part_code = p.part_code; 


SELECT 
    ps.part_sale_no,
    c.cust_no,
    c.cust_name,
    TO_CHAR(SUM(pp.part_pur_unit_price * pp.part_pur_quantity), '9990.99') AS grand_total
FROM 
    part_sale ps
JOIN 
    customer c ON ps.cust_no = c.cust_no
JOIN 
    part_purchase pp ON ps.part_sale_no = pp.part_sale_no
WHERE 
    c.cust_no = 1456
GROUP BY 
    ps.part_sale_no, c.cust_no, c.cust_name;


-- ROLLBACK;

-- SELECT * FROM part;