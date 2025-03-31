
-- Applied 6-1: Create Customer Agent Schema
-- Student ID: 30428831
-- Name: Tristan Sim

-- Run Applied 6-2: Create Student Enrolment Schema

set echo on
SPOOL applied7_dml_output.txt
SET DEFINE OFF;

-- ITO 4131 Unit Varaibles: CHAR, VARCHAR2, NUMBER and DATE ONLY

-- Drop the Sequence to Prevent Errors
DROP SEQUENCE STUDENT_SEQ;

--- Transaction 1: ==INSERT==-- =
/*1. Write SQL INSERT statements to add the data into the specified tables */
-- Student
INSERT INTO student (stu_nbr, stu_lname, stu_fname, stu_dob) VALUES (11111111, 'Bloggs', 'Fred', TO_DATE('01-Jan-2003', 'DD-Mon-YYYY'));
INSERT INTO student VALUES (11111112, 'Nice', 'Nick', TO_DATE('10-Oct-2004', 'DD-Mon-YYYY'));
INSERT INTO student VALUES (11111113, 'Wheat', 'Wendy', TO_DATE('05-May-2005', 'DD-Mon-YYYY'));
INSERT INTO student VALUES (11111114, 'Sheen', 'Cindy', TO_DATE('25-Dec-2004', 'DD-Mon-YYYY'));

-- UNIT
INSERT INTO unit (unit_code, unit_name) VALUES ('FIT9999', 'FIT Last Unit');
INSERT INTO unit (unit_code, unit_name) VALUES ('FIT9132', 'Introduction to Databases');
INSERT INTO unit (unit_code, unit_name) VALUES ('FIT9161', 'Project');
INSERT INTO unit (unit_code, unit_name) VALUES ('FIT5111', 'Student''s Life');

-- Enrollment 
INSERT INTO enrolment VALUES (11111111, 'FIT9132', 2022, 1, 35, 'N');
INSERT INTO enrolment VALUES (11111111, 'FIT9161', 2022, 1, 61, 'C');
INSERT INTO enrolment VALUES (11111111, 'FIT9132', 2022, 2, 42, 'N');
INSERT INTO enrolment VALUES (11111111, 'FIT5111', 2022, 2, 76, 'D');
INSERT INTO enrolment (stu_nbr, unit_code, enrol_year, enrol_semester) VALUES (11111111, 'FIT9132', 2023, 1);
INSERT INTO enrolment VALUES (11111112, 'FIT9132', 2022, 2, 83, 'HD');
INSERT INTO enrolment VALUES (11111112, 'FIT9161', 2022, 2, 79, 'D');
INSERT INTO enrolment (stu_nbr, unit_code, enrol_year, enrol_semester) VALUES (11111113, 'FIT9132', 2023, 1);
INSERT INTO enrolment (stu_nbr, unit_code, enrol_year, enrol_semester) VALUES (11111113, 'FIT5111', 2023, 1);
INSERT INTO enrolment (stu_nbr, unit_code, enrol_year, enrol_semester) VALUES (11111114, 'FIT9132', 2023, 1);
INSERT INTO enrolment (stu_nbr, unit_code, enrol_year, enrol_semester) VALUES (11111114, 'FIT5111', 2023, 1);


COMMIT; -- Makes Changes Permenant

---==INSERT using SEQUENCEs ==--
/*1. Create a sequence for the STUDENT table called STUDENT_SEQ */

CREATE SEQUENCE student_seq START WITH 11111115 INCREMENT BY 1; 

SELECT * FROM cat; -- cat refers to the Oracle Catalogue (The Objects Which you Own)

/*2. Add a new student (MICKEY MOUSE) and an enrolment for this student as listed below, 
treat all the data that you add as a single transaction. */
INSERT INTO student VALUES (
    student_seq.NEXTVAL, 
    'Mouse', 
    'Mickey', 
    TO_DATE('1-Jan-2006', 'DD-Mon-YYYY')
);

-- Assume Mickey Mouse is enrolling in 'FIT9132' in semester 1 of 2024 with mark 88, grade 'HD'
-- Retrieve the last generated student number using CURRVAL
INSERT INTO enrolment VALUES (
    student_seq.CURRVAL,
    'FIT9132', 
    2024, 
    1, 
    88, 
    'HD'
);

COMMIT;

