

/*
Script: SQL Kill all Active Users and Locking Sessions
Institution: Monash University Australia
Subject: ITO 4132 Introduction to Databases
Database Type:: Oracle SQL Database

Student Name: Tristan Sim 
Last Modified Date: 2nd April 2025

Remarks: This Script was provided by the Courtesy of the Lecturers of Monash University Australia
*/

-- Connects to Database and see How many Users are in an Active Session
SELECT
    username,
    sid,
    serial#,
    status,
    state,
    to_char(logon_time, 'MONdd hh24:mi') AS "Logon Time"
FROM
    v$session
WHERE
        type = 'USER'
    AND username = user
    AND upper(osuser)!= 'ORACLE'
ORDER BY
    "Logon Time";

    
-- Kills a Specific User of a Locking a Session

--  SID    SERIAL# STATUS   STATE               Logon Time          
------------ ---------- -------- ------------------- --------------------
--   395      17228 INACTIVE WAITING             MAR27 00:34         
--   587      28617 ACTIVE   WAITED SHORT TIME   MAR27 00:35 

-- begin
--    kill_own_session(1526,52369);
-- end;

DECLARE
    sid     NUMBER;
    serial# NUMBER;
    CURSOR inactive_sessions IS
    SELECT
        sid,
        serial#
    FROM
        v$session
    WHERE
        type = 'USER'
        AND username = user
        AND upper(osuser) != 'ORACLE'
        AND status = 'INACTIVE';
BEGIN
    OPEN inactive_sessions;
    LOOP
        FETCH inactive_sessions INTO sid, serial#;
        EXIT WHEN inactive_sessions%notfound;
        kill_own_session(sid, serial#);
    END LOOP;

    CLOSE inactive_sessions;
END;
/