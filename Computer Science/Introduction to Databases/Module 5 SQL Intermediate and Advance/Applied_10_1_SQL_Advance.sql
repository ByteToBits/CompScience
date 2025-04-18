
/*
Applied 10-1: SQL Advance
Institution: Monash University Australia
Subject: ITO 4132 Introduction to Databases
Database Type:: Oracle SQL Database

Student Name: Tristan Sim 
Last Modified Date: 10th April 2025
*/

-- SPOOL output.txt
-- SET LINESIZE 300


/* 1. Assuming that the student name is unique, display Claudette Serman’s academic record. 
Include the unit code, unit name, year, semester, mark and explained_grade in the listing. The Explained Grade 
column must show Fail for N, Pass for P, Credit for C, Distinction for D and High Distinction for HD. Order by year, 
within the same year order the list by semester, and within the same semester order the list by the unit code. */

SELECT
   unitcode,
   unitname,
   TO_CHAR(ofyear, 'yyyy') AS year,
   ofsemester,
   enrolmark,
   CASE upper(enrolgrade)
       WHEN 'N' THEN
          'Fail'
       WHEN 'P' THEN
          'Pass'
       WHEN 'C' THEN
          'Credit'
       WHEN 'D' THEN
          'Distinction'
       WHEN 'HD' THEN
          'High Distinction'
   END AS explained_grade
FROM
   uni.enrolment NATURAL JOIN uni.unit
WHERE
   stuid = (
       SELECT 
          stuid
       FROM
          uni.student
       WHERE
          upper(stufname) = upper('Claudette')
          AND upper(stulname) = upper('Serman')
   )
ORDER BY
   year,
   ofsemester,
   unitcode; 
/* Alternate Approach using Oracle's Decode */
SELECT
    unitcode,
    unitname,
    EXTRACT(YEAR FROM ofyear)  AS year,
    ofsemester,
    enrolmark,
    DECODE(UPPER(enrolgrade), 'N', 'Fail', 'P', 'Pass',
           'C', 'Credit', 'D', 'Distinction', 'HD',
           'High Distinction') AS explained_grade
FROM
    uni.enrolment
    NATURAL JOIN uni.unit
    NATURAL JOIN uni.student
WHERE
    upper(stufname) = upper('Claudette')
    AND UPPER(stulname) = upper('Serman')
ORDER BY
    year,
    ofsemester,
    unitcode;



/* 2. Find the number of scheduled classes assigned to each staff member for each semester in 2019. 
If the number of classes is 2 then this should be labelled as a correct load, more than 2 as an overload and less than 2 as an underload.
Include the staff id, staff first name, staff last name, semester, number of scheduled classes and load in the listing. Sort the 
list by decreasing order of the number of scheduled classes and when the number of classes is the same, sort by the staff id then by the semester.
*/

DESC uni.unit;
DESC uni.schedclass;
DESC uni.staff;

SELECT 
   s.staffid,
   s.stafffname,
   s.stafflname,
   sh.ofsemester,
   COUNT(*) AS numberclasses, 
   CASE
      WHEN COUNT(*) > 2 THEN
        'Overload'
      WHEN COUNT(*) = 2 THEN
        'Correct Load'
      ELSE
        'Underload'
   END AS load
FROM
   uni.schedclass sh
   JOIN uni.staff s ON s.staffid = sh.staffid
WHERE
   TO_CHAR(ofyear, 'yyyy') = '2019'
GROUP BY
   s.staffid,
   s.stafffname,
   s.stafflname,
   sh.ofsemester
ORDER BY
   numberclasses DESC, s.staffid ASC, sh.ofsemester ASC;


/* 3. Find the total number of prerequisite units for all units. Include in the list the unit code of units that do not have a prerequisite. 
Order the list in descending order of the number of prerequisite units and where several units have the same number of prerequisites order then by unit code.
*/

DESC uni.unit;
DESC uni.prereq;