---==Advanced INSERT==--
/*1. A new student has started a course. Subsequently this new student needs to enrol into 
Introduction to databases. Enter the new student's details, then insert his/her enrollment 
to the database using the sequence in combination with a SELECT statement. You can 
make up details of the new student and when they will attempt Introduction to databases 
and you may assume there is only one student with such a name in the system.

You must not do a manual lookup to find the unit code of the Introduction to Databases 
and the student number.
 */

INSERT INTO student VALUES (
    STUDENT_SEQ.nextval, 
    'Gerard', 
    'Frost', 
    TO_DATE('15-Feb-2002', 'DD-Mon-YYYY')
);

-- Insert Enrolment using Sequence + SELECT (Must not do a Manual Lookup)
INSERT INTO enrolment VALUES (

    -- Get the Student Number of the Student Previously Created | Alternative is 'student_seq.currval'
    (SELECT stu_nbr FROM student WHERE upper(stu_lname) = upper('Gerard') AND upper(stu_fname) = upper('Frost')),

    -- Get Unit Code from the Name 'Introduction to Database
    (SELECT unit_code FROM unit WHERE upper(unit_name) = upper('Introduction to Databases')), 

    -- Populate the Remaining Data 
    2025,
    2,
    92,
    'HD'
);

COMMIT;

---=Creating a table and inserting data as a single SQL statement==--
/*1. Create a table called FIT5111_STUDENT. The table should contain all enrolments for the 
unit FIT5111 */

-- Create a new table containing only enrolments for unit 'FIT5111'
DROP TABLE fit5111_student CASCADE CONSTRAINTS PURGE; 

-- Create a Table From Data from another Table using 'AS' 
CREATE TABLE fit5111_student 
   AS 
     SELECT * FROM enrolment WHERE upper(unit_code) = upper('FIT5111'); 

COMMENT ON COLUMN fit5111_student.stu_nbr IS 'Student Number';
COMMENT ON COLUMN fit5111_student.stu_nbr IS 'Student number';
COMMENT ON COLUMN fit5111_student.unit_code IS 'Unit code';
COMMENT ON COLUMN fit5111_student.enrol_year IS 'Enrolment year';
COMMENT ON COLUMN fit5111_student.enrol_semester IS 'Enrolment semester';
COMMENT ON COLUMN fit5111_student.enrol_mark IS 'Enrolment mark (real)';
COMMENT ON COLUMN fit5111_student.enrol_grade IS 'Enrolment grade (letter)';


/*2. Check the table exists */
SELECT * FROM cat; 

/*3. List the contents of the table */
SELECT * FROM fit5111_student;


---==8.2.5 UPDATE==--
/*1. Update the unit name of FIT9999 from 'FIT Last Unit' to 'place holder unit'.*/
SELECT * FROM unit; 
UPDATE unit SET unit_name = 'Placeholder Unit Name' WHERE upper(unit_code) = 'FIT9999'; 

/*2. Enter the mark and grade for the student with the student number of 11111113 
for Introduction to Databases that the student enrolled in semester 1 of 2023. 
The mark is 75 and the grade is D.*/
SELECT * FROM enrolment; 

UPDATE enrolment 
SET enrol_mark = 75, enrol_grade = 'D' 
WHERE stu_nbr = 11111113 AND enrol_semester = 1 AND enrol_year = 2023 
AND unit_code = (SELECT unit_code FROM unit WHERE upper(unit_name) = upper('Introduction to Database')); 

COMMIT; 

/*3. The university introduced a new grade classification scale. 
The new classification are:
0 - 44 is N
45 - 54 is P1
55 - 64 is P2
65 - 74 is C
75 - 84 is D
85 - 100 is HD
Change the database to reflect the new grade classification scale.
*/


/*4. Due to the new regulation, the Faculty of IT decided to change 'Project' unit code 
from FIT9161 into FIT5161. Change the database to reflect this situation.
Note: you need to disable the FK constraint before you do the modification 
then enable the FK to have it active again.
*/



--==DELETE==--
/*1. A student with student number 11111114 has taken intermission in semester 1 2023, 
hence all the enrolment of this student for semester 1 2023 should be removed. 
Change the database to reflect this situation.*/


/*2. The faculty decided to remove all Student's Life unit's enrolments. 
Change the database to reflect this situation.
Note: unit names are unique in the database.*/


/*3. Assume that Wendy Wheat (student number 11111113) has withdrawn from the university. 
Remove her details from the database.*/


SPOOL off
set echo off