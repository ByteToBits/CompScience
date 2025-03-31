
SELECT drone_id, drone_cost_hr/60 AS costpermin from drone.drone; 

-- Will Return the Student Information (Displays Null)
SELECT stuid, enrolmark, enrolgrade FROM uni.enrolment; 

-- Will Return the Student Information (Uses Oracle NVL Functon to replace Null Values)
SELECT stuid,
   NVL(enrolmark, 0),
   NVL(enrolgrade, 'WH')
FROM uni.enrolment; 

-- Oracle NVL Function Further Example
SELECT rent_no, drone_id, rent_out_dt, nvl(rent_in_dt,'Still Out') from drone.rental; 

-- Sorting Query Result (ORDER BY - Drone Fligt Time in Descending Order)
SELECT drone_id, drone_flight_time FROM drone.drone ORDER BY drone_flight_time DESC, drone_id; 

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