SELECT 
   u.unitcode,
   COUNT(PREREQUNITCODE) AS number_of_prerequisites
FROM
   uni.unit u 
   LEFT OUTER JOIN uni.prereq p ON u.unitcode = p.unitcode
GROUP BY
   u.unitcode
ORDER BY 
   number_of_prerequisites DESC, u.unitcode;


/* 4. Display the unit code and unit name for units that do not have a prerequisite. Order the list by unit code. There are many approaches that you
 can take in writing an SQL statement to answer this query. You can use the SET OPERATORS, OUTER JOIN and a SUBQUERY. Write SQL statements 
 based on all three approaches. */

-- Using Outer Join
SELECT
   u.unitcode,
   u.unitname
FROM
   uni.unit u
   LEFT OUTER JOIN uni.prereq p ON u.unitcode = p.unitcode
WHERE
   p.prerequnitcode IS NULL
ORDER BY
   u.unitcode;

-- Using Subquery
SELECT
   unitcode,
   unitname
FROM
   uni.unit
WHERE
   unitcode NOT IN (
      SELECT
         unitcode
      FROM
         uni.prereq
   )
ORDER BY
   unitcode;

-- Using Operator MINUS
SELECT
   u.unitcode,
   unitname
FROM
   uni.unit u 
MINUS
SELECT
   u.unitcode,
   unitname
FROM
   uni.unit u
   JOIN uni.prereq p ON u.unitcode = p.unitcode
ORDER BY
   unitcode;


/* 5. List the unit code, semester, number of enrolments and the average mark for each unit offering in 2019. 
A unit offering is a particular unit in a particular semester for a particular year - for example the offering of FIT3176 in 
semester 2 of 2019 is one offering. Include offerings without any enrolment in the list. Round the average to 2 digits after the decimal point. 
If the average result is 'null', display the average as 0.00. The average must be shown with two decimal digits and right aligned. Order the list 
by the average mark, and when the average mark for several offerings is the same, sort by the semester then by the unit code.
*/

DESC uni.offering;
DESC uni.enrolment;

SELECT
   unitcode,
   ofsemester,
   COUNT(stuid) AS number_of_enrollments,
   LPAD(TO_CHAR(NVL(AVG(enrolmark), 0), '990.99'), 12, ' ') AS average_mark
FROM
   uni.offering
   LEFT OUTER JOIN uni.enrolment USING (ofyear, ofsemester, unitcode)
WHERE
   EXTRACT (YEAR from ofyear) = 2019
GROUP BY
   unitcode,
   ofsemester
ORDER BY
   average_mark,
   ofsemester,
   unitcode;


/* 6. List all units offered in semester 2 2019 which do not have any students enrolled. Include the unit code, unit name, and the chief examiner's 
name in a single column titled ce_name. Order the list based on the unit code.
*/ 

DESC uni.offering;
DESC uni.enrolment;
DESC uni.staff;
DESC uni.unit;

SELECT
  o.unitcode,
  unitname, 
  s.stafffname || ' ' || s.stafflname 
FROM
  (( uni.offering o
  LEFT OUTER JOIN uni.enrolment e 
      ON o.unitcode = e.unitcode
      AND o.ofyear = e.ofyear
      AND o.ofsemester = e.ofsemester)
  JOIN uni.staff s ON s.staffid = o.staffid)
  JOIN uni.unit u ON o.unitcode = u.unitcode
WHERE
  TO_CHAR(o.ofyear, 'yyyy') = '2019'
  AND o.ofsemester = 2
GROUP BY
  o.unitcode,
  unitname,
  stafffname || ' ' || stafflname
HAVING
  COUNT(e.stuid) = 0
ORDER BY
  unitcode;


/* 7. List the id and full name (in a single column titled student_full_name) of students who are enrolled in both Introduction to databases and 
Introduction to computer architecture and networks (note: both unit names are unique) in semester 1 2020. You should note that the case provided for 
these unit names does not necessarily match the case in the database. Order the list by the student id.
*/

