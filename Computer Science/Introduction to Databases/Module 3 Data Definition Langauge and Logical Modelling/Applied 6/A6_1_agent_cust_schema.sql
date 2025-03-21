
-- Applied 6-1: Create Customer Agent Schema
-- Student ID: 30428831
-- Name: Tristan Sim

set echo on
SPOOL A6_1_agent_cust_schema_output.txt
SET DEFINE OFF;

-- ITO 4131 Unit Varaibles: CHAR, VARCHAR2, NUMBER and DATE ONLY
-- When Working with a Foreign Key Definition, Carefully Consider Appropriate Delete Approach (RESTRICT, CASCASE, NULIFY) for each scenario
-- ALTER TABLE is the Best Approach Tables to Create Tables first without errors and apply the Required Constraints

-- Clear Any Tables in the SQL Database (Deletion Order: FK Entity -> PK Entity)
DROP TABLE customer CASCADE constraints PURGE; 
DROP TABLE property_agent CASCADE constraints PURGE; 

-- Create an Agent Table
CREATE TABLE property_agent (
    -- Column Constraints (Since it's the only Primary Key, a Single Attribute/Column Constraint is enough)
    prop_agent_code        NUMBER(3) CONSTRAINT agent_pk PRIMARY KEY,  -- Column Constraints (Primary Key)
    prop_agent_areacode    NUMBER(3) NOT NULL,
    prop_agent_phone       CHAR(8) NOT NULL,
    prop_agent_lname       VARCHAR2(50) NOT NULL, 
    prop_agent_ytd_sales   NUMBER(8,2) NOT NULL
);

-- Comments for Agent Table
COMMENT ON COLUMN property_agent.prop_agent_code IS 'Agent Code (Unique For Each Agent)'; 
COMMENT ON COLUMN property_agent.prop_agent_areacode IS 'Area Code of Agent';
COMMENT ON COLUMN property_agent.prop_agent_phone IS 'Agent Phone Number';
COMMENT ON COLUMN property_agent.prop_agent_lname IS 'Agent Last Name';
COMMENT ON COLUMN property_agent.prop_agent_ytd_sales IS 'Agent Year to Date (YTD) Sales Made';

-- Create an Customer Table
CREATE TABLE customer (
    cust_code         NUMBER(5) NOT NULL,
    cust_lname        VARCHAR(50) NOT NULL,
    cust_fname        VARCHAR(50) NOT NULL,
    cust_initial      CHAR(1),
    cust_renew_date   DATE NOT NULL,
    agent_code        NUMBER(3),  -- Declared as a Foreign Key Below

    -- Table Constraint (Composite Key requires a Table Constraint to refer to Multiple Attributes/Columns)
    CONSTRAINT customer_pk PRIMARY KEY (cust_code),
    CONSTRAINT customer_agent_fk FOREIGN KEY (agent_code) 
        REFERENCES property_agent(prop_agent_code) ON DELETE SET NULL -- Overrides the RESTRICT Constraint
    -- When Working with a Foreign Key Definition, Carefully Consider Appropriate Delete Approach (RESTRICT, CASCASE, NULIFY) for each scenario
);

COMMENT ON COLUMN customer.cust_code IS 'Customer Code (Unique for Each Customer)';
COMMENT ON COLUMN customer.cust_lname IS 'Customer Last Name';
COMMENT ON COLUMN customer.cust_fname IS 'Customer First Name';
COMMENT ON COLUMN customer.cust_initial IS 'Initial of Customer (not mandatory)';
COMMENT ON COLUMN customer.cust_renew_date IS 'Insurance renewal date of customer';
COMMENT ON COLUMN customer.agent_code IS 'Agent Code (Uique for each agent)';

SPOOL off
set echo off