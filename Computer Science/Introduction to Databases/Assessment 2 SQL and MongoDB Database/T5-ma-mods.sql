/*****PLEASE ENTER YOUR DETAILS BELOW*****/
-- ITO4132
--T5-ma-mods.sql

--Student ID: 30428831
--Student Name: Tristan Sim

/* Comments for your marker:

For Task 5) B) Introduct a "part_restock_delivered" Attribute for users to Track if
Restock Parts Ordered have been Delivered or Restocked Parts Ordered but Not Delivered yet or Part Not
(Part Restock Delivered Status- N: Parts NOT Ordered | O: Parts Ordered but Not Delivered yet |  D: Ordered Parts Delivered)

*/

/* Drop Sequences if they were Previously Created */

DROP TABLE part_purchase CASCADE CONSTRAINTS PURGE;
DROP SEQUENCE part_sale_no_seq;
DROP TABLE part_sale CASCADE CONSTRAINTS PURGE;
DROP SEQUENCE part_purchase_no_seq;

/* (a)
Task Breakdown: 
1) Create a "Spare Part Sale Receipt" Table (Sale Number a Surrogate Primary Key starting at 100 | Total Paid: 99999.99)
   (Attributes: cust_no(FK), sale_no (PK), sale_date, sale_total_paid)
2) Create a "Parts Purchased" Table (part_pur_no is a Surrogate Primary Key | Unit Price: 9999.99 | Max Purchased Quantity: 99)
   (Attributes: part_pur_no (PK), sale_no(FK), part_code(FK), part_pur_unit_price, part_pur_quanity
3) Modify the Database to Accomate these changes: 
   "Customer" <- Buys -> "Spare Part Sale Receipt" <- Contains -> "Parts Purchased" <- Referenced From -> "Part" 

Pseudocode:
1) Create "Part_Sale" Table
2) Add Comments for Each Attributes
3) Create a Seqeunce Starting at 100
4) Add Foreign Key Constraint to Link to "cust_no" from "Customer" Table

5) Create "Part_Purchased" Table
6) Add Comments for Each Attributes
7) Create a Sequence Starting at 1
8) Add a Foreign Key Constraint to Link to Part_Sale(sale_no) and Part(part_cpde)
*/
CREATE TABLE part_sale (
    part_sale_no           NUMBER(6) PRIMARY KEY,
    cust_no                NUMBER(6) NOT NULL,
    part_sale_date         DATE,
    part_sale_total_paid   NUMBER(7,2) DEFAULT 0 NOT NULL 
);

COMMENT ON COLUMN part_sale.part_sale_no IS 'Part Sale Number - Surrogate Primary Key'; 
COMMENT ON COLUMN part_sale.cust_no IS 'Customer Number - Foreign Key Referenced From Customer Table';
COMMENT ON COLUMN part_sale.part_sale_date IS 'Part Sale Date - The Date the Part Sale was Transacted';
COMMENT ON COLUMN part_sale.part_sale_total_paid IS 'Total Paid for All Parts Sold in the Sale - (Example: 9999.99)';

CREATE SEQUENCE part_sale_no_seq START WITH 100 INCREMENT BY 1; 

ALTER TABLE part_sale ADD CONSTRAINT part_sale_customer_fk FOREIGN KEY (cust_no) REFERENCES customer(cust_no);

COMMIT;


CREATE TABLE part_purchase (
    part_pur_no           NUMBER(6) PRIMARY KEY,
    part_sale_no          NUMBER(6) NOT NULL,
    part_code             CHAR(6) NOT NULL,
    part_pur_unit_price   NUMBER(6,2) DEFAULT 0 NOT NULL, 
    part_pur_quantity     NUMBER(2) DEFAULT 0 NOT NULL
);

COMMENT ON COLUMN part_purchase.part_pur_no IS 'Part Purchase Number - Primary Surrogate Key';
COMMENT ON COLUMN part_purchase.part_sale_no IS 'Part Sale Number - Foreign Key Referenced From Part Sale Table';
COMMENT ON COLUMN part_purchase.part_code IS 'Part Code - Foreign KeyReference From the Part Code Table';
COMMENT ON COLUMN part_purchase.part_pur_unit_price IS 'Part Purchased Unit Price';
COMMENT ON COLUMN part_purchase.part_pur_quantity IS 'Part Purchase Quantity - Max of 99 Units'; 

CREATE SEQUENCE part_purchase_no_seq START WITH 1 INCREMENT BY 1; 

ALTER TABLE part_purchase ADD CONSTRAINT part_pur_part_sale_fk FOREIGN KEY (part_sale_no) REFERENCES part_sale(part_sale_no);
ALTER TABLE part_purchase ADD CONSTRAINT part_pur_part_fK FOREIGN KEY (part_code) REFERENCES part(part_code);
ALTER TABLE part_purchase ADD CONSTRAINT part_pur_quantity_max_chk CHECK (part_pur_quantity >= 0 AND part_pur_quantity <= 99);

COMMIT;



/* (b)
Task Breakdown: 
1) Modify "Part" Table to Include 'part_restock_level' Attribute
2) Modify "Part" Table to Include 'part_restock_date' Attribute 
3) Modify "Part" Table to Include 'part_restock_delivered' Attribute to Track if it has been delivered, ordered or not ordered
  (Part Restock Delivered Status- N: Parts NOT Ordered | O: Parts Ordered but Not Delivered yet |  D: Ordered Parts Delivered)
4) Update the Existing Part Table to Set a Default Restock Level of "part_stock/2" and Restock Data 1 JAN 2024
5) After all Parts have their attributes filled, 'part_restock_level' and 'part_last_restock_date' will have to the set to NOT NULL
6) Add Constraints to the 'part_restock_status' Attribute

Remarks: Need to Round the Part to Upper Value if 5.5 round to 6, use CEIL Function

Pseudocode:
1) Modify the Parts Table to Include New Attributes
2) Add Comments to these New Attributes
3) Update Existing Part Record to have Default Restock Level and Date
4) Alter Table where Restock Level and Date Must be Not NULL
5) Add Constraints
*/

DESC PART;

ALTER TABLE part ADD (
   part_restock_level       NUMBER(3), 
   part_restock_date        DATE,
   part_restock_delivered   CHAR(1) DEFAULT 'N' NOT NULL
); 

COMMENT ON COLUMN part.part_restock_level IS 'Part Restock Level - Manually Entered by User When Adding New Part'; 
COMMENT ON COLUMN part.part_restock_date IS 'Restocked Date when Last Restock was Delivered - Manually Entered by User When Adding New Part'; 
COMMENT ON COLUMN part.part_restock_delivered IS 'Part Restock Delivered Status- N: Parts NOT Ordered | O: Parts Ordered but Not Delivered yet |  D: Ordered Parts Delivered';

UPDATE part
SET 
   part_restock_level = CEIL(part_stock/2),
   part_restock_date = TO_DATE('01-JAN-2024', 'DD-MON-YYYY'),
   part_restock_delivered = 'D'; 

ALTER TABLE part
MODIFY (
   part_restock_level       NUMBER(3) NOT NULL, 
   part_restock_date        DATE NOT NULL
);

ALTER TABLE part ADD CONSTRAINT part_restock_delivered_chk CHECK (part_restock_delivered IN ('N', 'O', 'D'));

DESC PART;

COMMIT;