SELECT
    stuid,
    stufname
    || ' '
    || stulname AS student_full_name
FROM
    uni.student
WHERE
    stuid IN (
        SELECT
            stuid
        FROM
            uni.enrolment
            NATURAL JOIN uni.unit
        WHERE
            lower(unitname) = lower('Introduction to databases')
            AND ofsemester = 1
            AND to_char(ofyear, 'yyyy') = '2020'
        INTERSECT
        SELECT
            stuid
        FROM
            uni.enrolment
            NATURAL JOIN uni.unit
        WHERE
            lower(unitname) = lower('Introduction to computer architecture and networks')
            AND ofsemester = 1
            AND to_char(ofyear, 'yyyy') = '2020'
    )
ORDER BY
    stuid;


/* 8. Given that the payment rate for a tutorial is $42.85 per hour and the payment rate for a lecture is $75.60 per hour, calculate the 
weekly payment per type of class for each staff member in semester 1 2020. In the display, include staff id, staff name, type of class (lecture - L or tutorial - T), 
number of classes, number of hours (total duration), and weekly payment (number of hours * payment rate). The weekly payment must be displayed to two decimal 
points and right aligned. Order the list by the staff id and for a given staff id by the type of class.
*/ 

SELECT
    staffid,
    stafffname
    || ' '
    || stafflname AS staffname,
    'Lecture' AS type,
    COUNT(*) AS no_of_classes,
    SUM(clduration) AS total_hours,
    lpad(to_char(SUM(clduration) * 75.60, '$999.99'), 14, ' ') AS weekly_payment
FROM
    uni.schedclass
    NATURAL JOIN uni.staff
WHERE
    upper(cltype) = 'L'
    AND ofsemester = 1
    AND to_char(ofyear, 'yyyy') = '2020'
GROUP BY
    staffid,
    stafffname
    || ' '
    || stafflname
UNION
SELECT
    staffid,
    stafffname
    || ' '
    || stafflname AS staffname,
    'Tutorial' AS type,
    COUNT(*) AS no_of_classes,
    SUM(clduration) AS total_hours,
    lpad(to_char(SUM(clduration) * 42.85, '$999.99'), 14, ' ') AS weekly_payment
FROM
    uni.schedclass
    NATURAL JOIN uni.staff
WHERE
    upper(cltype) = 'T'
    AND ofsemester = 1
    AND to_char(ofyear, 'yyyy') = '2020'
GROUP BY
    staffid,
    stafffname
    || ' '
    || stafflname
ORDER BY
    staffid, type;



/* 9. Given that the payment rate for a tutorial is $42.85 per hour and the payment rate for a lecture is $75.60 per hour, calculate the total weekly payment 
(the sum of both tutorial and lecture payments) for each staff member in semester 1 2020. In the display, include staff id, staff name, total weekly payment 
for tutorials, total weekly payment for lectures and the total weekly payment as a single line of output. If the payment is null, show it as $0.00.
 The tutorial payment, lecture payment and total weekly payment must be displayed to two decimal points and right aligned. Order the list by the staff id.
*/ 


