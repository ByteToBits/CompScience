
-- Applied 6-1: Create Customer Agent Delete Tables
-- Student ID: 30428831
-- Name: Tristan Sim

-- ITO 4131 Unit Varaibles: CHAR, VARCHAR2, NUMBER and DATE ONLY
-- When Working with a Foreign Key Definition, Carefully Consider Appropriate Delete Approach (RESTRICT, CASCASE, NULIFY) for each scenario
-- ALTER TABLE is the Best Approach Tables to Create Tables first without errors and apply the Required Constraints

-- Clear Any Tables in the SQL Database (Deletion Order: FK Entity -> PK Entity)
DROP TABLE employee CASCADE constraints PURGE;
DROP TABLE department CASCADE constraints PURGE;

-- cat = Catalog which liss all the Items in the recycle bin
select * from cat; 

-- purge Recycle Bin
purge recyclebin; 