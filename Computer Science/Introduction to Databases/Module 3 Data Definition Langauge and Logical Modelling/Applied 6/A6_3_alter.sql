
/*
Applied 6-2: Create Student Enrolment Schema
Student ID: 30428831
Name: Tristan Sim
last modified date: 19/03/2025
*/

set echo on
SPOOL A6_2_stud_enrol_schema_output.txt
SET DEFINE OFF;

-- Place DROP commands at head of schema file
DROP TABLE course CASCADE CONSTRAINTS PURGE;
DROP TABLE course_unit CASCADE CONSTRAINTS PURGE;

-- Task 1: Create New Column in UNIT Table which repesents Credit Point
--         Add Constraint where Credit Point must be 3, 6 or 12
ALTER TABLE unit ADD (
    unit_credit_points  NUMBER(2,0) DEFAULT 6 NOT NULL, --  Column Constraint: Default Value 6
    CONSTRAINT unit_credits_points_check CHECK (unit_credit_points IN (3, 6 , 12))
);

COMMENT ON COLUMN unit.unit_credit_points IS 'Unit Credit Points'; 
DESC unit; -- Describe the Unit to Verify Successful Creation


-- Task 2: Create Course Table to Store Course Details
CREATE Table course (
    course_code          VARCHAR2(7) NOT NULL, 
    course_name          VARCHAR2(50) NOT NULL,
    course_totalpoints   NUMBER(3,0) NOT NULL
);

ALTER TABLE course ADD CONSTRAINT course_code_pk PRIMARY KEY (course_code); 

COMMENT ON COLUMN course.course_code IS 'Course Code (Primary Key)';
COMMENT ON COLUMN course.course_name IS 'Course Name';
COMMENT ON COLUMN course.course_totalpoints IS 'Course Total Points';

-- Task 3: Create a Bridging Entity (For Unit and Course)
--         (Each course includes several units, and each unit can be part of multiple courses. 
--         For example, FIT1047 and FIT1045 are part of C2000 and C2001 courses.)

CREATE TABLE course_unit (
    course_code   VARCHAR2(7) NOT NULL, 
    unit_code     CHAR(7) NOT NULL
); 

COMMENT ON COLUMN course_unit.course_code IS 'Course Code';
COMMENT ON COLUMN course_unit.unit_code IS 'Unit Code';

ALTER TABLE course_unit ADD CONSTRAINT course_unit_pk PRIMARY KEY (course_code, unit_code); 

-- Task 4: Adding Foreign Keys to Course Unit 

ALTER TABLE course_unit 
   ADD CONSTRAINT courseunit_unit_fk FOREIGN KEY (unit_code)
       REFERENCES unit (unit_code);

ALTER TABLE course_unit 
   ADD CONSTRAINT courseunit_course_fk FOREIGN KEY (course_code)
       REFERENCES course (course_code);

DESC course;
DESC course_unit;

SPOOL off
set echo off