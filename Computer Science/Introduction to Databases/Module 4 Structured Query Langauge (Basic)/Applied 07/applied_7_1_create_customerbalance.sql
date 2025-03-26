-- Connect using Connection 1
-- Step 1: Create the table
CREATE TABLE CUSTBALANCE (
    CUST_ID NUMBER,
    CUST_BAL NUMBER
);

-- Step 2: Insert the rows
INSERT INTO CUSTBALANCE (CUST_ID, CUST_BAL) VALUES (1, 100);
INSERT INTO CUSTBALANCE (CUST_ID, CUST_BAL) VALUES (2, 200);

-- Do NOT use COMMIT as per instruction
