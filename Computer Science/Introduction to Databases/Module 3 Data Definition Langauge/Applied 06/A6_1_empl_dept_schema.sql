
-- Applied 6-1: Create Customer Agent Schema
-- Student ID: 30428831
-- Name: Tristan Sim

set echo on
SPOOL A6_1_empl_dept_schema_output.txt
SET DEFINE OFF;

-- ITO 4131 Unit Varaibles: CHAR, VARCHAR2, NUMBER and DATE ONLY
-- When Working with a Foreign Key Definition, Carefully Consider Appropriate Delete Approach (RESTRICT, CASCASE, NULIFY) for each scenario
-- ALTER TABLE is the Best Approach Tables to Create Tables first without errors and apply the Required Constraints

-- Clear Any Tables in the SQL Database (Deletion Order: FK Entity -> PK Entity)
DROP TABLE employee CASCADE constraints PURGE;
DROP TABLE department CASCADE constraints PURGE;

-- Step 1: Create Tables 
-- Create Employee Table 
CREATE TABLE employee (
    emp_num       NUMBER(6) NOT NULL,
    emp_fname     VARCHAR2(20),
    emp_lname     VARCHAR2(25) NOT NULL,
    emp_email     VARCHAR2(25) NOT NULL,
    emp_phone     VARCHAR2(20),
    emp_hiredate  DATE NOT NULL,
    emp_title     VARCHAR2(45) NOT NULL,
    emp_comm      NUMBER(2,2),
    dept_num      NUMBER(3)
);
-- Create Department Table 
CREATE TABLE department (
    dept_num       NUMBER(3) NOT NULL,
    dept_name      VARCHAR2(50) NOT NULL,
    dept_mail_box  VARCHAR2(3),
    dept_phone     VARCHAR2(9),
    emp_num        NUMBER(6)
);


-- Step 2: Alter Table Constraints
-- Add Primary Key Constraints to Employee Table 
ALTER TABLE employee
ADD CONSTRAINT employee_pk PRIMARY KEY (emp_num); 

ALTER TABLE department 
ADD CONSTRAINT department_pk PRIMARY KEY (dept_num);

-- Add Foreign Key Constraints 
ALTER TABLE employee
ADD CONSTRAINT emp_dept_fk FOREIGN KEY (dept_num)
REFERENCES department(dept_num) ON DELETE SET NULL;

ALTER TABLE department
ADD CONSTRAINT dept_emp_fk FOREIGN KEY (emp_num)
REFERENCES employee(emp_num) ON DELETE SET NULL;

SPOOL off
set echo off