SELECT DISTINCT
    s.staffid,
    stafffname
    || ' '
    || stafflname AS staffname,
    lpad(to_char(nvl((
        SELECT
            SUM(clduration) * 42.85
        FROM
            uni.schedclass sc1
        WHERE
            sc1.staffid = sc.staffid
            AND upper(cltype) = 'T'
            AND ofsemester = 1
            AND to_char(ofyear, 'yyyy') = '2020'
    ), 0), '$990.99'), 16, ' ') AS tutorial_payment,
    lpad(to_char(nvl((
        SELECT
            SUM(clduration) * 75.60
        FROM
            uni.schedclass sc1
        WHERE
            sc1.staffid = sc.staffid
            AND upper(cltype) = 'L'
            AND ofsemester = 1
            AND to_char(ofyear, 'yyyy') = '2020'
    ), 0), '$990.99'), 16, ' ') AS lecture_payment,
    lpad(to_char(nvl((
        SELECT
            SUM(clduration) * 75.60
        FROM
            uni.schedclass sc1
        WHERE
            sc1.staffid = sc.staffid
            AND upper(cltype) = 'L'
            AND ofsemester = 1
            AND to_char(ofyear, 'yyyy') = '2020'
    ), 0) + nvl((
        SELECT
            SUM(clduration) * 42.85
        FROM
            uni.schedclass sc1
        WHERE
            sc1.staffid = sc.staffid
            AND upper(cltype) = 'T'
            AND ofsemester = 1
            AND to_char(ofyear, 'yyyy') = '2020'
    ), 0), '$990.99'), 20, ' ') AS total_weekly_payment
FROM
    uni.schedclass   sc
    JOIN uni.staff        s ON sc.staffid = s.staffid
ORDER BY
    staffid;


/* 10. Assume that all units are worth 6 credit points each, calculate each student’s Weighted Average Mark (WAM) and GPA. 
Please refer to these Monash websites: https://www.monash.edu/exams/results/wam and https://www.monash.edu/exams/results/gpa 
for more information about WAM and GPA respectively. Do not include NULL, WH or DEF grade in the calculation.

Calculation example for student 14374036 (Claudette Serman):
WAM = (56x6 + 16x6 + 81x6 + 77x6 + 64x6)/(6+6+6+6+6) = 58.80
GPA = (1x6+ 0.3x6 + 4x6 + 3x6 + 2x6)/(6+6+6+6+6) = 2.06

Calculation example for student 23545528 (Benny Plunket):
WAM = (53x3 + 97x3 + 78x6 + 94x6 + 85 x 6)/(3+3+6+6+6) = 83.00
GPA = (1x6 + 4x6+ 3x6 + 4x6 + 4x6)/(6+6+6+6+6) = 3.20

Include student id, student full name (in a 40 characters wide column headed student_fullname),
WAM and GPA in the display. Order the list by descending order of WAM then descending order of GPA. 
If two students have the same WAM and GPA, order them by their respective id.
*/

SELECT
    stuid,
    rpad(stufname
         || ' '
         || stulname, 40, ' ') AS student_fullname,
    to_char(SUM(
        CASE
            WHEN enrolmark IS NOT NULL
                 AND substr(unitcode, 4, 1) = '1' THEN
                enrolmark * 3
            WHEN enrolmark IS NOT NULL
                 AND substr(unitcode, 4, 1) <> '1' THEN
                enrolmark * 6
        END
    ) / SUM(
        CASE
            WHEN enrolmark IS NOT NULL
                 AND substr(unitcode, 4, 1) = '1' THEN
                3
            WHEN enrolmark IS NOT NULL
                 AND substr(unitcode, 4, 1) <> '1' THEN
                6
        END
    ), '990.99') AS wam,
    to_char(SUM(
        CASE
            WHEN enrolmark IS NOT NULL
                 AND upper(enrolgrade) = 'N' THEN
                0.3
            WHEN enrolmark IS NOT NULL
                 AND upper(enrolgrade) = 'P' THEN
                1
            WHEN enrolmark IS NOT NULL
                 AND upper(enrolgrade) = 'C' THEN
                2
            WHEN enrolmark IS NOT NULL
                 AND upper(enrolgrade) = 'D' THEN
                3
            WHEN enrolmark IS NOT NULL
                 AND upper(enrolgrade) = 'HD' THEN
                4
        END
        * 6) /(COUNT(enrolmark) * 6), '990.99') AS gpa
FROM
    uni.enrolment
    NATURAL JOIN uni.student
GROUP BY
    stuid,
    rpad(stufname
         || ' '
         || stulname, 40, ' ')
ORDER BY
    wam DESC,
    gpa DESC,
    stuid;