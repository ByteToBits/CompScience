/*
Applied 6-1: Create Student Enrolment Schema
Student ID: 30428831
Name: Tristan Sim
last modified date: 19/03/2025
*/

set echo on
SPOOL A6_2_stud_enrol_schema_output.txt
SET DEFINE OFF;

-- Place DROP commands at head of schema file
DROP TABLE student CASCADE constraints PURGE;
DROP TABLE enrollment CASCADE constraints PURGE;
DROP TABLE unit CASCADE constraints PURGE;

/* Create STUDENT Table and use Alter to Add Constraints ----------------------------- */

/* Step 1: Create Student Table */
CREATE TABLE student (
    stu_nbr     NUMBER(8) NOT NULL,
    stu_lname   VARCHAR2(50) NOT NULL,
    stu_fname   VARCHAR2(50) NOT NULL,
    stu_dob     DATE NOT NULL
);

/* Step 2: Add Comments for Documentation */ 
COMMENT ON COLUMN student.stu_nbr IS 'Student number';
COMMENT ON COLUMN student.stu_lname IS 'Student last name';
COMMENT ON COLUMN student.stu_fname IS 'Student first name';
COMMENT ON COLUMN student.stu_dob IS 'Student date of birth';

/* Step 3: Add Constraints */
ALTER TABLE student ADD CONSTRAINT student_pk PRIMARY KEY (stu_nbr); -- Set as Primary Key
ALTER TABLE student ADD CONSTRAINT sudent_nbr_check CHECK (stu_nbr > 10000000);  -- Enforce stu_nbr criteria
ALTER TABLE student ADD CONSTRAINT student_dob_chedck CHECK (stu_dob < SYSDATE); -- Date of Birth Cannot be the Future


/* Create UNIT Table and use Alter to Add Constraints ---------------------------------- */

/* Step 1: Create Unit Table */
CREATE TABLE unit (
    unit_code   CHAR(7) NOT NULL,
    unit_name   VARCHAR2(50) NOT NULL
);

/* Step 2: Add Comments for Documentation */ 
COMMENT ON COLUMN unit.unit_code IS 'Unit code';
COMMENT ON COLUMN unit.unit_name IS 'Unit name';

/* Step 3: Add Constraints */
ALTER TABLE unit ADD CONSTRAINT unit_pk PRIMARY KEY (unit_code); -- Set as Primary Key
ALTER TABLE unit ADD Constraint unit_name_check_unique UNIQUE (unit_name); -- Set Unit Name as Unique


/* Create ENROLMENT Table and use Alter to Add Constraints ---------------------------------- */

/* Step 1: Create Unit Table */
CREATE TABLE enrolment (
    stu_nbr          NUMBER(8,0) NOT NULL,
    unit_code        CHAR(7) NOT NULL,
    enrol_year       NUMBER(4) NOT NULL,
    enrol_semester   CHAR(1) NOT NULL,
    enrol_mark       NUMBER(3),
    enrol_grade      CHAR(2)
);

/* Step 2: Add Comments for Documentation */ 
COMMENT ON COLUMN enrolment.stu_nbr IS 'Student number';
COMMENT ON COLUMN enrolment.unit_code IS 'Unit code';
COMMENT ON COLUMN enrolment.enrol_year IS 'Enrolment year';
COMMENT ON COLUMN enrolment.enrol_semester IS 'Enrolment semester';
COMMENT ON COLUMN enrolment.enrol_mark IS 'Enrolment mark (real)';
COMMENT ON COLUMN enrolment.enrol_grade IS 'Enrolment grade (letter)';

/* Step 3: Add Constraints */
-- Define the Primary Key (Composite Key)
ALTER TABLE enrolment ADD CONSTRAINT enrolment_pk PRIMARY KEY (stu_nbr, unit_code, enrol_year, enrol_semester); 
-- Define the Foreign Key -- Delete Enrollment Records after Student has been Deleted
ALTER TABLE enrolment ADD CONSTRAINT enrolment_student_fk FOREIGN KEY (stu_nbr) REFERENCES student(stu_nbr) ON DELETE CASCADE;
-- Define the Foreign Key -- Delete Unit Records but Maintain Enrollment Record
ALTER TABLE enrolment ADD CONSTRAINT enrolment_unit_fk FOREIGN KEY (unit_code) REFERENCES unit(unit_code) ON DELETE SET NULL;
-- Define the Enrol Semester Constraint (Allowed Values: 1, 2, 3)
ALTER TABLE enrolment ADD CONSTRAINT enrol_semester_check CHECK (enrol_semester IN ('1', '2' , '3'));

SPOOL off
set echo off