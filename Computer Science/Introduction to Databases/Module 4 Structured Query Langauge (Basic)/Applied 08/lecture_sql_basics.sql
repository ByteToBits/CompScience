
SELECT drone_id, drone_cost_hr/60 AS costpermin from drone.drone; 

-- Will Return the Student Information (Displays Null)
SELECT stuid, enrolmark, enrolgrade FROM uni.enrolment; 

-- Will Return the Student Information (Uses Oracle NVL Functon to replace Null Values)
SELECT stuid,
   NVL(enrolmark, 0),
   NVL(enrolgrade, 'WH')
FROM uni.enrolment; 

-- Sorting Query Result (ORDER BY - Drone Fligt Time in Descending Order)
SELECT drone_id, drone_flight_time FROM drone.drone ORDER BY drone_flight_time DESC, drone_id; 

-- Obtain tthe ids of those Drones which have been Rented
-- DISTINCT: USE WITH CARE, Removes Duplicate Rows
Select DISTINCT drone_id from drone.rental ORDER BY drone_id ASC; 

-- SQL EQUI JOIN (Join Columns are Shown Twice)
SELECT * FROM
   drone.manufacturer
   JOIN drone.DRONE_TYPE
   ON manufacturer.manuf_id = drone_type.manuf_id
ORDER BY dt_code;

-- SQL NATURAL JOIN (Speical EQUI)
SELECT * FROM 
   drone.manufacturer
   NATURAL JOIN drone.drone_type
ORDER BY
   dt_code; 

-- Oracle NVL Function Further Example
-- SELECT rent_no, drone_id, rent_out_dt, nvl(rent_in_dt,'Still Out') from drone.rental; This will Output an Error
-- Because we are comparing a String to a Date Data Type; Convert Date to Characters
SELECT rent_no, drone_id, to_char(rent_out_dt, 'dd-Mon-yyyy') as dateout, nvl((TO_CHAR(rent_in_dt, 'dd-mon-yyy')),'Still Out') from drone.rental; 

-- Get Current Date, Sysdate, current_date, systimestamp
SELECT TO_CHAR(sysdate, 'dd-Mon-YYYY hh:mi:ss AM') AS Curent_datetime FROM dual; 

-- Example of Data/Number Output Formats and Comparisons
SELECT 
   drone_id, 
   TO_CHAR(drone_pur_date, 'dd-Mon-yyyy') AS purchase_date,
   TO_CHAR(drone_pur_price, '$9990.99') AS purchase_price,
   TO_CHAR(drone_flight_time, '9990.99') AS drone_flight_time
FROM 
   drone.drone
WHERE
  drone_pur_date > TO_DATE('01-Mar-2021','dd-Mon-yyyy')
ORDER BY
  drone_id; 